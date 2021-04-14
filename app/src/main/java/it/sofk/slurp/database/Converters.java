package it.sofk.slurp.database;

import androidx.room.TypeConverter;

import java.util.Date;

import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.enumeration.MacroGroup;

public class Converters {
    @TypeConverter
    public static Date dateFromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Frequency frequencyfromInteger(Integer value){
        switch (value){
            case 1: return Frequency.DAILY;
            case 2: return Frequency.WEEKLY;
            case 3: return Frequency.OCCASIONALLY;
            default: return null;
        }
    }

    @TypeConverter
    public static Integer frequencyToInteger(Frequency value){
        switch (value){
            case DAILY:         return 1;
            case WEEKLY:        return 2;
            case OCCASIONALLY:  return 3;
            default:            return null;
        }
    }

    @TypeConverter
    public static MacroGroup macroGroupFromInteger(Integer value){
        switch (value){
            case 1: return MacroGroup.CEREALI_DERIVATI_TUBERI;
            case 2: return MacroGroup.FRUTTA_VERDURA;
            case 3: return MacroGroup.CARNE_PESCE_UOVA_LEGUMI;
            case 4: return MacroGroup.LATTE_E_DERIVATI;
            case 5: return MacroGroup.GRASSI_DA_CONDIMENTO;
            case 6: return MacroGroup.FRUTTA_SECCA;
            case 7: return MacroGroup.ACQUA;
            default: return null;
        }
    }

    @TypeConverter
    public static Integer macroGroupToInteger(MacroGroup value){
        switch (value){
            case CEREALI_DERIVATI_TUBERI:   return 1;
            case FRUTTA_VERDURA:            return 2;
            case CARNE_PESCE_UOVA_LEGUMI:   return 3;
            case LATTE_E_DERIVATI:          return 4;
            case GRASSI_DA_CONDIMENTO:      return 5;
            case FRUTTA_SECCA:              return 6;
            case ACQUA:                     return 7;
            default:                        return null;
        }
    }

    @TypeConverter
    public static CaloricIntake caloricIntakefromInteger(Integer value){
        switch (value){
            case 1: return CaloricIntake.CAL_1500;
            case 2: return CaloricIntake.CAL_2000;
            case 3: return CaloricIntake.CAL_2500;
            default: return null;
        }
    }

    @TypeConverter
    public static Integer caloricIntakeToInteger(CaloricIntake value){
        switch (value){
            case CAL_1500: return 1;
            case CAL_2000: return 2;
            case CAL_2500: return 3;
            default: return null;
        }
    }
}
