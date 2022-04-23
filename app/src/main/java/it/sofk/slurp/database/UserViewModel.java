package it.sofk.slurp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import it.sofk.slurp.database.entity.User;
import it.sofk.slurp.enumeration.CaloricIntake;

public class UserViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<User> user;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        user = repository.getUser();
    }

    public LiveData<User> getUserInformation(){
        return user;
    }

    public double getWeight(){
        return user.getValue().weight;
    }

    public double getHeight(){
        return user.getValue().height;
    }

    public CaloricIntake getCaloricIntake(){
        return user.getValue().caloricIntake;
    }

    public void updateWeight(double weight){
        User newUser = user.getValue();
        newUser.setWeight(weight);
        repository.update(newUser);
    }

    public void updateHeight(double height){
        User newUser = user.getValue();
        newUser.setHeight(height);
        repository.update(newUser);
    }

    public void updateCaloricIntake(CaloricIntake caloricIntake){
        User newUser = user.getValue();
        newUser.setCaloricIntake(caloricIntake);
        repository.update(newUser);
    }

}
