package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "same_portion")
public class SamePortion {

    @PrimaryKey
    @NonNull
    private String alternativeName;

    public SamePortion(@NonNull String alternativeName) {
        this.alternativeName = alternativeName;
    }

    @NonNull
    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(@NonNull String alternativeName) {
        this.alternativeName = alternativeName;
    }
}
