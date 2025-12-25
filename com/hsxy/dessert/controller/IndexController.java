package com.hsxy.dessert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "Index";
    }
    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome";
    }
    @RequestMapping("/403")
    public String html403(){
        return "403";
    }
}