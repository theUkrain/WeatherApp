package weather_application;

public class HourClass {
    //  time
    private String time;
    public void setTime(String time) { this.time = time; }
    public String getTime() { return time; }

    //  temperature
    private double temperatureC;
    public void setTemperatureC(double temp) { this.temperatureC = temp; }
    public double getTemperatureC() { return temperatureC; }
    private double feelTemperatureC;
    public void setFeelTemperatureC(double temp) { this.feelTemperatureC = temp; }
    public double getFeelTemperatureC() { return feelTemperatureC; }
    private double temperatureF;
    public void setTemperatureF(double temp) { this.temperatureF = temp; }
    public double getTemperatureF() { return temperatureF; }
    private double feelTemperatureF;
    public void setFeelTemperatureF(double temp) { this.feelTemperatureF = temp; }
    public double getFeelTemperatureF() { return feelTemperatureF; }

    //  condition
    private String conditionText;
    public void setConditionText(String text) { this.conditionText = text; }
    public String getConditionText() { return conditionText; }
    private String conditionIcon;
    public void setConditionIcon(String icon) { this.conditionIcon = icon; }
    public String getConditionIcon() { return conditionIcon; }

    //  wind
    private double windKph;
    public void setWindKph(double windKph) { this.windKph = windKph; }
    public double getWindKph() { return windKph; }
    private double windMph;
    public void setWindMph(double windMph) { this.windMph = windMph; }
    public double getWindMph() { return windMph; }
    private int windDegree;
    public void setWindDegree(int degree) { this.windDegree = degree; }
    public int getWindDegree() { return windDegree; }
    private String windDir;
    public void setWindDir(String direction) { this.windDir = direction; }
    public String getWindDir() { return windDir; }

    //  humidity
    private int humidity;
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public int getHumidity() { return humidity; }

    //  cloud
    private int cloud;
    public void setCloud(int cloud) { this.cloud = cloud; }
    public int getCloud() { return cloud; }

    //  rain
    private int rainProb;
    public void setRainProb(int probability) { this.rainProb = probability; }
    public int getRainProb() { return rainProb; }

    //  snow
    private int snowProb;
    public void setSnowProb(int probability) { this.snowProb = probability; }
    public int getSnowProb() { return snowProb; }

    //  visibility
    private double visKm;
    public void setVisKm(double visibility) { this.visKm = visibility; }
    public double getVisKm() { return visKm; }
    private double visM;
    public void setVisM(double visibility) { this.visM = visibility; }
    public double getVisM() { return visM; }

    //  uv
    private double uv;
    public void setUv(double uv) { this.uv = uv; }
    public double getUv() { return uv; }

    public HourClass(){}

    @Override
    public String toString(){
        String output = "time: " + time + "\n\n" +
                        "temperature:\n" + 
                            "\ttemp c: " + temperatureC + "\n" +
                            "\ttemp f: " + temperatureF + "\n" + 
                            "\tftemp c: " + feelTemperatureC + "\n" + 
                            "\tftemp f: " + feelTemperatureF + "\n" + 
                        "condition:\n" + 
                            "\tcondition text: " + conditionText + "\n" + 
                            "\tcondition icon: " + conditionIcon + "\n" + 
                        "wind:\n" + 
                            "\twind_kph: " + windKph + "\n" + 
                            "\twind_mph: " + windMph + "\n" + 
                            "\twind_degree: " + windDegree + "\n" + 
                            "\twind_dir: " + windDir + "\n" + 
                        "humidity: " + humidity + "\n" + 
                        "cloud: " + cloud + "\n" + 
                        "rain: " + rainProb + "%\n" + 
                        "snow: " + snowProb + "%\n" + 
                        "visibility:\n" + 
                            "\tvis_km: " + visKm + "\n" + 
                            "\tvis_m: " + visM + "\n" + 
                        "uv: " + uv + "\n";
        return output;
    }
}
