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
import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.SamePortion;
import it.sofk.slurp.enumeration.Frequency;

@Dao
public abstract class FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(FoodInstance foodInstance);

    @Insert
    public abstract void insert(FoodType foodType);

    @Insert
    public abstract void insert(FoodGroup foodGroup);

    @Insert
    public abstract void insert(SamePortion samePortion);

    @Delete
    public abstract void delete(FoodInstance foodInstance);

    @Update
    public abstract void update(FoodInstance foodInstance);

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

    @Query("SELECT * FROM food_instance")
    public abstract LiveData<List<FoodInstance>> getFoods();

    @Query("SELECT food_instance.* FROM food_instance, food_type WHERE frequency = :frequency AND food_instance.foodType = food_type.name AND date = :date")
    public abstract LiveData<List<FoodInstance>> getFoods(Frequency frequency, LocalDate date);
    
    @Query("SELECT * FROM food_instance WHERE foodType = :foodType AND date = :date")
    public abstract LiveData<FoodInstance> getFood(String foodType, LocalDate date);

    @Query("SELECT * FROM food_type WHERE frequency = :frequency")
    public abstract LiveData<List<FoodType>> getFoodTypes(Frequency frequency);

    @Query("SELECT * FROM food_type")
    public abstract LiveData<List<FoodType>> getFoodTypes();

    @Query("SELECT * FROM food_type WHERE name = :name")
    public abstract FoodType getFoodType(String name);

    @Query("SELECT * FROM FoodGroup WHERE name = :name")
    public abstract FoodGroup getFoodGroup(String name);
}
