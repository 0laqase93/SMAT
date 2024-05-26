package celestial.smat;

import celestial.smat.Classes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Scene scene = null;
    public static NavigationController navigationController = null;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("space.fxml"));
        this.scene = new Scene(fxmlLoader.load(), 1000, 700);
        this.scene.setFill(Color.BLACK);
        stage.setTitle("SMAT");
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        navigationController = new NavigationController(PrincipalController.getSpace(), scene);
        new PhisicsController();
    }

    static void setScene(String nombre) throws IOException {
        scene.setRoot(new FXMLLoader(App.class.getResource(nombre + ".fxml")).load());
    }

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}
