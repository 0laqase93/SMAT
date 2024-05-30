package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Star implements CuerpoCeleste{
    private AnchorPane space;

    private String name;
    private Double temperature;
    private Double radius;
    private Double speed;
    private Double density;
    private Double mass;

    private Double x;
    private Double y;

    private Circle circle;

    // Constructors
    public Star(AnchorPane space, String name, Double temperature, Double radius, Double density, Double mass, Double speed) {
        this.space = space;
        this.x = 0.0;
        this.y = 0.0;

        this.name = name;
        this.mass = mass;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;


        this.circle = new Circle(radius * PhisicsController.ESCALARADIO * 0.03, Color.YELLOW);
        this.circle.setLayoutX(x + space.getPrefWidth() / 2);
        this.circle.setLayoutY(y + space.getPrefHeight() / 2);
        this.circle.setStroke(Color.WHITE);

        PrincipalController ps = new PrincipalController();

        space.getChildren().add(this.circle);

        circle.setOnMouseClicked(event -> {
            PrincipalController.seleccionar(this);
            event.consume();
        });

        circle.setOnMouseEntered(event -> circle.setCursor(Cursor.HAND));
    }

    // Getters
    public String getName() {
        return name;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getRadius() {
        return radius;
    }

    public Double[] getSpeed() {
        return new Double[]{0.0, 0.0};
    }

    public Double getDensity() {
        return density;
    }

    public Double getMass() {
        return mass;
    }

    public Circle getCircle() {
        return circle;
    }

    public Double getY() {
        return y + space.getPrefHeight() / 2;
    }

    public Double getX() {
        return x + space.getPrefWidth() / 2;
    }

    public Double getVelocidadX() {
        return speed;
    }

    public Double getVelocidadY() {
        return speed;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(Double temperature) {
        if (temperature < 2500) {
            circle.setFill(Color.rgb(160, 84, 12));
        } else if (temperature < 3500) {
            circle.setFill(Color.RED);
        } else if (temperature < 5000) {
            circle.setFill(Color.ORANGE);
        } else if (temperature < 6000) {
            circle.setFill(Color.YELLOW);
        } else if (temperature < 7500) {
            circle.setFill(Color.WHITE);
        } else {
            circle.setFill(Color.CYAN);
        }
        this.temperature = temperature;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setVelocidadX(Double speed) {
        this.speed = speed;
    }

    public void setVelocidadY(Double speed) {
        this.speed = speed;
    }

    public void actualizarMasa(Double masa) {
        this.mass += masa;
    }

    public void actualizarPosicion() { }
}
