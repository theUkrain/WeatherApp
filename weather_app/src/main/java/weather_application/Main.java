package weather_application;

public class Main {
    public static void main(String[] args) {
        WeatherFetcher wf = new WeatherFetcher("Bratislava");
        WeatherParser parser = new WeatherParser(wf.getResponce());

        WeatherModel model = parser.getWeather();
    }
}