package weather_application;

public class DayClass {
    //  day
    public String date;

    //  temperature
    public double maxt_c;
    public double maxt_f;
    public double mint_c;
    public double mint_f;
    public double avg_c;
    public double avg_f;

    //  wind
    public double maxwind_kph;
    public double maxwind_mph;

    //  visibility
    public double avg_vis_km;
    public double avg_vis_m;

    //  humidity
    public int avg_humidity;

    //  rain
    public int rain_prob;

    //  snow
    public int snow_prob;

    //  condition
    public String condition_text;
    public String condition_icon;

    //  uv
    public double uv;

    //  timing
    public String sunrise;
    public String sunset;
    public String moonrise;
    public String moonset;
    public String moon_phase;

    //  hourly
    public HourClass[] hours;

    public DayClass(){
        hours = new HourClass[24];
    }

    @Override
    public String toString(){
        String output = "date: " + date + "\n\n" +
                        "temperature:\n" + 
                            "\tmaxt_c: " + maxt_c + "\n" +
                            "\tmaxt_f: " + maxt_f + "\n" + 
                            "\tmint_c: " + mint_c + "\n" + 
                            "\tmint_f: " + mint_f + "\n" + 
                            "\tavg_c: " + avg_c + "\n" + 
                            "\tavg_f: " + avg_f + "\n" + 
                        "wind:\n" + 
                            "\tmaxwind_kph: " + maxwind_kph + "\n" + 
                            "\tmaxwind_mph: " + maxwind_mph + "\n" + 
                        "visibility:\n" + 
                            "\tavg_vis_km: " + avg_vis_km + "\n" + 
                            "\tavg_vis_m: " + avg_vis_m + "\n" + 
                        "humidity: " + avg_humidity + "\n" + 
                        "rain: " + rain_prob + "\n" + 
                        "snow: " + snow_prob + "\n" + 
                        "condition:\n" + 
                            "\tcondition text: " + condition_text + "\n" + 
                            "\tcondition icon: " + condition_icon + "\n" + 
                        "uv: " + uv + "\n" + 
                        "timing:\n" + 
                            "\tsunrise: " + sunrise + "\n" + 
                            "\tsunset " + sunset + "\n" + 
                            "\tmoonrise: " + moonrise + "\n" + 
                            "\tmoonset: " + moonset + "\n" + 
                            "\tmoon_phase: " + moon_phase + "\n" + "hours:\n";

        for(int i=0; i<hours.length; ++i){
            output+="hour " + i + "\n" + hours[i].toString();
        }
        return output;
    }
}
