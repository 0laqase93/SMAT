package celestial.smat;


import celestial.smat.Classes.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

public class PrincipalController {

    public static SolarSystem solarSystem;

    private static CuerpoCeleste selected;

    private DataBase db;

    private static Info infoPane;

    private Button loadButton;

    private Button addButton;

    private Button removeButton;

    private Circle orbitModify;

    static Color selectedColor;

    @FXML
    private AnchorPane window;

    private static AnchorPane space;

    private Button playAnimationButton;

    public CuerpoCeleste getSelected() {
        return selected;
    }

    public static Color getSelectedColor() {
        return selectedColor;
    }

    public static AnchorPane getSpace() {
        return space;
    }

    public AnchorPane getWindow() {
        return this.window;
    }

    void controlarAnimacion() {
        if (PhisicsController.animacion) {
            playAnimationButton.setText("■");
            PhisicsController.timer.start();
        } else {
            playAnimationButton.setText("▶");
            PhisicsController.timer.stop();
        }

        PhisicsController.animacion = !PhisicsController.animacion;
    }

    void deseleccionar() {
        if (selected != null) { // Para evitar problemas.
            selected.getCircle().setStroke(Color.WHITE);
            selected = null;
            infoPane.desactivar();
            space.getChildren().remove(addButton);
            space.getChildren().remove(removeButton);
        }
    }

    public static void seleccionar(CuerpoCeleste object) {
        // Variables para el manejo de gráficos.
        Circle selectedCircle = object.getCircle();

        if (selected != null) {
            selected.getCircle().setStroke(Color.WHITE);
        }

        selected = object;
        selectedCircle.setStroke(selectedColor);

        // Mostrar datos del planeta.
        infoPane.select(object);
        mostrarInfo();

        // Setear de nuevo el círculo después de la modificación.
        object.setCircle(selectedCircle);
    }

    void crear(CuerpoCeleste object) {
        // Panel de creación delante (Para evitar problemas)
        infoPane.getInfoPane().toFront();

        // Variables para el manejo de gráficos.
        Circle parent = object.getCircle();

        // Parar animación
        playAnimationButton.setText("⏵");
        playAnimationButton.setTextFill(Color.WHITE);
        playAnimationButton.setDisable(true);

        // Creación de planeta.
        Circle circle = new Circle();
        circle.setRadius(parent.getRadius() / 2);
        double num = parent.getLayoutX() + parent.getRadius() * 3;
        circle.setLayoutX(num);
        circle.setLayoutY(parent.getLayoutY());
        circle.setFill(Color.BLUE);
        circle.setStroke(selectedColor);

        // Creación de la órbita al objeto padre.
        Ellipse orbit = new Ellipse();
        double initialRadius = circle.getLayoutX() - parent.getLayoutX();
        orbit.setRadiusX(initialRadius);
        orbit.setRadiusY(initialRadius);
        orbit.setLayoutX(parent.getLayoutX());
        orbit.setLayoutY(parent.getLayoutY());
        orbit.setFill(Color.TRANSPARENT);
        orbit.setStroke(Color.WHITE);

        // Crear el botón para modificar la órbita de forma vertical.
        orbitModify = new Circle();
        orbitModify.setRadius(7);
        orbitModify.setLayoutX(parent.getLayoutX());
        orbitModify.setLayoutY(parent.getLayoutY() - orbit.getRadiusY());
        orbitModify.setFill(Color.BLACK);
        orbitModify.setStroke(Color.WHITE);

        // Eliminar botones de añadir y eliminar antes de la edición.
        space.getChildren().remove(addButton);
        space.getChildren().remove(removeButton);

        // Crear y añadir el planeta al sistema solar.
        Planet planet = new Planet(space, 1.0, "Tierra", 5.972e24, 287.15, 6371.0, 107280.0, 5.51, (Star) solarSystem.getStar());
        SolarSystem.addCuerpoCeleste(planet);

        // Cambiar el elemento al seleccionado.
        selected = planet;
        parent.setStroke(Color.WHITE);

        // Añadir eventos
        // Añadir evento de selección.
        circle.setOnMouseClicked(event -> {
            seleccionar(planet);
            event.consume();
        });

        // Añadir evento de clicar y arrastrar al planeta.
        circle.setOnMouseDragged(event -> {
            circle.setLayoutX(event.getSceneX());
            orbit.setRadiusX(circle.getLayoutX() - parent.getLayoutX());
        });

        // Añadir evento de clicar y arrastrar al botón de modificar la órbita(vertical).
        orbitModify.setOnMouseDragged(event -> {
            orbitModify.setLayoutY(event.getSceneY());
            orbit.setRadiusY(parent.getLayoutY() - orbitModify.getLayoutY());
        });

        orbitModify.setOnMouseEntered(event -> orbitModify.setCursor(Cursor.HAND));

        // Añadir todos los objetos al espacio.
        space.getChildren().addAll(orbit, orbitModify);

        // Enviar la órbita al final para evitar problemas.
        orbit.toBack();

        infoPane.select(planet);
    }

    static void mostrarInfo() {
        infoPane.getInfoPane().toFront();
        infoPane.activar();
    }

