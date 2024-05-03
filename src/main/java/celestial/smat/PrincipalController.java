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

    private Button addButton;

    private Button removeButton;

    private Button right;

    private Button left;

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

        // Add button
        addButton = new Button();
        addButton.setFont(new Font("Monospaced Bold", 32));
        addButton.setText("+");
        addButton.setTextFill(Color.WHITE);
        addButton.setStyle("-fx-background-color: transparent;");
        addButton.setPadding(new Insets(-10, 0, -7, 0));
        addButton.setLayoutX(selected.getLayoutX() - selected.getRadius() - 15);
        addButton.setLayoutY(selected.getLayoutY() - selected.getRadius());

        // Remove button
        removeButton = new Button();
        removeButton.setFont(new Font("Monospaced Bold", 32));
        removeButton.setText("-");
        removeButton.setTextFill(Color.WHITE);
        removeButton.setStyle("-fx-background-color: transparent;");
        removeButton.setPadding(new Insets(-10, 0, -7, 0));
        removeButton.setLayoutX(selected.getLayoutX() + selected.getRadius() - 5);
        removeButton.setLayoutY(selected.getLayoutY() - selected.getRadius());

        // Add events
        addButton.setOnMouseClicked(event -> createCircle(selected));

        // Add to the pane
        space.getChildren().add(addButton);
        space.getChildren().add(removeButton);
    }

    @FXML
    void select(MouseEvent event) {
        select(sun);
    }

    void createCircle(Circle parent) {
        Circle circle = new Circle();
        circle.setId(Math.random() + "");
        circle.setRadius(30);
        circle.setLayoutX(parent.getLayoutX() + 100);
        circle.setLayoutY(parent.getLayoutY());
        circle.setFill(Color.BLUE);

        space.getChildren().add(circle);

        right = new Button();
        right.setFont(new Font("Monospaced Bold", 32));
        right.setText(">");
        right.setTextFill(Color.WHITE);
        right.setStyle("-fx-background-color: transparent;");
        right.setPadding(new Insets(-10, 0, -7, 0));
        right.setLayoutX(circle.getLayoutX() + circle.getRadius());
        right.setLayoutY(circle.getLayoutY());

        left = new Button();
        left.setFont(new Font("Monospaced Bold", 32));
        left.setText("<");
        left.setTextFill(Color.WHITE);
        left.setStyle("-fx-background-color: transparent;");
        left.setPadding(new Insets(-10, 0, -7, 0));
        left.setLayoutX(circle.getLayoutX() - circle.getRadius());
        left.setLayoutY(circle.getLayoutY());

        space.getChildren().add(right);
        space.getChildren().add(left);
    }

    @FXML
    void initialize() {
        assert sun != null : "fx:id=\"sun\" was not injected: check your FXML file 'hello-view.fxml'.";
        selected = null;
        addButton = new Button();
        removeButton = new Button();

        sun.setOnMouseClicked(event -> select(sun));
    }
}
