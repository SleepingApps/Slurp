package it.sofk.slurp.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.database.Repository;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.enumeration.Frequency;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<FoodInstance>> foodInstances;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<FoodInstance>> getFoodInstances(){
        return repository.getFoodIstances(Frequency.DAILY, LocalDate.now());
    }
}
