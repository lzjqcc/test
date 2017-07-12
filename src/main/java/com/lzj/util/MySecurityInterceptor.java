package com.lzj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by win7 on 2017-07-12.
 */
//资源拦截

public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    //用于封装
    private  FilterInvocationSecurityMetadataSource securityMetadataSource;


    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource){
        this.securityMetadataSource=securityMetadataSource;
    }
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest  , servletResponse, filterChain);
        invoke(fi);

    }
    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        //校验访问url 的权限
        //这个是委托给AccessDecisionManager进行权限校验
        InterceptorStatusToken token= super.beforeInvocation(fi);
        try{
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token,null);

    }
    public void destroy() {

    }

    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }
}
