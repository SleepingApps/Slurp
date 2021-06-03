package it.sofk.slurp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import it.sofk.slurp.enumeration.MacroGroup;

@Entity
public class FoodGroup {

    @PrimaryKey
    @NonNull
    private String name;

    private MacroGroup macroGroup;

    public FoodGroup(@NonNull String name, MacroGroup macroGroup) {
        this.name = name;
        this.macroGroup = macroGroup;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public MacroGroup getMacroGroup() {
        return macroGroup;
    }

    public void setMacroGroup(MacroGroup macroGroup) {
        this.macroGroup = macroGroup;
    }
}
