package com.zgtech.edu.basicframework.kernel.controller.action;

import com.zgtech.edu.basicframework.kernel.model.dto.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class Basic {

    @GetMapping("/")
    public String index(Map<String, String> map) {
        map.put("Test", "test");
        return "index.html";
    }
}
