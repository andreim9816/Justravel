package project.justtravel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.justtravel.R;
import project.justtravel.data.Trip;
import project.justtravel.data.TripListAdapter;
import project.justtravel.utils.ClickInterface;
import project.justtravel.viewmodel.TripViewModel;

public class TripsFragment extends Fragment implements ClickInterface {
    private RecyclerView recyclerView;
    private TripViewModel tripViewModel;
    private FloatingActionButton fabButton;
    private TripListAdapter adapter;

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

        // onClick on FAB -> opens new Fragment
        fabButton = view.findViewById(R.id.fab);
        fabButton.setOnClickListener(v -> displayFragment(new AddTripFragment()));

        // set layout
        recyclerView = view.findViewById(R.id.tripsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        // set adapter
        adapter = new TripListAdapter(container.getContext(), this);
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

    /* Opens new fragment */
    private void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction(); //requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tripsListFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        Log.v("TripsFragment", "simple click " + trip.getName() + " " + trip.getDestination());
    }

    @Override
    public void onLongClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        Log.v("TripsFragment", "on LONGGGGG click " + trip.getName() + " " + trip.getDestination());
        displayFragment(new EditTripFragment());
    }
}