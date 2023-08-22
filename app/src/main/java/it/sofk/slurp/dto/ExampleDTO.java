package it.sofk.slurp.dto;

public class ExampleDTO {

    private String foodName;
    private String foodType;
    private int standardPortion;
    private String example;

    public ExampleDTO(String foodName, String foodType, String example) {
        this.foodName = foodName;
        this.foodType = foodType;
        this.example = example;
    }

    public int getStandardPortion() {
        return standardPortion;
    }

    public void setStandardPortion(int standardPortion) {
        this.standardPortion = standardPortion;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
