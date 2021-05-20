package it.sofk.slurp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.sofk.slurp.database.entity.FoodIstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.enumeration.Frequency;

@Dao
public interface FoodDao {

    @Insert
    void insert(FoodIstance foodIstance);

    @Insert
    void insert(FoodType foodType);

    @Delete
    void delete(FoodIstance foodIstance);

    @Update
    void update(FoodIstance foodIstance);

    @Query("SELECT * FROM food_instance")
    LiveData<List<FoodIstance>> getFoods();

    @Query("SELECT * FROM food_type WHERE frequency = :frequency")
    LiveData<List<FoodType>> getFoodTypes(Frequency frequency);
}
