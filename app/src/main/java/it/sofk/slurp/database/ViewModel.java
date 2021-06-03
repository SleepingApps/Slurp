package it.sofk.slurp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.enumeration.Frequency;

public class ViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<FoodInstance>> foodIstances;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodIstances = repository.getFoodIstances();
    }

    public LiveData<List<FoodInstance>> getFoodIstances(){
        return foodIstances;
    }

    public LiveData<FoodInstance> getFoodIstance(String foodType, Date date){
        return repository.getFoodInstance(foodType, date);
    }

    public void insert(FoodInstance foodInstance){
        repository.insert(foodInstance);
    }

    public LiveData<List<FoodType>> getFoodTypesByFrequency(Frequency frequency){
        return repository.getFoodTypes(frequency);
    }

    public void update(FoodInstance foodInstance){
        repository.update(foodInstance);
    }

    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency){
        return repository.getFoodIstances(frequency);
    }

    public FoodType getFoodTypeByName(String name){
        return repository.getFoodType(name);
    }

    public FoodGroup getFoodGroupByName(String name){
        return repository.getFoodGroupByName(name);
    }
}
