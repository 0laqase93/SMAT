package celestial.smat.Classes;

import javafx.animation.Timeline;

public class PhisicsController {
    Timeline gravitatonalAnimation;

    static final Double UA = 149597870.7 * 1000; // Unidad astronómica (Distancia entre el sol y la tierra en km) en M
    static final Double G = 6.67430e-11; // Constante de gravitación universal en m3⋅kg−1⋅s−2
    static final Double ESCALA = 250 / UA; // 1UA = 100 píxeles en la pantalla
    static final Double ESCALARADIO = 0.3e-3;
    static final Double PASOTIEMPO = 3600.0 * 24.0; // 1 día

    public PhisicsController() {
        gravitatonalAnimation = new Timeline();
    }

}
