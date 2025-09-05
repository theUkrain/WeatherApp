package weather_application;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;

public class DayTimeWidget {
    private Pane root;
    private StackPane sun;
    private double bigRadius;
    private double length;

    public DayTimeWidget(double length){
        this.length = length;
        root = new Pane();
        
        bigRadius = length / (Math.sqrt(2) + 0.14);

        Arc arc = new Arc();
        arc.setCenterX(length/2);
        arc.setCenterY(bigRadius * (1 + 0.07));
        arc.setRadiusX(bigRadius);
        arc.setRadiusY(bigRadius);
        arc.setFill(null);
        arc.setStroke(Color.rgb(114, 201, 234));
        arc.setType(ArcType.OPEN);
        arc.setStrokeWidth(length * 0.019);
        arc.setStartAngle(45);
        arc.setLength(90);

        sun = new StackPane();

        Circle sunHolder = new Circle(bigRadius*0.07);
        sunHolder.setVisible(false);

        Image sunImage = new Image(getClass().getResourceAsStream("/sunIcon.png"));
        ImageView sunIcon = new ImageView(sunImage);
        sunIcon.setFitHeight(bigRadius*0.14);
        sunIcon.setFitWidth(bigRadius*0.14);

        sun.getChildren().addAll(sunHolder, sunIcon);
        sun.setLayoutX(bigRadius * (0.92 - Math.cos(Math.toRadians(45))) - (bigRadius - length/2));
        sun.setLayoutY(bigRadius * (0.99 - Math.sin(Math.toRadians(45))));

        update(0);


        root.getChildren().addAll(arc, sun);
    }

    public Node getWidget(){
        return root;
    }

    public void update(double val){
        double ang = 45 + 90*val;
        sun.setLayoutX(bigRadius * (0.92 - Math.cos(Math.toRadians(ang))) - (bigRadius - length/2));
        sun.setLayoutY(bigRadius * (0.99 - Math.sin(Math.toRadians(ang))));
    }
}