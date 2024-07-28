module ec.edu.espol.ventanas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;


    opens ec.edu.espol.ventanas to javafx.fxml;
    exports ec.edu.espol.ventanas;
}
