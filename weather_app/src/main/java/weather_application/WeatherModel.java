package weather_application;

import java.util.ArrayList;

public class WeatherModel{
    //  location
    public String cityName;
    public String countryName;
    public String localTime;

    //  current
    public String last_update;
    public HourClass current;

    //  forecast
    public ArrayList<DayClass> forecast;

    public WeatherModel(){
        current = new HourClass();
        forecast = new ArrayList<>();
    }

    @Override
    public String toString(){
        String output = "city: " + cityName + "\n" + 
                        "country name: " + countryName + "\n" + 
                        "local time: " + localTime + "\n\n" + 
                        "last update: " + last_update + "\n" + 
                        "current:\n" + current.toString() + "\n" +  
                        "forecast:\n";

        for(int i=0; i<forecast.size(); ++i){
            output+="day " + i + "\n" + forecast.get(i).toString();
        }
        return output;
    }
}
