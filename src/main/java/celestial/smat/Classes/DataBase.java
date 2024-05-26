package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {
    public static Connection con;

    public DataBase() {
        con = crearConexion("33006", "SMATDB", "root", "root");
    }

    public ArrayList<String> nombresSistemas() {
        ArrayList<String> nombres = new ArrayList<>();
        String sql = "SELECT name FROM solarSystem;";

        try (
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ) {
            while (rs.next()) {
                nombres.add(rs.getString("name"));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("ERROR EN LA OBTENCIÓN DE NOMBRES DE SISTEMAS SOLARES: " + e.getMessage());
            alert.show();
        }

        return  nombres;
    }

    public void cargarDatos(String nombre, AnchorPane space) {
        // Eliminamos lo cargado
        space.getChildren().remove(PrincipalController.solarSystem.getStar().getCircle());

        for (CuerpoCeleste cuerpo : PrincipalController.solarSystem.getPlanets()) {
            space.getChildren().remove(cuerpo.getCircle());
        }

        Statement st = null;
        ResultSet rs = null;
        String sql = "";
        try {
            st = con.createStatement();


            // Cargar la estrella
            sql = "SELECT s.* FROM solarSystem ss INNER JOIN SMATDB.stars s ON ss.starId = s.id WHERE ss.name LIKE '" + nombre + "';";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                String name = rs.getString("name");
                Double temperature = rs.getDouble("temperature");
                Double radius = rs.getDouble("radius");
                Double density = rs.getDouble("density");
                Double mass = rs.getDouble("mass");
                Star star = new Star(space, name, temperature, radius, density, mass, 0.0);
                PrincipalController.solarSystem = new SolarSystem(star);
            }

            // cargar planetas
            sql = "SELECT p.* FROM solarSystem ss INNER JOIN SMATDB.planet p ON ss.id = p.solarSystemId WHERE ss.name LIKE '" + nombre + "';";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                Double temperature = rs.getDouble("temperature");
                Double radius = rs.getDouble("radius");
                Double density = rs.getDouble("density");
                Double mass = rs.getDouble("mass");
                Double x = rs.getDouble("x");
                Double y = rs.getDouble("y");
                Double xSpeed = rs.getDouble("xSpeed");
                Double ySpeed = rs.getDouble("ySpeed");

                Planet planet = new Planet(space, x, y, name, mass, temperature, radius, xSpeed, ySpeed, density);
                PrincipalController.solarSystem.addCuerpoCeleste(planet);
            }

            // Cargar satélites
            sql = "SELECT s.* FROM solarSystem ss INNER JOIN satellite s ON ss.id = s.solarSystemId WHERE ss.name LIKE '" + nombre + "';";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                Double temperature = rs.getDouble("temperature");
                Double radius = rs.getDouble("radius");
                Double density = rs.getDouble("density");
                Double mass = rs.getDouble("mass");
                Double x = rs.getDouble("x");
                Double y = rs.getDouble("y");
                Double xSpeed = rs.getDouble("xSpeed");
                Double ySpeed = rs.getDouble("ySpeed");

                Satellite satellite = new Satellite(space, x, y, name, mass, temperature, radius, xSpeed, ySpeed, density);
                PrincipalController.solarSystem.addCuerpoCeleste(satellite);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("ERROR: " + e.getMessage());
            alert.show();
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection crearConexion(String puerto, String baseDatos, String usuario, String passwd) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:" + puerto + "/" + baseDatos, usuario, passwd);
            return con;
        } catch (Exception e) {
            return null;
        }
    }
}
