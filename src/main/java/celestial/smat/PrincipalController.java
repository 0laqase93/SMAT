package celestial.smat;


import celestial.smat.Classes.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class PrincipalController {

    public static SolarSystem solarSystem;

    private static CuerpoCeleste selected;

    private DataBase db;

    private static Info infoPane;

    private Button loadButton;

    private Button saveButton;

    private Button addButton;

    private Button removeButton;

    private Circle orbitModify;

    private ImageView smatView;

    private AddController menuAgregar;

    private Button startPlayButton;

    private Button startLoadButton;

    static Color selectedColor;

    @FXML
    private AnchorPane window;

    private static AnchorPane space;

    public static Button playAnimationButton;

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

    public static void controlarAnimacion() {
        if (PhisicsController.animacion) {
            AddController.playButton.setText("▶");
            playAnimationButton.setText("▶");
            PhisicsController.timer.stop();
        } else {
            AddController.playButton.setText("■");
            playAnimationButton.setText("■");
            PhisicsController.timer.start();
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

    static void mostrarInfo() {
        infoPane.getInfoPane().toFront();
        infoPane.activar();
    }

    @FXML
    void initialize() throws InterruptedException {
        assert playAnimationButton != null;

        db = new DataBase();

        space = new AnchorPane();
        space.setPrefWidth(window.getPrefWidth() * 10);
        space.setPrefHeight(window.getPrefHeight() * 10);
        space.setLayoutX((window.getPrefWidth() - space.getPrefWidth()) / 2);
        space.setLayoutY((window.getPrefHeight() - space.getPrefHeight()) / 2);

        window.getChildren().add(space);

        Star sun = new Star(space, "Sol", 5778.0, 695700.0, 1.408, 1.989e30, 0.0);
        solarSystem = new SolarSystem("Inicial", sun);
        addButton = new Button();
        removeButton = new Button();

        // Selección de la estrella default.
        selected = sun;
        selectedColor = Color.PURPLE;

        // Botones de guardar y cargar
        loadButton = new Button();
        loadButton.setText("Cargar");
        loadButton.setDisable(true);
        loadButton.setOpacity(0);
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

        saveButton = new Button();
        saveButton.setText("Guardar");
        saveButton.setDisable(true);
        saveButton.setOpacity(0);
        saveButton.setStyle("-fx-background-color: transparent; " +
                            "-fx-border-color: white; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-family: monospace");
        saveButton.setLayoutX(window.getPrefWidth() - 160);
        saveButton.setLayoutY(10);
        saveButton.setOnMouseEntered(event -> {
            saveButton.setCursor(Cursor.HAND);
            saveButton.setStyle("-fx-background-color: transparent; " +
                                "-fx-border-color: gray; " +
                                "-fx-text-fill: gray; " +
                                "-fx-font-family: monospace");
        });
        saveButton.setOnMouseExited(event -> {
            saveButton.setCursor(Cursor.DEFAULT);
            saveButton.setStyle("-fx-background-color: transparent; " +
                    "-fx-border-color: white; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-family: monospace");
        });
        saveButton.setOnMouseClicked(event -> guardarSistema());

        window.getChildren().addAll(loadButton, saveButton);

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
        menuAgregar = new AddController(window, space);
        menuAgregar.invisible();
        menuAgregar.getMenu().setOnMouseClicked(event -> {
            if (selected != null) {
                seleccionar(selected);
                event.consume();
            }
        });

        playAnimationButton = new Button();
        playAnimationButton.setDisable(true);
        playAnimationButton.setOpacity(0);
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
                                     "-fx-font-size: 25px;" +
                                     "-fx-padding: -3 0 0 0;");

        playAnimationButton.setOnMouseEntered(event -> {
            playAnimationButton.setCursor(Cursor.HAND);
            playAnimationButton.setStyle("-fx-background-color: transparent; " +
                                         "-fx-border-color: gray; " +
                                         "-fx-text-fill: gray; " +
                                         "-fx-font-size: 25px;" +
                                         "-fx-padding: -3 0 0 0;");
        });
        playAnimationButton.setOnMouseExited(event -> {
            playAnimationButton.setCursor(Cursor.DEFAULT);
            playAnimationButton.setStyle("-fx-background-color: transparent; " +
                                         "-fx-border-color: white; " +
                                         "-fx-text-fill: white; " +
                                         "-fx-font-size: 25px;" +
                                         "-fx-padding: -3 0 0 0;");
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

        Image SMAT = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/SMAT.png")));
        smatView = new ImageView(SMAT);
        smatView.setLayoutX(0);
        smatView.setLayoutY(13);

        window.getChildren().add(smatView);

        Planet planet = new Planet(space, 1.0,"Inicio", 1.90e27, 165.0, 69911.0, 29.0, 1.33, sun);
        SolarSystem.addCuerpoCeleste(planet);

        startPlayButton = new Button();
        startPlayButton.setText("Jugar");
        startPlayButton.setLayoutX(window.getPrefWidth() / 2 - 118);
        startPlayButton.setLayoutY(window.getPrefHeight() / 2 - 37);
        startPlayButton.setAlignment(Pos.CENTER);
        startPlayButton.setContentDisplay(ContentDisplay.CENTER);
        startPlayButton.setStyle("-fx-background-color: BLACK; " +
                                 "-fx-border-color: white; " +
                                 "-fx-text-fill: white; " +
                                 "-fx-font-size: 40px;" +
                                 "-fx-padding: 10 12 12 12; " +
                                 "-fx-font-family: spaceman; " +
                                 "-fx-border-radius: 20px; " +
                                 "-fx-background-radius: 20px");

        startPlayButton.setOnMouseEntered(event -> {
            startPlayButton.setCursor(Cursor.HAND);
            startPlayButton.setStyle("-fx-background-color: BLACK; " +
                                     "-fx-border-color: gray; " +
                                     "-fx-text-fill: gray; " +
                                     "-fx-font-size: 40px;" +
                                     "-fx-padding: 10 12 12 12; " +
                                     "-fx-font-family: spaceman; " +
                                     "-fx-border-radius: 20px; " +
                                     "-fx-background-radius: 20px");
        });
        startPlayButton.setOnMouseExited(event -> {
            startPlayButton.setCursor(Cursor.DEFAULT);
            startPlayButton.setStyle("-fx-background-color: BLACK; " +
                                     "-fx-border-color: white; " +
                                     "-fx-text-fill: white; " +
                                     "-fx-font-size: 40px;" +
                                     "-fx-padding: 10 12 12 12; " +
                                     "-fx-font-family: spaceman; " +
                                     "-fx-border-radius: 20px; " +
                                     "-fx-background-radius: 20px");
        });

        startPlayButton.setOnMouseClicked(event -> startSimulation());

        window.getChildren().add(startPlayButton);

        startLoadButton = new Button();
        startLoadButton.setText("Cargar");
        startLoadButton.setLayoutX(window.getPrefWidth() / 2 - 140);
        startLoadButton.setLayoutY(window.getPrefHeight() / 2 + 160);
        startLoadButton.setAlignment(Pos.CENTER);
        startLoadButton.setContentDisplay(ContentDisplay.CENTER);
        startLoadButton.setStyle("-fx-background-color: BLACK; " +
                                 "-fx-border-color: white; " +
                                 "-fx-text-fill: white; " +
                                 "-fx-font-size: 40px;" +
                                 "-fx-padding: 10 12 12 12; " +
                                 "-fx-font-family: spaceman; " +
                                 "-fx-border-radius: 20px; " +
                                 "-fx-background-radius: 20px");

        startLoadButton.setOnMouseEntered(event -> {
            startLoadButton.setCursor(Cursor.HAND);
            startLoadButton.setStyle("-fx-background-color: BLACK; " +
                                     "-fx-border-color: gray; " +
                                     "-fx-text-fill: gray; " +
                                     "-fx-font-size: 40px;" +
                                     "-fx-padding: 10 12 12 12; " +
                                     "-fx-font-family: spaceman; " +
                                     "-fx-border-radius: 20px; " +
                                     "-fx-background-radius: 20px");
        });
        startLoadButton.setOnMouseExited(event -> {
            startLoadButton.setCursor(Cursor.DEFAULT);
            startLoadButton.setStyle("-fx-background-color: BLACK; " +
                                     "-fx-border-color: white; " +
                                     "-fx-text-fill: white; " +
                                     "-fx-font-size: 40px;" +
                                     "-fx-padding: 10 12 12 12; " +
                                     "-fx-font-family: spaceman; " +
                                     "-fx-border-radius: 20px; " +
                                     "-fx-background-radius: 20px");
        });

        startLoadButton.setOnMouseClicked(event -> cargarBoton());

        window.getChildren().add(startLoadButton);
}

    private void cargarBoton() {
        cargarSistema();
        startSimulation();
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

    private void guardarSistema() {
        PhisicsController.timer.stop();
        playAnimationButton.setText("▶");
        TextInputDialog dialog = new TextInputDialog("Sistema33");
        dialog.setTitle("GUARDAR SISTEMA SOLAR");
        dialog.setHeaderText("Introduzca el nombre del sistema para la base de datos.");
        dialog.setContentText("Nombre:");
        Optional<String> name = dialog.showAndWait();
        if (name.isPresent()) {
            if (!name.get().isEmpty()) {
                db.guardarDatos(name.get());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nombre incorrecto");
                alert.setHeaderText("Parece que el nombre no es correcto.");
                alert.setContentText("Se debe introducir un nombre para el sistema solar.");
                alert.showAndWait();
            }
        }
    }

    private void startSimulation() {
        smatView.setDisable(true);
        fadeOut(smatView, 0.5);

        startPlayButton.setDisable(true);
        fadeOut(startPlayButton, 0.5);

        startLoadButton.setDisable(true);
        fadeOut(startLoadButton, 0.5);

        menuAgregar.visible();

        loadButton.setDisable(false);
        fadeIn(loadButton, 0.5);

        saveButton.setDisable(false);
        fadeIn(saveButton, 0.5);

        playAnimationButton.setDisable(false);
        fadeIn(playAnimationButton, 0.5);
    }


    private void fadeIn(Node node, double durationInSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOut(Node node, double durationInSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

}
