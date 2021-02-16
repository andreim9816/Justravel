package project.justtravel.data.ApiAndDao;

import project.justtravel.data.model.Weather;

public interface GetWeatherCallback {
    void onSuccess(Weather weather);

    void onError();
}
