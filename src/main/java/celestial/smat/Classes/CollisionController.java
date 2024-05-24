package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class CollisionController {
    private final boolean activarColision = true;
    private static AnimationTimer timer;

    public CollisionController() {
        if (activarColision) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (!SolarSystem.cuerposCeleste.isEmpty()) {
                        boolean colision = false;
                        for (CuerpoCeleste c1 : new ArrayList<>(SolarSystem.cuerposCeleste)) {
                            // Calcular distancia entre el sol y el planeta
                            Double distancia = calcularDistancia(c1, SolarSystem.star);
                            Double sumaRadios = c1.getCircle().getRadius() + SolarSystem.star.getCircle().getRadius();
                            if (distancia <= sumaRadios) {
                                SolarSystem.cuerposCeleste.remove(c1);
                                PrincipalController.getSpace().getChildren().remove(c1.getCircle());
                                continue;
                            }

                            // Calcular distancia entre los demás planetas
                            for (CuerpoCeleste c2 : new ArrayList<>(SolarSystem.cuerposCeleste)) {
                                if (c2.equals(c1)) {
                                    continue;
                                }

                                distancia = calcularDistancia(c1, c2);
                                sumaRadios = c1.getCircle().getRadius() + c2.getCircle().getRadius();
                                if (distancia <= sumaRadios) {
                                    manejarColision(c1, c2);
                                    colision = true;
                                    break;
                                }
                            }

                            if (colision) {
                                break;
                            }
                        }
                    }
                }
            };
            timer.start();
        }
    }

    void manejarColision(CuerpoCeleste c1, CuerpoCeleste c2) {
        if (c1.getMass() >= c2.getMass()) {
            Double[] nuevasVelocidades = cambiarTrayectoria(c1, c2);
            c1.setVelocidadX(nuevasVelocidades[0]);
            c1.setVelocidadY(nuevasVelocidades[1]);
            c1.actualizarMasa(c2.getMass());

            PrincipalController.getSpace().getChildren().remove(c2.getCircle());
            SolarSystem.cuerposCeleste.remove(c2);
        } else {
            Double[] nuevasVelocidades = cambiarTrayectoria(c2, c1);
            c2.setVelocidadX(nuevasVelocidades[0]);
            c2.setVelocidadY(nuevasVelocidades[1]);
            c2.actualizarMasa(c1.getMass());

            PrincipalController.getSpace().getChildren().remove(c1.getCircle());
            SolarSystem.cuerposCeleste.remove(c1);
        }
    }

    Double calcularDistancia(CuerpoCeleste c1, CuerpoCeleste c2) {
        // d = sqrt((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2));
    }

    Double[] cambiarTrayectoria(CuerpoCeleste p1, CuerpoCeleste p2) {
        // Velocidades iniciales en m/s
        double v1x = p1.getVelocidadX() * 1000;
        double v1y = p1.getVelocidadY() * 1000;
        double v2x = p2.getVelocidadX() * 1000;
        double v2y = p2.getVelocidadY() * 1000;

        // Masas
        double m1 = p1.getMass();
        double m2 = p2.getMass();

        // Nuevas velocidades después de la colisión
        double v1xf = (m1 * v1x + m2 * v2x) / (m1 + m2);
        double v1yf = (m1 * v1y + m2 * v2y) / (m1 + m2);

        // Convertir de nuevo a km/s
        return new Double[]{v1xf / 1000, v1yf / 1000};
    }

    void crearFragmentos() {

    }
}
