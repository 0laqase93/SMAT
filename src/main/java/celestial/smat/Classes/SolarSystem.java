package celestial.smat.Classes;

import java.util.ArrayList;

public class SolarSystem {
    private Star star;
    private ArrayList<Planet> planets;

    // Constructor
    public SolarSystem(Star star) {
        this.star = star;
        planets = new ArrayList<>();
    }

    // Getters
    public Star getStar() {
        return star;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    // Setters
    public void setStar(Star star) {
        this.star = star;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    // Add planet
    public void addPlanet(Planet planet) {
        planets.add(planet);
    }
}
