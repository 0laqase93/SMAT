package celestial.smat.Classes;

public class Planet {
    private String name;
    private String temperature;
    private Double radius;
    private Star star;

    // Constructors
    public Planet(String name, String temperature, Double radius, Star star) {
        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.star = star;
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

    public Star getStar() {
        return star;
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

    public void setStar(Star star) {
        this.star = star;
    }
}
