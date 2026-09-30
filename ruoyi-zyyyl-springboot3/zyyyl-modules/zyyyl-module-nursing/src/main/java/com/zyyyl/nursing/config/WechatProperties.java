package com.zyyyl.nursing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信小程序配置。AppSecret 只从环境变量注入，禁止写入仓库。
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatProperties {

    /** 小程序 AppId */
    private String appId;

    /** 小程序 AppSecret */
    private String appSecret;

    /** jscode2session 接口地址 */
    private String sessionUrl = "https://api.weixin.qq.com/sns/jscode2session";

    /** client_credential 获取 access_token 接口地址 */
    private String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

    /** 获取手机号接口地址 */
    private String phoneUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";

    /** 连接超时毫秒 */
    private int connectTimeout = 3000;

    /** 读取超时毫秒 */
    private int readTimeout = 5000;
}
