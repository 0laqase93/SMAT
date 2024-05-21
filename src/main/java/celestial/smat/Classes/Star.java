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
    public Star(AnchorPane space, String name, Double mass, Double temperature, Double radius, Double speed, Double density) {
        this.space = space;
        this.x = 0.0;
        this.y = 0.0;

        this.name = name;
        this.mass = mass;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;


        this.circle = new Circle(radius * PhisicsController.ESCALARADIO * 0.3, Color.YELLOW);
        System.out.println(this.circle.getRadius());
        this.circle.setLayoutX(x + space.getPrefWidth() / 2);
        this.circle.setLayoutY(y + space.getPrefHeight() / 2);
        this.circle.setStroke(Color.WHITE);

        PrincipalController ps = new PrincipalController();

        this.circle.setOnMouseClicked(event -> {
            ps.seleccionar(this);
            event.consume();
        });

        this.circle.setOnMouseEntered(event -> {
            if (ps.getSelected() != null) {
                if (ps.getCreateMode() && ps.getSelected() != this.circle) {
                    this.circle.setCursor(Cursor.WAIT);
                } else {
                    this.circle.setCursor(Cursor.HAND);
                }
            } else {
                this.circle.setCursor(Cursor.HAND);
            }
        });

        space.getChildren().add(this.circle);

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

    public Double getSpeed() {
        return speed;
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(Double temperature) {
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
}
