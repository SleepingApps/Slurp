package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import it.sofk.slurp.enumeration.Frequency;

@Entity
public class FoodType {

    @PrimaryKey
    @NonNull
    private String name;

    private Frequency frequency;

    private int standardPortion; //in grams

    @ForeignKey(
            entity = FoodGroup.class,
            parentColumns = "foodGroup",
            childColumns = "name",
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
    )

    private String foodGroup;

    public FoodType(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public int getStandardPortion() {
        return standardPortion;
    }

    public void setStandardPortion(int standardPortion) {
        this.standardPortion = standardPortion;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }
}
