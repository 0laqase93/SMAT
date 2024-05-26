module celestial.smat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens celestial.smat to javafx.fxml;
    exports celestial.smat;
}