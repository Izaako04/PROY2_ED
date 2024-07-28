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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VSubirArchivoController implements Initializable {

    @FXML
    private ImageView archivoPreguntas;
    
    private Label lblArchivoSeleccionado;
    
    private File archivoSeleccionado;
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
        

    
    }
    
    private void handleCargarArchivo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        
        // Obtener el Stage desde el evento del mouse
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Mostrar el cuadro de diálogo de selección de archivo
        archivoSeleccionado = fileChooser.showOpenDialog(stage);
        if (archivoSeleccionado != null) {
            lblArchivoSeleccionado.setText("Archivo seleccionado: " + archivoSeleccionado.getName());
        } else {
            lblArchivoSeleccionado.setText("No se ha seleccionado ningún archivo");
        }
    }

    // Método para obtener el archivo seleccionado
    public File getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    @FXML
    private void subirArchivoPreguntas(MouseEvent event) {
        handleCargarArchivo(event);
    }
}
