package celestial.smat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class ControladorPrincipal {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle earth;

    @FXML
    private Circle earthOrbit;

    @FXML
    private Circle sun;

    @FXML
    void initialize() {
        assert earth != null : "fx:id=\"earth\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert earthOrbit != null : "fx:id=\"earthOrbit\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert sun != null : "fx:id=\"sun\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
