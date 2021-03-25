package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "food_instance")
public class FoodIstance {
    @PrimaryKey
    @NonNull
    @ForeignKey(
            entity = FoodType.class,
            parentColumns = "foodType",
            childColumns = "name",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
    private String foodType;

    @PrimaryKey
    @NonNull
    private Date date;

    private int portionConsumed;

    public FoodIstance(@NonNull String foodType, @NonNull Date date) {
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

    public int getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(int portionConsumed) {
        this.portionConsumed = portionConsumed;
    }
}
