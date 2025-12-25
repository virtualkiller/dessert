package com.hsxy.dessert.service;

import com.hsxy.dessert.entity.Category;
import com.hsxy.dessert.mapper.CategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Resource //@Autowired
    RedisTemplate redisTemplate;
    static String cacheCategories = "categoriesAll"; //缓存
    public List<Category> getAll() {
        //先缓取值
        List<Category> cacheCates
                = (List<Category>) redisTemplate.opsForValue().get(cacheCategories);
        if(cacheCates!=null && cacheCates.size()>0) {
            return cacheCates;
        }
        //缓存中数据不存在，才通过MyBatis从数据库取，并同步放入缓存
        List<Category> categories = categoryMapper.getAll();
        redisTemplate.opsForValue().set(cacheCategories,categories);
        //放入缓存
        return categories;
    }
    public int save(Category category) {
        int row = categoryMapper.save(category);
        if(row>0) { //添加成功，重置缓存数据
            List<Category> categories = categoryMapper.getAll();
            redisTemplate.opsForValue().set(cacheCategories,categories);
        }
        return row;
    }
    public int edit(Category category) {
        int row = categoryMapper.edit(category);
        if(row>0) { //修改成功，重置缓存数据
            List<Category> categories = categoryMapper.getAll();
            redisTemplate.opsForValue().set(cacheCategories,categories);
        }
        return row;
    }
    public int remove(Integer id) {
        int row = categoryMapper.remove(id);
        if(row>0) { //删除成功，重置缓存数据
            List<Category> categories = categoryMapper.getAll();
            redisTemplate.opsForValue().set(cacheCategories,categories);
        }
        return row;
    }
    public Category get(Integer id) {
        return categoryMapper.get(id);
    }
}


