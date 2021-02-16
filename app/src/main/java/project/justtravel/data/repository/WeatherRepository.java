package project.justtravel.data.repository;

import project.justtravel.data.ApiAndDao.GetWeatherCallback;
import project.justtravel.data.ApiAndDao.WeatherApi;
import project.justtravel.data.model.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    private static WeatherRepository weatherRepository;
    private WeatherApi weatherApi;
    private static String API_KEY = "c64930fc4372bdd96703ed4f7b5e9e53";

    public static String getAPI_KEY() {
        return API_KEY;
    }

    private WeatherRepository(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public static WeatherRepository getInstance() {
        if (weatherRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherRepository = new WeatherRepository(retrofit.create(WeatherApi.class));
        }
        return weatherRepository;
    }

    public void getWeather(final GetWeatherCallback callback, String destination, String API_KEY) {
        weatherApi.getWeather(destination, API_KEY, "metric")
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.isSuccessful()) {
                            Weather weather = response.body();
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
                    public void onFailure(Call<Weather> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
