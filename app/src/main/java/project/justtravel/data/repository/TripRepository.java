package project.justtravel.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import project.justtravel.data.ApiAndDao.TripDao;
import project.justtravel.data.TripRoomDatabase;
import project.justtravel.data.model.Trip;

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
        TripRoomDatabase.getDatabaseWriteExecutor().execute(() -> tripDao.insert(trip));
    }

    public void update(Trip trip) {
        TripRoomDatabase.getDatabaseWriteExecutor().execute(() -> tripDao.update(trip));
        Log.d("Trip repository", "Update: " + trip);
    }
}
