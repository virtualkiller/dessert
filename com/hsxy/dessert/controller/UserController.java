package com.hsxy.dessert.controller;

import com.hsxy.dessert.entity.AppUserDetails;
import com.hsxy.dessert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model){
        if(error!=null){
            model.addAttribute("error","error");
        }
        if(logout!=null){
            model.addAttribute("logout","logout");
        }
        return "Login";
    }

    @GetMapping("/login/success")
    @ResponseBody
    public String loginSuccess(){
        return "<script>window.parent.location.reload()</script>";
    }

    @Autowired
    UserService userService;
    @GetMapping("/register")
    public String register() {
        return "Register";
    }
    @PostMapping("/register") //处理注册
    public String register(String username,String password,String password2, Model model) {
        if(username.isEmpty() || password.isEmpty()) {
            model.addAttribute("msg","用户名和密码不能为空");
            return "Register";
        }
        if( !password.equals(password2) ) {
            model.addAttribute("msg", "两次密码输入必须相同");
            return "Register";
        }
        if(userService.existName(username)) {
            model.addAttribute("msg","用户名已存在");
            return "Register";
        }
        AppUserDetails appUserDetails = new AppUserDetails();
        appUserDetails.setUsername(username);
        appUserDetails.setPassword(password);
        if(userService.register(appUserDetails)) {
            model.addAttribute("msg","注册用户"+username+"成功");
        }else{
            model.addAttribute("msg","注册用户"+username+"失败");
        }
        return "Register";
    }

}
