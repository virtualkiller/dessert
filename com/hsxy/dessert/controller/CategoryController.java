package com.hsxy.dessert.controller;

import com.hsxy.dessert.entity.Category;
import com.hsxy.dessert.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //分类列表页
    @RequestMapping("/categories")
    public String category(Model model) {
        List<Category> all = categoryService.getAll();
        model.addAttribute("categories", all);
        return "Category";
    }
    //添加
    @RequestMapping("/category/add")
    public String add(){
        return "CategoryAdd.html";
    }
    //提交添加
    @PostMapping("/category")
    public String save(Category category){
        categoryService.save(category);
        return "redirect:/categories";
    }
    //编辑
    @RequestMapping("/category/edit/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        Category category=categoryService.get(id);
        model.addAttribute("category",category);
        return "CategoryEdit";
    }
    @PutMapping("/category")
    public String edit(Category category){
        categoryService.edit(category);
        return "redirect:/categories";
    }
    //删除
    @RequestMapping("/category/del/{id}")
    public String delete(@PathVariable("id") Integer id){
        categoryService.remove(id);
        return "redirect:/categories";
    }
}
