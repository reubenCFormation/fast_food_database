package com.uniquename.fastfooddatabase.JavaClasses;

public class FastFoodMeals {

   private int id;

    private String mealTitle;
    private int mealPrice;

    private int fastFoodRestaurantId;

    public FastFoodMeals(int id, String mealTitle, int mealPrice, int fastFoodRestaurantId){
        this.id=id;
        this.mealTitle=mealTitle;
        this.mealPrice=mealPrice;
        this.fastFoodRestaurantId=fastFoodRestaurantId;
    }

    public int getId(){
        return this.id;
    }

    public String getMealTitle(){
        return this.mealTitle;
    }

    public int getMealPrice(){
        return this.mealPrice;
    }

    public int getFastFoodRestaurantId(){
        return this.fastFoodRestaurantId;
    }
}
