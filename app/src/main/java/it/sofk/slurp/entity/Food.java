package it.sofk.slurp.entity;

import androidx.annotation.Nullable;
import androidx.room.Entity;

@Entity(tableName = "food")
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
        if(getClass() != obj.getClass()){
            return false;
        }
        Food food = (Food) obj;
        if (food == null) return false;

        return this.foodType == food.foodType && this.portionConsumed == food.portionConsumed;
    }
}
