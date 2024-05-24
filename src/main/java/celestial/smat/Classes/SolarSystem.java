package celestial.smat.Classes;

import java.util.ArrayList;

public class SolarSystem {
    public static Star star;
    public static ArrayList<CuerpoCeleste> cuerposCeleste;

    // Constructor
    public SolarSystem(Star star) {
        SolarSystem.star = star;
        cuerposCeleste = new ArrayList<>();
    }

    // Getters
    public Star getStar() {
        return star;
    }

    public ArrayList<CuerpoCeleste> getPlanets() {
        return cuerposCeleste;
    }

    // Setters
    public void setStar(Star star) {
        SolarSystem.star = star;
    }

    public void setPlanets(ArrayList<CuerpoCeleste> cuerposCeleste) {
        SolarSystem.cuerposCeleste = cuerposCeleste;
    }

    // Add planet
    public static void addCuerpoCeleste(CuerpoCeleste cuerpoCeleste) {
        cuerposCeleste.add(cuerpoCeleste);
    }
}
