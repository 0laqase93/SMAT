package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.ArrayList;

public class Fragment implements CuerpoCeleste{
    private AnchorPane space;

    private String name;
    private Double temperature;
    private Double radius;
    private Double density;
    private Double mass;

    private Double x;
    private Double y;
    private Double velocidadX;
    private Double velocidadY;

    private Circle circle;
    ArrayList<Circle> puntos;
    Polyline linea;
    Timeline timeline;

    // Constructors
    public Fragment(String name, Circle circle) {
        this.name = name;
        this.circle = circle;
    }

    public Fragment(AnchorPane space, Double distanceSol, String name, Double mass, Double temperature, Double radius, Double speed, Double density, CuerpoCeleste parent) throws InterruptedException {
        this.space = space;
        this.x = (distanceSol * PhisicsController.UA) * PhisicsController.ESCALA + parent.getX();
        this.y = parent.getY();
        this.velocidadX = 0.0;
        this.velocidadY = speed * 1000;

        this.name = name;
        this.mass = mass;
        this.temperature = temperature;
        this.radius = radius;
        this.density = density;

        this.circle = new Circle(radius * PhisicsController.ESCALARADIO, Color.DARKBLUE);
        circle.setLayoutX(this.x + parent.getCircle().getRadius());
        circle.setLayoutY(this.y);
        circle.setStroke(Color.WHITE);

        space.getChildren().add(circle);

        asignarEventos();
        crearCola();
    }

    public Fragment(AnchorPane space, Double x, Double y, String name, Double mass, Double temperature, Double radius, Double speedX, Double speedY, Double density) throws InterruptedException {
        this.space = space;
        this.x = x;
        this.y = y;
        this.velocidadX = speedX * 1000;
        this.velocidadY = speedY * 1000;

        this.name = name;
        this.mass = mass;
        this.temperature = temperature;
        this.radius = radius;
        this.density = density;

        this.circle = new Circle(radius * PhisicsController.ESCALARADIO, Color.ORANGE);
        circle.setLayoutX(this.x);
        circle.setLayoutY(this.y);
        circle.setStroke(Color.WHITE);

        space.getChildren().add(circle);

        asignarEventos();
        crearCola();
    }

    public void asignarEventos() {
        circle.setOnMouseClicked(event -> {
            PrincipalController.seleccionar(this);
            event.consume();
        });

        circle.setOnMouseEntered(event -> circle.setCursor(Cursor.HAND));
    }

