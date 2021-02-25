package it.sofk.slurp.entity;

import androidx.annotation.Nullable;

public class Food {

    private FoodType foodType;
    private double portionConsumed;

    public Food(FoodType foodType, double portionConsumed) {
        this.foodType = foodType;
        this.portionConsumed = portionConsumed;
    }

    public Food(FoodType foodType) {
        this.foodType = foodType;
        this.portionConsumed = 0;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public double getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(double portionConsumed) {
        this.portionConsumed = portionConsumed;
    }

    public void incrementPortionConsumed(double increment){
        this.portionConsumed += increment;
    }

    public void decreasePortionConsumed(double increment){
        this.portionConsumed -= increment;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Food food = (Food) obj;
        if (food == null) return false;

        if(this.foodType == food.foodType && this.portionConsumed == food.portionConsumed)
            return  true;

        return false;
    }
}
