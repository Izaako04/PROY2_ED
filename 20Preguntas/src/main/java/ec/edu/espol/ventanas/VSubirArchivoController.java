/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.ventanas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VSubirArchivoController implements Initializable {

    @FXML
    private ImageView archivoPreguntas;
    @FXML
    private Button btnIniciarsin;
    @FXML
    private Button btnContinuar;
    @FXML
    private ImageView archivoPreguntas1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        archivoPreguntas.setOnMouseClicked(e -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo de texto");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos de texto", "*.txt")
        );
        //File archivoSeleccionado = fileChooser.showOpenDialog(primaryStage);
        //if (archivoSeleccionado != null) {
//            String contenido = leerArchivo(archivoSeleccionado);
//            textArea.setText(contenido);

            // Procesar el contenido del archivo para inicializar el juego
//            HashMap<String, ArrayList<Integer>> animales = Reader.readerToHashMap(archivoSeleccionado.getPath());
//            TreeG4 arbol = new TreeG4(animales);
//            juego = new Game(arbol, 20);
////            actualizarPregunta(lblPregunta);
//        }
    });  
    }
}
