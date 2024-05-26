package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;

public class PhisicsController {
    private final boolean fisicasActivadas = true;
    public static AnimationTimer timer;
    public static Boolean animacion;

    static final Double UA = 149597870.7 * 1000; // Unidad astronómica (Distancia entre el sol y la tierra en km) en m
    static final Double G = 6.67430e-11; // Constante de gravitación universal en m3⋅kg−1⋅s−2
    static final Double ESCALA = 200 / UA; // 1UA = 100 píxeles en la pantalla
    static final Double ESCALARADIO = 0.4e-3;
    static final Double PASOTIEMPO = 3600.0 * 5.3;

    public PhisicsController() {
        if (fisicasActivadas) {
            new CollisionController();
            animacion = true;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    AnchorPane space = PrincipalController.getSpace();
                    if (!SolarSystem.cuerposCeleste.isEmpty()) {
                        for (CuerpoCeleste cuerpoCeleste : SolarSystem.cuerposCeleste) {
                            cuerpoCeleste.actualizarPosicion();
                            if (space.getLayoutX() > cuerpoCeleste.getCircle().getLayoutX()) System.out.println(cuerpoCeleste.getCircle().getLayoutX());
                            if (cuerpoCeleste.getCircle().getLayoutX() <= space.getLayoutX() ||
                                    cuerpoCeleste.getCircle().getLayoutY() <= space.getLayoutY() ||
                                    cuerpoCeleste.getCircle().getLayoutX() + cuerpoCeleste.getCircle().getRadius() * 2 >= space.getPrefWidth() ||
                                    cuerpoCeleste.getCircle().getLayoutY()  + cuerpoCeleste.getCircle().getRadius() * 2 >= space.getPrefHeight()) {
                                space.getChildren().remove(cuerpoCeleste.getCircle());
                                SolarSystem.cuerposCeleste.remove(cuerpoCeleste);
                                return;
                            }
                        }
                    }
                }
            };
            timer.start();
        }
    }
}
