package com.zyyyl.nursing.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.JSON;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.config.DifyProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Dify API 客户端。负责流式对话与对话历史查询，Token 从环境变量注入。
 */
@Slf4j
@Component
public class DifyClient {

    private static final String SSE_DATA_PREFIX = "data: ";
    private static final String SSE_DONE = "[DONE]";

    private final DifyProperties properties;
    private final CloseableHttpClient httpClient;

    public DifyClient(DifyProperties properties) {
        this.properties = properties;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(5))
                .setResponseTimeout(Timeout.ofSeconds(properties.getTimeoutSeconds()))
                .build();
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * 流式对话。每收到一段回答就回调 consumer。
     *
     * @param prompt   用户提问
     * @param chatId   会话ID，可空
     * @param userId   业务用户标识
     * @param consumer 增量内容回调
     */
    public void streamChat(String prompt, String chatId, String userId, Consumer<String> consumer) {
        requireToken();
        Map<String, Object> body = new HashMap<>();
        body.put("query", prompt);
        body.put("inputs", new HashMap<>());
        body.put("response_mode", "streaming");
        body.put("user", userId);
        if (StringUtils.isNotEmpty(chatId) && !"null".equals(chatId)) {
            body.put("conversation_id", chatId);
        }
        HttpPost request = new HttpPost(buildUrl("/chat-messages"));
        applyJson(request);
        request.setEntity(new StringEntity(JSON.toJSONString(body), ContentType.APPLICATION_JSON));
        try {
            httpClient.execute(request, response -> {
                int status = response.getCode();
                if (status < 200 || status >= 300) {
                    String message = EntityUtils.toString(response.getEntity());
                    log.error("Dify 对话请求失败 status={}", status);
                    throw new DifySseException("Dify 服务返回错误：" + status + " " + message);
                }
                try (var reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(response.getEntity().getContent(),
                                java.nio.charset.StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (Thread.currentThread().isInterrupted()) {
                            log.warn("Dify 流式响应被客户端取消");
                            break;
                        }
                        String content = extractContent(line);
                        if (StringUtils.isNotEmpty(content)) {
                            consumer.accept(content);
                        }
                    }
                }
                return null;
            });
        } catch (DifySseException e) {
            throw e;
        } catch (Exception e) {
            throw new DifySseException("Dify 服务调用失败", e);
        }
    }

    /**
     * 查询用户对话列表
     */
    public JsonNode listConversations(String userId) {
        return getJson("/conversations?user=" + urlEncode(userId)
                + "&limit=" + properties.getConversationLimit());
    }

    /**
     * 查询指定会话的历史消息
     */
    public JsonNode listMessages(String userId, String chatId) {
        return getJson("/messages?user=" + urlEncode(userId) + "&conversation_id=" + urlEncode(chatId));
    }

    /**
     * 删除指定会话
     */
    public void deleteConversation(String userId, String chatId) {
        requireToken();
        HttpDelete request = new HttpDelete(buildUrl("/conversations/" + urlEncode(chatId)));
        request.addHeader("Authorization", "Bearer " + properties.getToken());
        try {
            httpClient.execute(request, response -> {
                if (response.getCode() < 200 || response.getCode() >= 300) {
                    throw new ServiceException("删除 Dify 会话失败：" + response.getCode());
                }
                return null;
            });
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("删除 Dify 会话失败：" + e.getMessage());
        }
    }

    private JsonNode getJson(String path) {
        requireToken();
        HttpGet request = new HttpGet(buildUrl(path));
        request.addHeader("Authorization", "Bearer " + properties.getToken());
        try {
            String body = httpClient.execute(request, response -> {
                if (response.getCode() < 200 || response.getCode() >= 300) {
                    throw new ServiceException("Dify 查询失败：" + response.getCode());
                }
                return EntityUtils.toString(response.getEntity());
            });
            return JSON.getObjectMapper().readTree(body);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Dify 查询失败：" + e.getMessage());
        }
    }

    private void requireToken() {
        if (StringUtils.isEmpty(properties.getToken())) {
            throw new ServiceException("Dify API Key 未配置");
        }
        if (StringUtils.isEmpty(properties.getBaseUrl())) {
            throw new ServiceException("Dify 服务地址未配置");
        }
    }

    private String buildUrl(String path) {
        String base = properties.getBaseUrl();
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        if (!base.endsWith("/v1") && !base.contains("/v1/")) {
            base = base + "/v1";
        }
        return base + path;
    }

    private void applyJson(HttpPost request) {
        request.addHeader("Authorization", "Bearer " + properties.getToken());
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Accept", "text/event-stream");
    }

    /**
     * 从 SSE 行里提取 answer / text 字段。
     */
    private String extractContent(String line) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        String json = line.startsWith(SSE_DATA_PREFIX) ? line.substring(SSE_DATA_PREFIX.length()).trim() : line;
        if (SSE_DONE.equals(json) || json.startsWith("event:")) {
            return null;
        }
        try {
            ObjectMapper mapper = JSON.getObjectMapper();
            JsonNode node = mapper.readTree(json);
            for (String field : new String[] { "answer", "text" }) {
                JsonNode value = node.get(field);
                if (value != null && !value.isNull() && StringUtils.isNotEmpty(value.asText())) {
                    return value.asText();
                }
            }
        } catch (Exception e) {
            log.trace("忽略无法解析的 SSE 片段：{}", line);
        }
        return null;
    }

    private String urlEncode(String value) {
        return value == null ? "" : java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8);
    }

}
