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
import it.sofk.slurp.database.dao.HistoryDao;
import it.sofk.slurp.database.dao.UserDao;
import it.sofk.slurp.database.entity.Examples;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.Portion;
import it.sofk.slurp.database.entity.SamePortion;
import it.sofk.slurp.database.entity.User;
import it.sofk.slurp.database.entity.Week;
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
        User.class,
        Week.class
}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    public abstract FoodDao foodDao();
    public abstract UserDao userDao();
    public abstract HistoryDao historyDao();

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

                List<Examples> examplesList = new ArrayList<>();

                examplesList.add(new Examples("Pane", "1 panino piccolo"));
                examplesList.add(new Examples("Pane", "½ ciabattina/francesino/ ferrarese"));
                examplesList.add(new Examples("Pane", "1 rosetta piccola"));
                examplesList.add(new Examples("Pane", "1 fetta media di pagnotta/filone"));
                examplesList.add(new Examples("Pane", "1/5 baguette"));

                examplesList.add(new Examples("Pasta, riso, mais, farro, orzo, ecc.", "4 cucchiai di riso/farro/orzo"));
                examplesList.add(new Examples("Pasta, riso, mais, farro, orzo, ecc.", "6-8 cucchiai di pastina"));

                examplesList.add(new Examples("Sostituti del pane", "3-4 fette biscottate"));
                examplesList.add(new Examples("Sostituti del pane", "1 pacchetto di cracker"));

                examplesList.add(new Examples("Prodotti da forno dolci", "1 brioche"));
                examplesList.add(new Examples("Prodotti da forno dolci", "1 croissant"));
                examplesList.add(new Examples("Prodotti da forno dolci", "1 cornetto"));
                examplesList.add(new Examples("Prodotti da forno dolci", "1 fetta di ciambellone"));
                examplesList.add(new Examples("Prodotti da forno dolci", "1 fetta di crostata"));
                examplesList.add(new Examples("Prodotti da forno dolci", "2-3 biscotti frollini"));
                examplesList.add(new Examples("Prodotti da forno dolci", "4-5 biscotti secchi"));

                examplesList.add(new Examples("Cereali per la prima colazione", "6-8 cucchiai rasi di fiocchi di mais semplici"));
                examplesList.add(new Examples("Cereali per la prima colazione", "5-6 cucchiai rasi di altri cereali dolcificati"));
                examplesList.add(new Examples("Cereali per la prima colazione", "3 cucchiai rasi di muesli o altri aggregati"));

                examplesList.add(new Examples("Patate", "2 patate piccole"));

                examplesList.add(new Examples("Frutta fresca", "1 frutto medio (mela, pera, arancia, ecc.)"));
                examplesList.add(new Examples("Frutta fresca", "2 frutti piccoli (albicocche, susine, mandarini, ecc.)"));
                examplesList.add(new Examples("Frutta fresca", "150g di frutta pronta al consumo"));

                examplesList.add(new Examples("Frutta essiccata/disidratata non zuccherata", "2 cucchiai rasi di uvetta"));
                examplesList.add(new Examples("Frutta essiccata/disidratata non zuccherata", "2 prugne secche"));

                examplesList.add(new Examples("Verdure fresche", "2-3 pomodori"));
                examplesList.add(new Examples("Verdure fresche", "3-4 carote"));
                examplesList.add(new Examples("Verdure fresche", "1 peperone"));
                examplesList.add(new Examples("Verdure fresche", "1 finocchio"));
                examplesList.add(new Examples("Verdure fresche", "2 carciofi"));
                examplesList.add(new Examples("Verdure fresche", "2-3 zucchine"));
                examplesList.add(new Examples("Verdure fresche", "7-10 ravanelli"));
                examplesList.add(new Examples("Verdure fresche", "1-2 cipolle"));
                examplesList.add(new Examples("Verdure fresche", "½ piatto di spinaci o bieta"));
                examplesList.add(new Examples("Verdure fresche", "½ piatto di broccoli"));
                examplesList.add(new Examples("Verdure fresche", "½ piatto di cavolfiori"));
                examplesList.add(new Examples("Verdure fresche", "½ piatto di melanzane"));

                examplesList.add(new Examples("Insalate a foglia", "1 scodella o ciotola grande (da 500ml)"));

                examplesList.add(new Examples("Carne rossa", "1 fettina"));
                examplesList.add(new Examples("Carne rossa", "1 svizzera (hamburger)"));
                examplesList.add(new Examples("Carne rossa", "4-5 pezzi di spezzatino"));

                examplesList.add(new Examples("Carne bianca", "1 fetta di petto di pollo o tacchino"));
                examplesList.add(new Examples("Carne bianca", "1 piccola coscia di pollo"));

                examplesList.add(new Examples("Pesce", "1 piccolo pesce"));
                examplesList.add(new Examples("Pesce", "1 filetto medio"));
                examplesList.add(new Examples("Pesce", "3 gamberoni"));
                examplesList.add(new Examples("Pesce", "20 gamberetti"));
                examplesList.add(new Examples("Pesce", "25 cozze"));

                examplesList.add(new Examples("Pesce conservato", "1 scatoletta piccola di tonno o sgombro sott’olio o in salamoia"));
                examplesList.add(new Examples("Pesce conservato", "4-5 fette sottili di salmone affumicato"));
                examplesList.add(new Examples("Pesce conservato", "½ filetto baccalà"));

                examplesList.add(new Examples("Uova", "1 uovo medio"));

                examplesList.add(new Examples("Legumi freschi, surgelati, ammollati o in scatola", "mezzo piatto"));

                examplesList.add(new Examples("Legumi secchi", "3-4 cucchiai medi"));

                examplesList.add(new Examples("Latte", "1 bicchiere piccolo"));
                examplesList.add(new Examples("Latte", "½ tazza media o una tazza da cappuccino"));

                examplesList.add(new Examples("Yogurt", "1 vasetto"));

                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "1 bocconcino piccolo di mozzarella"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Ricotta"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Stracchino"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Provola"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Camembert"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Feta"));
                examplesList.add(new Examples("Formaggi fino al 25% di grassi", "Caciottina fresca"));

                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Gorgonzola"));
                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Caciotta"));
                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Groviera"));
                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Parmigiano"));
                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Caprini"));
                examplesList.add(new Examples("Formaggi con più del 25% di grassi", "Pecorini"));

                examplesList.add(new Examples("Olio di oliva", "1 cucchiaio"));

                examplesList.add(new Examples("Oli vegetali", "Mais"));
                examplesList.add(new Examples("Oli vegetali", "Arachidi"));
                examplesList.add(new Examples("Oli vegetali", "Girasole"));

                examplesList.add(new Examples("Burro", "½ noce"));
                examplesList.add(new Examples("Burro", "1 confezione alberghiera"));

                examplesList.add(new Examples("Grassi di origine animale", "Lardo"));
                examplesList.add(new Examples("Grassi di origine animale", "Strutto"));
                examplesList.add(new Examples("Grassi di origine animale", "Sugna"));
                examplesList.add(new Examples("Grassi di origine animale", "Panna"));

                examplesList.add(new Examples("Grassi di origine vegetale", "Margarina"));
                examplesList.add(new Examples("Grassi di origine vegetale", "Alternative vegetali alla panna"));

                examplesList.add(new Examples("Frutta secca a guscio", "7-8 noci"));
                examplesList.add(new Examples("Frutta secca a guscio", "15-20 mandorle/ nocciole"));
                examplesList.add(new Examples("Frutta secca a guscio", "3 cucchiai rasi di arachidi o pinoli o semi di girasole"));

                examplesList.add(new Examples("Acqua", "1 bicchiere medio"));

                FoodDao dao = INSTANCE.foodDao();

                for(FoodGroup foodGroup : foodGroupList) dao.insert(foodGroup);
                for(SamePortion samePortion : samePortionList) dao.insert(samePortion);
                for(FoodType foodType : foodTypeList) dao.insert(foodType);
                for(FoodType foodType : foodTypeList) dao.insert(new FoodInstance(foodType.getName(), LocalDate.now()));
                for(Portion portion : portions) dao.insert(portion);
                for(Examples example : examplesList) dao.insert(example);


                UserDao userDao = INSTANCE.userDao();

                User user = new User(CaloricIntake.CAL_2000);
                user.setHeight(0d);
                user.setWeight(0d);
                userDao.insert(user);
            });


        }
    };

}
