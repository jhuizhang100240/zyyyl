package com.zyyyl.web.controller.monitor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.framework.web.domain.Server;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 服务器监控
 * 
 * @author geek
 */
@Tag(name = "服务器监控")
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @Operation(summary = "获取服务器监控信息")
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
