package it.sofk.slurp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.entity.Examples;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.Portion;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.enumeration.MacroGroup;

@androidx.room.Database(entities = {
        FoodInstance.class,
        FoodType.class,
        FoodGroup.class,
        Examples.class,
        Portion.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    public abstract FoodDao foodDao();

    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "FoodDatabase").addCallback(callback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                List<FoodGroup> foodGroupList = new ArrayList<>();

                foodGroupList.add(new FoodGroup("Cereali e derivati",           MacroGroup.CEREALI_DERIVATI_TUBERI));
                foodGroupList.add(new FoodGroup("Tuberi",                       MacroGroup.CEREALI_DERIVATI_TUBERI));
                foodGroupList.add(new FoodGroup("Frutta",                       MacroGroup.FRUTTA_VERDURA));
                foodGroupList.add(new FoodGroup("Verdura",                      MacroGroup.FRUTTA_VERDURA));
                foodGroupList.add(new FoodGroup("Carne",                        MacroGroup.CARNE_PESCE_UOVA_LEGUMI));
                foodGroupList.add(new FoodGroup("Pesce e prodotti della pesca", MacroGroup.CARNE_PESCE_UOVA_LEGUMI));
                foodGroupList.add(new FoodGroup("Uova",                         MacroGroup.CARNE_PESCE_UOVA_LEGUMI));
                foodGroupList.add(new FoodGroup("Legumi",                       MacroGroup.CARNE_PESCE_UOVA_LEGUMI));
                foodGroupList.add(new FoodGroup("Latte e derivati",             MacroGroup.LATTE_E_DERIVATI));
                foodGroupList.add(new FoodGroup("Oli e Grassi",                 MacroGroup.GRASSI_DA_CONDIMENTO));
                foodGroupList.add(new FoodGroup("Frutta secca",                 MacroGroup.FRUTTA_SECCA));
                foodGroupList.add(new FoodGroup("Acqua",                        MacroGroup.ACQUA));

                List<FoodType> foodTypeList = new ArrayList<>();

                foodTypeList.add(new FoodType("Pane",                                               Frequency.DAILY,    50,     "Cereali e derivati"));
                foodTypeList.add(new FoodType("Pasta, riso, mais, farro, orzo, ecc.",               Frequency.DAILY,    80,     "Cereali e derivati"));
                foodTypeList.add(new FoodType("Sostituti del pane",                                 Frequency.WEEKLY,   30,     "Cereali e derivati"));
                foodTypeList.add(new FoodType("Prodotti da forno dolci",                            Frequency.WEEKLY,   50,     "Cereali e derivati"));
                foodTypeList.add(new FoodType("Cereali per la prima colazione",                     Frequency.WEEKLY,   30,     "Cereali e derivati"));
                foodTypeList.add(new FoodType("Tuberi",                                             Frequency.WEEKLY,   200,    "Tuberi"));
                foodTypeList.add(new FoodType("Frutta fresca",                                      Frequency.DAILY,    150,    "Frutta"));
                foodTypeList.add(new FoodType("Frutta essiccata/disidratata non zuccherata",        Frequency.DAILY,    30,     "Frutta"));
                foodTypeList.add(new FoodType("Verdure fresca",                                     Frequency.DAILY,    200,    "Verdura"));
                foodTypeList.add(new FoodType("Insalate a foglia",                                  Frequency.DAILY,    80,     "Verdura"));
                foodTypeList.add(new FoodType("Carne rossa",                                        Frequency.WEEKLY,   100,    "Carne"));
                foodTypeList.add(new FoodType("Carne bianca",                                       Frequency.WEEKLY,   100,    "Carne"));
                foodTypeList.add(new FoodType("Pesce",                                              Frequency.WEEKLY,   150,    "Pesce e prodotti della pesca"));
                foodTypeList.add(new FoodType("Pesce conservato",                                   Frequency.WEEKLY,   50,     "Pesce e prodotti della pesca"));
                foodTypeList.add(new FoodType("Uova",                                               Frequency.WEEKLY,   50,     "Uova"));
                foodTypeList.add(new FoodType("Legumi freschi, surgelati, ammollati o in scatola",  Frequency.WEEKLY,   150,    "Legumi"));
                foodTypeList.add(new FoodType("Legumi secchi",                                      Frequency.WEEKLY,   50,     "Legumi"));
                foodTypeList.add(new FoodType("Latte",                                              Frequency.DAILY,    125,    "Latte e derivati"));
                foodTypeList.add(new FoodType("Yogurt",                                             Frequency.DAILY,    125,    "Latte e derivati"));
                foodTypeList.add(new FoodType("Formaggi fino al 25% di grassi",                     Frequency.WEEKLY,   125,    "Latte e derivati"));
                foodTypeList.add(new FoodType("Formaggi con più del 25% di grassi",                 Frequency.WEEKLY,   125,    "Latte e derivati"));
                foodTypeList.add(new FoodType("Olio di oliva",                                      Frequency.DAILY,    10,     "Oli e Grassi"));
                foodTypeList.add(new FoodType("Oli vegetali",                                       Frequency.DAILY,    10,     "Oli e Grassi"));
                foodTypeList.add(new FoodType("Burro",                                              Frequency.DAILY,    10,     "Oli e Grassi"));
                foodTypeList.add(new FoodType("Grassi di origine animale",                          Frequency.DAILY,    10,     "Oli e Grassi"));
                foodTypeList.add(new FoodType("Grassi di origine vegetale",                         Frequency.DAILY,    10,     "Oli e Grassi"));
                foodTypeList.add(new FoodType("Frutta secca a guscio",                              Frequency.WEEKLY,   30,     "Frutta secca"));
                foodTypeList.add(new FoodType("Semi oleaosi",                                       Frequency.WEEKLY,   30,     "Frutta secca"));
                foodTypeList.add(new FoodType("Acqua",                                              Frequency.DAILY,    200,    "Frutta secca"));


                FoodDao dao = INSTANCE.foodDao();

                for(FoodGroup foodGroup : foodGroupList) dao.insert(foodGroup);
                for(FoodType foodType : foodTypeList) dao.insert(foodType);
                for(FoodType foodType : foodTypeList) dao.insert(new FoodInstance(foodType.getName(), new Date()));
            });
        }
    };

}
