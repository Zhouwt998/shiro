package com.zgtech.edu.basicframework.kernel.controller.action;

import com.zgtech.edu.basicframework.kernel.model.mapped.Food;
import com.zgtech.edu.basicframework.kernel.service.food.FoodService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class FoodAction{

    @Autowired
    private FoodService foodService;

    @GetMapping("/foodUI")
    public String addFoodUI(){
        System.out.println(SecurityUtils.getSubject().getSession().getTimeout());
        return "foodUI.html";
    }

    @GetMapping("/foodUI/food")
    public String updFoodUI(Model model,@RequestParam Integer foodId){
        Food food = foodService.findFood(foodId);
        if(food == null){
            model.addAttribute("msg","找不到该菜品");
            return "updFoodUI.html";
        }
        model.addAttribute(food);
        return "updFoodUI.html";
    }
}
