module ec.edu.espol.preguntas {
    requires javafx.controls;
    requires javafx.fxml;


    opens ec.edu.espol.preguntas to javafx.fxml;
    exports ec.edu.espol.preguntas;
}
