package celestial.smat.Classes;

import javafx.scene.shape.Circle;

public class Star implements CuerpoCeleste{
    private String name;
    private String temperature;
    private Circle circle;

    // Constructors
    public Star(String name, String temperature, Circle circle) {
        this.name = name;
        this.temperature = temperature;
        this.circle = circle;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getTemperature() {
        return temperature;
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

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
