package it.sofk.slurp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.sofk.slurp.database.dao.FoodDao;
import it.sofk.slurp.database.entity.Examples;
import it.sofk.slurp.database.entity.FoodGroup;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.database.entity.Portion;
import it.sofk.slurp.enumeration.Frequency;

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
                FoodDao dao = INSTANCE.foodDao();

                FoodType foodType = new FoodType("Pasta");
                foodType.setFrequency(Frequency.DAILY);
                dao.insert(foodType);

                FoodInstance food = new FoodInstance("Pasta", new Date());
                food.setPortionConsumed(0);
                dao.insert(food);


            });
        }
    };

}
