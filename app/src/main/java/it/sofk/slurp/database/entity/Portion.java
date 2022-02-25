package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import it.sofk.slurp.enumeration.CaloricIntake;

@Entity(
        primaryKeys = {
                "foodType",
                "caloricIntake"
        }
)
public class Portion {

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
    private CaloricIntake caloricIntake;

    private double numberOf;

    public Portion(@NonNull String foodType, @NonNull CaloricIntake caloricIntake, double numberOf) {
        this.foodType = foodType;
        this.caloricIntake = caloricIntake;
        this.numberOf = numberOf;
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

    public double getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(double numberOf) {
        this.numberOf = numberOf;
    }
}
