package com.zgtech.edu.basicframework.kernel.configure;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyErrorPageRegistrar implements ErrorViewResolver{
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView();

        /*状态码*/
        int statusValue =status.value();
        if(statusValue == 404){
            modelAndView.setViewName("redirect:/404");
        }else{
            modelAndView.setViewName("redirect:/500");
        }
        return modelAndView;
    }
}
