package com.hsxy.dessert.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.hsxy.dessert.entity.Category;
import com.hsxy.dessert.entity.Dessert;
import com.hsxy.dessert.entity.DessertDetail;
import com.hsxy.dessert.entity.SearchCondition;
import com.hsxy.dessert.service.CategoryService;
import com.hsxy.dessert.service.DessertService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
public class DessertController {
    @Autowired
    DessertService dessertService;
    @RequestMapping("/desserts")
    public String getAll(Model model, @RequestParam(defaultValue = "1",
            value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<DessertDetail> all = dessertService.getAll();
        PageInfo<DessertDetail> pageInfo = new PageInfo<DessertDetail>(all,pageSize);
        model.addAttribute("dessertDetails", all);
        model.addAttribute("pageInfo",pageInfo);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories",categories);
        return "Dessert";
    }

    @Autowired
    CategoryService categoryService;
    static int pageSize = 5;
    @RequestMapping("/search")
    public String search(SearchCondition condition,Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<DessertDetail> all = dessertService.search(condition);
        PageInfo<DessertDetail> pageInfo = new PageInfo<DessertDetail>(all,pageSize);
        model.addAttribute("dessertDetails",all);
        model.addAttribute("pageInfo",pageInfo);
        List<Category>categories = categoryService.getAll();
        model.addAttribute("categories",categories);
        model.addAttribute("condition",condition);
        return "Dessert";
    }

    @RequestMapping("/dessert/add")
    public String save(Model model){
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories",categories);
        return "DessertAdd";
    }

    @PostMapping("/dessert")
    public String save(Dessert dessert, @RequestParam(required=false) MultipartFile photo)
            throws FileNotFoundException {
        String saveDir="photo/"; //存放目录
        String path = ClassUtils.getDefaultClassLoader().getResource("static/"+saveDir).getPath();
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); //创建目录...\desserts\target\classes\static\photo
        }
        String chgFileName=null;
        if (!photo.isEmpty()) { // 判断是否存在上传文件，是则保存且设置photoUrl
            String fileName = photo.getOriginalFilename();
            String type = fileName.indexOf(".")==-1? null
                    :fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
            if(type!=null) { // 判断上传文件类型
                if("GIF".equals(type.toUpperCase())
                        || "PNG".equals(type.toUpperCase())
                        || "JPG".equals(type.toUpperCase())
                        || "JPEG".equals(type.toUpperCase())) { // 换名
                    chgFileName=String.valueOf(System.currentTimeMillis())+"."+type;
                    File dest = new File(directory+ File.separator + chgFileName);
                    try { // 保存 ...target\classes\static\photo\1649246610037.jpg
                        photo.transferTo(dest);
                        dessert.setPhotoUrl("/"+saveDir+chgFileName);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        dessertService.save(dessert);
        return "redirect:/desserts";
    }

    @GetMapping("/dessert/edit/{id}")    // 转至编辑页面
    public String edit(@PathVariable Integer id, Model model){
        List<Category> categories = categoryService.getAll();  // 下拉选择需要
        model.addAttribute("categories",categories);
        Dessert dessert = dessertService.get(id);
        model.addAttribute("dessert",dessert);
        return "DessertEdit";
    }

    static String saveDir="photo/";        // 添加或编辑时，存放甜点图片目录的相对位置


    @PutMapping("/dessert")    //编辑功能
    public String edit(Dessert dessert, @RequestParam(required = false)
    MultipartFile photo, HttpServletRequest request) throws IOException{
        String path=ClassUtils.getDefaultClassLoader()
                .getResource("static/"+saveDir).getPath();
        File directory = new File(path);    // 与static/photo在一个位置
        if (!directory.exists()) {
            directory.mkdirs(); //C:\...\sp-desserts\target\classes\static\photo
        }
        String chgFileName=null;
        if (!photo.isEmpty()) { // 有上传文件
            String fileName = photo.getOriginalFilename();
            String type = fileName.indexOf(".")==-1? null
                    :fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
            if(type!=null) {
                if ("GIF".equals(type.toUpperCase())
                        || "PNG".equals(type.toUpperCase())
                        || "JPEG".equals(type.toUpperCase())) { // 换名
                    chgFileName=String.valueOf(System.currentTimeMillis())+"."+type;
                    File dest = new File(directory + File.separator + chgFileName);
                    try { //sp-desserts\target\classes\static\photo\1649246610037.jpg
                        photo.transferTo(dest);
                        dessert.setPhotoUrl("/"+saveDir+chgFileName);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        dessertService.edit(dessert);
        return "redirect:/desserts";
    }

    @RequestMapping("/dessert/del/{id}")
    public String delete(@PathVariable("id") Integer id){
        dessertService.remove(id);
        return "redirect:/desserts";
    }

    @RequestMapping("/releaseNew/{row}")
    public String releaseNew(Model model,@PathVariable(value = "row",required = false) Integer row){
        if (row==null){
            row=8;
        }
        List<DessertDetail> newDesserts = dessertService.getReleaseNew(row);
        model.addAttribute("dessertDetails", newDesserts);
        return "DessertNewRelease";
    }
}
