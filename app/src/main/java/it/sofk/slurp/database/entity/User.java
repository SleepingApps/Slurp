package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import it.sofk.slurp.enumeration.CaloricIntake;

@Entity
public class User {

    @PrimaryKey
    public Integer id;

    @Nullable
    public Double weight;

    @Nullable
    public Double height;

    @NonNull
    public CaloricIntake caloricIntake;

    @Ignore
    public User(double weight, double height, @NonNull CaloricIntake caloricIntake) {
        this(caloricIntake);
        this.weight = weight;
        this.height = height;
    }


    public User(@NonNull CaloricIntake caloricIntake) {
        this.id = 0;
        this.caloricIntake = caloricIntake;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @NonNull
    public CaloricIntake getCaloricIntake() {
        return caloricIntake;
    }

    public void setCaloricIntake(@NonNull CaloricIntake caloricIntake) {
        this.caloricIntake = caloricIntake;
    }
}
