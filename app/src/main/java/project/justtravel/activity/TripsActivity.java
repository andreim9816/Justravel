package project.justtravel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import project.justtravel.R;
import project.justtravel.fragments.AddTripFragment;
import project.justtravel.fragments.TripsFragment;

public class TripsActivity extends AppCompatActivity {

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
}