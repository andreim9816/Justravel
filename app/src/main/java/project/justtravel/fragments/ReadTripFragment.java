package project.justtravel.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import project.justtravel.BuildConfig;
import project.justtravel.R;
import project.justtravel.data.ApiAndDao.GetWeatherCallback;
import project.justtravel.data.model.Trip;
import project.justtravel.data.model.WeatherResponse;
import project.justtravel.data.repository.WeatherRepository;
import project.justtravel.ui.home.HomeFragment;

import static project.justtravel.fragments.AddTripFragment.CITY_BREAK_TYPE;
import static project.justtravel.fragments.AddTripFragment.MOUNTAINS_TYPE;
import static project.justtravel.fragments.AddTripFragment.SEASIDE_TYPE;

public class ReadTripFragment extends Fragment {

    private ImageView tripImageView, weatherIconImageView;
    private TextView weatherConditionTextView, temperatureTextView, minTempTextView, maxTempTextView;
    private TextView destinationTextView, startDateTextView, endDateTextView, priceTextView, ratingTextView;
    private Button backButton;

    public ReadTripFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_trip, container, false);

        // init UI elements
        initElements(view);

        return view;
    }

    private void initElements(View view) {
        tripImageView = view.findViewById(R.id.shapeableImageView);
        weatherIconImageView = view.findViewById(R.id.weather_icon);
        weatherConditionTextView = view.findViewById(R.id.weatherConditionTextView);
        destinationTextView = view.findViewById(R.id.destinationTextView);
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        minTempTextView = view.findViewById(R.id.minTempTextView);
        maxTempTextView = view.findViewById(R.id.maxTempTextView);
        startDateTextView = view.findViewById(R.id.startDateTextView);
        endDateTextView = view.findViewById(R.id.endDateTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        ratingTextView = view.findViewById(R.id.ratingTextView);
        backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            modifyUIElements(bundle.getParcelable(HomeFragment.TRIP_OBJECT_PASSED));
        } else {
            Toast.makeText(getContext(), "Internal error regarding object passing! Please exit", Toast.LENGTH_LONG).show();
        }
    }

    /* Function that updates the values according to the trip that is being edited */
    private void modifyUIElements(Trip trip) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        switch (trip.getType()) {
            case CITY_BREAK_TYPE:
                tripImageView.setBackgroundResource(R.drawable.citybreak);
                break;
            case SEASIDE_TYPE:
                tripImageView.setBackgroundResource(R.drawable.seaside);
                break;
            case MOUNTAINS_TYPE:
                tripImageView.setBackgroundResource(R.drawable.mountains);
                break;
            default:
                tripImageView.setBackgroundResource(R.drawable.not_found);
                break;
        }

        destinationTextView.setText(trip.getDestination());
        priceTextView.setText(trip.getPrice() + "");
        ratingTextView.setText(trip.getRating() + "");

        startDateTextView.setText(simpleDateFormat.format(trip.getStartDate()));
        endDateTextView.setText(simpleDateFormat.format(trip.getEndDate()));

        /* Populate views with data */
        getWeather(trip.getDestination(), WeatherRepository.getApiKey(), "metric");
    }

    public void getWeather(String destination, String API_KEY, String units) {
        WeatherRepository weatherRepository = WeatherRepository.getInstance();

        weatherRepository.getWeather(new GetWeatherCallback() {
            @Override
            public void onSuccess(WeatherResponse weather) {

                /* Get the values, round and convert them to string */
                float minTemp = weather.getMain().getTemp_min();
                float maxTemp = weather.getMain().getTemp_max();
                float temperature = weather.getMain().getTemp();

                String strMinTemp = Math.round(minTemp) + "";
                String strMaxTemp = Math.round(maxTemp) + "";
                String strTemp = Math.round(temperature) + "°C";

                minTempTextView.setText(strMinTemp);
                maxTempTextView.setText(strMaxTemp);
                temperatureTextView.setText(strTemp);

                /* Set weather icon, according to its response */
                String imageName = weather.getWeatherList().get(0).getIcon();
                int res = getResources().getIdentifier('_' + imageName, "drawable", BuildConfig.APPLICATION_ID);
                weatherIconImageView.setImageResource(res);

                /* Set weather description */
                weatherConditionTextView.setText(weather.getWeatherList().get(0).getMain());
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Destination not found! No weather data to be displayed!", Toast.LENGTH_LONG).show();
                String NotApplied = "N/A";

                minTempTextView.setText(NotApplied);
                maxTempTextView.setText(NotApplied);
                temperatureTextView.setText(NotApplied);

                /* Set weather icon */
                weatherIconImageView.setImageResource(R.drawable.ic_image_not_found);

                /* Set weather description */
                weatherConditionTextView.setText(NotApplied);
            }
        }, destination, API_KEY, units);
    }
}