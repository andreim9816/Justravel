package project.justtravel.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("weather")
    private List<Weather> weatherList;
    private Main main;
    @SerializedName("cod")
    private int code;

    public WeatherResponse(List<Weather> weatherList, Main main, int code) {
        this.weatherList = weatherList;
        this.main = main;
        this.code = code;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
