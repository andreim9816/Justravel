package project.justtravel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wajahatkarim3.roomexplorer.RoomExplorer;

import java.net.ContentHandler;
import java.util.List;

import project.justtravel.R;
import project.justtravel.data.Trip;
import project.justtravel.data.TripListAdapter;
import project.justtravel.data.TripRoomDatabase;
import project.justtravel.viewmodel.TripViewModel;

public class TripsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TripViewModel tripViewModel;

    public TripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get a new or existing ViewModel from the ViewModelProvider.
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // set layout
        recyclerView = view.findViewById(R.id.tripsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        // set adapter
        final TripListAdapter adapter = new TripListAdapter(container.getContext());
        recyclerView.setAdapter(adapter);


        // Add an observer on the LiveData returned by getAllTrips() function
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        tripViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                // update the cached copy
                adapter.setTrips(trips);
            }
        });

//        RoomExplorer.show(container.getContext(), TripRoomDatabase.class, "trip_database");

        return view;
    }
}