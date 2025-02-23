package com.zp.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/password")
    @ResponseBody
    public void password(){
        System.out.println(passwordEncoder.encode("123"));
    }

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/mylogin")
    public ModelAndView mylogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mylogin");
        return modelAndView;
    }

    @RequestMapping("/content")
    public ModelAndView content(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("content");
        return modelAndView;
    }

    /**
     * @Secured是SpringSecurity的注解
     * @RoleAllowed是jsr250的注解
     * @PreAuthorize是spring的注解
     * 以上注解需要开启对应的配置支持
     * @return
     */
    @RequestMapping("/contentjson")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String contentJson(){
        return "contentJson";
    }

    @RequestMapping("/user")
    public ModelAndView user(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
