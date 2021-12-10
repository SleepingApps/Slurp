package it.sofk.slurp.dto;

public class FoodDTO {

    private String name;
    private double eatenPortions;
    private double maxPortions;

    public FoodDTO(String name, double eatenPortions, double maxPortions) {
        this.name = name;
        this.eatenPortions = eatenPortions;
        this.maxPortions = maxPortions;
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
}
