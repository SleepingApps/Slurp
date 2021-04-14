package it.sofk.slurp.enumeration;

public enum CaloricIntake {
    CAL_1500(1),
    CAL_2000(2),
    CAL_2500(3);

    public final int i;
    CaloricIntake(int i) {
        this.i = i;
    }
}
