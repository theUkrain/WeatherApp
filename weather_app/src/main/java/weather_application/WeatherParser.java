package weather_application;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParser {
    private JSONObject responce;
    private WeatherModel weather;

    public WeatherParser(String jsonResponce){
        weather = new WeatherModel();
        if(!jsonResponce.equals("_Error")){
            responce = new JSONObject(jsonResponce);
            parse();
        }
    }

    private void parse(){
        weather.cityName = responce.getJSONObject("location").getString("name");
        weather.countryName = responce.getJSONObject("location").getString("country");
        weather.localTime = responce.getJSONObject("location").getString("localtime");

        weather.last_update = responce.getJSONObject("current").getString("last_updated");
        weather.current = parseHour(responce.getJSONObject("current"), true);

        weather.forecast = parseForecast();
    }

    private HourClass parseHour(Object hobj, boolean current){
        JSONObject hour = (JSONObject)hobj;
        HourClass obj = new HourClass();
        if(!current){
            obj.time = hour.getString("time");
        }else obj.time = "-";
        
        obj.t_c = hour.getDouble("temp_c");
        obj.feelslike_c = hour.getDouble("feelslike_c");
        obj.t_f = hour.getDouble("temp_f");
        obj.feelslike_f = hour.getDouble("feelslike_f");

        obj.condition_text = hour.getJSONObject("condition").getString("text");
        obj.condition_icon = hour.getJSONObject("condition").getString("icon");

        obj.wind_kph = hour.getDouble("wind_kph");
        obj.wind_mph = hour.getDouble("wind_mph");
        obj.wind_degree = hour.getInt("wind_degree");
        obj.wind_dir = hour.getString("wind_dir");

        obj.humidity = hour.getInt("humidity");
        obj.cloud = hour.getInt("cloud");
        if(!current){
            obj.rain_prob = hour.getInt("chance_of_rain");
            obj.snow_prob = hour.getInt("chance_of_snow");
        }else{
            obj.rain_prob = 0;
            obj.snow_prob = 0;
        }

        obj.vis_km = hour.getDouble("vis_km");
        obj.vis_m = hour.getDouble("vis_miles");

        obj.uv = hour.getDouble("uv");
        return obj;
    }

    private DayClass parseDay(Object obj){
        JSONObject dayObj = (JSONObject) obj;
        DayClass day = new DayClass();

        day.date = dayObj.getString("date");

        day.maxt_c = dayObj.getJSONObject("day").getDouble("maxtemp_c");
        day.maxt_f = dayObj.getJSONObject("day").getDouble("maxtemp_f");
        day.mint_c = dayObj.getJSONObject("day").getDouble("mintemp_c");
        day.mint_f = dayObj.getJSONObject("day").getDouble("mintemp_f");
        day.avg_c = dayObj.getJSONObject("day").getDouble("avgtemp_c");
        day.avg_f = dayObj.getJSONObject("day").getDouble("avgtemp_f");

        day.maxwind_kph = dayObj.getJSONObject("day").getDouble("maxwind_kph");
        day.maxwind_mph = dayObj.getJSONObject("day").getDouble("maxwind_mph");

        day.avg_vis_km = dayObj.getJSONObject("day").getDouble("avgvis_km");
        day.avg_vis_m = dayObj.getJSONObject("day").getDouble("avgvis_miles");

        day.avg_humidity = dayObj.getJSONObject("day").getInt("avghumidity");
        day.rain_prob = dayObj.getJSONObject("day").getInt("daily_chance_of_rain");
        day.snow_prob = dayObj.getJSONObject("day").getInt("daily_chance_of_snow");

        day.condition_text = dayObj.getJSONObject("day").getJSONObject("condition").getString("text");
        day.condition_icon = dayObj.getJSONObject("day").getJSONObject("condition").getString("icon");

        day.uv = dayObj.getJSONObject("day").getDouble("uv");

        day.sunrise = dayObj.getJSONObject("astro").getString("sunrise");
        day.sunset = dayObj.getJSONObject("astro").getString("sunset");
        day.moonrise = dayObj.getJSONObject("astro").getString("moonrise");
        day.moonset = dayObj.getJSONObject("astro").getString("moonset");
        day.moon_phase = dayObj.getJSONObject("astro").getString("moon_phase");

        HourClass[] hours = new HourClass[24];
        JSONArray hour = dayObj.getJSONArray("hour");
        int i=0;
        for(Object hourObj: hour){
            hours[i] = parseHour(hourObj, false);
            ++i;
        }
        day.hours = hours;
        return day;
    }

    private ArrayList<DayClass> parseForecast(){
        ArrayList<DayClass> forecast = new ArrayList<>();
        JSONArray forecastday = responce.getJSONObject("forecast").optJSONArray("forecastday");
        for(Object dayObj: forecastday){
            DayClass day = parseDay(dayObj);
            forecast.add(day);
        }
        return forecast;
    }

    public WeatherModel getWeather(){
        return weather;
    }

}
