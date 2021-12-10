package it.sofk.slurp.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.dto.FoodDTO;
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

    @Deprecated
    public LiveData<List<FoodInstance>> getFoodIstances(){
        return foodIstances;
    }

    @Deprecated
    public LiveData<FoodInstance> getFoodIstance(String foodType, LocalDate date){
        return repository.getFoodInstance(foodType, date);
    }

    @Deprecated
    public LiveData<List<FoodType>> getFoodTypes(){
        return foodTypes;
    }

    @Deprecated
    public void insert(FoodInstance foodInstance){
        repository.insert(foodInstance);
    }

    @Deprecated
    public LiveData<List<FoodType>> getFoodTypesByFrequency(Frequency frequency){
        return repository.getFoodTypes(frequency);
    }

    @Deprecated
    public void update(FoodInstance foodInstance){
        repository.update(foodInstance);
    }

    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency){
        return repository.getFoodIstances(frequency, LocalDate.now());
    }

    @Deprecated
    public FoodType getFoodTypeByName(String name){
        return repository.getFoodType(name);
    }

    @Deprecated
    public FoodGroup getFoodGroupByName(String name){
        return repository.getFoodGroupByName(name);
    }

    @Deprecated
    public LiveData<List<String>> getEquivalentNames(Frequency frequency){
        return repository.getEquivalentsNames(frequency);
    }

    public double maxPortion(String equivalentName, CaloricIntake caloricIntake){
        Integer maxPortion = repository.maxPortion(equivalentName, caloricIntake);

        return (double) maxPortion / 2;
    }

    public List<FoodInstance> getFoodInstancesFromAlternativeName(String name){
        return repository.getFoodInstancesFromAlternativeName(name);
    }


    public List<FoodDTO> getFoods(Frequency frequency){
        List<FoodDTO> foodList = new ArrayList<>();
        List<String> names = repository.getEquivalentsNames(frequency).getValue();
        for (String name : names) {
            List<FoodInstance> foodInstances = getFoodInstancesFromAlternativeName(name);
            FoodInstance foodInstance = foodInstances.get(0);

            FoodDTO food = new FoodDTO(name, foodInstance.getPortionConsumed(), maxPortion(name, repository.getUser().getValue().getCaloricIntake()));

            foodList.add(food);
        }
        return foodList;
    }

    public void updateEatenPortionFromName(String name, double number){
        repository.updateFromAlternativeName(name, number);
    }
}
