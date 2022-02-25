package it.sofk.slurp.database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.dao.UserDao;
import it.sofk.slurp.database.entity.Examples;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.Portion;
import it.sofk.slurp.database.entity.SamePortion;
import it.sofk.slurp.database.entity.User;
import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.enumeration.MacroGroup;

@androidx.room.Database(entities = {
        FoodInstance.class,
        FoodType.class,
        FoodGroup.class,
        Examples.class,
        Portion.class,
        SamePortion.class,
        User.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    public abstract FoodDao foodDao();
    public abstract UserDao userDao();

    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "FoodDatabase.db")
                            .addCallback(callback)
                            //.createFromAsset("FoodDatabase.db")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
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
                foodTypeList.add(new FoodType("Patate",                                             Frequency.WEEKLY,   200,    "Tuberi"));
                foodTypeList.add(new FoodType("Frutta fresca",                                      Frequency.DAILY,    150,    "Frutta"));
                foodTypeList.add(new FoodType("Frutta essiccata/disidratata non zuccherata",        Frequency.DAILY,    30,     "Frutta"));
                foodTypeList.add(new FoodType("Verdure fresche",                                     Frequency.DAILY,    200,    "Verdura"));
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
                foodTypeList.add(new FoodType("Acqua",                                              Frequency.DAILY,    200,    "Acqua"));

                List<SamePortion> samePortionList = new ArrayList<>();

                //boh devi risolvere un errore qua sotto, ho cambiato un pò di cose l'altra volta per le cose della query sulle porzioni. pls ricordati

                samePortionList.add(new SamePortion(foodTypeList.get(0).getName()));
                foodTypeList.get(0).setSamePortion(foodTypeList.get(0).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(1).getName()));
                foodTypeList.get(1).setSamePortion(foodTypeList.get(1).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(2).getName()));
                foodTypeList.get(2).setSamePortion(foodTypeList.get(2).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(3).getName()));
                foodTypeList.get(3).setSamePortion(foodTypeList.get(3).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(4).getName()));
                foodTypeList.get(4).setSamePortion(foodTypeList.get(4).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(5).getName()));
                foodTypeList.get(5).setSamePortion(foodTypeList.get(5).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(10).getName()));
                foodTypeList.get(10).setSamePortion(foodTypeList.get(10).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(11).getName()));
                foodTypeList.get(11).setSamePortion(foodTypeList.get(11).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(12).getName()));
                foodTypeList.get(12).setSamePortion(foodTypeList.get(12).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(13).getName()));
                foodTypeList.get(13).setSamePortion(foodTypeList.get(13).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(14).getName()));
                foodTypeList.get(14).setSamePortion(foodTypeList.get(14).getName());
                samePortionList.add(new SamePortion(foodTypeList.get(28).getName()));
                foodTypeList.get(28).setSamePortion(foodTypeList.get(28).getName());



                samePortionList.add(new SamePortion("Frutta"));
                foodTypeList.get(6).setSamePortion("Frutta");
                foodTypeList.get(7).setSamePortion("Frutta");
                samePortionList.add(new SamePortion("Verdura"));
                foodTypeList.get(8).setSamePortion("Verdura");
                foodTypeList.get(9).setSamePortion("Verdura");
                samePortionList.add(new SamePortion("Legumi"));
                foodTypeList.get(15).setSamePortion("Legumi");
                foodTypeList.get(16).setSamePortion("Legumi");
                samePortionList.add(new SamePortion("Latte e Yogurt"));
                foodTypeList.get(17).setSamePortion("Latte e Yogurt");
                foodTypeList.get(18).setSamePortion("Latte e Yogurt");
                samePortionList.add(new SamePortion("Formaggi"));
                foodTypeList.get(19).setSamePortion("Formaggi");
                foodTypeList.get(20).setSamePortion("Formaggi");
                samePortionList.add(new SamePortion("Grassi"));
                foodTypeList.get(21).setSamePortion("Grassi");
                foodTypeList.get(22).setSamePortion("Grassi");
                foodTypeList.get(23).setSamePortion("Grassi");
                foodTypeList.get(24).setSamePortion("Grassi");
                foodTypeList.get(25).setSamePortion("Grassi");
                samePortionList.add(new SamePortion("Frutta secca"));
                foodTypeList.get(26).setSamePortion("Frutta secca");
                foodTypeList.get(27).setSamePortion("Frutta secca");

                List<Portion> portions = new ArrayList<>();

                portions.add(new Portion("Pane", CaloricIntake.CAL_1500, 2.5));
                portions.add(new Portion("Pane", CaloricIntake.CAL_2000, 3.5));
                portions.add(new Portion("Pane", CaloricIntake.CAL_2500, 4.5));

                portions.add(new Portion("Pasta, riso, mais, farro, orzo, ecc.", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Pasta, riso, mais, farro, orzo, ecc.", CaloricIntake.CAL_2000, 1.5));
                portions.add(new Portion("Pasta, riso, mais, farro, orzo, ecc.", CaloricIntake.CAL_2500, 1.5));

                portions.add(new Portion("Sostituti del pane", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Sostituti del pane", CaloricIntake.CAL_2000, 1));
                portions.add(new Portion("Sostituti del pane", CaloricIntake.CAL_2500, 1));

                portions.add(new Portion("Prodotti da forno dolci", CaloricIntake.CAL_1500, 0.5));
                portions.add(new Portion("Prodotti da forno dolci", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Prodotti da forno dolci", CaloricIntake.CAL_2500, 2));

                portions.add(new Portion("Cereali per la prima colazione", CaloricIntake.CAL_1500, 0.5));
                portions.add(new Portion("Cereali per la prima colazione", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Cereali per la prima colazione", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Patate", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Patate", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Patate", CaloricIntake.CAL_2500, 2));

                portions.add(new Portion("Frutta fresca", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Frutta fresca", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Frutta fresca", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Frutta essiccata/disidratata non zuccherata", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Frutta essiccata/disidratata non zuccherata", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Frutta essiccata/disidratata non zuccherata", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Verdure fresche", CaloricIntake.CAL_1500, 2.5));
                portions.add(new Portion("Verdure fresche", CaloricIntake.CAL_2000, 2.5));
                portions.add(new Portion("Verdure fresche", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Insalate a foglia", CaloricIntake.CAL_1500, 2.5));
                portions.add(new Portion("Insalate a foglia", CaloricIntake.CAL_2000, 2.5));
                portions.add(new Portion("Insalate a foglia", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Carne rossa", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Carne rossa", CaloricIntake.CAL_2000, 1));
                portions.add(new Portion("Carne rossa", CaloricIntake.CAL_2500, 1));

                portions.add(new Portion("Carne bianca", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Carne bianca", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Carne bianca", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Pesce", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Pesce", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Pesce", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Pesce conservato", CaloricIntake.CAL_1500, 0));
                portions.add(new Portion("Pesce conservato", CaloricIntake.CAL_2000, 1));
                portions.add(new Portion("Pesce conservato", CaloricIntake.CAL_2500, 1));

                portions.add(new Portion("Uova", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Uova", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Uova", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Legumi freschi, surgelati, ammollati o in scatola", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Legumi freschi, surgelati, ammollati o in scatola", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Legumi freschi, surgelati, ammollati o in scatola", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Legumi secchi", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Legumi secchi", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Legumi secchi", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Latte", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Latte", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Latte", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Yogurt", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Yogurt", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Yogurt", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Formaggi fino al 25% di grassi", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Formaggi fino al 25% di grassi", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Formaggi fino al 25% di grassi", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Formaggi con più del 25% di grassi", CaloricIntake.CAL_1500, 3));
                portions.add(new Portion("Formaggi con più del 25% di grassi", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Formaggi con più del 25% di grassi", CaloricIntake.CAL_2500, 3));

                portions.add(new Portion("Olio di oliva", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Olio di oliva", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Olio di oliva", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Oli vegetali", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Oli vegetali", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Oli vegetali", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Burro", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Burro", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Burro", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Grassi di origine animale", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Grassi di origine animale", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Grassi di origine animale", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Grassi di origine vegetale", CaloricIntake.CAL_1500, 2));
                portions.add(new Portion("Grassi di origine vegetale", CaloricIntake.CAL_2000, 3));
                portions.add(new Portion("Grassi di origine vegetale", CaloricIntake.CAL_2500, 4));

                portions.add(new Portion("Frutta secca a guscio", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Frutta secca a guscio", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Frutta secca a guscio", CaloricIntake.CAL_2500, 2.5));

                portions.add(new Portion("Semi oleaosi", CaloricIntake.CAL_1500, 1));
                portions.add(new Portion("Semi oleaosi", CaloricIntake.CAL_2000, 2));
                portions.add(new Portion("Semi oleaosi", CaloricIntake.CAL_2500, 2.5));

                portions.add(new Portion("Acqua", CaloricIntake.CAL_1500, 6));
                portions.add(new Portion("Acqua", CaloricIntake.CAL_2000, 8));
                portions.add(new Portion("Acqua", CaloricIntake.CAL_2500, 10));


                FoodDao dao = INSTANCE.foodDao();

                for(FoodGroup foodGroup : foodGroupList) dao.insert(foodGroup);
                for(SamePortion samePortion : samePortionList) dao.insert(samePortion);
                for(FoodType foodType : foodTypeList) dao.insert(foodType);
                for(FoodType foodType : foodTypeList) dao.insert(new FoodInstance(foodType.getName(), LocalDate.now()));
                for(Portion portion : portions) dao.insert(portion);

                UserDao userDao = INSTANCE.userDao();

                User user = new User(CaloricIntake.CAL_2000);
                user.setHeight(0d);
                user.setWeight(0d);
                userDao.insert(user);
            });


        }
    };

}
