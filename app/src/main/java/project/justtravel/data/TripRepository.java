package project.justtravel.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TripRepository {
    private TripDao tripDao;
    private LiveData<List<Trip>> allTrips;

    public TripRepository(Application application) {
        TripRoomDatabase db = TripRoomDatabase.getDatabase(application);
        tripDao = db.tripDao();
        allTrips = tripDao.getAllTrips();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }

    public void insert(Trip trip) {
        TripRoomDatabase.databaseWriteExecutor.execute(() -> tripDao.insert(trip));
    }

    public void update(Trip trip) {
        TripRoomDatabase.databaseWriteExecutor.execute(() -> tripDao.update(trip));
        Log.d("Trip repository", "Update: " + trip);
    }
}
