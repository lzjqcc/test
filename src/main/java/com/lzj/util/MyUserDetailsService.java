package com.lzj.util;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017-07-12.
 */
public class MyUserDetailsService  implements UserDetailsService{
    //DaoAuthenticationProvider
   // JdbcDaoImpl
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails user=null;
        if (username.equals("li")){
            List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
            user=new MyUserDetails(username,"123","116",list);
            return user;
        }else if (username.equals("admin")){
            List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            user=new MyUserDetails(username,"admin","188",list);
            return user;
        }
        return null;

    }
}
