package project.justtravel.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDao {
    @Insert()
    void insert(Trip trip);

    @Query("SELECT * " +
            "FROM trip_table")
    LiveData<List<Trip>> getAllTrips();
}
