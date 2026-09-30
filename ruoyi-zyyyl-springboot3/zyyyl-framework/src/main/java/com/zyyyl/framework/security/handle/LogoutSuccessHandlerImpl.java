package com.zyyyl.framework.security.handle;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.zyyyl.common.constant.Constants;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.domain.model.LoginUser;
import com.zyyyl.common.utils.JSON;
import com.zyyyl.common.utils.MessageUtils;
import com.zyyyl.common.utils.ServletUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.framework.manager.AsyncManager;
import com.zyyyl.framework.manager.factory.AsyncFactory;
import com.zyyyl.framework.web.service.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author geek
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT,
                    MessageUtils.message("user.logout.success")));
        }
        ServletUtils.renderString(response,
                JSON.toJSONString(AjaxResult.success(MessageUtils.message("user.logout.success"))));
    }
}
