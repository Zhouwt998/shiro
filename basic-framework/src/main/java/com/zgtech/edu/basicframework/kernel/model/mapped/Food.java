package com.zgtech.edu.basicframework.kernel.model.mapped;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

@Table("nm_food")
public class Food implements Serializable{

    @Id
    private Integer foodId;

    /*菜品名称*/
    private String name;

    /*就餐日期*/
    private String repastDate;

    /*菜品原材料*/
    private String raw;

    /*类别*/
    private String foodType;

    /*工艺*/
    private String craft;

    /*形状*/
    private String shape;

    /*口味*/
    private String taste;

    /*蛋白质*/
    private Float protein;

    /*脂肪*/
    private Float fat;

    /*碳水化合物*/
    private Float carbohydrate;

    /*矿物质*/
    private Float mineral;

    /*膳食纤维*/
    private Float fiber;

    /*菜品图片地址*/
    private String foodUrl;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepastDate() {
        return repastDate;
    }

    public void setRepastDate(String repastDate) {
        this.repastDate = repastDate;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Float getMineral() {
        return mineral;
    }

    public void setMineral(Float mineral) {
        this.mineral = mineral;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
    }

    public String getFoodUrl() {
        return foodUrl;
    }

    public void setFoodUrl(String foodUrl) {
        this.foodUrl = foodUrl;
    }

}
