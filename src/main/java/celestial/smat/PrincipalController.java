package celestial.smat;


import celestial.smat.Classes.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PrincipalController {

    private SolarSystem solarSystem;

    private Timeline animation;

    private CuerpoCeleste selected;

    private Info infoPane;

    private Button addButton;

    private Button removeButton;

    private Button finishCreate;

    private Boolean createMode;

    private Circle orbitModify;

    private Color selectedColor;

    @FXML
    private AnchorPane space;

    @FXML
    private Circle sun;

    @FXML
    private Button playAnimationButton;

    /**
     * Función que se encarga de controlar la animación orbital.
     * @param event Evento de ratón.
     */
    @FXML
    void ControlarAnimacion(MouseEvent event) {
        if (animation.getStatus() == Animation.Status.RUNNING) {
            // En caso de que la animación esté activa
            playAnimationButton.setText("⏵");
            playAnimationButton.setTextFill(Color.WHITE);
            animation.stop();
        } else {
            // En caso de que la animación no esté activa
            playAnimationButton.setText("⏹");
            playAnimationButton.setTextFill(Color.RED);
            animation.play();
        }
    }

    /**
     * Función que se encarga de deseleccionar si se presiona en el fondo.
     */
    void deseleccionar() {
        if (selected != null && !createMode) { // Para evitar problemas.
            selected.getCircle().setStroke(Color.WHITE);
            selected = null;
            infoPane.desactivar();
            space.getChildren().remove(addButton);
            space.getChildren().remove(removeButton);
        }
    }

    /**
     * Función que se encarga de seleccionar un cuerpo celeste.
     * @param object Objeto a seleccionar.
     */
    void seleccionar(CuerpoCeleste object) {
        // Variables para el manejo de gráficos.
        Circle selectedCircle = object.getCircle();

        // Si está en el modo de creación no se podrá seleccionar otro.
        if (!createMode) {
            if (selected != null) {
                selected.getCircle().setStroke(Color.WHITE);
            }

            this.selected = object;
            assert selectedCircle != null;
            selectedCircle.setStroke(selectedColor);

            // Borrar los botones de la anterior selección.
            space.getChildren().remove(addButton);
            space.getChildren().remove(removeButton);

            // Mostrar botones de selección.
            // Botón de añadir.
            addButton = new Button();
            addButton.setFont(new Font("Monospaced Bold", 32));
            addButton.setText("+");
            addButton.setTextFill(Color.WHITE);
            addButton.setStyle("-fx-background-color: transparent;");
            addButton.setPadding(new Insets(-10, 0, -7, 0));
            addButton.setLayoutX(selectedCircle.getLayoutX() - selectedCircle.getRadius() - 15);
            addButton.setLayoutY(selectedCircle.getLayoutY() - selectedCircle.getRadius());

            // Botón de eliminar.
            removeButton = new Button();
            removeButton.setFont(new Font("Monospaced Bold", 32));
            removeButton.setText("-");
            removeButton.setTextFill(Color.WHITE);
            removeButton.setStyle("-fx-background-color: transparent;");
            removeButton.setPadding(new Insets(-10, 0, -7, 0));
            removeButton.setLayoutX(selectedCircle.getLayoutX() + selectedCircle.getRadius() - 5);
            removeButton.setLayoutY(selectedCircle.getLayoutY() - selectedCircle.getRadius());

            // Añadir evento de selección.
            addButton.setOnMouseClicked(event -> {
                crear(object);
                event.consume();
            });

            // Añadir al espacio.
            space.getChildren().add(addButton);
            space.getChildren().add(removeButton);

            // Mostrar datos del planeta.
            infoPane.select(object);
            mostrarInfo();

            // Setear de nuevo el círculo después de la modificación.
            object.setCircle(selectedCircle);
        }
    }

    /**
     * Función para crear un nuevo cuerpo estelar
     * @param object Objeto padre al cual se quiere linkear el objeto
     */
    void crear(CuerpoCeleste object) {
        // Panel de creación delante (Para evitar problemas)
        infoPane.getInfoPane().toFront();

        // Habilitar el modo de creación.
        createMode = true;

        // Variables para el manejo de gráficos.
        Circle parent = object.getCircle();

        // Parar animación
        animation.stop();
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
        Planet planet = new Planet("Tierra", 287.15, 6371.0, 107280.0, 5.51, (Star) solarSystem.getStar(), circle, orbit);
        solarSystem.addPlanet(planet);

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

        // Cambiar el elemento al seleccionado.
        selected = planet;
        parent.setStroke(Color.WHITE);

        // Crear animación
        // x = xc + a ⋅ cos(θ)
        // y = yc + b ⋅ sin(θ)
        // Donde (xc,yc) son las coordenadas del centro de la elipse, a y b son las
        // longitudes de los semiejes mayor y menor respectivamente, y θ es el ángulo.

        final double[] angle = {0};

        animation.getKeyFrames().add(
                new KeyFrame(
                        Duration.seconds(0.05),
                        event -> {
                            // Calcular la nueva posición basada en el ángulo.
                            double newX = orbit.getLayoutX() + orbit.getRadiusX() * Math.cos(Math.toRadians(angle[0]));
                            double newY = orbit.getLayoutY() + orbit.getRadiusY() * Math.sin(Math.toRadians(angle[0]));

                            // Actualizar la posición del círculo.
                            circle.setLayoutX(newX);
                            circle.setLayoutY(newY);

                            // Actualizar la posición de la órbita.
                            orbit.setLayoutX(parent.getLayoutX());
                            orbit.setLayoutY(parent.getLayoutY());

                            // Incrementar el ángulo para el siguiente fotograma.
                            angle[0] += 1.5; // Ajusta la velocidad de la órbita.
                        }
                )
        );

        // Añadir todos los objetos al espacio.
        space.getChildren().addAll(orbit, orbitModify, circle, finishCreate);

        // Enviar la órbita al final para evitar problemas.
        orbit.toBack();

        infoPane.select(planet);
    }

    /**
     * Función que se encarga de mostrar el panel de información del planeta seleccionado.
     */
    void mostrarInfo() {
        infoPane.getInfoPane().toFront();
        infoPane.activar();
    }

    /**
     * Función que se usa para terminar el modo de edición.
     */
    void agregarCirculo() {
        // Panel de creación delante (Para evitar problemas)
        infoPane.getInfoPane().toFront();

        // Deshabilitar el modo de edición.
        createMode = false;

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
        space.getChildren().removeAll(finishCreate, orbitModify);

        // Eliminar el evento de edición de la órbita.
        selected.getCircle().setOnMouseDragged(null);
    }

    @FXML
    void initialize() {
        // Asignación de variables iniciales.
        assert sun != null;
        Star sunStar = new Star("Sol", 5772.0, 696340.0, 0.0, 1.41, sun);
        solarSystem = new SolarSystem(sunStar);
        animation = new Timeline();
        addButton = new Button();
        removeButton = new Button();
        createMode = false;

        // Selección de la estrella default.
        selected = sunStar;
        selectedColor = Color.PURPLE;

        // Crear objetos.
        // Botón de finalizar edición.
        finishCreate = new Button();
        finishCreate.setFont(new Font("Monospaced Bold", 32));
        finishCreate.setText("+");
        finishCreate.setTextFill(Color.BLACK);
        finishCreate.setLayoutX(10);
        finishCreate.setLayoutY(10);

        // Crear el panel de información.
        infoPane = new Info(space);
        infoPane.getInfoPane().setOnMouseClicked(event -> {
            if (selected != null) {
                seleccionar(selected);
                event.consume();
            }
        });

        // Crear menu de agregar
        MenuAgregar menuAgregar = new MenuAgregar(space);
        menuAgregar.getMenu().setOnMouseClicked(event -> {
            if (selected != null) {
                seleccionar(selected);
                event.consume();
            }
        });

        // Añadir los paneles al espacio.
        space.getChildren().addAll(infoPane.getInfoPane(), menuAgregar.getMenu());

        // Crear animación para los botones de + y -.
        playAnimationButton.setDisable(true); // Para evitar problemas.
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(false);

        animation.getKeyFrames().add(
                new KeyFrame(
                        Duration.seconds(0.05),
                        event -> {
                            if (selected != null) { // Para evitar problemas.
                                // Botón +.
                                addButton.setLayoutX(selected.getCircle().getLayoutX() - selected.getCircle().getRadius() - 15);
                                addButton.setLayoutY(selected.getCircle().getLayoutY() - selected.getCircle().getRadius());

                                // Botón -.
                                removeButton.setLayoutX(selected.getCircle().getLayoutX() + selected.getCircle().getRadius() - 5);
                                removeButton.setLayoutY(selected.getCircle().getLayoutY() - selected.getCircle().getRadius());
                            }
                        }
                )
        );

        // Asignación de eventos.
        space.setOnMouseClicked(event -> {
            if (!createMode) {
                deseleccionar();
                event.consume();
            }
        });

        sun.setOnMouseClicked(event -> {
            seleccionar(sunStar);
            event.consume();
        });

        // Añadir evento al botón de creación de planeta.
        finishCreate.setOnMouseClicked(event -> {
            agregarCirculo();
            event.consume();
        });

    }
}
