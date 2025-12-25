package com.hsxy.dessert.mapper;


import com.hsxy.dessert.entity.AppGrantedAuthority;
import com.hsxy.dessert.entity.AppUserDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AppUserMapper {
    @Select("select * from t_user where username=#{username}")
    AppUserDetails selectUserByUsername(String username);
    @Select("select role as authority from t_user_role ur left join t_role r on ur.role_id=r.id where user_id=#{userId} ")
    List<AppGrantedAuthority> selectUserAuthorities(Integer userId);
    @Insert("insert into t_user(username,password,active) values(#{username},#{password},1)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int register(AppUserDetails appUserDetails);

    @Insert("insert into t_user_role(user_id,role_id) values(#{userId},#{roleId})")
    int addRole(int userId, int roleId);

}
