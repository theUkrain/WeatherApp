
package weather_application;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

// import java.io.File;
// import java.io.IOException;
// import java.io.PrintStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
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

    private StackPane rightWrapper;
    private GridPane rightDisplay;

    private TableView<HourClass> hourTable;

    public void start(Stage primaryStage) {
        setupWeather("Bratislava");

        pane = new Pane();

        Scene scene = new Scene(pane, 840, 540);
        scene.getStylesheets().add("styles.css");

        setupPrimaryView();

        // DayTimeWidget dtw3 = new DayTimeWidget(400);
        // Node widget3 = dtw3.getWidget();
        // widget3.setLayoutX(10);
        // widget3.setLayoutY(100);

        // Slider slider = new Slider();
        // slider.setLayoutX(100);
        // slider.setLayoutY(400);
        // slider.valueProperty().addListener((obs, oldVal, newVal) -> {
        //     System.out.println(newVal);
        //     dtw3.update(newVal.doubleValue()/100);
        // });

        // pane.getChildren().addAll(widget3, slider);

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

        if(!model.isEmpty()){
            iconImage.setImage(new Image("https:" + model.getCurrent().getConditionIcon()));
            cityNameLabel.setText(model.getCityName());
            tempLabel.setText(model.getCurrent().getTemperatureC() + "°C");
            dateSelector.getItems().clear();
            for(DayClass item: model.getForecast()){
                dateSelector.getItems().add(TimeConverter.dateToWords(item.getDate()));
            }
            dateSelector.getSelectionModel().selectFirst();
            selectedDate = model.getForecast().get(0).getDate();
            updateRightDisplay();
            updateHourTable(timeCalculator());
        }else{
            iconImage.setImage(null);
            cityNameLabel.setText("---");
            tempLabel.setText("---°C");
            dateSelector.getItems().clear();
            rightDisplay = setupRightDisplay();
            updateHourTable();
        }
    }

    private void updateWeather(int day){
        DayClass dayObj = model.getForecast().get(day);

        iconImage.setImage(new Image("https:" + dayObj.getConditionIcon()));
        tempLabel.setText(dayObj.getAvgTemperatureC() + "°C");
        updateHourTable(day);
        updateRightDisplay(day);
    }

    private void updateRightDisplay(){
            rightWrapper.getChildren().remove(rightDisplay);
            rightDisplay = setupRightDisplay();
            rightWrapper.getChildren().add(rightDisplay);
            StackPane.setAlignment(rightDisplay, Pos.BOTTOM_RIGHT);
    }

    private void updateRightDisplay(int day){
            rightWrapper.getChildren().remove(rightDisplay);
            rightDisplay = setupRightDisplay(model.getForecast().get(day));
            rightWrapper.getChildren().add(rightDisplay);
            StackPane.setAlignment(rightDisplay, Pos.BOTTOM_RIGHT);
    }

    private void updateRightDisplay(HourClass hour){
            rightWrapper.getChildren().remove(rightDisplay);
            rightDisplay = setupRightDisplay(hour);
            rightWrapper.getChildren().add(rightDisplay);
            StackPane.setAlignment(rightDisplay, Pos.BOTTOM_RIGHT);
    }

    private void setupPrimaryView(){
        BorderPane borderPane = setupBorderPane();
        StackPane wrapper = new StackPane(borderPane);
        wrapper.setPadding(new Insets(30, 60, 30, 30));
        wrapper.prefWidthProperty().bind(pane.widthProperty());
        wrapper.prefHeightProperty().bind(pane.heightProperty());

        StackPane icon = setupIcon(model.getCurrent().getConditionIcon());

        // Rectangle marker = new Rectangle(16.875, 16.875);
        // marker.setFill(Color.DARKGREEN);
        // marker.setLayoutX(50);
        // marker.setLayoutY(50);

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

        // Rectangle centerPlaceHolder = new Rectangle(450, 360);
        // centerPlaceHolder.setFill(Color.TOMATO);
        // centerPlaceHolder.setVisible(false);
        StackPane centerWrapper = new StackPane();

        hourTable = setupTimeDisplay();

        hourTable.setMaxSize(450, 360);

        if(!model.isEmpty()){
            updateHourTable(timeCalculator());
        }

        centerWrapper.getChildren().add(hourTable);
        StackPane.setAlignment(hourTable, Pos.BOTTOM_LEFT);
        bp.setCenter(centerWrapper);

        //Rectangle rightPlaceHolder = new Rectangle(180, 270);
        Rectangle rightPlaceHolder = new Rectangle(270, 270);
        // rightPlaceHolder.setFill(Color.GOLD);
        rightPlaceHolder.setVisible(false);
        rightWrapper = new StackPane();
        rightDisplay = setupRightDisplay();
        rightWrapper.getChildren().addAll(rightPlaceHolder, rightDisplay);
        StackPane.setAlignment(rightPlaceHolder, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(rightDisplay, Pos.BOTTOM_RIGHT);
        bp.setRight(rightWrapper);

        return bp;
    }

    private GridPane setupRightDisplay(){
        if(model.isEmpty()){
            return new GridPane();
        }
        return setupRightDisplay(model.getForecast().get(0));
    }

    private GridPane setupRightDisplay(DayClass day){
        GridPane display = new GridPane();
        // display.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        // display.setGridLinesVisible(true);
        display.setPrefSize(270, 270);
        display.setMinSize(270, 270);
        display.setMaxSize(270, 270);

        Label avgTempLabel = new Label(day.getAvgTemperatureC() + "°C");
        display.add(getWrapper(avgTempLabel, 67.5, 67.5), 0, 0, 1, 1);

        Label tempVarLabel = new Label(day.getMaxTemperatureC() + "°C / " + day.getMinTemperatureC() + "°C");
        display.add(getWrapper(tempVarLabel, 202.5, 67.5), 1, 0, 3, 1);

        Image windImage = new Image(getClass().getResourceAsStream("/windIcon.png"));
        ImageView windIcon = new ImageView(windImage);
        windIcon.setFitHeight(67.5);
        windIcon.setFitWidth(67.5);
        display.add(getWrapper(windIcon, 67.5, 67.5), 0, 1, 1, 1);

        Label windSpeedLabel = new Label(day.getMaxWindKph() + " kph");
        display.add(getWrapper(windSpeedLabel, 67.5, 67.5), 1, 1, 1, 1);

        Rectangle rainProbHolder = new Rectangle(67.5, 67.5);
        // rainProbHolder.setVisible(false);
        rainProbHolder.setFill(Color.LIGHTGREEN);
        display.add(getWrapper(rainProbHolder, 67.5, 67.5), 2, 1, 1, 1);

        Label rainProbLabel = new Label(day.getRainProb() + "%");
        display.add(getWrapper(rainProbLabel, 67.5, 67.5), 3, 1, 1, 1);

        Label sunriseLabel = new Label(TimeConverter.standToMillitary(day.getSunrise()));
        display.add(getWrapper(sunriseLabel, 67.5, 67.5), 0, 2, 1, 1);

        DayTimeWidget dtw = new DayTimeWidget(125);
        if(day.getDate().equals((model.getLastUpdate().split(" "))[0])){
            dtw.update(dayTimeCalculator(day.getSunrise(), day.getSunset(), model.getLocalTime()));
        }else dtw.update(0.4);
        Node widget = dtw.getWidget();
        display.add(getWrapper(widget, 135, 67.5), 1, 2, 2, 1);

        Label sunsetLabel = new Label(TimeConverter.standToMillitary(day.getSunset()));
        display.add(getWrapper(sunsetLabel, 67.5, 67.5), 3, 2, 1, 1);

        Label moonPhaseLabel = new Label(day.getMoonPhase());
        display.add(getWrapper(moonPhaseLabel, 202.5, 67.5), 0, 3, 3, 1);

        Rectangle moonPhaseIconHolder = new Rectangle(67.5, 67.5);
        // moonPhaseIconHolder.setVisible(false);
        moonPhaseIconHolder.setFill(Color.LIGHTGREEN);
        display.add(getWrapper(moonPhaseIconHolder, 67.5, 67.5), 3, 3, 1, 1);

        return display;
    }

    private GridPane setupRightDisplay(HourClass object){
        GridPane display = new GridPane();
        // display.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        // display.setGridLinesVisible(true);
        display.setPrefSize(270, 253.125);
        display.setMinSize(270, 253.125);
        display.setMaxSize(270, 253.125);

        Label timeLabel = new Label(object.getTime());
        display.add(getWrapper(timeLabel, 135, 16.875), 0, 0, 2, 1);

        Label tempLabel = new Label(object.getTemperatureC() + "°C / " + object.getFeelTemperatureC() + "°C");
        display.add(getWrapper(tempLabel, 135, 33.75), 0, 1, 2, 1);

        Rectangle windHolder = new Rectangle(135, 101.25);
        windHolder.setFill(Color.GREEN);
        // windHolder.setVisible(false);
        display.add(getWrapper(windHolder, 135, 101.25), 0, 2, 2, 2);

        ImageView conditionIcon = setupIcon(object.getConditionIcon(), 135, 135);
        display.add(getWrapper(conditionIcon, 135, 135), 2, 0, 2, 3);

        Label conditionLabel = new Label(object.getConditionText());
        display.add(getWrapper(conditionLabel, 135, 16.875), 2, 3, 2, 1);

        Label cloudLabel = new Label(object.getCloud() + "%");
        display.add(getWrapper(cloudLabel, 101.25, 101.25), 0, 4, 1, 1);

        Label rainLabel = new Label(object.getRainProb() + "%");
        display.add(getWrapper(rainLabel, 101.25, 101.25), 1, 4, 2, 1);

        display.getRowConstraints().addAll(
            fixedRow(16.875),
            fixedRow(33.75),
            fixedRow(84.375),
            fixedRow(16.875),
            fixedRow(101.25)
        );


        return display;
    }

    private static RowConstraints fixedRow(double h){
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(h); rc.setPrefHeight(h); rc.setMaxHeight(h);
        return rc;
    }


    private StackPane getWrapper(Node node, double width, double height){
        StackPane wrapper = new StackPane();
        Rectangle holder = new Rectangle(width, height);
        holder.setVisible(false);
        wrapper.getChildren().addAll(holder, node);
        return wrapper;
    }

    private double dayTimeCalculator(String sunrise, String sunset, String time){
        sunrise = TimeConverter.standToMillitary(sunrise);
        String[] rise = sunrise.split(":");
        sunset = TimeConverter.standToMillitary(sunset);
        String[] set = sunset.split(":");
        time = time.split(" ")[1];
        String[] cur = time.split(":");
        int sunriseVal = Integer.parseInt(rise[0]) * 60 + Integer.parseInt(rise[1]);
        int sunsetVal = Integer.parseInt(set[0]) * 60 + Integer.parseInt(set[1]);
        int timeVal = Integer.parseInt(cur[0]) * 60 + Integer.parseInt(cur[1]);
        if(timeVal < sunriseVal){
            return 0;
        }
        if(timeVal > sunsetVal){
            return 1;
        }
        return (double)(timeVal - sunriseVal) / (double)(sunsetVal - sunriseVal);
    }

    private int timeCalculator(){
        int dayCurrent = Integer.parseInt((model.getLastUpdate().split("[:\s-]+"))[2]);
        int dayChosen = Integer.parseInt(selectedDate.split("-")[2]);
        System.out.println(dayChosen + " " + dayCurrent + " " + Math.abs(dayChosen - dayCurrent));
        return Math.abs(dayChosen - dayCurrent);
    }

    private TableView<HourClass> setupTimeDisplay(){
        TableView<HourClass> ht = new TableView<>();

        TableColumn<HourClass, String> hourColumn = new TableColumn<>("Hour");
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        hourColumn.setCellFactory(column -> new TableCell<HourClass, String>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                }else{
                    String[] time = item.split(" ");
                    setText(time[1]);
                }
            }
        });
        ht.getColumns().add(hourColumn);

        TableColumn<HourClass, String> tempColumn = new TableColumn<>("Temperature");
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureC"));
        ht.getColumns().add(tempColumn);

        TableColumn<HourClass, String> probColumn = new TableColumn<>("Probability");
        probColumn.setCellValueFactory(new PropertyValueFactory<>("rainProb"));
        ht.getColumns().add(probColumn);

        TableColumn<HourClass, String> iconColumn = new TableColumn<>("Icon");
        iconColumn.setCellValueFactory(new PropertyValueFactory<>("conditionIcon"));
        iconColumn.setCellFactory(column -> new TableCell<HourClass, String>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setGraphic(null);
                }else{
                    setGraphic(setupIcon(item, 15, 15));
                }
            }
        });
        ht.getColumns().add(iconColumn);

        TableColumn<HourClass, Integer> windColumn = new TableColumn<>("Wind");
        windColumn.setCellValueFactory(new PropertyValueFactory<>("windDegree"));
        windColumn.setCellFactory(column -> new TableCell<HourClass, Integer>(){
            @Override
            protected void updateItem(Integer item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setGraphic(null);
                }else{
                    setGraphic(setupWindDirectionIcon((double)item));
                }
            }
        });
        ht.getColumns().add(windColumn);

        ht.setRowFactory(tv -> {
            TableRow<HourClass> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty()){
                    // System.out.println(row.getIndex());
                    // System.out.println(row.getItem().getTime());
                    System.out.println(row.getIndex());
                    updateRightDisplay(row.getItem());
                }
            });
            return row;
        });

        return ht;
    }

    private ImageView setupIcon(String iconText, int width, int height){
        iconText = "https:" + iconText;
        Image image = new Image(iconText);
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        return iv;
    }

    private ImageView setupWindDirectionIcon(double dir){
        Image searchImage = new Image(getClass().getResourceAsStream("/arrow.png"));
        ImageView iv = new ImageView(searchImage);
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        iv.setRotate(dir);
        return iv;
    }


    private void updateHourTable(int day){
        ObservableList<HourClass> hourList = FXCollections.observableArrayList(model.getForecast().get(day).getHours());
        hourTable.setItems(hourList);
    }

    private void updateHourTable(){
        hourTable.setItems(null);
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

        Button refreshButton = setupRefreshButton();

        StackPane cityNameWrapper = new StackPane();
        Rectangle cityNamePlaceHolder = new Rectangle(210, 30);
        cityNamePlaceHolder.setVisible(false);
        cityNameLabel = new Label("---");
        if(!model.isEmpty()){
            cityNameLabel.setText(model.getCityName());
        }
        cityNameLabel.getStyleClass().add("header-label");
        cityNameWrapper.getChildren().addAll(cityNamePlaceHolder, cityNameLabel);
        StackPane.setAlignment(cityNameLabel, Pos.CENTER_LEFT);

        StackPane tempWrapper = new StackPane();
        Rectangle temperaturePlaceHolder = new Rectangle(120, 30);
        temperaturePlaceHolder.setVisible(false);
        tempLabel = new Label("---°C");
        if(!model.isEmpty()){
            tempLabel.setText(model.getCurrent().getTemperatureC() + "°C");
        }
        tempLabel.getStyleClass().add("header-label");
        tempWrapper.getChildren().addAll(temperaturePlaceHolder, tempLabel);
        StackPane.setAlignment(tempLabel, Pos.CENTER_LEFT);

        // Rectangle datePlaceHolder = new Rectangle(150,30);
        // datePlaceHolder.setFill(Color.GRAY);
        dateSelector = setupDateSelection();

        top.add(searchBar, 0, 0, 2, 1);
        top.add(searchButton, 2, 0);
        top.add(refreshButton, 3, 0);

        top.add(cityNameWrapper, 0, 1);
        top.add(tempWrapper, 1, 1);
        top.add(dateSelector, 2, 1, 2, 1);

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

    private Button setupRefreshButton(){
        Button refreshButton = new Button();
        refreshButton.setId("search-button");

        refreshButton.setOnAction(event -> {
            if(model != null && !model.isEmpty()){
                updateWeather(model.getCityName());
            }else{
                updateWeather("");
            }
        });

        Image refreshImage = new Image(getClass().getResourceAsStream("/refreshIcon.png"));
        ImageView refreshIcon = new ImageView(refreshImage);

        refreshIcon.setFitHeight(20);
        refreshIcon.setFitWidth(20);

        refreshButton.setGraphic(refreshIcon);

        return refreshButton;
    }

    private ComboBox<String> setupDateSelection(){
        ComboBox<String> ds = new ComboBox<>();

        ds.setOnAction(event -> {
            if(dateSelector.getValue() != null){
                selectedDate = TimeConverter.wordsToDate(dateSelector.getValue());
                System.out.println(selectedDate);
                // timeCalculator();
                // updateHourTable(timeCalculator());
                updateWeather(timeCalculator());
            }
        });

        ds.setMaxSize(150, 30);

        if(model.isEmpty()){
            return ds;
        }

        for(DayClass item: model.getForecast()){
            ds.getItems().add(TimeConverter.dateToWords(item.getDate()));
        }
        ds.getSelectionModel().selectFirst();
        selectedDate = model.getForecast().get(0).getDate();

        return ds;
    }

    public static void main(String[] args){
        launch(args);
    }
}