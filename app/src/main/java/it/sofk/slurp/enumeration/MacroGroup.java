package it.sofk.slurp.enumeration;

public enum MacroGroup {
    CEREALI_DERIVATI_TUBERI(1),
    FRUTTA_VERDURA(2),
    CARNE_PESCE_UOVA_LEGUMI(3),
    LATTE_E_DERIVATI(4),
    GRASSI_DA_CONDIMENTO(5),
    FRUTTA_SECCA(6),
    ACQUA(7);

    public final int i;
    MacroGroup(int i) {
        this.i = i;
    }
}
