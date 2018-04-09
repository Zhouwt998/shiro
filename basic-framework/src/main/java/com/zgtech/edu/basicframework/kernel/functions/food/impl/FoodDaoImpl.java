package com.zgtech.edu.basicframework.kernel.functions.food.impl;

import com.zgtech.edu.basicframework.kernel.functions.food.FoodDao;
import com.zgtech.edu.basicframework.kernel.model.mapped.Food;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodDaoImpl implements FoodDao{

    @Autowired
    private Dao dao;

    @Override
    public Food findFood(Integer foodId) {
        return dao.fetch(Food.class,foodId);
    }

    /*添加菜品*/
    @Override
    public void addFood(Food food) {
        dao.insert(food);
    }

    /*修改菜品*/
    @Override
    public void updFood(Food food) {
        dao.update(food);
    }
}