    // Getters
    public String getName() {
        return name;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getRadius() {
        return radius;
    }

    public Double[] getSpeed() {
        return new Double[]{velocidadX, velocidadY};
    }

    public Double getDensity() {
        return density;
    }

    public Double getMass() {
        return mass;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    public Double getVelocidadX() {
        return velocidadX;
    }

    public Double getVelocidadY() {
        return  velocidadY;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
        double volume = (4.0 / 3.0) * Math.PI * Math.pow(this.radius * 1000, 3);
        this.mass = volume * this.density * 1000;
        circle.setRadius(radius * PhisicsController.ESCALARADIO);
    }

    public void setDensity(Double density) {
        this.density = density;
        // Cálculo del radio usando la fórmula R = (3M / 4πρ)^(1/3)
        // Convertir densidad de g/cm³ a kg/m³
        double densityInKgPerM3 = density * 1000;

        // Calcular el radio en metros
        double radiusInMeters = Math.cbrt((3 * mass) / (4 * Math.PI * densityInKgPerM3));

        // Convertir el radio a kilómetros
        double radiusInKm = radiusInMeters / 1000;
        setRadius(radiusInKm);
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setVelocidadX(Double velocidadX) {
        this.velocidadX = velocidadX;
    }

    public void setVelocidadY(Double velocidadY) {
        this.velocidadY = velocidadY;
    }

    public void actualizarPosicion() {
        Double fuerzaTotalX = 0.0;
        Double fuerzaTotalY = 0.0;

        Double[] fuerzas = new Double[2];
        Double fx = 0.0;
        Double fy = 0.0;
        for (CuerpoCeleste CuerpoCeleste : SolarSystem.cuerposCeleste) {
            if (CuerpoCeleste.equals(this)) {
                continue;
            }
            fuerzas = atraccionGravitatoria(this, CuerpoCeleste);
            fx = fuerzas[0];
            fy = fuerzas[1];
            fuerzaTotalX += fx;
            fuerzaTotalY += fy;
        }
        fuerzas = atraccionGravitatoria(this, SolarSystem.star);
        fx = fuerzas[0];
        fy = fuerzas[1];
        fuerzaTotalX += fx;
        fuerzaTotalY += fy;


        // Cálculo de la aceleración
        // f = m * a > a = f / m
        this.velocidadX += fuerzaTotalX / this.mass * PhisicsController.PASOTIEMPO;
        this.velocidadY += fuerzaTotalY / this.mass * PhisicsController.PASOTIEMPO;

        this.x += this.velocidadX / PhisicsController.PASOTIEMPO;
        this.y += this.velocidadY / PhisicsController.PASOTIEMPO;

        this.circle.setLayoutX(this.x);
        this.circle.setLayoutY(this.y);
    }

    public static Double[] atraccionGravitatoria(Fragment planet, CuerpoCeleste otro) {
        Double[] resultado = new Double[2];

        // Calcular distancia entre los cuerpos
        Double distanciaX = (otro.getX() - planet.getX()) / PhisicsController.ESCALA;
        Double distanciaY = (otro.getY() - planet.getY()) / PhisicsController.ESCALA;
        Double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

        // Fórmula de gravitación universal de Newton
        Double fuerza = PhisicsController.G * planet.getMass() * otro.getMass() / Math.pow(distancia, 2);

        // Descomposición de fuerzas (trigonometría)
        Double theta = Math.atan2(distanciaY, distanciaX); // Cáĺculo del ángulo
        Double fuerzaX = Math.cos(theta) * fuerza;
        Double fuerzaY = Math.sin(theta) * fuerza;
        resultado[0] = fuerzaX;
        resultado[1] = fuerzaY;

        return resultado;
    }

    public void actualizarVelocidades(Double[] velocidades) {
        this.velocidadX += velocidades[0];
        this.velocidadY += velocidades[1];
    }

    public void actualizarMasa(Double masa) {
        this.mass += masa;
    }

    public void crearCola() {
        int cantidad = 30; // Número de puntos en la cola
        double retraso = 0.01; // Retraso entre puntos

        puntos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            Circle circle = new Circle();
            circle.setRadius(3);
            circle.setFill(Color.WHITE);
            circle.setCenterX(this.x);
            circle.setCenterY(this.y);
            puntos.add(circle);
        }

        linea = new Polyline();
        linea.setStroke(Color.WHITE);
        space.getChildren().add(linea);
        linea.toBack();

        timeline = new Timeline(new KeyFrame(Duration.seconds(retraso), event -> {
            for (int i = puntos.size() - 1; i > 0; i--) {
                puntos.get(i).setCenterX(puntos.get(i - 1).getCenterX());
                puntos.get(i).setCenterY(puntos.get(i - 1).getCenterY());
            }
            puntos.get(0).setCenterX(this.circle.getLayoutX());
            puntos.get(0).setCenterY(this.circle.getLayoutY());

            linea.getPoints().clear();
            for (Circle punto : puntos) {
                linea.getPoints().addAll(punto.getCenterX(), punto.getCenterY());
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void borrarCola() {
        timeline.stop();
        for (Circle punto : puntos) {
            space.getChildren().remove(punto);
        }
        puntos.clear();
        linea.setFill(Color.TRANSPARENT);
        linea.getPoints().clear();
    }
}
