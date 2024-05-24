package celestial.smat.Classes;

import javafx.scene.shape.Circle;

public interface CuerpoCeleste {

    String getName();
    Double getTemperature(); // k
    Double getRadius(); // m
    Double getSpeed(); // m/s
    Double getDensity(); // g/cm³
    Double getMass(); // km
    Double getX();
    Double getY();
    Double getVelocidadX();
    Double getVelocidadY();
    void setVelocidadX(Double velocidad);
    void setVelocidadY(Double velocidad);
    void actualizarMasa(Double masa);
    void actualizarPosicion();


    Circle getCircle();

    void setCircle(Circle circle);
}
