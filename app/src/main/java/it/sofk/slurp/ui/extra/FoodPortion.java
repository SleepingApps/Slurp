package it.sofk.slurp.ui.extra;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

        LinkedHashMap<String, Integer> foodTypes = new LinkedHashMap<>();
        for(ExampleDTO example : examplesData) {
            if (food.getName().equals(example.getFoodName())) {
                foodTypes.put(example.getFoodType(), example.getStandardPortion());
            }
        }
        Logger.getAnonymousLogger().info(foodTypes.toString());
        for(Map.Entry e: foodTypes.entrySet()) {
            description.append("<br><h5>" + e.getKey() + " " + e.getValue() + "g</h5>");
            description.append("<ul>");
            for (ExampleDTO example : examplesData) {
                if (food.getName().equals(example.getFoodName()) && e.getKey().equals(example.getFoodType())) {
                    description.append("<li>" + example.getExample() + "</li>");
                }
            }
            description.append("</ul>");
        }



        return description.toString();
    }
}
