package it.sofk.slurp.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.sofk.slurp.database.Repository;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;

public class StartWeekViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<FoodType>> foodTypes;

    public StartWeekViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodTypes = repository.getFoodTypes();
    }

    public LiveData<List<FoodType>> getFoodTypes(){
        return foodTypes;
    }

    public void insert(FoodInstance foodInstance){
        repository.insert(foodInstance);
    }

}
