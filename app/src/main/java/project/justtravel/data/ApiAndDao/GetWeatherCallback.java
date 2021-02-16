package project.justtravel.data.ApiAndDao;

import project.justtravel.data.model.WeatherResponse;

public interface GetWeatherCallback {
    void onSuccess(WeatherResponse weather);

    void onError();
}
