package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "food_instance",
        primaryKeys = {
            "foodType",
            "date"
})
public class FoodInstance {
    @NonNull
    @ForeignKey(
            entity = FoodType.class,
            parentColumns = "foodType",
            childColumns = "name",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
    private String foodType;

    @NonNull
    private LocalDate date;

    private double portionConsumed;

    public FoodInstance(@NonNull String foodType, @NonNull LocalDate date) {
        this.foodType = foodType;
        this.date = date;
        portionConsumed = 0;
    }

    @NonNull
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(@NonNull String foodType) {
        this.foodType = foodType;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = date;
    }

    public double getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(double portionConsumed) {
        this.portionConsumed = portionConsumed;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null || getClass() != obj.getClass()) return false;

        FoodInstance food = (FoodInstance) obj;
        return this.foodType.equals(food.foodType) && this.date == food.date && this.portionConsumed == food.portionConsumed;
    }
}
