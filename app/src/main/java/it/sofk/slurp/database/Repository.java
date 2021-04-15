package it.sofk.slurp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.entity.FoodIstance;

public class Repository {

    private FoodDao foodDao;
    private LiveData<List<FoodIstance>> foodIstances;

    public Repository(Application application){
        Database database = Database.getInstance(application);
        foodDao = database.foodDao();
        foodIstances = foodDao.getFoods();
    }

    public LiveData<List<FoodIstance>> getFoodIstances(){
        return foodIstances;
    }

    public void insert(FoodIstance foodIstance){
        Database.databaseWriteExecutor.execute(() -> foodDao.insert(foodIstance));
    }
}