    void agregarCirculo() {
        // Panel de creación delante (Para evitar problemas)
        infoPane.getInfoPane().toFront();

        // Volvemos a activar el botón de la animación.
        playAnimationButton.setDisable(false);

        // Variables para el manejo de gráficos.
        Circle circle = selected.getCircle();

        // Añadir botones.
        // Botón de añadir.
        addButton.setLayoutX(circle.getLayoutX() - circle.getRadius() - 15);
        addButton.setLayoutY(circle.getLayoutY() - circle.getRadius());

        // Botón de eliminar.
        removeButton.setLayoutX(circle.getLayoutX() + circle.getRadius() - 5);
        removeButton.setLayoutY(circle.getLayoutY() - circle.getRadius());

        // Añadir botones de añadir y eliminar.
        space.getChildren().addAll(addButton, removeButton);

        // Eliminar botones de finalizar edición y el modificador de la órbita.
        space.getChildren().removeAll(orbitModify);

        // Eliminar el evento de edición de la órbita.
        selected.getCircle().setOnMouseDragged(null);
    }

    @FXML
    void initialize() {
        // Asignación de variables iniciales.
        assert playAnimationButton != null;

        db = new DataBase();

        space = new AnchorPane();
        space.setPrefWidth(window.getPrefWidth() * 10);
        space.setPrefHeight(window.getPrefHeight() * 10);
        space.setLayoutX((window.getPrefWidth() - space.getPrefWidth()) / 2);
        space.setLayoutY((window.getPrefHeight() - space.getPrefHeight()) / 2);

        window.getChildren().add(space);

        Star sun = new Star(space, "Sol", 5778.0, 695700.0, 1.408, 1.989e30, 0.0);
        solarSystem = new SolarSystem(sun);
        addButton = new Button();
        removeButton = new Button();

        // Selección de la estrella default.
        selected = sun;
        selectedColor = Color.PURPLE;

        // Botones de guardar y cargar
        loadButton = new Button();
        loadButton.setText("Cargar");
        loadButton.setStyle("-fx-background-color: transparent; " +
                            "-fx-border-color: white; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-family: monospace");
        loadButton.setLayoutX(window.getPrefWidth() - 75);
        loadButton.setLayoutY(10);
        loadButton.setOnMouseEntered(event -> {
            loadButton.setCursor(Cursor.HAND);
            loadButton.setStyle("-fx-background-color: transparent; " +
                                "-fx-border-color: gray; " +
                                "-fx-text-fill: gray; " +
                                "-fx-font-family: monospace");
        });
        loadButton.setOnMouseExited(event -> {
            loadButton.setCursor(Cursor.DEFAULT);
            loadButton.setStyle("-fx-background-color: transparent; " +
                                "-fx-border-color: white; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-family: monospace");
        });
        loadButton.setOnMouseClicked(event -> cargarSistema());

        window.getChildren().add(loadButton);

        // Crear objetos.
        // Crear el panel de información.
        infoPane = new Info(window);
        infoPane.getInfoPane().setOnMouseClicked(event -> {
            if (selected != null) {
                seleccionar(selected);
                event.consume();
            }
        });

        // Crear menu de agregar
        AddController menuAgregar = new AddController(window, space);
        menuAgregar.getMenu().setOnMouseClicked(event -> {
            if (selected != null) {
                seleccionar(selected);
                event.consume();
            }
        });

        playAnimationButton = new Button();
        playAnimationButton.setText("■");
        playAnimationButton.setLayoutX(10);
        playAnimationButton.setLayoutY(window.getPrefHeight() - 70);
        playAnimationButton.setAlignment(Pos.CENTER);
        playAnimationButton.setContentDisplay(ContentDisplay.CENTER);
        playAnimationButton.setPrefWidth(60);
        playAnimationButton.setPrefHeight(60);
        playAnimationButton.setStyle("-fx-background-color: transparent; " +
                                     "-fx-border-color: white; " +
                                     "-fx-text-fill: white; " +
                                     "-fx-font-size: 25px");

        playAnimationButton.setOnMouseEntered(event -> {
            playAnimationButton.setCursor(Cursor.HAND);
            playAnimationButton.setStyle("-fx-background-color: transparent; " +
                                         "-fx-border-color: gray; " +
                                         "-fx-text-fill: gray; " +
                                         "-fx-font-size: 25px");
        });
        playAnimationButton.setOnMouseExited(event -> {
            playAnimationButton.setCursor(Cursor.DEFAULT);
            playAnimationButton.setStyle("-fx-background-color: transparent; " +
                                         "-fx-border-color: white; " +
                                         "-fx-text-fill: white; " +
                                         "-fx-font-size: 25px");
        });

        window.getChildren().add(playAnimationButton);

        // Añadir los paneles al espacio.
        window.getChildren().addAll(infoPane.getInfoPane(), menuAgregar.getMenu());

        // Asignación de eventos.
        space.setOnMouseClicked(event -> {
            deseleccionar();
            event.consume();
        });

        playAnimationButton.setOnMouseClicked(event -> controlarAnimacion());
        playAnimationButton.setOnAction(event -> playAnimationButton.setCursor(Cursor.HAND));
    }

    private void cargarSistema() {
        ArrayList<String> nombres = db.nombresSistemas();
        ChoiceDialog cd = new ChoiceDialog("Seleccionar", nombres);
        cd.setTitle("CARGAR SISTEMA SOLAR");
        cd.setHeaderText("A continuación se le presentará una lista de los nombres de los sistemas solares disponibles:");
        cd.setContentText("Elija un sistema:");
        Optional<String> result = cd.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals("Seleccionar")) {
                db.cargarDatos(result.get(), space);
                PhisicsController.timer.stop();
                playAnimationButton.setText("▶");
            }
        }
    }
}
