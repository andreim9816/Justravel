package project.justtravel.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import project.justtravel.R;
import project.justtravel.data.model.Trip;
import project.justtravel.data.TripListAdapter;
import project.justtravel.utils.ClickInterface;
import project.justtravel.viewmodel.TripViewModel;


public class HomeFragment extends Fragment implements ClickInterface {
    public static final String TRIP_OBJECT_PASSED = "TRIP_OBJECT_PASSED";
    private RecyclerView recyclerView;
    private NavController navController;
    private FloatingActionButton fabButton;
    private TripListAdapter adapter;
    private static final String TAG = "TripsFragment";
    private TripViewModel tripViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        // Get a new or existing ViewModel from the ViewModelProvider.
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // onClick on FAB -> opens new Fragment
        fabButton = view.findViewById(R.id.fab);
        fabButton.setOnClickListener(v -> {
            nextFragment(R.id.action_nav_home_to_addTripFragment, null);
        });

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TRIP_OBJECT_PASSED, trip);
        nextFragment(R.id.action_nav_home_to_readTripFragment, bundle);
    }

    @Override
    public void onLongClick(int position) {
        Trip trip = adapter.getTrips().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TRIP_OBJECT_PASSED, trip);
        nextFragment(R.id.action_nav_home_to_editTripFragment, bundle);
    }

    public void nextFragment(int resId, Bundle bundle) {
        navController.navigate(resId, bundle);
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