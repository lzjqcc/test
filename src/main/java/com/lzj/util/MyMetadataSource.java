package com.lzj.util;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by win7 on 2017-07-12.
 */
public class MyMetadataSource implements FilterInvocationSecurityMetadataSource {
    //初始化页面对应的权限
    @PostConstruct
    public void init(){
        //访问admin文件夹下页面所需权限
        List<ConfigAttribute> admin=new ArrayList<ConfigAttribute>();
        RequestMatcher adminMatcher=new AntPathRequestMatcher("/admin/*");
        ConfigAttribute c=new SecurityConfig("ROLE_ADMIN");
        admin.add(c);
        //访问user文件夹下页面所需要的权限
        List<ConfigAttribute> user=new ArrayList<ConfigAttribute>();
        RequestMatcher userMatcher=new AntPathRequestMatcher("/user/*");
        ConfigAttribute userC=new SecurityConfig("ROLE_USER");
        user.add(userC);

        requestMap.put(adminMatcher,admin);
        requestMap.put(userMatcher,user);
    }
    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap=new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
    //根据url来
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

       final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }
    //返回所有的Configattribute  Configattribute封装ROLE_ADMIN（权限）
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
