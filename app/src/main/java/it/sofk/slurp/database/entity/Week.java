package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Week {

    @PrimaryKey
    @NonNull
    public LocalDate startDate;
    public LocalDate endDate;

    public Week(@NonNull LocalDate startDate) {
        this.startDate = startDate;
        this.endDate = startDate.plusDays(7);
    }

    public @NonNull LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(@NonNull LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(@NonNull LocalDate endDate) {
        this.endDate = endDate;
    }
}
