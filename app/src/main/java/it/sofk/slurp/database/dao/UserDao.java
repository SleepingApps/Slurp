package it.sofk.slurp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import it.sofk.slurp.database.entity.User;
import it.sofk.slurp.enumeration.CaloricIntake;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM User WHERE id = 0")
    LiveData<User> getUser();

    @Query("SELECT caloricIntake FROM User WHERE id = 0")
    LiveData<CaloricIntake> getActualCaloricIntake();
}
