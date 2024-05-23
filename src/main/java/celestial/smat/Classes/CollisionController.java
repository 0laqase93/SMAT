package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CollisionController {
    private boolean activarColision = true;
    private static AnimationTimer timer;

    public CollisionController() {
        if (activarColision) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (!SolarSystem.planets.isEmpty()) {
                        boolean colision = false;
                        for (Planet p1 : new ArrayList<>(SolarSystem.planets)) {
                            // Calcular distancia entre el sol y el planeta
                            Double distancia = calcularDistancia(p1, SolarSystem.star);
                            Double sumaRadios = p1.getCircle().getRadius() + SolarSystem.star.getCircle().getRadius();
                            if (distancia <= sumaRadios) {
                                SolarSystem.planets.remove(p1);
                                PrincipalController.getSpace().getChildren().remove(p1.getCircle());
                            }

                            // Calcular distancia entre los demás planetas
                            for (Planet p2 : new ArrayList<>(SolarSystem.planets)) {
                                if (p2.equals(p1)) {
                                    continue;
                                }

                                distancia = calcularDistancia(p1, p2);
                                sumaRadios = p1.getCircle().getRadius() + p2.getCircle().getRadius();
                                if (distancia <= sumaRadios) {
                                    manejarColision(p1, p2);
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

    void manejarColision(Planet p1, Planet p2) {
        if (p1.getMass() >= p2.getMass()) {
            Double[] velocidades = cambiarTrayectoria(p1, p2);
            p1.actualizarVelocidades(velocidades);

            PrincipalController.getSpace().getChildren().remove(p2.getCircle());
            SolarSystem.planets.remove(p2);
        } else {
            Double[] velocidades = cambiarTrayectoria(p1, p2);
            p2.actualizarVelocidades(velocidades);

            PrincipalController.getSpace().getChildren().remove(p1.getCircle());
            SolarSystem.planets.remove(p1);
        }
    }

    Double calcularDistancia(CuerpoCeleste c1, CuerpoCeleste c2) {
        // d = Raiz((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2));
    }

    Double[] cambiarTrayectoria(Planet p1, Planet p2) {
        // Conservación del Momento
        // m1⋅v1inicial+m2⋅v2inicial=m1⋅v1final+m2⋅v2final
        Double velocidadX = ((p1.getMass() - p2.getMass()) * (p1.getVelocidadX() * 1000) + 2 * p2.getMass() * (p2.getVelocidadX() * 1000)) / (p1.getMass() + p2.getMass());
        Double velocidadY = ((p1.getMass() - p2.getMass()) * (p1.getVelocidadY() * 1000) + 2 * p2.getMass() * (p2.getVelocidadY() * 1000)) / (p1.getMass() + p2.getMass());

        return new Double[]{(velocidadX / 1000), (velocidadY / 1000)};
    }
}
