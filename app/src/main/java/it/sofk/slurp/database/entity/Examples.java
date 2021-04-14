package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
    primaryKeys = {
        "foodType",
        "example"
})
public class Examples {

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
    private String example;

    public Examples(@NonNull String foodType, @NonNull String example) {
        this.foodType = foodType;
        this.example = example;
    }

    @NonNull
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(@NonNull String foodType) {
        this.foodType = foodType;
    }

    @NonNull
    public String getExample() {
        return example;
    }

    public void setExample(@NonNull String example) {
        this.example = example;
    }

}
