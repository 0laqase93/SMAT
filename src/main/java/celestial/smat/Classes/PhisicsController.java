package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;

public class PhisicsController {
    private final boolean fisicasActivadas = true;
    public static AnimationTimer timer;
    public static Boolean animacion = false;

    static final Double UA = 149597870.7 * 1000; // Unidad astronómica (Distancia entre el sol y la tierra en km) en m
    static final Double G = 6.67430e-11; // Constante de gravitación universal en m3⋅kg−1⋅s−2
    static final Double ESCALA = 191 / UA; // 1UA = 100 píxeles en la pantalla
    static final Double ESCALARADIO = 0.4e-3;
    static final Double PASOTIEMPO = 3600.0 * 7.6;

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
                            if (cuerpoCeleste.getCircle().getLayoutX() <= 0 ||
                                    cuerpoCeleste.getCircle().getLayoutY() <= 0 ||
                                    cuerpoCeleste.getCircle().getLayoutX() >= space.getPrefWidth() ||
                                    cuerpoCeleste.getCircle().getLayoutY() >= space.getPrefHeight()) {
                                space.getChildren().remove(cuerpoCeleste.getCircle());
                                SolarSystem.cuerposCeleste.remove(cuerpoCeleste);
                                cuerpoCeleste.borrarCola();
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
