package celestial.smat.Classes;

import celestial.smat.PrincipalController;
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
    private Double distanceSol;

    private ArrayList<CuerpoCeleste> satellites = new ArrayList<>();

    private Double x;
    private Double y;
    private Double velocidadX;
    private Double velocidadY;

    private Boolean vistaOrbita;

    private Circle circle;

    // Constructors
    public Planet(String name, Circle circle) {
        this.name = name;
        this.circle = circle;
    }

    public Planet(AnchorPane space, Double distanceSol, String name, Double temperature, Double radius, Double speed, Double density, CuerpoCeleste parent) {
        this.space = space;
        this.x = (distanceSol * PhisicsController.UA) * PhisicsController.ESCALA + parent.getX() + parent.getCircle().getRadius();
        this.y = parent.getY();

        this.name = name;
        this.temperature = temperature;
        this.radius = radius;
        this.speed = speed;
        this.density = density;

        this.circle = new Circle(radius * PhisicsController.ESCALARADIO, Color.DARKBLUE);
        circle.setLayoutX(this.x);
        circle.setLayoutY(this.y);
        circle.setStroke(Color.WHITE);
        //circle.setStroke(PrincipalController.getSelectedColor());
        System.out.println(this.x + " " + this.y);

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

    public void actualizarPosicion() {
        Double xNueva = this.x * PhisicsController.ESCALA + space.getPrefWidth() / 2;
        Double yNueva = this.y * PhisicsController.ESCALA + space.getPrefHeight() / 2;


    }
}
