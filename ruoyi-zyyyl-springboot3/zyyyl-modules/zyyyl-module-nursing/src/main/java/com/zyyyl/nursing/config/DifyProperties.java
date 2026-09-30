package com.zyyyl.nursing.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Dify 配置。Token 只从环境变量注入，禁止写入仓库。
 */
@Data
@Component
@ConfigurationProperties(prefix = "dify")
public class DifyProperties {

    /** Dify API 基础地址，例如 http://192.168.100.128/v1 */
    private String baseUrl;

    /** Dify 应用 API Key */
    private String token;

    /** 单次对话超时（秒） */
    private int timeoutSeconds = 120;

    /** 对话历史条数上限 */
    private int conversationLimit = 20;

    /** Dify 工具调用本应用时使用的服务 Token */
    private String serveToken;

    /** 允许调用 /dify/serve/* 的来源IP白名单 */
    private List<String> serveAllowedIps = new ArrayList<>(List.of("127.0.0.1", "::1", "192.168.100.128"));

}
