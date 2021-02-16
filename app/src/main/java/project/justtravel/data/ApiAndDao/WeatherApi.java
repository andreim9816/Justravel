package project.justtravel.data.ApiAndDao;

import project.justtravel.data.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("/data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("q") String destination,
                                     @Query("APPID") String API_KEY,
                                     @Query("units") String units);
}
