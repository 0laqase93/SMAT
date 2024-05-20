package celestial.smat.Classes;

import celestial.smat.PrincipalController;
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
    private Double distanceSol;

    private final Star star;
    private ArrayList<CuerpoCeleste> satellites = new ArrayList<>();

    private Double x;
    private Double y;
    private Double velocidadX;
    private Double velocidadY;

    private Boolean vistaOrbita;

    private Circle circle;
    private final Ellipse orbit;

    // Constructors
    public Planet(String name, Circle circle) {
        this.star = null;
        this.orbit = null;

        this.name = name;
        this.circle = circle;
    }

    public Planet(AnchorPane space, Double distanceSol, String name, Double temperature, Double radius, Double speed, Double density, Star star, Circle parent, Ellipse orbit) {
        this.space = space;
        this.x = parent.getLayoutX() + parent.getRadius() + (distanceSol * PhisicsController.UA) * PhisicsController.ESCALA;
        this.y = parent.getLayoutY();

        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;
        this.star = star;

        this.circle = new Circle(radius * PhisicsController.ESCALARADIO, Color.DARKBLUE);
        circle.setLayoutX(this.x);
        circle.setLayoutY(this.y);
        circle.setStroke(PrincipalController.getSelectedColor());
        System.out.println(this.x + " " + this.y);

        this.orbit = orbit;

        space.getChildren().add(circle);
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

    public Star getStar() {
        return star;
    }

    public Circle getCircle() {
        return circle;
    }

    public Ellipse getOrbit() {
        return orbit;
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

    public void actualizarPosicion() {
        Double xNueva = this.x * PhisicsController.ESCALA + space.getPrefWidth() / 2;
        Double yNueva = this.y * PhisicsController.ESCALA + space.getPrefHeight() / 2;


    }
}
