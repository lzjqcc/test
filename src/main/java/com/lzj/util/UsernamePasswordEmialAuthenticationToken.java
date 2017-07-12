package com.lzj.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by win7 on 2017-07-12.
 */
public class UsernamePasswordEmialAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String email;
    public UsernamePasswordEmialAuthenticationToken(Object principal, Object credentials,String email) {
        super(principal, credentials);
        this.email=email;
    }
    public UsernamePasswordEmialAuthenticationToken(Object principal, Object credentials,String email, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.email=email;
    }

    public String getEmail() {
        return email;
    }
}
