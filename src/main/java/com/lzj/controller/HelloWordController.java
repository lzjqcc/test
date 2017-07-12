package com.lzj.controller;

import com.lzj.dao.UserDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.omg.IOP.ServiceContextHolder;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * Created by win7 on 2017-07-10.
 */
@Controller
public class HelloWordController {
    @Resource
    private UserDao userDao;
   private AbstractAuthenticationProcessingFilter a;
   //通过登录用户的权限信息、资源、获取资源所需的权限来根据不同的授权策略来判断用户是否有权限访问资源
    private  AccessDecisionManager manager;
    private  FilterInvocationSecurityMetadataSource  securityMetadataSource;
    private  FilterSecurityInterceptor filterSecurityInterceptor;
    //AbstractSecurityInterceptor
    @RequestMapping("/loginx")
    public String index(){
        //AbstractUserDetailsAuthenticationProvider
        System.out.print("springmvc");
        return "login";
    }
    @RequestMapping("/delete")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    public String delete(Model model){
        model.addAttribute("message",userDao.delete());
        return "welcome";
    }
    @RequestMapping("/insert")
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public String insert(Model model){
        model.addAttribute("message",userDao.insert());
        return "welcome";
    }
    @RequestMapping("/find")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public String find(Model model){
        model.addAttribute("message",userDao.find());
        return "welcome";
    }
    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("username",getPrincipal());
        return "welcome";
    }
    @RequestMapping("/user")
    public String user(Model model){
        model.addAttribute("username",getPrincipal());
        return "user/index";
    }
    private String getPrincipal(){
        String userName=null;
        Object object=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof UserDetails){
            userName=((UserDetails)object).getUsername();
        }else {
            userName=object.toString();
        }
        return userName;
    }
}


