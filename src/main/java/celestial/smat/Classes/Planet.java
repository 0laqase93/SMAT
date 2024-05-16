package celestial.smat.Classes;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public class Planet implements CuerpoCeleste{
    private String name;
    private String temperature;
    private Star star;
    private Circle circle;
    private Ellipse orbitCircle;
    private ArrayList<Satellite> satellites = new ArrayList<>();

    // Constructors
    public Planet(String name, String temperature, Star star, Circle circle, Ellipse orbitCircle) {
        this.name = name;
        this.temperature = temperature;
        this.star = star;
        this.circle = circle;
        this.orbitCircle = orbitCircle;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
    }

    public Star getStar() {
        return star;
    }

    public Circle getCircle() {
        return circle;
    }

    public Ellipse getOrbitCircle() {
        return orbitCircle;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setOrbitCircle(Ellipse orbitCircle) {
        this.orbitCircle = orbitCircle;
    }
}
