package com.lzj.util;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by win7 on 2017-07-12.
 */
//登录使用的类

/**
 * MyAuthenticationFilter首先拦截登录
 * //登录认证首先被AbstractAuthenticationProcessingFilter 拦截， 然通过AuthenticationManager子类ProviderManager在方法authenticate中 委托AuthenticationProvider去认证
 * 这里的Provider就是MyDaoAuthenticationProvider这里包含了登录认证。
 *
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private String email;

    public String  obtainemail(HttpServletRequest request){
        return request.getParameter("email");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (true && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String email=obtainemail(request);
        //将请求参数封装成toke 令牌
        UsernamePasswordEmialAuthenticationToken token=new UsernamePasswordEmialAuthenticationToken(obtainUsername(request),obtainPassword(request),email);

        this.setDetails(request,token);
        Authentication authentication=null;
        try {
            //登录失败捕获异常
            authentication = this.getAuthenticationManager().authenticate(token);
        }catch (AccountExpiredException e){
            //登录失败后重定向的页面。
            SimpleUrlAuthenticationFailureHandler failureHandler=new SimpleUrlAuthenticationFailureHandler("/login.jsp");
            try {
                failureHandler.onAuthenticationFailure(request,response,e);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ServletException e1) {
                e1.printStackTrace();
            }
        }
        return authentication;
    }
}
