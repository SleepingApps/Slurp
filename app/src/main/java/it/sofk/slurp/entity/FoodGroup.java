package it.sofk.slurp.entity;

import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.enumeration.MacroGroup;

public class FoodGroup {

    private String name;
    private MacroGroup macroGroup;

    public FoodGroup(String name, MacroGroup macroGroup) {
        this.name = name;
        this.macroGroup = macroGroup;
    }

    public String getName() {
        return name;
    }

    public MacroGroup getMacroGroup() {
        return macroGroup;
    }
}
