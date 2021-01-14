package it.sofk.slurp.entity;

public class Food {

    private FoodType foodType;
    private double portionConsumed;

    public Food(FoodType foodType, double portionConsumed) {
        this.foodType = foodType;
        this.portionConsumed = portionConsumed;
    }

    public Food(FoodType foodType) {
        this.foodType = foodType;
        this.portionConsumed = 0;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public double getPortionConsumed() {
        return portionConsumed;
    }

    public void setPortionConsumed(double portionConsumed) {
        this.portionConsumed = portionConsumed;
    }

    public void incrementPortionConsumed(double increment){
        this.portionConsumed += increment;
    }

    public void decreasePortionConsumed(double increment){
        this.portionConsumed -= increment;
    }
}
