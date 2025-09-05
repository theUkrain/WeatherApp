package weather_application;

public class DayClass {
    //  day
    private String date;
    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }

    //  temperature
    private double maxTemperatureC;
    public void setMaxTemperatureC(double temperature) { this.maxTemperatureC = temperature; }
    public double getMaxTemperatureC() { return maxTemperatureC; }
    private double maxTemperatureF;
    public void setMaxTemperatureF(double temperature) { this.maxTemperatureF = temperature; }
    public double getMaxTemperatureF() { return maxTemperatureF; }
    private double minTemperatureC;
    public void setMinTemperatureC(double temperature) { this.minTemperatureC = temperature; }
    public double getMinTemperatureC() { return minTemperatureC; }
    private double minTemperatureF;
    public void setMinTemperatureF(double temperature) { this.minTemperatureF = temperature; }
    public double getMinTemperatureF() { return minTemperatureF; }
    private double avgTemperatureC;
    public void setAvgTemperatureC(double temperature) { this.avgTemperatureC = temperature; }
    public double getAvgTemperatureC() { return avgTemperatureC; }
    private double avgTemperatureF;
    public void setAvgTemperatureF(double temperature) { this.avgTemperatureF = temperature; }
    public double getAvgTemperatureF() { return avgTemperatureF; }

    //  wind
    private double maxWindKph;
    public void setMaxWindKph(double speed) { this.maxWindKph = speed; }
    public double getMaxWindKph() { return maxWindKph; }
    private double maxWindMph;
    public void setMaxWindMph(double speed) { this.maxWindMph = speed; }
    public double getMaxWindMph() { return maxWindMph; }

    //  visibility
    private double avgVisKm;
    public void setAvgVisKm(double visibility) { this.avgVisKm = visibility; }
    public double getAvgVisKm() { return avgVisKm; }
    private double avgVisM;
    public void setAvgVisM(double visibility) { this.avgVisM = visibility; }
    public double getAvgVisM() { return avgVisM; }

    //  humidity
    private int avgHumidity;
    public void setAvgHumidity(int humidity) { this.avgHumidity = humidity; }
    public int getAvgHumidity() { return avgHumidity; }

    //  rain
    private int rainProb;
    public void setRainProb(int probability) { this.rainProb = probability; }
    public int getRainProb() { return rainProb; }

    //  snow
    private int snowProb;
    public void setSnowProb(int probability) { this.snowProb = probability; }
    public int getSnowProb() { return snowProb; }

    //  condition
    private String conditionText;
    public void setConditionText(String text) { this.conditionText = text; }
    public String getConditionText() { return conditionText; }
    private String conditionIcon;
    public void setConditionIcon(String icon) { this.conditionIcon = icon; }
    public String getConditionIcon() { return conditionIcon; }

    //  uv
    private double uv;
    public void setUv(double uv) { this.uv = uv; }
    public double getUv() { return uv; }

    //  timing
    private String sunrise;
    public void setSunrise(String time) { this.sunrise = time; }
    public String getSunrise() { return sunrise; }
    private String sunset;
    public void setSunset(String time) { this.sunset = time; }
    public String getSunset() { return sunset; }
    private String moonrise;
    public void setMoonrise(String time) { this.moonrise = time; }
    public String getMoonrise() { return moonrise; }
    private String moonset;
    public void setMoonset(String time) { this.moonset = time; }
    public String getMoonset() { return moonset; }
    private String moonPhase;
    public void setMoonPhase(String phase) { this.moonPhase = phase; }
    public String getMoonPhase() { return moonPhase; }

    //  hourly
    private HourClass[] hours;
    public void setHours(HourClass[] hours) { this.hours = hours; }
    public HourClass[] getHours() { return hours; }

    public DayClass(){
        hours = new HourClass[24];
    }

    @Override
    public String toString(){
        String output = "date: " + date + "\n\n" +
                        "temperature:\n" + 
                            "\tmaxt_c: " + maxTemperatureC + "\n" +
                            "\tmaxt_f: " + maxTemperatureF + "\n" + 
                            "\tmint_c: " + minTemperatureC + "\n" + 
                            "\tmint_f: " + minTemperatureF + "\n" + 
                            "\tavg_c: " + avgTemperatureC + "\n" + 
                            "\tavg_f: " + avgTemperatureF + "\n" + 
                        "wind:\n" + 
                            "\tmaxwind_kph: " + maxWindKph + "\n" + 
                            "\tmaxwind_mph: " + maxWindMph + "\n" + 
                        "visibility:\n" + 
                            "\tavg_vis_km: " + avgVisKm + "\n" + 
                            "\tavg_vis_m: " + avgVisM + "\n" + 
                        "humidity: " + avgHumidity + "\n" + 
                        "rain: " + rainProb + "\n" + 
                        "snow: " + snowProb + "\n" + 
                        "condition:\n" + 
                            "\tcondition text: " + conditionText + "\n" + 
                            "\tcondition icon: " + conditionIcon + "\n" + 
                        "uv: " + uv + "\n" + 
                        "timing:\n" + 
                            "\tsunrise: " + sunrise + "\n" + 
                            "\tsunset " + sunset + "\n" + 
                            "\tmoonrise: " + moonrise + "\n" + 
                            "\tmoonset: " + moonset + "\n" + 
                            "\tmoon_phase: " + moonPhase + "\n" + "hours:\n";

        for(int i=0; i<hours.length; ++i){
            output+="hour " + i + "\n" + hours[i].toString();
        }
        return output;
    }
}
