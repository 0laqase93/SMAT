package celestial.smat.Classes;

import javafx.scene.shape.Circle;

public class Satellite implements CuerpoCeleste {
    private String name;
    private String temperature;
    private Double radius;
    private Planet parent;
    private Circle circle;

    // Constructors
    public Satellite(String name, String temperature, Double radius, Planet parent, Circle circle) {
        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.parent = parent;
        this.circle = circle;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public Double getRadius() {
        return radius;
    }

    public Planet getStar() {
        return parent;
    }

    public Circle getCircle() {
        return circle;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public void setStar(Planet parent) {
        this.parent = parent;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
