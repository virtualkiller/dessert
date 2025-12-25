package com.hsxy.dessert.mapper;

import com.hsxy.dessert.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CategoryMapper {
    @Select("select id,name,descp from category")
    List<Category> getAll();
    @Insert("insert into category(name,descp) values(#{name},#{descp})")
    int save(Category category);

    @Select("select id,name,descp from category where id=#{id}")
    Category get(Integer id);

    @Update("update category set name=#{name},descp=#{descp} where id=#{id}")
    int edit(Category category);

    @Delete("delete from category where id=#{id}")
    int remove(Integer id);
}

