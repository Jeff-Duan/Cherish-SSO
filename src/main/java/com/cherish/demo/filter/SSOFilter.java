package com.cherish.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class SSOFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SSOFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

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

        //放行登陆
        if ("/sso/save".equals(requestURI)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        //未登陆回调SSO-Client(500)
        if (StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/logout".equals(requestURI)) {
            httpServletResponse.sendError(500, "请勿在未登陆状态访问注销,属于非法访问!!!!!!!!!!!!!!!!!!!!!!!!!!!!!.");
            return;
        }
        //未登陆回调SSO-Client(500)
        if (StringUtils.isEmpty(userOptional.orElse(null)) && "/sso/callBack".equals(requestURI)) {
            httpServletResponse.sendError(500, "请勿越过SSO直接回调SSO-Client,这将会导致无法获取SSO-Client的URL.");
            return;
        }
        //不存在SSO-Client回调地址(500)
        if (!callBackOptional.isPresent()) {
            httpServletResponse.sendError(500, "请勿越过SSO-Client直接登陆SSO,这将会导致无法获取SSO-Client的URL.");
            return;
        }
        //若已登录,回调SSO-Client地址
        if (!StringUtils.isEmpty(userOptional.orElse(null))) {

            logger.info("Jsession:"+httpServletRequest.getRequestedSessionId());

            httpServletResponse.sendRedirect((String) session.getAttribute("CallBackUrl"));
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
