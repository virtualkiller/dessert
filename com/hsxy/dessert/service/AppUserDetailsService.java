package com.hsxy.dessert.service;


import com.hsxy.dessert.entity.AppGrantedAuthority;
import com.hsxy.dessert.entity.AppUserDetails;
import com.hsxy.dessert.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppUserDetailsService implements UserDetailsService {
@Autowired
AppUserMapper appUserMapper;
PasswordEncoder delegatingPasswordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDetails user=appUserMapper.selectUserByUsername(username);
        if(user!=null) {
            List<AppGrantedAuthority> authorities = appUserMapper.selectUserAuthorities(user.getId());
            user.setAuthorities(authorities);
            user.setPassword(delegatingPasswordEncoder.encode(user.getPassword()));
            System.out.println(delegatingPasswordEncoder.encode(user.getPassword()));
        }
        return user;
    }
}
