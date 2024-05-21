package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.ArrayList;

public class Planet implements CuerpoCeleste{
    private AnchorPane space;

    private String name;
    private Double temperature;
    private Double radius;
    private Double speed;
    private Double density;
    private Double mass;

    private Double distanceSol;

    private ArrayList<CuerpoCeleste> satellites = new ArrayList<>();

    private Double x;
    private Double y;
    private Double velocidadX;
    private Double velocidadY;

    private Double fuerzaX;
    private Double fuerzaY;

    private ArrayList<Point2D> orbit;

    private Boolean vistaOrbita;

    private Circle circle;

    // Constructors
    public Planet(String name, Circle circle) {
        this.name = name;
        this.circle = circle;
    }

    public Planet(AnchorPane space, Double distanceSol, String name, Double mass, Double temperature, Double radius, Double speed, Double density, CuerpoCeleste parent) {
        this.space = space;
        this.x = (distanceSol * PhisicsController.UA) * PhisicsController.ESCALA + parent.getX() + parent.getCircle().getRadius();
        this.y = parent.getY();
        this.velocidadX = 0.0;
        this.velocidadY = speed * 2500;

        this.name = name;
        this.mass = mass;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;

        this.circle = new Circle(radius * PhisicsController.ESCALARADIO, Color.DARKBLUE);
        circle.setLayoutX(this.x);
        circle.setLayoutY(this.y);
        circle.setStroke(Color.WHITE);
        //circle.setStroke(PrincipalController.getSelectedColor());

        space.getChildren().add(circle);

        orbit = new ArrayList<>();
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

    public Double getSpeed() {
        return speed;
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

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setDistanceSol(Double distanceSol) {
        this.distanceSol = distanceSol;
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
        for (Planet planet : SolarSystem.planets) {
            if (planet.equals(this)) {
                continue;
            }
            fuerzas = atraccionGravitatoria(this, planet);
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


        // MCUA
        // f = m * a => a = f / m
        this.velocidadX += fuerzaTotalX / this.mass * PhisicsController.PASOTIEMPO;
        this.velocidadY += fuerzaTotalY / this.mass * PhisicsController.PASOTIEMPO;

        this.x += this.velocidadX / PhisicsController.PASOTIEMPO;
        this.y += this.velocidadY / PhisicsController.PASOTIEMPO;

        this.circle.setLayoutX(this.x);
        this.circle.setLayoutY(this.y);

        this.orbit.add(new Point2D(this.x, this.y));
    }

    public static Double[] atraccionGravitatoria(Planet planeta, CuerpoCeleste otro) {
        Double[] resultado = new Double[2];

        // Calcular distancia entre los cuerpos
        Double distanciaX = (otro.getX() - planeta.getX()) / PhisicsController.ESCALA;
        Double distanciaY = (otro.getY() - planeta.getY()) / PhisicsController.ESCALA;
        Double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

        if (otro instanceof Star) {
            planeta.setDistanceSol(distancia);
        }

        // Fórmula de gravitación universal de Newton
        Double fuerza = PhisicsController.G * planeta.getMass() * otro.getMass() / Math.pow(distancia, 2);

        // Descomposición de fuerzas (trigonometría)
        Double theta = Math.atan2(distanciaY, distanciaX); // Cáĺculo del ángulo
        Double fuerzaX = Math.cos(theta) * fuerza;
        Double fuerzaY = Math.sin(theta) * fuerza;
        resultado[0] = fuerzaX;
        resultado[1] = fuerzaY;

        return resultado;
    }
}
