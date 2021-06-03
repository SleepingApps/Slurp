package it.sofk.slurp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.enumeration.Frequency;

public class Repository {

    private FoodDao foodDao;
    private LiveData<List<FoodInstance>> foodIstances;


    public Repository(Application application){
        Database database = Database.getInstance(application);
        foodDao = database.foodDao();
        foodIstances = foodDao.getFoods();
    }

    public LiveData<List<FoodInstance>> getFoodIstances(){
        return foodIstances;
    }

    public void insert(FoodInstance foodInstance){
        Database.databaseWriteExecutor.execute(() -> foodDao.insert(foodInstance));
    }

    public LiveData<List<FoodType>> getFoodTypes(Frequency frequency) {
        return foodDao.getFoodTypes(frequency);
    }

    public LiveData<FoodInstance> getFoodInstance(String foodType, Date date){
        return foodDao.getFood(foodType, date);
    }

    public FoodType getFoodType(String name) {
        return foodDao.getFoodType(name);
    }

    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency){
        return foodDao.getFoods(frequency);
    }

    public void update(FoodInstance foodInstance){
        Database.databaseWriteExecutor.execute(() -> foodDao.update(foodInstance));
    }
}
