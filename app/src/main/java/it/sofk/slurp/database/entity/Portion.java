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

    @NonNull
    private Integer numberOf;

    public Portion(@NonNull String foodType, @NonNull CaloricIntake caloricIntake, @NonNull Integer numberOf) {
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

    @NonNull
    public Integer getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(@NonNull Integer numberOf) {
        this.numberOf = numberOf;
    }
}
