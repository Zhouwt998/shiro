package com.zgtech.edu.basicframework.kernel.controller.api;

import com.zgtech.edu.basicframework.kernel.model.dto.AjaxResult;
import com.zgtech.edu.basicframework.kernel.model.mapped.Food;
import com.zgtech.edu.basicframework.kernel.service.food.FoodService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FoodRest {

    @Autowired
    private FoodService foodService;

    @PostMapping("/food")
    public AjaxResult addFood(Food food){
        foodService.addFood(food);
        if(food.getFoodId() == null){
            return new AjaxResult("200","添加失败");
        }
        return new AjaxResult("200","添加成功");
    }

    @PostMapping("/food/{foodId}")
    public AjaxResult upd(Food food){
        foodService.updFood(food);

        return new AjaxResult("200","修改成功");
    }
}
