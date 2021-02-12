package project.justtravel.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.justtravel.R;
import project.justtravel.data.Trip;
import project.justtravel.data.TripListAdapter;
import project.justtravel.utils.ClickInterface;
import project.justtravel.viewmodel.SharedTripViewModel;
import project.justtravel.viewmodel.TripViewModel;

public class TripsFragment extends Fragment implements ClickInterface {
    private RecyclerView recyclerView;
    private FloatingActionButton fabButton;
    private TripListAdapter adapter;
    private String TAG = "TripsFragment";
    private SharedTripViewModel sharedTripViewModel;
    private TripViewModel tripViewModel;

    private OnTripsFragmentListener mCallback;

    public interface OnTripsFragmentListener {
        public void sendTripObjectToEditFragment(Trip trip);
    }

    public TripsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        // Get a new or existing ViewModel from the ViewModelProvider.
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        sharedTripViewModel = new ViewModelProvider(this).get(SharedTripViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // onClick on FAB -> opens new Fragment
        fabButton = view.findViewById(R.id.fab);
        fabButton.setOnClickListener(v -> displayFragment(new AddTripFragment(), AddTripFragment.getTAG()));

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
                Log.d(TAG, "OBSERVE ON CHANGED");
                adapter.setTrips(trips);
            }
        });

        // Add an observer on the LiveData
//        sharedTripViewModel.getTrip().observe(getViewLifecycleOwner(), new Observer<Trip>() {
//            @Override
//            public void onChanged(Trip trip) {
//                //TODO schimbat noua valoare
//                // ceva de genul adapter.set(trip) si adaug eu o metoda in adapter
//            }
//        });

//        RoomExplorer.show(container.getContext(), TripRoomDatabase.class, "trip_database");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /* Opens new fragment */
    private void displayFragment(Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction(); //requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tripsListFrameLayout, fragment, TAG);
//        fragmentTransaction.addToBackStack(null); // we save the current fragment
        fragmentTransaction.commitNow();
    }

    @Override
    public void onClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        Log.d("TripsFragment", "simple click " + trip.getName() + " " + trip.getDestination());
    }

    @Override
    public void onLongClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        OnTripsFragmentListener listener = (OnTripsFragmentListener) getActivity(); // gets the fragment's activity
        Log.d("TripsFragment", "on LONGGGGG click " + trip.getName() + " " + trip.getDestination());
        displayFragment(new EditTripFragment(), EditTripFragment.getTAG());

//        for (int entry = 0; entry < getActivity().getSupportFragmentManager().getBackStackEntryCount(); entry++) {
//            Log.d("Trips activity", "Found fragment: " + getParentFragmentManager().getBackStackEntryAt(entry).getId());
//        }

        if (listener == null) {
            Toast.makeText(getActivity(), "Cannot edit this trip!", Toast.LENGTH_LONG).show();
        } else {
            listener.sendTripObjectToEditFragment(trip);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "OnAttach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}