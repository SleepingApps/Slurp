package it.sofk.slurp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.dao.UserDao;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.User;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;

public class Repository {

    private FoodDao foodDao;
    private UserDao userDao;
    private LiveData<List<FoodInstance>> foodIstances;
    private LiveData<List<FoodType>> foodTypes;
    private LiveData<User> user;


    public Repository(Application application){
        Database database = Database.getInstance(application);
        foodDao = database.foodDao();
        userDao = database.userDao();
        foodIstances = foodDao.getFoods();
        foodTypes = foodDao.getFoodTypes();
        user = userDao.getUser();
    }

    public LiveData<User> getUser(){
        return user;
    }

    public void updateWeight(double weight){
        User user = getUser().getValue();
        user.setWeight(weight);
        Database.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public void updateHeight(double height){
        User user = getUser().getValue();
        user.setHeight(height);
        Database.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public void updateCaloricIntake(CaloricIntake caloricIntake){
        User user = getUser().getValue();
        user.setCaloricIntake(caloricIntake);
        Database.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public LiveData<List<FoodInstance>> getFoodIstances(){
        return foodIstances;
    }

    public void insert(FoodInstance foodInstance){
        Database.databaseWriteExecutor.execute(() -> foodDao.insert(foodInstance));
    }

    public LiveData<List<FoodType>> getFoodTypes() {
        return foodTypes;
    }

    public LiveData<List<FoodType>> getFoodTypes(Frequency frequency) {
        return foodDao.getFoodTypes(frequency);
    }

    public LiveData<FoodInstance> getFoodInstance(String foodType, LocalDate date){
        return foodDao.getFood(foodType, date);
    }

    public FoodType getFoodType(String name) {
        return foodDao.getFoodType(name);
    }

    public LiveData<List<FoodInstance>> getFoodIstances(Frequency frequency, LocalDate date){
        return foodDao.getFoods(frequency, date);
    }

    public void update(FoodInstance foodInstance){
        Database.databaseWriteExecutor.execute(() -> foodDao.update2(foodInstance));
    }

    public void updateFromAlternativeName(String name, double number){
        Database.databaseWriteExecutor.execute(() -> foodDao.updateFromAlternativeName(name, number));
    }

    public FoodGroup getFoodGroupByName(String name){
        return foodDao.getFoodGroup(name);
    }

    public LiveData<List<String>> getEquivalentsNames(Frequency frequency) {
        return foodDao.getFoodNames(frequency);
    }

    public Integer maxPortion(String equivalentName, CaloricIntake caloricIntake){
        Integer max = foodDao.maxPortion(equivalentName, caloricIntake);
        if(max == null) max = 0;

        return max;
    }

    public List<FoodInstance> getFoodInstancesFromAlternativeName(String name){
        return foodDao.getFoodInstancesFromAlternativeName(name);
    }

    public LiveData<List<FoodDTO>> getFoods(Frequency frequency, LocalDate date, CaloricIntake caloricIntake){
        return foodDao.getFoodDTO(frequency, date, caloricIntake);
    }

    public void update(FoodDTO foodDTO){
        Database.databaseWriteExecutor.execute(() -> foodDao.updateDTO(foodDTO));
    }
}
