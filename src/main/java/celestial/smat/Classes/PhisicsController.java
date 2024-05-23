package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;

public class PhisicsController {
    public static AnimationTimer timer;
    public static Boolean animacion;

    static final Double UA = 149597870.7 * 1000; // Unidad astronómica (Distancia entre el sol y la tierra en km) en m
    static final Double G = 6.67430e-11; // Constante de gravitación universal en m3⋅kg−1⋅s−2
    static final Double ESCALA = 100 / UA; // 1UA = 100 píxeles en la pantalla
    static final Double ESCALARADIO = 0.3e-3;
    static final Double PASOTIEMPO = 3600.0 * 10.75;

    public PhisicsController() {
        new CollisionController();
        animacion = true;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                AnchorPane space = PrincipalController.getSpace();
                if (!SolarSystem.planets.isEmpty()) {
                    for (Planet planet : SolarSystem.planets) {
                        planet.actualizarPosicion();
                        System.out.println(planet.getCircle().getLayoutX() + " " + planet.getCircle().getLayoutY());
                        System.out.println(space.getLayoutX() + " " + space.getLayoutY() + " " + space.getPrefWidth() + " " + space.getPrefHeight());
                        if (space.getLayoutX() > planet.getCircle().getLayoutX()) System.out.println(planet.getCircle().getLayoutX());
                        if (planet.getCircle().getLayoutX() <= space.getLayoutX() ||
                                planet.getCircle().getLayoutY() <= space.getLayoutY() ||
                                planet.getCircle().getLayoutX() + planet.getCircle().getRadius() * 2 >= space.getPrefWidth() ||
                                planet.getCircle().getLayoutY()  + planet.getCircle().getRadius() * 2 >= space.getPrefHeight()) {
                            space.getChildren().remove(planet.getCircle());
                            SolarSystem.planets.remove(planet);
                            break;
                        }
                    }
                }
            }
        };
        timer.start();
    }
}
