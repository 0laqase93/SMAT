package celestial.smat.Classes;

import celestial.smat.Exceptions.DuplicatedNameException;
import celestial.smat.PrincipalController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DataBase {
    public static Connection con;

    public DataBase() {
        con = crearConexion("33006", "SMATDB", "root", "dbrootpass");
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
            sql = "SELECT s.*, ss.name AS 'sName' FROM solarSystem ss INNER JOIN SMATDB.stars s ON ss.starId = s.id WHERE ss.name LIKE '" + nombre + "';";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                String solarSystemName = rs.getString("sName");
                String name = rs.getString("name");
                Double temperature = rs.getDouble("temperature");
                Double radius = rs.getDouble("radius");
                Double density = rs.getDouble("density");
                Double mass = rs.getDouble("mass");
                Star star = new Star(space, name, temperature, radius, density, mass, 0.0);
                PrincipalController.solarSystem = new SolarSystem(solarSystemName, star);
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

                Planet planet = new Planet(space, x, y, name, mass, temperature, radius, xSpeed / 1000, ySpeed / 1000, density);
                SolarSystem.addCuerpoCeleste(planet);
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

                Satellite satellite = new Satellite(space, x, y, name, mass, temperature, radius, xSpeed / 1000, ySpeed / 1000, density);
                SolarSystem.addCuerpoCeleste(satellite);
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

    public void guardarDatos(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM solarSystem WHERE name LIKE ?";
        try {
            // Verificar si el sistema solar ya existe en la base de datos
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // Si el sistema solar existe, mostrar los planetas asociados y preguntar al usuario si desea sobreescribirlos
            if (rs.next()) {
                String planetas = obtenerNombresPlanetas(name);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Nombre existente");
                alert.setHeaderText("Sistema solar con el mismo nombre.");
                alert.setContentText("Parece que se ha encontrado un sistema solar con el mismo nombre. Sus planetas son: " + planetas + "\n¿Desea sobreescribirlo?");
                ButtonType si = new ButtonType("SÍ");
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(si, no);
                Optional<ButtonType> result = alert.showAndWait();

                // Si el usuario confirma la sobrescritura, actualizar los planetas y satélites asociados
                if (result.isPresent() && result.get() == si) {
                    for (CuerpoCeleste object : PrincipalController.solarSystem.getPlanets()) {
                        String cuerpoCelesteType = (object instanceof Planet) ? "planet" : "satellite";
                        sql = "UPDATE " + cuerpoCelesteType + " SET name = ?, temperature = ?, radius = ?, density = ?, mass = ?, x = ?, y = ?, xSpeed = ?, ySpeed = ? WHERE solarSystemId = (SELECT id FROM solarSystem WHERE name LIKE ?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, object.getName());
                        ps.setDouble(2, object.getTemperature());
                        ps.setDouble(3, object.getRadius());
                        ps.setDouble(4, object.getDensity());
                        ps.setDouble(5, object.getMass());
                        ps.setDouble(6, object.getX());
                        ps.setDouble(7, object.getY());
                        ps.setDouble(8, object.getVelocidadX());
                        ps.setDouble(9, object.getVelocidadY());
                        ps.setString(10, name);
                        ps.executeUpdate();
                    }
                } else {
                    throw new DuplicatedNameException("No pueden haber dos sistemas con el mismo nombre.");
                }
            } else {
                // Verificar si hay una estrella con ese nombre
                sql = "SELECT * FROM stars WHERE name LIKE ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1, SolarSystem.star.getName());
                rs = ps.executeQuery();
                if (rs.next()) {
                    throw new DuplicatedNameException("No pueden haber dos estrellas con el mismo nombre.");
                }

                int id = 0;

                // Guardar la estrella
                sql = "INSERT INTO stars (name, temperature, radius, density, mass, x, y) VALUE (?, ?, ?, ?, ?, ?, ?);";
                ps = con.prepareStatement(sql);
                Star star = SolarSystem.star;
                ps.setString(1, star.getName());
                ps.setDouble(2, star.getTemperature());
                ps.setDouble(3, star.getRadius());
                ps.setDouble(4, star.getDensity());
                ps.setDouble(5, star.getMass());
                ps.setDouble(6, star.getX());
                ps.setDouble(7, star.getY());
                ps.executeUpdate();

                // Coger id de la estrella
                sql = "SELECT id FROM stars WHERE name LIKE ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1, star.getName());
                rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id");
                }

                // Guardar sistema solar
                sql = "INSERT INTO solarSystem (name, starId) VALUES (?, ?);";
                ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, id);
                ps.executeUpdate();

                // Coger id del sistema solar
                sql = "SELECT id FROM solarSystem WHERE name LIKE ?;";
                ps = con.prepareStatement(sql);
                ps.setString(1, SolarSystem.name);
                rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id");
                }

                // Guardar planetas
                for (CuerpoCeleste object : PrincipalController.solarSystem.getPlanets()) {
                    if (object instanceof Planet) {
                        sql = "INSERT INTO planet (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, object.getName());
                        ps.setDouble(2, object.getTemperature());
                        ps.setDouble(3, object.getRadius());
                        ps.setDouble(4, object.getDensity());
                        ps.setDouble(5, object.getMass());
                        ps.setDouble(6, object.getX());
                        ps.setDouble(7, object.getY());
                        ps.setDouble(8, object.getVelocidadX());
                        ps.setDouble(9, object.getVelocidadY());
                        ps.setInt(10, id);
                    } else {
                        sql = "INSERT INTO satellite (name, temperature, radius, density, mass, x, y, xSpeed, ySpeed, solarSystemId) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, object.getName());
                        ps.setDouble(2, object.getTemperature());
                        ps.setDouble(3, object.getRadius());
                        ps.setDouble(4, object.getDensity());
                        ps.setDouble(5, object.getMass());
                        ps.setDouble(6, object.getX());
                        ps.setDouble(7, object.getY());
                        ps.setDouble(8, object.getVelocidadX());
                        ps.setDouble(9, object.getVelocidadY());
                        ps.setInt(10, id);
                    }
                    ps.executeUpdate();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SISTEMA SOLAR GUARDADO");
                alert.setHeaderText("El sistema solar se ha guardado exitosamente");
                alert.setContentText("No ha habido ningún problema a la hora de guardar la estructura del sistema solar.");
                alert.showAndWait();
            }
        } catch (DuplicatedNameException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nombre inválido");
            alert.setHeaderText("Nombre duplicado.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para obtener los nombres de los planetas asociados a un sistema solar
    private String obtenerNombresPlanetas(String name) throws SQLException {
        StringBuilder planetas = new StringBuilder();
        String sql = "SELECT p.name FROM solarSystem ss INNER JOIN planet p ON ss.id = p.solarSystemId WHERE ss.name LIKE ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            planetas.append("\n").append(rs.getString("name"));
        }
        return planetas.toString();
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
