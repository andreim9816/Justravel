package project.justtravel.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import project.justtravel.R;
import project.justtravel.data.model.Trip;
import project.justtravel.ui.home.HomeFragment;
import project.justtravel.viewmodel.TripViewModel;

import static project.justtravel.fragments.AddTripFragment.CITY_BREAK_TYPE;
import static project.justtravel.fragments.AddTripFragment.MOUNTAINS_TYPE;
import static project.justtravel.fragments.AddTripFragment.SEASIDE_TYPE;
import static project.justtravel.fragments.AddTripFragment.checkFieldIsEmptySetError;
import static project.justtravel.fragments.AddTripFragment.transformData;

public class EditTripFragment extends Fragment {

    private final static String TAG = "EditTripFragment";
    private NavController navController;
    private TextView title;
    private EditText nameEditText, destinationEditText;
    private RadioGroup radioGroup;
    private RadioButton cityBreakRadioButton, seasideRadioButton, mountainsRadioButton;
    private Slider priceSlider;
    private RatingBar ratingBar;
    private EditText startDateEditText, endDateEditText;
    private Button editTripButton;

    private Trip tripToBeEdited;
    private TripViewModel tripViewModel;

    public EditTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        // init UI elements
        initUiElements(view);

//        // override onBackPressed
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                //go back to trips fragment
//                displayFragment(new TripsFragment());
//            }
//        });

        // onClick on startDate
        startDateEditText.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view12, year1, monthOfYear, dayOfMonth) -> startDateEditText.setText(transformData(dayOfMonth, monthOfYear, year1)), year, month, day);
            datePickerDialog.show();
        });

        // onClick on startDate
        endDateEditText.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year12, monthOfYear, dayOfMonth) -> endDateEditText.setText(transformData(dayOfMonth, monthOfYear, year12)), year, month, day);
            datePickerDialog.show();
        });

        // onClick editTripButton
        editTripButton.setOnClickListener(v -> {
            boolean error = checkFieldIsEmptySetError(nameEditText, getString(R.string.trip_name_empty))
                    | checkFieldIsEmptySetError(destinationEditText, getString(R.string.trip_destination_empty))
                    | checkFieldIsEmptySetError(startDateEditText, getString(R.string.trip_start_date_empty))
                    | checkFieldIsEmptySetError(endDateEditText, getString(R.string.trip_end_date_empty));

            if (error) {
                Toast.makeText(getActivity(), getString(R.string.toast_fields_empty), Toast.LENGTH_LONG).show();
            } else if (false) {
                //TODO compare startDate <= endDate
            } else {
                int type = CITY_BREAK_TYPE;

                if (seasideRadioButton.isChecked()) {
                    type = SEASIDE_TYPE;
                } else if (mountainsRadioButton.isChecked()) {
                    type = MOUNTAINS_TYPE;
                }

                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Trip trip = new Trip(tripToBeEdited.getId(),
                            nameEditText.getText().toString(),
                            destinationEditText.getText().toString(),
                            type,
                            (int) priceSlider.getValue(),
                            simpleDateFormat.parse(startDateEditText.getText().toString()),
                            simpleDateFormat.parse(endDateEditText.getText().toString()),
                            ratingBar.getRating()
                    );

                    tripViewModel.update(trip);

//                    displayFragment(new TripsFragment());
                    navController.navigate(R.id.nav_home);
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

    /* Function that init UI elements*/
    private void initUiElements(View view) {
        title = view.findViewById(R.id.newTripTextView);
        nameEditText = view.findViewById(R.id.editTextTripName);
        destinationEditText = view.findViewById(R.id.editTextTripDestination);
        radioGroup = view.findViewById(R.id.radioGroup);
        cityBreakRadioButton = view.findViewById(R.id.cityBreakRadioButton);
        seasideRadioButton = view.findViewById(R.id.seaSideRadioButton);
        mountainsRadioButton = view.findViewById(R.id.mountainsRadioButton);
        priceSlider = view.findViewById(R.id.priceSlider);
        ratingBar = view.findViewById(R.id.ratingBar);
        editTripButton = view.findViewById(R.id.addTripButton);
        startDateEditText = view.findViewById(R.id.startDatePickerEditText);
        endDateEditText = view.findViewById(R.id.endDatePickerEditText);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tripToBeEdited = getArguments().getParcelable(HomeFragment.TRIP_OBJECT_PASSED);
            modifyUIElements(tripToBeEdited);
        } else {
            Toast.makeText(getContext(), "Trip was not sent successfully!", Toast.LENGTH_LONG).show();
        }
    }

//    /* Opens new fragment */
//    private void displayFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction(); //requireActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.tripsListFrameLayout, fragment);
//        fragmentTransaction.commit();
//    }

//    public void getSharedTrip(Trip trip) {
//        tripToBeEdited = trip;
//        modifyUIElements();
//    }

    /* Function that updates the values according to the trip that is being edited */
    private void modifyUIElements(Trip tripToBeEdited) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        title.setText(R.string.edit_trip_title);
        nameEditText.setText(tripToBeEdited.getName());
        destinationEditText.setText(tripToBeEdited.getDestination());

        switch (tripToBeEdited.getType()) {
            case CITY_BREAK_TYPE:
                cityBreakRadioButton.setChecked(true);
                break;
            case SEASIDE_TYPE:
                seasideRadioButton.setChecked(true);
                break;
            case MOUNTAINS_TYPE:
                mountainsRadioButton.setChecked(true);
                break;
        }

        priceSlider.setValue(tripToBeEdited.getPrice());
        ratingBar.setRating(tripToBeEdited.getRating());
        editTripButton.setText(R.string.edit_trip_text);

        startDateEditText.setText(simpleDateFormat.format(tripToBeEdited.getStartDate()));
        endDateEditText.setText(simpleDateFormat.format(tripToBeEdited.getEndDate()));
    }

    public static String getTAG() {
        return TAG;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "OnAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ");
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