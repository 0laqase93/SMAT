package celestial.smat.Classes;

public class Satellite {
    private String name;
    private String temperature;
    private Double radius;
    private Planet planet;

    // Constructors
    public Satellite(String name, String temperature, Double radius, Planet planet) {
        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.planet = planet;
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
        return planet;
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

    public void setStar(Planet planet) {
        this.planet = planet;
    }
}
