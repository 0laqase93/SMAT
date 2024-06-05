package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class CollisionController {
    private final boolean activarColision = true;
    private static AnimationTimer timer;
    public static AnimationTimer smokeAnimation;

    public CollisionController() {
        if (activarColision) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (!SolarSystem.cuerposCeleste.isEmpty()) {
                        boolean colision = false;
                        for (CuerpoCeleste c1 : new ArrayList<>(SolarSystem.cuerposCeleste)) {
                            Double distancia = calcularDistancia(c1, SolarSystem.star);
                            Double sumaRadios = c1.getCircle().getRadius() + SolarSystem.star.getCircle().getRadius();

                            if (distancia <= sumaRadios) {
                                SolarSystem.cuerposCeleste.remove(c1);
                                PrincipalController.getSpace().getChildren().remove(c1.getCircle());
                                c1.borrarCola();
                                if (c1 instanceof Fragment) {
                                    continue;
                                }
                                try {
                                    crearFragmentos(c1, SolarSystem.star);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
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
                                    try {
                                        manejarColision(c1, c2);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
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

    void manejarColision(CuerpoCeleste c1, CuerpoCeleste c2) throws InterruptedException {

        if (c1.getMass() >= c2.getMass()) {
            if (c1 instanceof Fragment) {
                Double[] nuevasVelocidades = cambiarTrayectoria(c2, c1);
                c2.setVelocidadX(nuevasVelocidades[0]);
                c2.setVelocidadY(nuevasVelocidades[1]);
                c2.actualizarMasa(c1.getMass() / 2);

                PrincipalController.getSpace().getChildren().remove(c1.getCircle());
                SolarSystem.cuerposCeleste.remove(c1);
                c1.borrarCola();
                if (c1 instanceof Fragment || c2 instanceof Fragment) {
                    return;
                }
                crearFragmentos(c1, c2);
                return;
            }
            Double[] nuevasVelocidades = cambiarTrayectoria(c1, c2);
            c1.setVelocidadX(nuevasVelocidades[0]);
            c1.setVelocidadY(nuevasVelocidades[1]);
            c1.actualizarMasa(c2.getMass() / 2);

            PrincipalController.getSpace().getChildren().remove(c2.getCircle());
            SolarSystem.cuerposCeleste.remove(c2);
            c2.borrarCola();
            if (c1 instanceof Fragment || c2 instanceof Fragment) {
                return;
            }
            crearFragmentos(c2, c1);
        } else {
            Double[] nuevasVelocidades = cambiarTrayectoria(c2, c1);
            c2.setVelocidadX(nuevasVelocidades[0]);
            c2.setVelocidadY(nuevasVelocidades[1]);
            c2.actualizarMasa(c1.getMass() / 2);

            PrincipalController.getSpace().getChildren().remove(c1.getCircle());
            SolarSystem.cuerposCeleste.remove(c1);
            c1.borrarCola();
            if (c1 instanceof Fragment || c2 instanceof Fragment) {
                return;
            }
            crearFragmentos(c1, c2);
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

    void crearFragmentos(CuerpoCeleste c1, CuerpoCeleste c2) throws InterruptedException {
        Random random = new Random();
        int numFragmentos = 10 + random.nextInt(11);

        // Calcular la masa total de los fragmentos
        double fragmentMass = (c1.getMass() / 2) / numFragmentos;
        double masaFragmentoTotal = 0.0;
        for (int i = 0; i < numFragmentos; i++) {
            masaFragmentoTotal += c1.getMass() * numFragmentos / 10;
        }

        // Calcular la energía cinética total del cuerpo celeste antes de la colisión
        double energiaTotalOriginal = 0.5 * c1.getMass() * (c1.getVelocidadX() * c1.getVelocidadX() + c1.getVelocidadY() * c1.getVelocidadY()); // Energía cinética de c1

        // Calcular la energía cinética promedio por fragmento
        double energiaPromedioPorFragmento = energiaTotalOriginal / numFragmentos;

        for (int i = 0; i < numFragmentos; i++) {
            // Generar un ángulo aleatorio para cada fragmento
            double angle = 2 * Math.PI * random.nextDouble();

            // Generar una velocidad aleatoria para el fragmento
            double speed = Math.sqrt(2 * energiaPromedioPorFragmento / masaFragmentoTotal) * 0.01; // Usamos la fórmula de energía cinética: E = 0.5 * m * v^2
            double velocidadX = speed * Math.cos(angle);
            double velocidadY = speed * Math.sin(angle);

            double collisionPointX = (c1.getX() + c2.getX()) / 2;
            double collisionPointY = (c1.getY() + c2.getY()) / 2;
            // Desplazar la posición inicial del fragmento ligeramente en la dirección de su velocidad
            double initialX = collisionPointX + (c1.getCircle().getRadius() / 2) * Math.cos(angle);
            double initialY = collisionPointY + (c1.getCircle().getRadius() / 2) * Math.sin(angle);

            // Crear el satélite (fragmento) en la posición del cuerpo colisionado
            Fragment fragmento = new Fragment(PrincipalController.getSpace(), initialX, initialY, "Fragmento", fragmentMass, c1.getTemperature(), velocidadX, velocidadY, c1.getDensity()
            );

            // Añadir el fragmento al sistema solar
            SolarSystem.cuerposCeleste.add(fragmento);
            fragmento.getCircle().toBack();
        }

        for (int i = 0; i < numFragmentos; i++) {
            // Generar un ángulo aleatorio para cada fragmento
            double angle = 2 * Math.PI * random.nextDouble();

            // Generar una velocidad aleatoria para el fragmento
            double speed = Math.sqrt(2 * energiaPromedioPorFragmento / masaFragmentoTotal) * 0.01; // Usamos la fórmula de energía cinética: E = 0.5 * m * v^2
            double velocidadX = speed * Math.cos(angle);
            double velocidadY = speed * Math.sin(angle);

            double collisionPointX = (c1.getX() + c2.getX()) / 2;
            double collisionPointY = (c1.getY() + c2.getY()) / 2;
            // Desplazar la posición inicial del fragmento ligeramente en la dirección de su velocidad
            double initialX = collisionPointX + (c1.getCircle().getRadius() / 2000) * Math.cos(angle);
            double initialY = collisionPointY + (c1.getCircle().getRadius() / 2000) * Math.sin(angle);

            // Crear el satélite (fragmento) en la posición del cuerpo colisionado
            Fragment fragmento = new Fragment(PrincipalController.getSpace(), initialX, initialY, "Fragmento", fragmentMass / 10, c1.getTemperature(), velocidadX, velocidadY, c1.getDensity()
            );

            // Añadir el fragmento al sistema solar
            SolarSystem.cuerposCeleste.add(fragmento);
            fragmento.getCircle().toBack();
        }

        addSmoke(c1);
    }

    void addSmoke(CuerpoCeleste c) {
        int smokeParticles = (int)c.getCircle().getRadius() * 5;
        double x = c.getX();
        double y = c.getY();
        for (int i = 0; i < smokeParticles; i++) {
            Circle smokeParticle = new Circle(x, y, 3, Color.LIGHTGRAY);
            smokeParticle.setOpacity(Math.random());
            PrincipalController.getSpace().getChildren().add(smokeParticle);

            smokeAnimation = new AnimationTimer() {
                double deltaX = (Math.random() - 0.5) * 5;
                double deltaY = (Math.random() - 0.5) * 5;
                double deltaOpacity = Math.random() * 0.05;

                @Override
                public void handle(long now) {
                    smokeParticle.setCenterX(smokeParticle.getCenterX() + deltaX);
                    smokeParticle.setCenterY(smokeParticle.getCenterY() + deltaY);
                    smokeParticle.setOpacity(smokeParticle.getOpacity() - deltaOpacity);

                    if (smokeParticle.getCenterX() <= 0 || smokeParticle.getCenterY() <= 0 || smokeParticle.getCenterX() >= PrincipalController.getSpace().getPrefWidth() || smokeParticle.getCenterY() >= PrincipalController.getSpace().getPrefHeight()) {
                        PrincipalController.getSpace().getChildren().remove(smokeParticle);
                        this.stop();
                    }

                    if (smokeParticle.getOpacity() <= 0) {
                        PrincipalController.getSpace().getChildren().remove(smokeParticle);
                        this.stop();
                    }
                }
            };
            smokeAnimation.start();
        }
    }

}
