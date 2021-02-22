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
import project.justtravel.fragments.AddTripFragment;
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
                                            dao.insert(new Trip(1, "English adventure", "Manchester", AddTripFragment.CITY_BREAK_TYPE, 3000, new Date(), new Date(), 5f));
                                            dao.insert(new Trip(2, "French trip", "Paris", AddTripFragment.CITY_BREAK_TYPE, 1550, new Date(), new Date(), 3.5f));
                                            dao.insert(new Trip(3, "Ski trip", "Sinaia", AddTripFragment.MOUNTAINS_TYPE, 3550, new Date(), new Date(), 4.5f));
                                            dao.insert(new Trip(4, "Culture travel", "London", AddTripFragment.CITY_BREAK_TYPE, 1950, new Date(), new Date(), 3.5f));
                                            dao.insert(new Trip(5, "Relax allDay", "Hawaii", AddTripFragment.SEASIDE_TYPE, 4550, new Date(), new Date(), 5f));
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


