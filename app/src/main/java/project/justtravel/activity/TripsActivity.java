package project.justtravel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import project.justtravel.R;
import project.justtravel.data.Trip;
import project.justtravel.fragments.EditTripFragment;
import project.justtravel.fragments.TripsFragment;

public class TripsActivity extends AppCompatActivity implements TripsFragment.OnTripsFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        // display fragment
        displayFragment(new TripsFragment());
    }

    /* Opens new fragment */
    public void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tripsListFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void sendTripObjectToEditFragment(Trip trip) {
        FragmentManager fm = getSupportFragmentManager();
        Log.d("Trips activity", "NEXT: ");
        for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
            Log.d("Trips activity", "Found fragment: " + fm.getBackStackEntryAt(entry).getId());
        }
        // sends the object further to the EditTripFragment
        EditTripFragment editTripFragment = (EditTripFragment) getSupportFragmentManager().findFragmentByTag(EditTripFragment.getTAG());
        System.out.println(editTripFragment);
        editTripFragment.getSharedTrip(trip);
    }
}