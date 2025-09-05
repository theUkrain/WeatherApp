package weather_application;

import java.util.ArrayList;

public class WeatherModel{
    private boolean empty = true;
    public boolean isEmpty(){ return empty; }
    public void actualise(){ if(cityName == null) empty = true; else empty = false;}

    //  location
    private String cityName;
    public void setCityName(String name) { this.cityName = name; }
    public String getCityName() { return cityName; }
    private String countryName;
    public void setCountryName(String name) { this.countryName = name; }
    public String getCountryName() { return countryName; }
    private String localTime;
    public void setLocalTime(String time) { this.localTime = time; }
    public String getLocalTime() { return localTime; }

    //  current
    private String lastUpdate;
    public void setLastUpdate(String time) { this.lastUpdate = time; }
    public String getLastUpdate() { return lastUpdate; }
    private HourClass current;
    public void setCurrent(HourClass current) { this.current = current; }
    public HourClass getCurrent() { return current; }

    //  forecast
    private ArrayList<DayClass> forecast;
    public void setForecast(ArrayList<DayClass> forecast) { this.forecast = forecast; }
    public ArrayList<DayClass> getForecast() { return forecast; }

    public WeatherModel(){
        current = new HourClass();
        forecast = new ArrayList<>();
    }

    @Override
    public String toString(){
        String output = "city: " + cityName + "\n" + 
                        "country name: " + countryName + "\n" + 
                        "local time: " + localTime + "\n\n" + 
                        "last update: " + lastUpdate + "\n" + 
                        "current:\n" + current.toString() + "\n" +  
                        "forecast:\n";

        for(int i=0; i<forecast.size(); ++i){
            output+="day " + i + "\n" + forecast.get(i).toString();
        }
        return output;
    }
}
