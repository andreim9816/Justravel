package project.justtravel.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import project.justtravel.data.ApiAndDao.TripDao;
import project.justtravel.data.model.Trip;
import project.justtravel.utils.DateConverter;

@Database(entities = {Trip.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class TripRoomDatabase extends RoomDatabase {
    public abstract TripDao tripDao();

    private static volatile TripRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }

    public static TripRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripRoomDatabase.class, "trip_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            TripDao dao = INSTANCE.tripDao();
                                            dao.insert(new Trip(1, "numeExcursie1", "numeDestinatie1", 0, 3000, new Date(), new Date(), 5));
                                            dao.insert(new Trip(2, "numeExcursie2", "numeDestinatie2", 5, 550, new Date(), new Date(), (float) 3.5));
                                        }
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


