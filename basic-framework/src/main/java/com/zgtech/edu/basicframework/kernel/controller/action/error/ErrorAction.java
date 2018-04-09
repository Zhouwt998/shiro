package com.zgtech.edu.basicframework.kernel.controller.action.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorAction {

    @GetMapping("/403")
    public String unauthorizd(){
        return "/error/403.html";
    }

    @GetMapping("/404")
    public String notfound(){
        return "/error/404.html";
    }

    @GetMapping("/500")
    public String servererror(){
        return "/error/500.html";
    }
}
