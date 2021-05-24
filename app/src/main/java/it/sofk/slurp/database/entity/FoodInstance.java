package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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
    private Date date;

    private double portionConsumed;

    public FoodInstance(@NonNull String foodType, @NonNull Date date) {
        this.foodType = foodType;
        this.date = date;
    }

    @NonNull
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(@NonNull String foodType) {
        this.foodType = foodType;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public double getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(double portionConsumed) {
        this.portionConsumed = portionConsumed;
    }
}
