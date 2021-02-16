package project.justtravel.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import project.justtravel.data.model.Trip;
import project.justtravel.data.repository.TripRepository;


public class TripViewModel extends AndroidViewModel {
    private TripRepository repository;
    private LiveData<List<Trip>> allTrips;

    public TripViewModel(Application application) {
        super(application);
        repository = new TripRepository(application);
        allTrips = repository.getAllTrips();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }

    public void insert(Trip trip) {
        repository.insert(trip);
    }

    public void update(Trip trip) {
        repository.update(trip);
    }
}
