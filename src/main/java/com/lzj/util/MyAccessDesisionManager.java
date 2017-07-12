package com.lzj.util;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by win7 on 2017-07-12.
 */
//用户权限与访问url需要的权限进行对比

public class MyAccessDesisionManager implements AccessDecisionManager {
    /**
     *
     * @param authentication 用户权限
     * @param object 访问的url
     * @param configAttributes 访问该url应拥有的权限
     * @throws AccessDeniedException
     */
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {

            if (object==null){
                return;
            }
            Iterator<ConfigAttribute> iterator=configAttributes.iterator();
            boolean flag=false;
            while (iterator.hasNext()){
                ConfigAttribute configAttribute=iterator.next();
                if (configAttribute.getAttribute().equals(authentication)){
                    flag=true;
                }

            }
            if (!flag){
                throw new AccessDeniedException("no right");
            }
            /*throw new AccessDeniedException(messages.getMessage(
                    "AbstractAccessDecisionManager.accessDenied", "Access is denied"));*/


        // To get this far, every AccessDecisionVoter abstained
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
