package celestial.smat.Classes;

import javafx.scene.shape.Circle;

public class Star implements CuerpoCeleste{
    private String name;
    private Double temperature;
    private Double radius;
    private Double speed;
    private Double density;

    private Circle circle;

    // Constructors
    public Star(String name, Double temperature, Double radius, Double speed, Double density, Circle circle) {
        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;
        this.circle = circle;
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

    public Circle getCircle() {
        return circle;
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
