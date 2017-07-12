package com.lzj.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


import java.util.Collection;
import java.util.Set;

/**
 * Created by win7 on 2017-07-12.
 */
public class MyUserDetails extends User {
    private Set<GrantedAuthority> authorities=null;
    private String email;
    public MyUserDetails(String username, String password, String email,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email=email;
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
    public void setEmail(String email){
        this.email=email;
    }

    public String getEmail() {
        return email;
    }
}
