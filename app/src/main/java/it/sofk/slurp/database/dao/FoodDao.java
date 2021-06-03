package it.sofk.slurp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.enumeration.Frequency;

@Dao
public interface FoodDao {

    @Insert
    void insert(FoodInstance foodInstance);

    @Insert
    void insert(FoodType foodType);

    @Insert
    void insert(FoodGroup foodGroup);

    @Delete
    void delete(FoodInstance foodInstance);

    @Update
    void update(FoodInstance foodInstance);

    @Query("SELECT * FROM food_instance")
    LiveData<List<FoodInstance>> getFoods();

    @Query("SELECT food_instance.* FROM food_instance JOIN food_type WHERE frequency = :frequency")
    LiveData<List<FoodInstance>> getFoods(Frequency frequency);
    
    @Query("SELECT * FROM food_instance WHERE foodType = :foodType AND date = :date")
    LiveData<FoodInstance> getFood(String foodType, Date date);

    @Query("SELECT * FROM food_type WHERE frequency = :frequency")
    LiveData<List<FoodType>> getFoodTypes(Frequency frequency);

    @Query("SELECT * FROM food_type WHERE name = :name")
    FoodType getFoodType(String name);
}
