package it.sofk.slurp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.sofk.slurp.database.entity.FoodIstance;

public class ViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<FoodIstance>> foodIstances;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodIstances = repository.getFoodIstances();
    }

    public LiveData<List<FoodIstance>> getFoodIstances(){
        return foodIstances;
    }

    public void insert(FoodIstance foodIstance){
        repository.insert(foodIstance);
    }
}
