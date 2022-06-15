package com.example.proiect.domain;

import android.graphics.Bitmap;

public class Dish {


    private String price;
    private String ingredients;
    private Boolean isAvailable;

    public Dish() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Dish(String dishName, String price, String ingredients, Boolean isAvailable) {
        this.dishName = dishName;
        this.price = price;
        this.ingredients =ingredients;
        this.isAvailable = isAvailable;
    }

    private String dishName;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

}
