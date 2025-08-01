package weather_application;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import org.json.*;

public class Main {
    public static void main(String[] args) {
        // String apiKey = "de12737d1cdc477ab4a185942252107";
        // //String request = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=Bratislava&aqi=yes";
        // // String request = "http://api.weatherapi.com/v1/future.json?key=" + apiKey + "&q=Bratislava";
        // String request = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=Bratislava&days=5&aqi=no&alerts=no";

        // try{
        //     URL url = new URL(request);
        //     HttpURLConnection con = (HttpURLConnection)url.openConnection();
        //     con.setRequestMethod("GET");

        //     BufferedReader br = new BufferedReader(
        //         new InputStreamReader(con.getInputStream())
        //     );
        //     String line;
        //     StringBuffer sb = new StringBuffer();
        //     while((line = br.readLine()) != null){
        //         sb.append(line);
        //     }
        //     br.close();

        //     System.out.println(sb.toString());

        //     // String jsonString = sb.toString();
        //     // JSONObject forecast = new JSONObject(jsonString);

        //     // String cityName = forecast.getJSONObject("location").getString("name");

        //     // double temp_c = forecast.getJSONObject("current").getDouble("temp_c");

        //     // String lastUpdate = forecast.getJSONObject("current").getString("last_updated");

        //     // System.out.println(lastUpdate);
            
        //     // System.out.println(cityName + ": " + temp_c);

        // }catch(Exception e){
        //     System.out.println(e.getMessage());
        // }

        WeatherFetcher wf = new WeatherFetcher("Bratislava");

    }
}