package project.justtravel.data.repository;

import project.justtravel.data.ApiAndDao.GetWeatherCallback;
import project.justtravel.data.ApiAndDao.WeatherApi;
import project.justtravel.data.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    private static WeatherRepository weatherRepository;
    private WeatherApi weatherApi;
    private final static String API_KEY = "c64930fc4372bdd96703ed4f7b5e9e53";
    private final static String API_URL = "https://api.openweathermap.org";

    private WeatherRepository(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public static WeatherRepository getInstance() {
        if (weatherRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherRepository = new WeatherRepository(retrofit.create(WeatherApi.class));
        }
        return weatherRepository;
    }

    public void getWeather(final GetWeatherCallback callback, String destination, String API_KEY, String units) {
        weatherApi.getWeather(destination, API_KEY, units)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse weather = response.body();
                            if (weather != null) {
                                callback.onSuccess(weather);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
