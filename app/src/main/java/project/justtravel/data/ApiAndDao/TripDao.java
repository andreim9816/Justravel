package project.justtravel.data.ApiAndDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import project.justtravel.data.model.Trip;

@Dao
public interface TripDao {
    @Insert()
    void insert(Trip trip);

    @Query("SELECT * " +
            "FROM trip_table")
    LiveData<List<Trip>> getAllTrips();

    @Update()
    void update(Trip trip);
}
