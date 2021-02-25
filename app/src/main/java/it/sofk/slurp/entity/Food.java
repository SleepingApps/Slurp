package it.sofk.slurp.entity;

import androidx.annotation.Nullable;

public class Food {

    private FoodType foodType;
    private float portionConsumed;

    public Food(FoodType foodType, float portionConsumed) {
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

    public float getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(float portionConsumed) {
        this.portionConsumed = portionConsumed;
    }

    public void incrementPortionConsumed(float increment){
        this.portionConsumed += increment;
    }

    public void decreasePortionConsumed(float increment){
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
