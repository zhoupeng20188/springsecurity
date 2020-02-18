package com.zp.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
