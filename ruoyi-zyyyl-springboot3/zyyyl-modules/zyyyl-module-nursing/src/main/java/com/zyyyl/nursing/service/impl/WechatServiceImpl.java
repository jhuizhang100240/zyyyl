package com.zyyyl.nursing.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.config.WechatProperties;
import com.zyyyl.nursing.service.IWechatService;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序服务实现。AppId / AppSecret 缺失时直接给出业务错误，不回退到 Mock，
 * 避免在生产环境把授权失败伪装成成功。
 */
@Slf4j
@Service
public class WechatServiceImpl implements IWechatService {

    /** access_token 提前 5 分钟过期，避免边界失效 */
    private static final long TOKEN_SAFE_WINDOW_MILLIS = 5 * 60 * 1000L;

    private final WechatProperties properties;
    private final RestClient restClient;

    private volatile String accessToken;
    private volatile long accessTokenExpireAt;

    public WechatServiceImpl(WechatProperties properties) {
        this.properties = properties;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(properties.getConnectTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());
        this.restClient = RestClient.builder().requestFactory(requestFactory).build();
    }

    @Override
    public String getOpenid(String code) {
        requireConfigured();
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("微信登录凭证不能为空");
        }
        Map<String, Object> response;
        try {
            String raw = restClient.get()
                    .uri(properties.getSessionUrl()
                            + "?appid={appid}&secret={secret}&js_code={jsCode}&grant_type=authorization_code",
                            properties.getAppId(), properties.getAppSecret(), code)
                    .retrieve()
                    .body(String.class);
            response = parseJsonObject(raw);
        } catch (Exception e) {
            log.error("微信 jscode2session 调用失败，code长度={}", code.length(), e);
            throw new ServiceException("微信登录服务暂时不可用");
        }
        Integer errcode = intValue(response == null ? null : response.get("errcode"));
        String openid = response == null ? null : stringValue(response.get("openid"));
        if (errcode != null && errcode != 0) {
            String errmsg = response == null ? null : stringValue(response.get("errmsg"));
            log.warn("微信 jscode2session 返回错误 errcode={} errmsg={}", errcode, errmsg);
            throw new ServiceException("微信登录凭证无效或已过期");
        }
        if (StringUtils.isEmpty(openid)) {
            throw new ServiceException("微信登录未返回 openid");
        }
        return openid;
    }

    @Override
    public String getPhone(String phoneCode) {
        requireConfigured();
        if (StringUtils.isEmpty(phoneCode)) {
            throw new ServiceException("微信手机号授权凭证不能为空");
        }
        String token = getAccessToken();
        Map<String, Object> body = new HashMap<>();
        body.put("code", phoneCode);
        Map<String, Object> response;
        try {
            String raw = restClient.post()
                    .uri(properties.getPhoneUrl() + "?access_token={token}", token)
                    .body(body)
                    .retrieve()
                    .body(String.class);
            response = parseJsonObject(raw);
        } catch (Exception e) {
            log.error("微信 getuserphonenumber 调用失败", e);
            throw new ServiceException("微信手机号服务暂时不可用");
        }
        Integer errcode = intValue(response == null ? null : response.get("errcode"));
        if (errcode != null && errcode != 0) {
            String errmsg = response == null ? null : stringValue(response.get("errmsg"));
            log.warn("微信 getuserphonenumber 返回错误 errcode={} errmsg={}", errcode, errmsg);
            throw new ServiceException("微信手机号授权凭证无效或已过期");
        }
        Object phoneInfo = response == null ? null : response.get("phone_info");
        if (!(phoneInfo instanceof Map<?, ?> info)) {
            throw new ServiceException("微信手机号服务未返回手机号");
        }
        String phone = stringValue(info.get("phoneNumber"));
        if (StringUtils.isEmpty(phone)) {
            phone = stringValue(info.get("purePhoneNumber"));
        }
        if (StringUtils.isEmpty(phone)) {
            throw new ServiceException("微信手机号服务未返回手机号");
        }
        return phone;
    }

    private String getAccessToken() {
        long now = System.currentTimeMillis();
        String cached = accessToken;
        if (StringUtils.isNotEmpty(cached) && now < accessTokenExpireAt) {
            return cached;
        }
        synchronized (this) {
            now = System.currentTimeMillis();
            if (StringUtils.isNotEmpty(accessToken) && now < accessTokenExpireAt) {
                return accessToken;
            }
            Map<String, Object> response;
            try {
                String raw = restClient.get()
                        .uri(properties.getTokenUrl()
                                + "?grant_type=client_credential&appid={appid}&secret={secret}",
                                properties.getAppId(), properties.getAppSecret())
                        .retrieve()
                        .body(String.class);
                response = parseJsonObject(raw);
            } catch (Exception e) {
                log.error("微信 access_token 获取失败", e);
                throw new ServiceException("微信服务暂时不可用");
            }
            Integer errcode = intValue(response == null ? null : response.get("errcode"));
            String token = response == null ? null : stringValue(response.get("access_token"));
            if ((errcode != null && errcode != 0) || StringUtils.isEmpty(token)) {
                String errmsg = response == null ? null : stringValue(response.get("errmsg"));
                log.warn("微信 access_token 返回错误 errcode={} errmsg={}", errcode, errmsg);
                throw new ServiceException("微信服务暂时不可用");
            }
            Integer expiresIn = intValue(response.get("expires_in"));
            long ttl = expiresIn == null || expiresIn <= 0 ? 3600 : expiresIn;
            accessToken = token;
            accessTokenExpireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(ttl) - TOKEN_SAFE_WINDOW_MILLIS;
            return token;
        }
    }

    private void requireConfigured() {
        if (StringUtils.isEmpty(properties.getAppId()) || StringUtils.isEmpty(properties.getAppSecret())) {
            throw new ServiceException("微信小程序 AppId 或 AppSecret 未配置");
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJsonObject(String raw) throws Exception {
        if (StringUtils.isEmpty(raw)) {
            return new HashMap<>();
        }
        return com.zyyyl.common.utils.JSON.getObjectMapper().readValue(raw, Map.class);
    }

    private String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private Integer intValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.valueOf(String.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
