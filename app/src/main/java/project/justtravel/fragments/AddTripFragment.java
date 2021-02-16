package project.justtravel.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import project.justtravel.R;
import project.justtravel.data.model.Trip;
import project.justtravel.viewmodel.TripViewModel;

public class AddTripFragment extends Fragment {
    private NavController navController;
    private EditText nameEditText, destinationEditText;
    private RadioGroup radioGroup;
    private RadioButton cityBreakRadioButton, seasideRadioButton, mountainsRadioButton;
    private Slider priceSlider;
    private RatingBar ratingBar;
    private EditText startDateEditText, endDateEditText;
    private Button addTripButton;
    private TripViewModel tripViewModel;

    public static final int CITY_BREAK_TYPE = 0;
    public static final int SEASIDE_TYPE = 1;
    public static final int MOUNTAINS_TYPE = 2;

    public AddTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        // init UI elements
        initUiElements(view);

        // onClick on startDate
        startDateEditText.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view12, year12, monthOfYear, dayOfMonth) -> startDateEditText.setText(transformData(dayOfMonth, monthOfYear, year12)), year, month, day);
            datePickerDialog.show();
        });

        // onClick on startDate
        endDateEditText.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year1, monthOfYear, dayOfMonth) -> endDateEditText.setText(transformData(dayOfMonth, monthOfYear, year1)), year, month, day);
            datePickerDialog.show();
        });

        // onClick addTripButton
        addTripButton.setOnClickListener(v -> {
            boolean error = checkFieldIsEmptySetError(nameEditText, getString(R.string.trip_name_empty))
                    | checkFieldIsEmptySetError(destinationEditText, getString(R.string.trip_destination_empty))
                    | checkFieldIsEmptySetError(startDateEditText, getString(R.string.trip_start_date_empty))
                    | checkFieldIsEmptySetError(endDateEditText, getString(R.string.trip_end_date_empty));

            if (error) {
                Toast.makeText(getActivity(), getString(R.string.toast_fields_empty), Toast.LENGTH_LONG).show();
            } else if (false) {
                //TODO compare if startDate <= endDate
            } else {
                int type = CITY_BREAK_TYPE;

                if (seasideRadioButton.isChecked()) {
                    type = SEASIDE_TYPE;
                } else if (mountainsRadioButton.isChecked()) {
                    type = MOUNTAINS_TYPE;
                }

                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Trip trip = new Trip(0,
                            nameEditText.getText().toString(),
                            destinationEditText.getText().toString(),
                            type,
                            (int) priceSlider.getValue(),
                            simpleDateFormat.parse(startDateEditText.getText().toString()),
                            simpleDateFormat.parse(endDateEditText.getText().toString()),
                            ratingBar.getRating()
                    );

                    tripViewModel.insert(trip);
                    nextFragment(R.id.action_addTripFragment_to_nav_home, null);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public static boolean checkFieldIsEmptySetError(EditText nameEditText, String error) {
        if (nameEditText.getText().toString().isEmpty()) {
            nameEditText.setError(error);
            return true;
        }
        return false;
    }

    /* Function that init UI elements*/
    private void initUiElements(View view) {
        nameEditText = view.findViewById(R.id.editTextTripName);
        destinationEditText = view.findViewById(R.id.editTextTripDestination);
        radioGroup = view.findViewById(R.id.radioGroup);
        cityBreakRadioButton = view.findViewById(R.id.cityBreakRadioButton);
        seasideRadioButton = view.findViewById(R.id.seaSideRadioButton);
        mountainsRadioButton = view.findViewById(R.id.mountainsRadioButton);
        priceSlider = view.findViewById(R.id.priceSlider);
        ratingBar = view.findViewById(R.id.ratingBar);
        addTripButton = view.findViewById(R.id.addTripButton);
        startDateEditText = view.findViewById(R.id.startDatePickerEditText);
        endDateEditText = view.findViewById(R.id.endDatePickerEditText);
    }

    public static String transformData(int day, int month, int year) {
        return day + "/" + (month + 1) + "/" + year;
    }

    public void nextFragment(int resId, Bundle bundle) {
        navController.navigate(resId, bundle);
    }
}