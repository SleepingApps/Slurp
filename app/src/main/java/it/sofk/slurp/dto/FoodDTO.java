package it.sofk.slurp.dto;

import androidx.room.Ignore;

import java.time.LocalDate;

public class FoodDTO {

    private String name;
    private double eatenPortions;
    private double maxPortions;
    private LocalDate date;

    @Ignore
    @Deprecated
    public FoodDTO(String name, double eatenPortions, double maxPortions) {
        this.name = name;
        this.eatenPortions = eatenPortions;
        this.maxPortions = maxPortions;
        this.date = LocalDate.now();
    }

    public FoodDTO(String name, double eatenPortions, double maxPortions, LocalDate date) {
        this.name = name;
        this.eatenPortions = eatenPortions;
        this.maxPortions = maxPortions;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public double getEatenPortions() {
        return eatenPortions;
    }

    public double getMaxPortions() {
        return maxPortions;
    }

    public void setEatenPortions(double eatenPortions) {
        this.eatenPortions = eatenPortions;
    }

    public LocalDate getDate(){
        return date;
    }
}
