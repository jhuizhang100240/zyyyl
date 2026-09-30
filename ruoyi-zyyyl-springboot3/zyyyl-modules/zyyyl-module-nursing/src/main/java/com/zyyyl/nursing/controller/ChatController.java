package com.zyyyl.nursing.controller;

import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.JsonNode;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.utils.SecurityUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.ai.DifyClient;

import lombok.extern.slf4j.Slf4j;

/**
 * AI 对话控制器。使用 SseEmitter 转发 Dify 流式响应，客户端断开即中断上游读取。
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class ChatController {

    /** SSE 超时时间，默认 3 分钟 */
    private static final long SSE_TIMEOUT_MILLIS = 180_000L;

    private final DifyClient difyClient;
    private final ThreadPoolTaskExecutor taskExecutor;

    public ChatController(DifyClient difyClient, ThreadPoolTaskExecutor taskExecutor) {
        this.difyClient = difyClient;
        this.taskExecutor = taskExecutor;
    }

    /**
     * AI 流式对话
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestParam String prompt,
            @RequestParam(required = false) String chatId) {
        if (StringUtils.isEmpty(prompt)) {
            throw new com.zyyyl.common.exception.ServiceException("提问内容不能为空");
        }
        Long userId = SecurityUtils.getUserId();
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT_MILLIS);
        Thread worker = new Thread(() -> {
            try {
                difyClient.streamChat(prompt, chatId, String.valueOf(userId), content -> {
                    try {
                        emitter.send(SseEmitter.event().data(content));
                    } catch (Exception e) {
                        throw new com.zyyyl.nursing.ai.DifySseException("客户端已断开", e);
                    }
                });
                emitter.complete();
            } catch (Exception e) {
                log.error("AI 流式对话失败 userId={}", userId, e);
                try {
                    emitter.send(SseEmitter.event().name("error")
                            .data("抱歉，服务暂时不可用，请稍后重试。"));
                } catch (Exception ignored) {
                    log.debug("客户端已断开，无法发送错误提示");
                }
                emitter.complete();
            }
        }, "dify-chat-" + userId);
        worker.setDaemon(true);
        emitter.onCompletion(worker::interrupt);
        emitter.onTimeout(worker::interrupt);
        taskExecutor.execute(worker);
        return emitter;
    }

    /**
     * 获取当前用户对话列表
     */
    @GetMapping("/history")
    public AjaxResult getConversations() {
        JsonNode node = difyClient.listConversations(String.valueOf(SecurityUtils.getUserId()));
        return AjaxResult.success(node.get("data"));
    }

    /**
     * 获取指定会话历史消息
     */
    @GetMapping("/history/{chatId}")
    public AjaxResult getHistoryMessages(@PathVariable String chatId) {
        JsonNode node = difyClient.listMessages(String.valueOf(SecurityUtils.getUserId()), chatId);
        return AjaxResult.success(node.get("data"));
    }

    /**
     * 删除指定会话
     */
    @DeleteMapping("/history/{chatId}")
    public AjaxResult deleteHistoryByChatId(@PathVariable String chatId) {
        difyClient.deleteConversation(String.valueOf(SecurityUtils.getUserId()), chatId);
        return AjaxResult.success();
    }
}
