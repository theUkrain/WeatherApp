package weather_application;

public class HourClass {
    //  time
    public String time;
    
    //  temperature
    public double t_c;
    public double feelslike_c;
    public double t_f;
    public double feelslike_f;

    //  condition
    public String condition_text;
    public String condition_icon;

    //  wind
    public double wind_kph;
    public double wind_mph;
    public int wind_degree;
    public String wind_dir;

    //  humidity
    public int humidity;

    //  cloud
    public int cloud;

    //  rain
    public int rain_prob;

    //  snow
    public int snow_prob;

    //  visibility
    public double vis_km;
    public double vis_m;

    //  uv
    public double uv;

    public HourClass(){}

    @Override
    public String toString(){
        String output = "time: " + time + "\n\n" +
                        "temperature:\n" + 
                            "\ttemp c: " + t_c + "\n" +
                            "\ttemp f: " + t_f + "\n" + 
                            "\tftemp c: " + feelslike_c + "\n" + 
                            "\tftemp f: " + feelslike_f + "\n" + 
                        "condition:\n" + 
                            "\tcondition text: " + condition_text + "\n" + 
                            "\tcondition icon: " + condition_icon + "\n" + 
                        "wind:\n" + 
                            "\twind_kph: " + wind_kph + "\n" + 
                            "\twind_mph: " + wind_mph + "\n" + 
                            "\twind_degree: " + wind_degree + "\n" + 
                            "\twind_dir: " + wind_dir + "\n" + 
                        "humidity: " + humidity + "\n" + 
                        "cloud: " + cloud + "\n" + 
                        "rain: " + rain_prob + "%\n" + 
                        "snow: " + snow_prob + "%\n" + 
                        "visibility:\n" + 
                            "\tvis_km: " + vis_km + "\n" + 
                            "\tvis_m: " + vis_m + "\n" + 
                        "uv: " + uv + "\n";
        return output;
    }
}
