package com.zyyyl.framework.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zyyyl.common.constant.CacheConstants;
import com.zyyyl.common.core.domain.model.LoginUser;
import com.zyyyl.common.utils.SecurityUtils;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.framework.web.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 家属端 token 过滤器。家属端登录态使用独立缓存命名空间，与后台登录态隔离。
 * 该过滤器只负责恢复家属端登录态；后台 token 仍由 JwtAuthenticationTokenFilter 处理。
 */
@Component
public class MemberTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${token.header}")
    private String header;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader(header);
        LoginUser memberUser = tokenService.getLoginUserFromNamespace(token, CacheConstants.MEMBER_LOGIN_TOKEN_KEY);
        if (StringUtils.isNotNull(memberUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(memberUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    memberUser, null, memberUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
