package com.zgtech.edu.basicframework.kernel.service.food.impl;

import com.zgtech.edu.basicframework.kernel.model.mapped.Food;
import com.zgtech.edu.basicframework.kernel.service.BaseService;
import com.zgtech.edu.basicframework.kernel.service.food.FoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl extends BaseService implements FoodService{

    @Override
    public Food findFood(Integer foodId) {
        return foodDao.findFood(foodId);
    }

    @Override
    public void addFood(Food food) {
        foodDao.addFood(food);
    }

    @Override
    public void updFood(Food food) {
        foodDao.updFood(food);
    }
}
