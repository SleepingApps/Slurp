package it.sofk.slurp.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class

ViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<FoodType>> foodTypes;


    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodTypes = repository.getFoodTypes();
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
    public void update(FoodInstance foodInstance){
        repository.update(foodInstance);
    }

    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency){
        return repository.getFoodIstances(frequency, LocalDate.now());
    }

    public LiveData<List<FoodDTO>> getFoods(Frequency frequency) {
        return repository.getFoods(frequency, LocalDate.now(), CaloricIntake.CAL_2000);
    }

    public void update(FoodDTO food){
        repository.update(food);
    }

}
