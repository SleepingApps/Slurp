package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import it.sofk.slurp.enumeration.CaloricIntake;

@Entity
public class Portion {

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
    private CaloricIntake caloricIntake;

    private int numberOf;

    public Portion(@NonNull String foodType, @NonNull CaloricIntake caloricIntake) {
        this.foodType = foodType;
        this.caloricIntake = caloricIntake;
    }

    @NonNull
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(@NonNull String foodType) {
        this.foodType = foodType;
    }

    @NonNull
    public CaloricIntake getCaloricIntake() {
        return caloricIntake;
    }

    public void setCaloricIntake(@NonNull CaloricIntake caloricIntake) {
        this.caloricIntake = caloricIntake;
    }

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }
}
