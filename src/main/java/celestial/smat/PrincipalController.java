package celestial.smat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class PrincipalController {

    private Object selected;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane space;

    @FXML
    private Circle sun;

    private Circle prueba;

    private Circle prueba2;

    private Button addButton;

    private Button removeButton;

    @FXML
    void select(Circle selected) {
        if (this.selected != null) {
            ((Circle) this.selected).setStroke(Color.WHITE);
        }
        this.selected = selected;
        selected.setStroke(Color.PURPLE);

        // Button appears
        // Delete last Button
        space.getChildren().remove(addButton);
        space.getChildren().remove(removeButton);

        addButton = new Button();
        addButton.setFont(new Font("Monospaced Bold", 32));
        addButton.setText("+");
        addButton.setTextFill(Color.WHITE);
        addButton.setStyle("-fx-background-color: transparent;");
        addButton.setPadding(new Insets(-10, 0, -7, 0));
        addButton.setLayoutX(selected.getLayoutX() - selected.getRadius() - 15);
        addButton.setLayoutY(selected.getLayoutY() - selected.getRadius());

        removeButton = new Button();
        removeButton.setFont(new Font("Monospaced Bold", 32));
        removeButton.setText("-");
        removeButton.setTextFill(Color.WHITE);
        removeButton.setStyle("-fx-background-color: transparent;");
        removeButton.setPadding(new Insets(-10, 0, -7, 0));
        removeButton.setLayoutX(selected.getLayoutX() + selected.getRadius() - 5);
        removeButton.setLayoutY(selected.getLayoutY() - selected.getRadius());

        space.getChildren().add(addButton);
        space.getChildren().add(removeButton);
    }

    @FXML
    void select(MouseEvent event) {
        select(sun);
    }

    @FXML
    void initialize() {
        assert sun != null : "fx:id=\"sun\" was not injected: check your FXML file 'hello-view.fxml'.";
        selected = null;


        prueba = new Circle();
        prueba.setRadius(60);
        prueba.setLayoutX(100);
        prueba.setLayoutY(100);
        prueba.setFill(Color.BLUE);

        prueba2 = new Circle();
        prueba2.setRadius(30);
        prueba2.setLayoutX(350);
        prueba2.setLayoutY(350);
        prueba2.setFill(Color.BLUE);

        sun.setOnMouseClicked(event -> select(sun));
        prueba.setOnMouseClicked(event -> select(prueba));
        prueba2.setOnMouseClicked(event -> select(prueba2));


        space.getChildren().add(prueba);
        space.getChildren().add(prueba2);
    }
}
