package celestial.smat.Classes;

import javafx.scene.shape.Circle;

public interface CuerpoCeleste {

    String getName();
    Double getTemperature(); // k
    Double getRadius(); // m
    Double getSpeed(); // m/s
    Double getDensity(); // g/cm³


    Circle getCircle();

    void setCircle(Circle circle);
}
