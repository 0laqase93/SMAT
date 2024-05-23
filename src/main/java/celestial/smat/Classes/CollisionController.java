package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

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
                        ArrayList<Planet> colisionados = new ArrayList<>();
                        for (Planet p1 : new ArrayList<>(SolarSystem.planets)) {
                            // Calcular distancia entre el sol y el planeta
                            Double distancia = calcularDistancia(p1, SolarSystem.star);
                            Double sumaRadios = p1.getCircle().getRadius() + SolarSystem.star.getCircle().getRadius();
                            if (distancia <= sumaRadios) {
                                colisionados.add(p1);
                            }

                            // Calcular distancia entre los demás planetas
                            for (Planet p2 : new ArrayList<>(SolarSystem.planets)) {
                                if (p2.equals(p1)) {
                                    continue;
                                }

                                distancia = calcularDistancia(p1, p2);
                                sumaRadios = p1.getCircle().getRadius() + p2.getCircle().getRadius();
                                if (distancia <= sumaRadios) {
                                    colisionados.add(p1);
                                    colisionados.add(p2);
                                }
                            }
                        }

                        Platform.runLater(() -> {
                            for (Planet p : colisionados) {
                                SolarSystem.planets.remove(p);
                                PrincipalController.getSpace().getChildren().remove(p.getCircle());
                            }
                        });
                    }
                }
            };
            timer.start();
        }
    }

    Double calcularDistancia(CuerpoCeleste c1, CuerpoCeleste c2) {
        // d = Raiz((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(c2.getX() - c1.getX(), 2) + Math.pow(c2.getY() - c1.getY(), 2));
    }
}
