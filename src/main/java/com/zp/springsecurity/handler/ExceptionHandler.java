package com.zp.springsecurity.handler;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {
    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("======resolveException in...");
        System.out.println(ex);
        ModelAndView mv = new ModelAndView();
        if (ex instanceof AccessDeniedException) {
            mv.setViewName("403");
        } else if (ex instanceof NotFoundException){
            mv.setViewName("404");
        } else {
            mv.setViewName("500");
        }
        return mv;
    }
}
