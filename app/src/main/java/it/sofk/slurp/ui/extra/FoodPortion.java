package it.sofk.slurp.ui.extra;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import it.sofk.slurp.dto.ExampleDTO;
import it.sofk.slurp.dto.FoodDTO;

public class FoodPortion {

    public static FoodDTO increasePortion(@NonNull FoodDTO food) {
        FoodDTO newFood = new FoodDTO(food.getName(),
                food.getEatenPortions() + 0.5,
                food.getMaxPortions(), food.getDate());

        return newFood;
    }

    public static FoodDTO decreasePortion(@NonNull FoodDTO food) {
        FoodDTO newFood = new FoodDTO(food.getName(),
                food.getEatenPortions() - 0.5,
                food.getMaxPortions(), food.getDate());

        return newFood;
    }

    public static String CreateAndGetDescription(FoodDTO food, List<ExampleDTO> examplesData) {
        StringBuilder description = new StringBuilder();
        for(ExampleDTO example : examplesData) {
            if (food.getName().equals(example.getFoodName())) {
                description.append(example.getExample());
                description.append("\n");
            }
        }


        return description.substring(0, description.length()-1);
    }
}
