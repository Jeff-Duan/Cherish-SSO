package com.cherish.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class SSOFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SSOFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        //获取请求和响应
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取全局会话
        HttpSession session = httpServletRequest.getSession();
        //获取Http请求参数中的SSO-Client回调地址
        Optional<String> callBackParameterOptional = Optional.ofNullable(httpServletRequest.getParameter("callBackUrl"));
        //保存CallBackURL
        if (callBackParameterOptional.isPresent()) {
            session.setAttribute("CallBackUrl", callBackParameterOptional.get());
        }
        //获取Session中的SSO-Client回调地址
        Optional<String> callBackOptional = Optional.ofNullable((String) session.getAttribute("CallBackUrl"));
        //获取User
        Optional<String> userOptional = Optional.ofNullable((String) session.getAttribute("User"));
        //获取Http请求的URL
        String requestURI = httpServletRequest.getRequestURI();
        //SSO-Client回调地址不存在[非法]
        if (!callBackOptional.isPresent()) {
            httpServletResponse.sendError(500, "请勿越过SSO-Client直接登陆SSO,这将会导致无法获取SSO-Client的URL.");
            return;
        }
        //提交登陆[无条件]
        if ("/sso/save".equals(requestURI)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        //注销[未登录-非法]
        if (StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/logout".equals(requestURI)) {
            httpServletResponse.sendError(500, "请勿在未登陆状态访问注销,属于非法访问!!!!!!!!!!!!!!!!!!!!!!!!!!!!!.");
            return;
        }
        //注销[已登录-放行]
        if (!StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/logout".equals(requestURI)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        //回调SSO-Client[未登陆-非法]
        if (StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/callBack".equals(requestURI)) {
            httpServletResponse.sendError(500, "请勿越过SSO直接回调SSO-Client,这将会导致无法获取SSO-Client的URL.");
            return;
        }
        //回调SSO-Client[登陆-放行]
        if (!StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/callBack".equals(requestURI)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        logger.info("SSO-Client正在尝试登陆SSO.");
        chain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }

}
