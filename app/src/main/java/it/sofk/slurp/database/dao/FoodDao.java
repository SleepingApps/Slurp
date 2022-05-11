package it.sofk.slurp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.Portion;
import it.sofk.slurp.database.entity.SamePortion;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;

@Dao
public abstract class FoodDao {

    @Query("SELECT DISTINCT alternativeName as name, food_instance.portionConsumed as eatenPortions, Portion.numberOf as maxPortions, food_instance.date as date " +
            "FROM same_portion, food_type, food_instance, Portion " +
            "WHERE food_type.samePortion = same_portion.alternativeName " +
            "AND Portion.foodType = food_type.name " +
            "AND food_type.name = food_instance.foodType " +
            "AND food_type.frequency = :frequency " +
            "AND food_instance.date >= :startDate " +
            "AND food_instance.date <= :endDate " +
            "AND Portion.caloricIntake = (SELECT caloricIntake FROM User WHERE id = 0)")
    public abstract LiveData<List<FoodDTO>> getFoodDTO(Frequency frequency, LocalDate startDate, LocalDate endDate);


    @Query("SELECT DISTINCT alternativeName as name, food_instance.portionConsumed as eatenPortions, Portion.numberOf as maxPortions, food_instance.date as date " +
            "FROM same_portion, food_type, food_instance, Portion " +
            "WHERE food_type.samePortion = same_portion.alternativeName " +
            "AND Portion.foodType = food_type.name " +
            "AND food_type.name = food_instance.foodType " +
            "AND food_type.frequency = :frequency " +
            "AND food_instance.date = :date " +
            "AND Portion.caloricIntake = (SELECT caloricIntake FROM User WHERE id = 0)")
    public abstract LiveData<List<FoodDTO>> getFoodDTO(Frequency frequency, LocalDate date);

    @Query("SELECT DISTINCT alternativeName as name, food_instance.portionConsumed as eatenPortions, Portion.numberOf as maxPortions, food_instance.date as date " +
            "FROM same_portion, food_type, food_instance, Portion " +
            "WHERE food_type.samePortion = same_portion.alternativeName " +
            "AND Portion.foodType = food_type.name " +
            "AND food_type.name = food_instance.foodType " +
            "AND food_type.frequency = :frequency " +
            "AND food_instance.date = :date " +
            "AND Portion.caloricIntake = :caloricIntake")
    public abstract LiveData<List<FoodDTO>> getFoodDTO(Frequency frequency, LocalDate date, CaloricIntake caloricIntake);

    @Query("SELECT DISTINCT alternativeName as name, food_instance.portionConsumed as eatenPortions, Portion.numberOf as maxPortions, food_instance.date as date " +
            "FROM same_portion, food_type, food_instance, Portion " +
            "WHERE food_type.samePortion = same_portion.alternativeName " +
            "AND Portion.foodType = food_type.name " +
            "AND food_type.name = food_instance.foodType " +
            "AND food_type.frequency = :frequency " +
            "AND food_instance.date >= :startDate " +
            "AND food_instance.date <= :endDate " +
            "AND Portion.caloricIntake = :caloricIntake")
    public abstract LiveData<List<FoodDTO>> getFoodDTO(Frequency frequency, LocalDate startDate, LocalDate endDate, CaloricIntake caloricIntake);

    @Transaction
    public void updateDTO(FoodDTO food){
        updateFromAlternativeName(food.getName(), food.getEatenPortions(), food.getDate());
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(FoodInstance foodInstance);

    @Insert
    public abstract void insert(FoodType foodType);

    @Insert
    public abstract void insert(Portion portion);

    @Insert
    public abstract void insert(FoodGroup foodGroup);

    @Insert
    public abstract void insert(SamePortion samePortion);

    @Delete
    public abstract void delete(FoodInstance foodInstance);

    @Update
    public abstract void update(FoodInstance foodInstance);

    @Transaction
    public void updateFromAlternativeName(String name, double number, LocalDate date){
        List<FoodInstance> f = getFoodInstancesFromAlternativeName(name, date);
        for (FoodInstance food : f) {
            food.setPortionConsumed(number);
            update2(food);
        }

    }

    @Transaction
    public void update2(FoodInstance foodInstance){
        for(FoodInstance f : getFoodInstancesWithSamePortion(foodInstance.getFoodType(), foodInstance.getDate())) {
            f.setPortionConsumed(foodInstance.getPortionConsumed());
            update(f);
        }
    }
    @Query("SELECT food_instance.* " +
            "FROM food_instance, food_type " +
            "WHERE food_type.samePortion IN (SELECT food_type.samePortion FROM food_type WHERE name = :foodType ) AND " +
            "   date = :date AND " +
            "   food_instance.foodType = food_type.name")
    public abstract List<FoodInstance> getFoodInstancesWithSamePortion(String foodType, LocalDate date);

    @Query("SELECT food_instance.* FROM food_instance, food_type WHERE foodType = name AND samePortion = :name AND date = :date")
    public abstract List<FoodInstance> getFoodInstancesFromAlternativeName(String name, LocalDate date);

    @Query("SELECT food_instance.* FROM food_instance, food_type WHERE frequency = :frequency AND food_instance.foodType = food_type.name AND date = :date")
    public abstract LiveData<List<FoodInstance>> getFoods(Frequency frequency, LocalDate date);

    @Query("SELECT * FROM food_type")
    public abstract LiveData<List<FoodType>> getFoodTypes();

}
