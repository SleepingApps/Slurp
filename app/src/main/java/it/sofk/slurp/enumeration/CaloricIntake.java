package it.sofk.slurp.enumeration;

public enum CaloricIntake {
    CAL_1500(1),
    CAL_2000(2),
    CAL_2500(3);

    public final int i;
    CaloricIntake(int i) {
        this.i = i;
    }

    static public CaloricIntake getFromIndex(int i){
        switch (i){
            case 1:
                return CAL_1500;
            case 3:
                return CAL_2500;
            default:
                return CAL_2000;
        }
    }
}
