package celestial.smat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    private static Scene scene = null;

    @Override
    public void start(Stage stage) throws IOException {
        String nombre = "hello-view";
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(nombre + ".fxml"));
        scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("SMAT");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    static void setScene(String nombre) throws IOException {
        scene.setRoot(new FXMLLoader(App.class.getResource(nombre + ".fxml")).load());
    }

    public static void main(String[] args) {
        launch();
    }
}