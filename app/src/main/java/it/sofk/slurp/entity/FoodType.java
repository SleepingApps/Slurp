package it.sofk.slurp.entity;

import java.util.Dictionary;

import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;

public class FoodType {

    private String name;
    private FoodGroup group;
    private Frequency frequency;
    Dictionary<CaloricIntake, Double> portions;

    public FoodType(String name, FoodGroup group, Frequency frequency ,Dictionary<CaloricIntake, Double> portions) {
        this.name = name;
        this.group = group;
        this.frequency = frequency;
        this.portions = portions;
    }

    public String getName() {
        return name;
    }

    public FoodGroup getGroup() {
        return group;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Double getPortion(CaloricIntake caloricIntake){
        return portions.get(caloricIntake);
    }
}
