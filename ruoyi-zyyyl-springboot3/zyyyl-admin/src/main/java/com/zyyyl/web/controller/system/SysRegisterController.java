package com.zyyyl.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.domain.model.RegisterBody;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.framework.web.service.SysRegisterService;
import com.zyyyl.system.service.ISysConfigService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 注册验证
 * 
 * @author geek
 */
@Tag(name = "注册验证")
@RestController
public class SysRegisterController extends BaseController {

    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Operation(summary = "注册方法")
    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
