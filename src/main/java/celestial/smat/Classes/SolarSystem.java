package celestial.smat.Classes;

import java.util.ArrayList;

public class SolarSystem {
    public static Star star;
    public static ArrayList<Planet> planets;

    // Constructor
    public SolarSystem(Star star) {
        SolarSystem.star = star;
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
        SolarSystem.star = star;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        SolarSystem.planets = planets;
    }

    // Add planet
    public static void addPlanet(Planet planet) {
        planets.add(planet);
    }
}
