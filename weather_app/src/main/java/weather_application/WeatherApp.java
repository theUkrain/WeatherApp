package weather_application;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javafx.application.Application;
import javafx.stage.Stage;

public class WeatherApp extends Application {
    
    public void start(Stage primaryStage) {
        WeatherFetcher wf = new WeatherFetcher("Bratislava");
        WeatherParser parser = new WeatherParser(wf.getResponce());

        WeatherModel model = parser.getWeather();

        File file = new File("output.txt");
        try{
            file.createNewFile();
            try(PrintStream out = new PrintStream(file)){
                out.println(model);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println(model.cityName);
        System.out.println(model.localTime);
        System.out.println(model.current.t_c + "°C");
        
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}