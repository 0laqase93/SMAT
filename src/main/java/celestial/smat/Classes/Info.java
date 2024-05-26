package celestial.smat.Classes;

import celestial.smat.Exceptions.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.Objects;

public class Info {
    private final AnchorPane infoPane;

    private final Circle preview;

    private final TextField nameField;
    private final TextField temperatureField;
    private final TextField radiusField;
    private final TextField speedField;
    private final TextField densityField;

    private CuerpoCeleste selected;

    private DecimalFormat formato;

    ImageView saveIconView;

    public Info(AnchorPane window) {
        AnchorPane infoPane = new AnchorPane();
        infoPane.setPrefWidth(200);
        infoPane.setPrefHeight(270);
        infoPane.setLayoutX(10);
        infoPane.setLayoutY((window.getPrefHeight() - infoPane.getPrefHeight())/2);
        infoPane.setStyle("-fx-background-color: #1c2833;" +
                          "-fx-border-radius: 10;" +
                          "-fx-background-radius: 10;" +
                          "-fx-border-color: white;");
        infoPane.setVisible(false);

        preview = new Circle();
        preview.setRadius(40);
        preview.setLayoutX(infoPane.getPrefWidth()/2);
        preview.setLayoutY(infoPane.getPrefWidth()/2 - 50);
        preview.setStroke(Color.WHITE);

        int tamanioIcono = 30;
        int inset = 10;
        int verticalInset = 34;

        nameField = new TextField();
        nameField.setLayoutX(inset + tamanioIcono);
        nameField.setPrefWidth(infoPane.getPrefWidth() - nameField.getLayoutX() - inset);
        nameField.setLayoutY(100);
        nameField.setStyle("-fx-background-color: #10181c; " +
                           "-fx-font-family: monospace;" +
                           "-fx-border-color: black; " +
                           "-fx-text-fill: white;" +
                           "-fx-border-radius: 10;" +
                           "-fx-background-radius: 10;");

        temperatureField = new TextField();
        temperatureField.setLayoutX(inset + tamanioIcono);
        temperatureField.setPrefWidth(180 - temperatureField.getLayoutX() - inset * 3);
        temperatureField.setLayoutY(nameField.getLayoutY() + verticalInset);
        temperatureField.setStyle("-fx-background-color: #10181c; " +
                                  "-fx-font-family: monospace;" +
                                  "-fx-border-color: black; " +
                                  "-fx-text-fill: white;" +
                                  "-fx-border-radius: 10;" +
                                  "-fx-background-radius: 10;");

        radiusField = new TextField();
        radiusField.setLayoutX(inset + tamanioIcono);
        radiusField.setPrefWidth(180 - radiusField.getLayoutX() - inset * 3);
        radiusField.setLayoutY(temperatureField.getLayoutY() + verticalInset);
        radiusField.setStyle("-fx-background-color: #10181c; " +
                             "-fx-font-family: monospace;" +
                             "-fx-border-color: black; " +
                             "-fx-text-fill: white;" +
                             "-fx-border-radius: 10;" +
                             "-fx-background-radius: 10;");

        speedField = new TextField();
        speedField.setLayoutX(inset + tamanioIcono);
        speedField.setPrefWidth(180 - speedField.getLayoutX() - inset * 3);
        speedField.setLayoutY(radiusField.getLayoutY() + verticalInset);
        speedField.setStyle("-fx-background-color: #10181c; " +
                            "-fx-font-family: monospace;" +
                            "-fx-border-color: black; " +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;");

        densityField = new TextField();
        densityField.setLayoutX(inset + tamanioIcono);
        densityField.setPrefWidth(180 - densityField.getLayoutX() - inset * 3);
        densityField.setLayoutY(speedField.getLayoutY() + verticalInset);
        densityField.setStyle("-fx-background-color: #10181c; " +
                              "-fx-font-family: monospace;" +
                              "-fx-border-color: black; " +
                              "-fx-text-fill: white;" +
                              "-fx-border-radius: 10;" +
                              "-fx-background-radius: 10;");

        Label temperatureUnitField = new Label();
        temperatureUnitField.setText("K");
        temperatureUnitField.setLayoutX(temperatureField.getLayoutX() + temperatureField.getPrefWidth() + (double) inset / 2);
        temperatureUnitField.setLayoutY(temperatureField.getLayoutY() + 5);
        temperatureUnitField.setStyle("-fx-text-fill: white; -fx-font-family: monospace;");

        Label radiusUnitField = new Label();
        radiusUnitField.setText("km");
        radiusUnitField.setLayoutX(radiusField.getLayoutX() + radiusField.getPrefWidth() + (double) inset / 2);
        radiusUnitField.setLayoutY(radiusField.getLayoutY() + 5);
        radiusUnitField.setStyle("-fx-text-fill: white; -fx-font-family: monospace");

        Label speedUnitField = new Label();
        speedUnitField.setText("km/s");
        speedUnitField.setLayoutX(speedField.getLayoutX() + speedField.getPrefWidth() + (double) inset / 2);
        speedUnitField.setLayoutY(speedField.getLayoutY() + 5);
        speedUnitField.setStyle("-fx-text-fill: white; -fx-font-family: monospace");

        Label densityUnitField = new Label();
        densityUnitField.setText("g/cm³");
        densityUnitField.setLayoutX(densityField.getLayoutX() + densityField.getPrefWidth() + (double) inset / 2);
        densityUnitField.setLayoutY(densityField.getLayoutY() + 5);
        densityUnitField.setStyle("-fx-text-fill: white; -fx-font-family: monospace");

        Image nameIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/planetaIcon.png")));
        Image temperatureIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/temperaturaIcon.png")));
        Image radiusIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/radioIcon.png")));
        Image speedIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/velocidadIcon.png")));
        Image densityIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/densidadIcon.png")));
        Image saveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/saveIcon.png")));

        ImageView nameIconView = new ImageView();
        nameIconView.setImage(nameIcon);
        nameIconView.setLayoutX(inset);
        nameIconView.setLayoutY(nameField.getLayoutY());

        ImageView temperatureIconView = new ImageView();
        temperatureIconView.setImage(temperatureIcon);
        temperatureIconView.setLayoutX(inset);
        temperatureIconView.setLayoutY(temperatureField.getLayoutY());

        ImageView radiusIconView = new ImageView();
        radiusIconView.setImage(radiusIcon);
        radiusIconView.setLayoutX(inset);
        radiusIconView.setLayoutY(radiusField.getLayoutY());

        ImageView speedIconView = new ImageView();
        speedIconView.setImage(speedIcon);
        speedIconView.setLayoutX(inset);
        speedIconView.setLayoutY(speedField.getLayoutY());

        ImageView densityIconView = new ImageView();
        densityIconView.setImage(densityIcon);
        densityIconView.setLayoutX(inset);
        densityIconView.setLayoutY(densityField.getLayoutY());

        saveIconView = new ImageView();
        saveIconView.setImage(saveIcon);
        saveIconView.setLayoutX(inset);
        saveIconView.setLayoutY(inset);

        saveIconView.setOnMouseEntered(event -> saveIconView.setCursor(Cursor.HAND));
        saveIconView.setOnMouseClicked(event -> actualizarInfo());

        infoPane.getChildren().addAll(preview, nameField, temperatureField, radiusField, speedField, densityField, temperatureUnitField, radiusUnitField, speedUnitField, densityUnitField, nameIconView, temperatureIconView, radiusIconView, speedIconView, densityIconView, saveIconView);

        this.infoPane = infoPane;
    }

    public AnchorPane getInfoPane() {
        return infoPane;
    }

    public void select(CuerpoCeleste object) {
        selected = object;
        preview.setFill(object.getCircle().getFill());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!nameField.isFocused()) {
                    nameField.setText(object.getName());
                }
                if (!temperatureField.isFocused()) {
                    temperatureField.setText(object.getTemperature().intValue() + "");
                }
                if (!radiusField.isFocused()) {
                    radiusField.setText(object.getRadius().intValue() + "");
                }
                if (!speedField.isFocused()) {
                    speedField.setText(object.getSpeed()[0].intValue() + "/" + object.getSpeed()[1].intValue());
                }
                if (!densityField.isFocused()) {
                    densityField.setText(object.getDensity().intValue() + "");
                }
            }
        };
        timer.start();

        // Añadir listeners a los campos de texto para pausar el timer mientras el usuario edita
        addFocusListener(nameField, timer);
        addFocusListener(temperatureField, timer);
        addFocusListener(radiusField, timer);
        addFocusListener(speedField, timer);
        addFocusListener(densityField, timer);

        // Añadir listeners para detectar la tecla "Enter"
        addEnterKeyListener(nameField);
        addEnterKeyListener(temperatureField);
        addEnterKeyListener(radiusField);
        addEnterKeyListener(speedField);
        addEnterKeyListener(densityField);
    }

    private void addFocusListener(TextField textField, AnimationTimer timer) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timer.stop(); // Pausar el timer cuando el campo está enfocado
                } else {
                    timer.start(); // Reanudar el timer cuando el campo pierde el foco
                }
            }
        });
    }

    private void addEnterKeyListener(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Acción a realizar cuando se presiona "Enter"
                textField.getParent().requestFocus(); // Para mover el foco fuera del TextField
                actualizarInfo();
            }
        });
    }

    public void desactivar() {
        this.infoPane.setVisible(false);
        this.nameField.setDisable(true);
        this.temperatureField.setDisable(true);
        this.radiusField.setDisable(true);
    }

    public void activar() {
        this.infoPane.setVisible(true);
        this.nameField.setDisable(false);
        this.temperatureField.setDisable(false);
        this.radiusField.setDisable(false);
    }

    public void actualizarInfo() {
        String camposVacios = "";
        Boolean campoVacio = false;
        try {
            if (nameField.getText().isEmpty()) {
                camposVacios += "El nombre \n";
                campoVacio = true;
            }
            if (temperatureField.getText().isEmpty()) {
                camposVacios += "La temperatura\n";
                campoVacio = true;
            }
            if (radiusField.getText().isEmpty()) {
                camposVacios += "El radio\n";
                campoVacio = true;
            }
            if (speedField.getText().isEmpty()) {
                camposVacios += "La velocidad\n";
                campoVacio = true;
            }
            if (densityField.getText().isEmpty()) {
                camposVacios += "La densidad\n";
                campoVacio = true;
            }

            if (campoVacio) {
                throw new NoInputException(camposVacios);
            }

            String name = nameField.getText();
            Double temperature = Double.parseDouble(temperatureField.getText());
            Double radius = Double.parseDouble(radiusField.getText());
            String speed = speedField.getText();
            Double density = Double.parseDouble(densityField.getText());

            // 0 Kelvin / temperatura Plank
            if (temperature < 0 || temperature > 1.416808e32) {
                throw new InvalidTemperatureInputException("Formato de temperatura inválido");
            }

            // distancia de plank / la mitad del tamaño del universo
            if (radius < 1.616e-35 || radius > 23.25e9) {
                throw new InvalidRadiusInputException("Formato de radio inválido");
            }

            String[] aux = speed.split("/");
            if (aux.length != 2) {
                throw new InvalidSpeedInputException("Formato inválido");
            }
            // 0 km/s / velocidad de la luz
            Double[] speedDouble = {Double.parseDouble(aux[0]), Double.parseDouble(aux[1])};
            if (speedDouble[0] < 0 || speedDouble[0] >= 299792.458 || speedDouble[1] < 0 || speedDouble[1] >= 299792.458) {
                throw new InvalidSpeedInputException("Límite excedido");
            }

            if (density < 8.988e-5 || density > 4e14) {
                throw new InvalidDensityInputException("Densidad inválida");
            }

            selected.setName(name);
            selected.setTemperature(temperature);
            selected.setRadius(radius);
            selected.setVelocidadX(Double.parseDouble(speed.split("/")[0]));
            selected.setVelocidadY(Double.parseDouble(speed.split("/")[1]));
            selected.setDensity(density);

            // Cargar y establecer el ícono de "tick"
            Image saveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/tickIcon.png")));
            saveIconView.setImage(saveIcon);

            // Crear una pausa de 1 segundo
            PauseTransition pause = new PauseTransition(Duration.seconds(1));

            // Después de la pausa, cambiar el ícono de nuevo al de "save"
            pause.setOnFinished(event -> {
                Image saveIconBack = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/celestial/smat/Images/saveIcon.png")));
                saveIconView.setImage(saveIconBack);
            });

            // Iniciar la pausa
            pause.play();
        } catch (NoInputException e) {
            createAlert(1, "Campos vacíos", "Fallo al sobreescribir. Los siguientes campos están vacíos:", e.getMessage());
        } catch (InvalidTemperatureInputException e) {
            createAlert(1, e.getMessage(), "Fallo al sobreescribir. La temperatura parece inválida.", "Los valores que maneja la temperatura están entre el 0 Kelvin y la temperatura de plank (1.416808×10^32 Kelvin).");
        } catch (InvalidRadiusInputException e) {
            createAlert(1, e.getMessage(), "Fallo al sobreescribir. El radio parece inválido.", "Los valores que maneja el radio están entre la longitud de plank (1.616x10^-32 Km) y la mitad del universo observable (2.2x10^23 Km).");
        } catch (InvalidSpeedInputException e) {
            createAlert(1, e.getMessage(), "La velocidad parece inválida.","Fallo al sobreescribir. La velocidad tiene que escribirse en formato: 'x/x'. Maneja los valores entre 0km/s y la velocidad de la luz (299.792,458 km/s) sin incluir la velocidad de la luz.");
        } catch (InvalidDensityInputException e) {
            createAlert(1, e.getMessage(), "La densidad parece inválida.", "Fallo al sobreescribir. Los valores que maneja la densidad están entre la mínima densidad del hidrógeno gaseoso (8.988×10^−5 g/cm^3) y la densidad de las estrellas de neutrones (4×10^14 g/cm^3).");
        }
    }

    public void createAlert(Integer type, String title, String headerText, String contextText) {
        if (type == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contextText);
            alert.showAndWait();
        }
    }
}
