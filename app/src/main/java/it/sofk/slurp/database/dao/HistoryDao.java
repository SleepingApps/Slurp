package it.sofk.slurp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.sofk.slurp.database.entity.Week;
import it.sofk.slurp.dto.WeekListItem;

@Dao
public interface HistoryDao {

    @Query("SELECT startDate " +
            "FROM Week")
    LiveData<List<WeekListItem>> getWeeks();

    @Insert
    void addWeek(Week week);

}
