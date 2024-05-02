package celestial.smat.Classes;

public class Star {
    private String name;
    private String temperature;
    private Double radius;

    // Constructors
    public Star(String name, String temperature, Double radius) {
        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
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
}
