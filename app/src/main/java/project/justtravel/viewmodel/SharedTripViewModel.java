package project.justtravel.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import project.justtravel.data.model.Trip;

public class SharedTripViewModel extends ViewModel {
    private MutableLiveData<Trip> sharedTrip;

    public LiveData<Trip> getTrip() {
        return sharedTrip;
    }

    public void setTrip(Trip trip) {
        sharedTrip.setValue(trip);
    }
}
