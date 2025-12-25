package com.hsxy.dessert.service;

import com.hsxy.dessert.entity.AppUserDetails;
import com.hsxy.dessert.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    AppUserMapper appUserMapper;
    public boolean existName(String username) {
        AppUserDetails appUserDetails = appUserMapper.selectUserByUsername(username);
        if (appUserDetails!=null) {
            return true;
        }
        return false;
    }
    @Value("${role_id}") // 注册用户的角色值，配置在 application.properties 中
    int roleId; // 默认值目前为2，即 normal 角色
    @Transactional // 注册涉及两表数据插入，应该使用事务管理
    public boolean register(AppUserDetails appUserDetails) {
        if(appUserMapper.register(appUserDetails)>0) {
            return appUserMapper.addRole(appUserDetails.getId(), roleId)>0;
        }
        return false;
    }
}

