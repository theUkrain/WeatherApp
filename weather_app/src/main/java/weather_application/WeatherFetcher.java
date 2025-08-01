package weather_application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WeatherFetcher {
    private String jsonResponce;
    
    public WeatherFetcher(String cityName){

        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String apiKey = props.getProperty("apiKey");

        String request = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "q=" + cityName + 
                            "&days=5&aqi=no&alerts=no";

        sendRequest(request);
    }

    public void sendRequest(String request){
        try {
            URL url = new URL(request);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream())
            );
            String line;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();

            jsonResponce = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String getResponce(){
        return jsonResponce;
    }
}
