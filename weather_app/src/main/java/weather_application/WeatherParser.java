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
        weather.setCityName(responce.getJSONObject("location").getString("name"));
        weather.setCountryName(responce.getJSONObject("location").getString("country"));
        weather.setLocalTime(responce.getJSONObject("location").getString("localtime"));

        weather.setLastUpdate(responce.getJSONObject("current").getString("last_updated"));
        weather.setCurrent(parseHour(responce.getJSONObject("current"), true));

        weather.setForecast(parseForecast());
        weather.actualise();
    }

    private HourClass parseHour(Object hobj, boolean current){
        JSONObject hour = (JSONObject)hobj;
        HourClass obj = new HourClass();
        if(!current){
            obj.setTime(hour.getString("time"));
        }else obj.setTime("-");
        
        obj.setTemperatureC(hour.getDouble("temp_c"));
        obj.setFeelTemperatureC(hour.getDouble("feelslike_c"));
        obj.setTemperatureF(hour.getDouble("temp_f"));
        obj.setFeelTemperatureF(hour.getDouble("feelslike_f"));

        obj.setConditionText(hour.getJSONObject("condition").getString("text"));
        obj.setConditionIcon(hour.getJSONObject("condition").getString("icon"));

        obj.setWindKph(hour.getDouble("wind_kph"));
        obj.setWindMph(hour.getDouble("wind_mph"));
        obj.setWindDegree(hour.getInt("wind_degree"));
        obj.setWindDir(hour.getString("wind_dir"));

        obj.setHumidity(hour.getInt("humidity"));
        obj.setCloud(hour.getInt("cloud"));
        if(!current){
            obj.setRainProb(hour.getInt("chance_of_rain"));
            obj.setSnowProb(hour.getInt("chance_of_snow"));
        }else{
            obj.setRainProb(0);
            obj.setSnowProb(0);
        }

        obj.setVisKm(hour.getDouble("vis_km"));
        obj.setVisM(hour.getDouble("vis_miles"));

        obj.setUv(hour.getDouble("uv"));
        return obj;
    }

    private DayClass parseDay(Object obj){
        JSONObject dayObj = (JSONObject) obj;
        DayClass day = new DayClass();

        day.setDate(dayObj.getString("date"));

        day.setMaxTemperatureC(dayObj.getJSONObject("day").getDouble("maxtemp_c"));
        day.setMaxTemperatureF(dayObj.getJSONObject("day").getDouble("maxtemp_f"));
        day.setMinTemperatureC(dayObj.getJSONObject("day").getDouble("mintemp_c"));
        day.setMinTemperatureF(dayObj.getJSONObject("day").getDouble("mintemp_f"));
        day.setAvgTemperatureC(dayObj.getJSONObject("day").getDouble("avgtemp_c"));
        day.setAvgTemperatureF(dayObj.getJSONObject("day").getDouble("avgtemp_f"));

        day.setMaxWindKph(dayObj.getJSONObject("day").getDouble("maxwind_kph"));
        day.setMaxWindMph(dayObj.getJSONObject("day").getDouble("maxwind_mph"));

        day.setAvgVisKm(dayObj.getJSONObject("day").getDouble("avgvis_km"));
        day.setAvgVisM(dayObj.getJSONObject("day").getDouble("avgvis_miles"));

        day.setAvgHumidity(dayObj.getJSONObject("day").getInt("avghumidity"));
        day.setRainProb(dayObj.getJSONObject("day").getInt("daily_chance_of_rain"));
        day.setSnowProb(dayObj.getJSONObject("day").getInt("daily_chance_of_snow"));

        day.setConditionText(dayObj.getJSONObject("day").getJSONObject("condition").getString("text"));
        day.setConditionIcon(dayObj.getJSONObject("day").getJSONObject("condition").getString("icon"));

        day.setUv(dayObj.getJSONObject("day").getDouble("uv"));

        day.setSunrise(dayObj.getJSONObject("astro").getString("sunrise"));
        day.setSunset(dayObj.getJSONObject("astro").getString("sunset"));
        day.setMoonrise(dayObj.getJSONObject("astro").getString("moonrise"));
        day.setMoonset(dayObj.getJSONObject("astro").getString("moonset"));
        day.setMoonPhase(dayObj.getJSONObject("astro").getString("moon_phase"));

        HourClass[] hours = new HourClass[24];
        JSONArray hour = dayObj.getJSONArray("hour");
        int i=0;
        for(Object hourObj: hour){
            hours[i] = parseHour(hourObj, false);
            ++i;
        }
        day.setHours(hours);
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
