package com.zyyyl.nursing.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.annotation.Anonymous;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.common.utils.ip.IpUtils;
import com.zyyyl.nursing.config.DifyProperties;
import com.zyyyl.nursing.service.DifyServeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Dify 工具调用入口。只允许配置的来源IP调用，并可附加服务 Token 二次校验。
 */
@Slf4j
@Anonymous
@RestController
@RequestMapping("/dify/serve")
public class DifyServeController {

    public static final String SERVE_TOKEN_HEADER = "X-Dify-Serve-Token";

    private final DifyServeService difyServeService;
    private final DifyProperties difyProperties;

    public DifyServeController(DifyServeService difyServeService, DifyProperties difyProperties) {
        this.difyServeService = difyServeService;
        this.difyProperties = difyProperties;
    }

    @GetMapping("/getElderBasicInfo")
    public Map<String, Object> getElderBasicInfo(@RequestParam String nameOrId,
            @RequestHeader(value = SERVE_TOKEN_HEADER, required = false) String serveToken,
            HttpServletRequest request) {
        verifyCaller(serveToken, request);
        return difyServeService.getElderBasicInfo(nameOrId);
    }

    @GetMapping("/getElderHealthInfo")
    public Map<String, Object> getElderHealthInfo(@RequestParam String nameOrId,
            @RequestHeader(value = SERVE_TOKEN_HEADER, required = false) String serveToken,
            HttpServletRequest request) {
        verifyCaller(serveToken, request);
        return difyServeService.getElderHealthInfo(nameOrId);
    }

    @GetMapping("/getReservationByToday")
    public Map<String, Object> getReservationByToday(@RequestParam(required = false) String datetime,
            @RequestHeader(value = SERVE_TOKEN_HEADER, required = false) String serveToken,
            HttpServletRequest request) {
        verifyCaller(serveToken, request);
        return difyServeService.getReservationsByDate(datetime);
    }

    /**
     * 校验来源IP白名单；配置了 serveToken 时必须携带匹配的服务 Token。
     */
    private void verifyCaller(String serveToken, HttpServletRequest request) {
        String clientIp = IpUtils.getIpAddr(request);
        if (StringUtils.isNotEmpty(difyProperties.getServeToken())
                && !difyProperties.getServeToken().equals(serveToken)) {
            log.warn("Dify 工具调用被拒绝：服务 Token 不匹配，来源IP={}", clientIp);
            throw new ServiceException("调用身份校验失败");
        }
        if (difyProperties.getServeAllowedIps() != null
                && !difyProperties.getServeAllowedIps().isEmpty()
                && !difyProperties.getServeAllowedIps().contains(clientIp)) {
            log.warn("Dify 工具调用被拒绝：来源IP不在白名单，来源IP={}", clientIp);
            throw new ServiceException("调用来源不在白名单");
        }
    }
}
