package celestial.smat.Classes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Info {
    private final AnchorPane infoPane;

    private final Circle preview;

    private final TextField nameField;
    private final TextField temperatureField;
    private final TextField radiusField;
    private final TextField speedField;
    private final TextField densityField;

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

        infoPane.getChildren().addAll(preview, nameField, temperatureField, radiusField, speedField, densityField, temperatureUnitField, radiusUnitField, speedUnitField, densityUnitField, nameIconView, temperatureIconView, radiusIconView, speedIconView, densityIconView);

        this.infoPane = infoPane;
    }

    // Constructor
    public AnchorPane getInfoPane() {
        return infoPane;
    }

    public void select(CuerpoCeleste object) {
        preview.setFill(object.getCircle().getFill());
        nameField.setText(object.getName());
        temperatureField.setText(object.getTemperature() + "");
        radiusField.setText(object.getRadius() + "");
        speedField.setText(object.getSpeed() + "");
        densityField.setText(object.getDensity() + "");
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
}
