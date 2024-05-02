module celestial.smat {
    requires javafx.controls;
    requires javafx.fxml;


    opens celestial.smat to javafx.fxml;
    exports celestial.smat;
}