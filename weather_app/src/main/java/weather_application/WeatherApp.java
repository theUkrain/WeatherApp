package weather_application;

// import java.io.File;
// import java.io.IOException;
// import java.io.PrintStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WeatherApp extends Application {
    private WeatherModel model;

    private Pane pane;

    private ImageView iconImage;
    private TextArea searchBar;
    private Label cityNameLabel;
    private Label tempLabel;
    private ComboBox<String> dateSelector;
    private String selectedDate;

    public void start(Stage primaryStage) {
        setupWeather("");

        pane = new Pane();

        Scene scene = new Scene(pane, 840, 540);
        scene.getStylesheets().add("styles.css");

        setupPrimaryView();
        
        primaryStage.setTitle("Weather App");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void setupWeather(String cityName){
        WeatherFetcher wf = new WeatherFetcher(cityName);
        WeatherParser parser = new WeatherParser(wf.getResponce());
        model = parser.getWeather();

        // File file = new File("output.txt");
        // try{
        //     file.createNewFile();
        //     try(PrintStream out = new PrintStream(file)){
        //         out.println(model);
        //     }
        // }catch(IOException e){
        //     e.printStackTrace();
        // }
        // System.out.println(model.cityName);
        // System.out.println(model.localTime);
        // System.out.println(model.current.t_c + "°C");

    }

    private void updateWeather(String cityName){
        setupWeather(cityName);

        if(model.cityName != null){
            iconImage.setImage(new Image("https:" + model.current.condition_icon));
            cityNameLabel.setText(model.cityName);
            tempLabel.setText(model.current.t_c + "°C");
            dateSelector.getItems().clear();
            for(DayClass item: model.forecast){
                dateSelector.getItems().add(TimeConverter.dateToWords(item.date));
            }
            dateSelector.getSelectionModel().selectFirst();
            selectedDate = model.forecast.get(0).date;
        }else{
            iconImage.setImage(null);
            cityNameLabel.setText("---");
            tempLabel.setText("---°C");
            dateSelector.getItems().clear();
        }
    }

    private void setupPrimaryView(){
        BorderPane borderPane = setupBorderPane();
        StackPane wrapper = new StackPane(borderPane);
        wrapper.setPadding(new Insets(30, 60, 30, 30));
        wrapper.prefWidthProperty().bind(pane.widthProperty());
        wrapper.prefHeightProperty().bind(pane.heightProperty());

        StackPane icon = setupIcon(model.current.condition_icon);

        pane.getChildren().addAll(wrapper, icon);
    }

    private StackPane setupIcon(String iu){
        Rectangle iconPlaceHolder = new Rectangle(600, 30, 180, 180);
        
        StackPane icon = new StackPane();
        icon.setLayoutX(600);
        icon.setLayoutY(30);

        String iconURL = "https:" + iu;

        Image image = new Image(iconURL);
        ImageView iv = new ImageView(image);
        iv.setFitWidth(180);
        iv.setFitHeight(180);

        iv.setPreserveRatio(true);
        iv.setSmooth(true);

        iconImage = iv;

        icon.getChildren().addAll(iconPlaceHolder, iv);
        return icon;
    }

    private BorderPane setupBorderPane(){
        BorderPane bp = new BorderPane();

        
        Rectangle form = new Rectangle(30,30);

        StackPane topWrapper = new StackPane();
        // Rectangle topPlaceHolder = new Rectangle(540, 90);
        // topPlaceHolder.setFill(Color.INDIGO);
        GridPane top = setupTop();
        // topWrapper.getChildren().addAll(topPlaceHolder, top);
        topWrapper.getChildren().addAll(top);
        StackPane.setAlignment(top, Pos.TOP_LEFT);
        // StackPane.setAlignment(topPlaceHolder, Pos.TOP_LEFT);
        bp.setTop(topWrapper);

        Rectangle centerPlaceHolder = new Rectangle(450, 360);
        centerPlaceHolder.setFill(Color.TOMATO);
        StackPane centerWrapper = new StackPane(centerPlaceHolder);
        //centerWrapper.getChildren().add(form);
        StackPane.setAlignment(centerPlaceHolder, Pos.BOTTOM_LEFT);
        bp.setCenter(centerWrapper);

        //Rectangle rightPlaceHolder = new Rectangle(180, 270);
        Rectangle rightPlaceHolder = new Rectangle(270, 270);
        rightPlaceHolder.setFill(Color.GOLD);
        StackPane rightWrapper = new StackPane(rightPlaceHolder);
        StackPane.setAlignment(rightPlaceHolder, Pos.BOTTOM_RIGHT);
        bp.setRight(rightWrapper);
        

        return bp;
    }

    private GridPane setupTop(){
        GridPane top = new GridPane();
        top.setHgap(30);
        top.setVgap(30);

        // Rectangle searchBarPlaceHolder = new Rectangle(360, 30);
        searchBar = setupSearchBar();
        // searchBarPlaceHolder.setFill(Color.CORAL);
        // Rectangle searchButtonPlaceHolder = new Rectangle(30, 30);
        Button searchButton = setupSearchButton();
        // searchButtonPlaceHolder.setFill(Color.GREEN);

        StackPane cityNameWrapper = new StackPane();
        Rectangle cityNamePlaceHolder = new Rectangle(210, 30);
        cityNamePlaceHolder.setVisible(false);
        cityNameLabel = new Label(model.cityName != null ? model.cityName: "---");
        cityNameLabel.getStyleClass().add("header-label");
        cityNameWrapper.getChildren().addAll(cityNamePlaceHolder, cityNameLabel);
        StackPane.setAlignment(cityNameLabel, Pos.CENTER_LEFT);

        StackPane tempWrapper = new StackPane();
        Rectangle temperaturePlaceHolder = new Rectangle(120, 30);
        temperaturePlaceHolder.setVisible(false);
        tempLabel = new Label((model.cityName != null ? model.current.t_c : "---") + "°C");
        tempLabel.getStyleClass().add("header-label");
        tempWrapper.getChildren().addAll(temperaturePlaceHolder, tempLabel);
        StackPane.setAlignment(tempLabel, Pos.CENTER_LEFT);

        // Rectangle datePlaceHolder = new Rectangle(150,30);
        // datePlaceHolder.setFill(Color.GRAY);
        dateSelector = setupDateSelection();

        top.add(searchBar, 0, 0, 2, 1);
        top.add(searchButton, 2, 0);

        top.add(cityNameWrapper, 0, 1);
        top.add(tempWrapper, 1, 1);
        top.add(dateSelector, 2, 1);

        return top;
    }

    private TextArea setupSearchBar(){
        TextArea sb = new TextArea();
        sb.setMaxSize(360, 30);
        sb.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                String cityName = sb.getText().strip();
                sb.setText("");
                updateWeather(cityName);
            }
        });

        return sb;
    }

    private Button setupSearchButton(){
        Button searchButton = new Button();
        searchButton.setId("search-button");

        searchButton.setOnAction(event -> {
            String cityName = searchBar.getText().strip();
            searchBar.setText("");
            updateWeather(cityName);
        });

        Image searchImage = new Image(getClass().getResourceAsStream("/searchIcon.png"));
        ImageView searchIcon = new ImageView(searchImage);

        searchIcon.setFitHeight(18);
        searchIcon.setFitWidth(18);

        searchButton.setGraphic(searchIcon);

        return searchButton;
    }

    private ComboBox<String> setupDateSelection(){
        ComboBox<String> ds = new ComboBox<>();

        ds.setOnAction(event -> {
            if(dateSelector.getValue() != null){
                selectedDate = TimeConverter.wordsToDate(dateSelector.getValue());
                System.out.println(selectedDate);
            }
        });

        ds.setMaxSize(150, 30);

        if(model.cityName == null){
            return ds;
        }

        for(DayClass item: model.forecast){
            ds.getItems().add(TimeConverter.dateToWords(item.date));
        }
        ds.getSelectionModel().selectFirst();
        selectedDate = model.forecast.get(0).date;

        return ds;
    }

    public static void main(String[] args){
        launch(args);
    }
}