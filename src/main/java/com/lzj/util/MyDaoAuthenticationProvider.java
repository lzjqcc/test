package com.lzj.util;

import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by win7 on 2017-07-12.
 */
//登录认证首先被AbstractAuthenticationProcessingFilter 拦截 然通过AuthenticationManager  子类ProviderManager在方法authenticate中 委托AuthenticationProvider去认证
    //
public class MyDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    //这个是比较用户登录与存储的数据
    //错误抛出AuthenticationException
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)  {
        UsernamePasswordEmialAuthenticationToken myToken= (UsernamePasswordEmialAuthenticationToken) authentication;
        MyUserDetails myUserDetails= (MyUserDetails) userDetails;
        if (!(userDetails.getUsername().equals(myToken.getPrincipal()) && userDetails.getPassword().equals(myToken.getCredentials()) && myUserDetails.getEmail().equals(myToken.getEmail()))){
            throw new AccountExpiredException("用户名或密码或邮箱错误");
        }
    }
    //这个authentication是在MyAuthenticationFilter中放入的
    @Override
    public Authentication authenticate(Authentication authentication) {
        //这个是从userdetailsService中获取
        MyUserDetails userDetails= (MyUserDetails) retrieveUser(authentication.getName(), (UsernamePasswordEmialAuthenticationToken) authentication);
        try{
            additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) authentication);
        }catch (AuthenticationException e){


            throw e;

        }
        return createSuccessAuthentication(authentication.getPrincipal(),authentication,userDetails);
    }
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        MyUserDetails userDetails= (MyUserDetails) user;
        UsernamePasswordEmialAuthenticationToken token=new UsernamePasswordEmialAuthenticationToken(principal,authentication.getCredentials(),userDetails.getEmail(),userDetails.getAuthorities());

        token.setDetails(authentication.getDetails());
        return token;
    }
    //从UserDetailService中获取UserDetails
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = getUserDetailsService().loadUserByUsername(username);
        }
        catch (UsernameNotFoundException notFound) {
            throw notFound;
        }
        catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
