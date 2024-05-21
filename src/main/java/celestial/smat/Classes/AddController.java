package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class AddController {
    private Boolean mostrado;

    private final AnchorPane menu;
    private final AnchorPane space;

    private Group addSelected;

    int alturaSaliente = 20;
    int cantidadMostrado = 100;
    int separacion = 60;
    private final Label flecha;

    public AddController(AnchorPane space) {
        this.space = space;

        double escondidoY = space.getPrefHeight() - alturaSaliente;
        double mostradoY = escondidoY - cantidadMostrado;

        mostrado = false;

        menu = new AnchorPane();
        menu.setLayoutX(0);
        menu.setLayoutY(escondidoY);
        menu.setPrefSize(space.getPrefWidth(), cantidadMostrado + alturaSaliente);

        Rectangle saliente = new Rectangle();
        saliente.setWidth(40);
        saliente.setLayoutX((menu.getPrefWidth() - saliente.getWidth()) / 2);
        saliente.setHeight(alturaSaliente);
        saliente.setStroke(Color.WHITE);
        saliente.setArcHeight(10);
        saliente.setArcWidth(10);
        saliente.setFill(Color.valueOf("#1c2833"));
        saliente.setOnMouseClicked(event -> mostrarMenu());
        saliente.setOnMouseEntered(event -> saliente.setCursor(Cursor.HAND));

        flecha = new Label();
        flecha.setText("▲");
        flecha.setTextFill(Color.WHITE);
        flecha.setLayoutY(0);
        flecha.setLayoutX(menu.getPrefWidth() / 2 - 5);
        flecha.setOnMouseClicked(event -> mostrarMenu());
        flecha.setOnMouseEntered(event -> flecha.setCursor(Cursor.HAND));

        Rectangle fondo = new Rectangle();
        fondo.setLayoutY(alturaSaliente);
        fondo.setWidth(menu.getPrefWidth());
        fondo.setHeight(cantidadMostrado);
        fondo.setStroke(Color.WHITE);
        fondo.setArcHeight(10);
        fondo.setArcWidth(10);
        fondo.setFill(Color.valueOf("#1c2833"));

        ArrayList<CuerpoCeleste> opciones = new ArrayList<>();

        Circle planetCircle = new Circle(40, Color.DARKBLUE);
        planetCircle.setStroke(Color.WHITE);
        planetCircle.setLayoutY(planetCircle.getRadius() + alturaSaliente + (fondo.getHeight() - planetCircle.getRadius() * 2) / 2);
        planetCircle.setLayoutX(separacion + planetCircle.getRadius());
        Planet planet = new Planet("Planeta", planetCircle);

        Label planetLabel = new Label();
        planetLabel.setText(planet.getName());
        planetLabel.setTextFill(Color.WHITE);
        planetLabel.setStyle("-fx-font-family: monospace;");

        Group planetGroup = new Group(planetCircle, planetLabel);
        planetGroup.setOnMouseEntered(event -> planetGroup.setCursor(Cursor.HAND));
        planetGroup.setOnMouseClicked(event -> selected(planetGroup));

        Platform.runLater(() -> {
            planetLabel.setLayoutX(planetCircle.getLayoutX() - planetLabel.getWidth() / 2);
            planetLabel.setLayoutY(planetCircle.getLayoutY() - planetLabel.getHeight() / 2);
        });

        Circle satelliteCircle = new Circle(40, Color.DARKRED);
        satelliteCircle.setStroke(Color.WHITE);
        satelliteCircle.setLayoutY(satelliteCircle.getRadius() + alturaSaliente + (fondo.getHeight() - satelliteCircle.getRadius() * 2) / 2);
        satelliteCircle.setLayoutX(planetCircle.getLayoutX() + satelliteCircle.getRadius() + separacion);
        satelliteCircle.setOnMouseEntered(event -> satelliteCircle.setCursor(Cursor.HAND));
        Satellite satellite = new Satellite("Satélite", satelliteCircle);

        Label satelliteLabel = new Label();
        satelliteLabel.setText(satellite.getName());
        satelliteLabel.setTextFill(Color.WHITE);
        satelliteLabel.setStyle("-fx-font-family: monospace;");

        Group satelliteGroup = new Group(satelliteCircle, satelliteLabel);
        satelliteGroup.setOnMouseEntered(event -> satelliteGroup.setCursor(Cursor.HAND));
        satelliteGroup.setOnMouseClicked(event -> selected(satelliteGroup));

        Platform.runLater(() -> {
            satelliteLabel.setLayoutX(satelliteCircle.getLayoutX() - satelliteLabel.getWidth() / 2);
            satelliteLabel.setLayoutY(satelliteCircle.getLayoutY() - satelliteLabel.getHeight() / 2);
        });

        opciones.add(planet);
        opciones.add(satellite);

        menu.getChildren().addAll(saliente, flecha, fondo, planetGroup, satelliteGroup);
    }

    public AnchorPane getMenu() {
        return menu;
    }

    public void mostrarMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), menu);

        if (mostrado) {
            flecha.setText("▲");
            transition.setToY(0);
        } else {
            menu.toFront();
            flecha.setText("▼");
            transition.setToY(-cantidadMostrado);
        }

        transition.play();

        mostrado = !mostrado;
    }

    public void selected(Group object) {
        Circle circle = (Circle) object.getChildren().get(0);
        Label label = (Label) object.getChildren().get(1);
        if (addSelected != null) {
            ((Circle) addSelected.getChildren().get(0)).setStroke(Color.WHITE);
        }

        addSelected = object;

        ((Circle) object.getChildren().get(0)).setStroke(PrincipalController.getSelectedColor());

        switch (label.getText()) {
            case "Planeta":
                createPlanet();
                break;
            case "Satélite":
                createSatellite();
                break;
            case null,
                 default:;
        }
    }

    public void createPlanet() {

        space.setOnMouseClicked(event -> {
            Circle circle = new Circle(20, Color.DARKBLUE);
            circle.setLayoutX(event.getX());
            circle.setLayoutY(event.getY());
            space.getChildren().add(circle);
        });
    }

    public void createSatellite() {
        space.setOnMouseClicked(event -> {
            Circle circle = new Circle(20, Color.DARKRED);
            circle.setLayoutX(event.getX());
            circle.setLayoutY(event.getY());
            space.getChildren().add(circle);
        });
    }
}