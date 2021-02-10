package project.justtravel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.justtravel.R;
import project.justtravel.fragments.AddTripFragment;

public class MainActivity extends AppCompatActivity {
    ImageView logoImageView;
    TextView descriptionTextView;
    private final static int TIME_OUT = 4000; // After 4 seconds, the next activity is launched

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize UI items
        initUiElements();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TripsActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }

    private void initUiElements() {
        logoImageView = findViewById(R.id.logoImageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
    }
}