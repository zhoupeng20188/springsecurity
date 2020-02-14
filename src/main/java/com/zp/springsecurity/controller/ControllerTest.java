package com.zp.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerTest {

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/content")
    public ModelAndView content(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("content");
        return modelAndView;
    }
}
