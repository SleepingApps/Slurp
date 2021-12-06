package it.sofk.slurp.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;

public class ViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<FoodInstance>> foodIstances;
    private LiveData<List<FoodType>> foodTypes;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodIstances = repository.getFoodIstances();
        foodTypes = repository.getFoodTypes();
    }

    public LiveData<List<FoodInstance>> getFoodIstances(){
        return foodIstances;
    }

    public LiveData<FoodInstance> getFoodIstance(String foodType, LocalDate date){
        return repository.getFoodInstance(foodType, date);
    }

    public LiveData<List<FoodType>> getFoodTypes(){
        return foodTypes;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency){
        return repository.getFoodIstances(frequency, LocalDate.now());
    }

    public FoodType getFoodTypeByName(String name){
        return repository.getFoodType(name);
    }

    public FoodGroup getFoodGroupByName(String name){
        return repository.getFoodGroupByName(name);
    }

    public LiveData<List<String>> getEquivalentNames(Frequency frequency){
        return repository.getEquivalentsNames(frequency);
    }

    public double maxPortion(String equivalentName, CaloricIntake caloricIntake){
        Integer maxPortion = repository.maxPortion(equivalentName, caloricIntake);

        return (double) maxPortion / 2;
    }
}
