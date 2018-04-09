package com.zgtech.edu.basicframework.kernel.service.food;

import com.zgtech.edu.basicframework.kernel.model.mapped.Food;

public interface FoodService {

    /*查询菜品*/
    Food findFood(Integer foodId);

    /*添加菜品*/
    void addFood(Food food);

    /*修改菜品*/
    void updFood(Food food);
}
