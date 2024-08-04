/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.ventanas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VSubirArchivoController implements Initializable {

    
    private Label lblArchivoSeleccionadoP;
    
    private File archivoSeleccionado;
    @FXML
    private Button btnIniciarsin;
    @FXML
    private Button btnContinuar;
    @FXML
    private ImageView archivoPreguntas1;
    @FXML
    private ImageView archivoPreguntas2;
    @FXML
    private Text simboloduda;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        configBtnArchivos();
        
    }
    
    private void handleCargarArchivo(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        
        // Mostrar el cuadro de diálogo de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            File targetFolder = new File("Txts");
            if (!targetFolder.exists()) {
                targetFolder.mkdirs(); // Crea la carpeta si no existe
            }
            File targetFile = new File(targetFolder, selectedFile.getName());
            Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
        }
    }

    // Método para obtener el archivo seleccionado
    public File getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    private void subirArchivoPreguntas(MouseEvent event) throws IOException {
        handleCargarArchivo(event);
    }
    
    private void subirArchivoRespuestas(MouseEvent event) throws IOException {
        handleCargarArchivo(event);
    }
    
    private void configBtnArchivos(){
        archivoPreguntas1.setOnMouseClicked(event -> {
              try{
                  subirArchivoPreguntas(event);
                  muestraAlerta("Archivo", "Su archivo de Preguntas se ha subido con Exito!");
                }catch (IOException ex) {
                    ex.printStackTrace();
                    }
        });
        
        archivoPreguntas2.setOnMouseClicked((event -> {
              try{
                  subirArchivoRespuestas(event);
                  muestraAlerta("Archivo", "Su archivo de Respuestas se ha subido con Exito!");
                }catch (IOException ex) {
                    ex.printStackTrace();
                    }
        }));
        
        simboloduda.setOnMouseClicked((event -> {
              try{
                  subirArchivoRespuestas(event);
                  muestraAlerta("Archivo", "Su archivo de Respuestas se ha subido con Exito!");
                }catch (IOException ex) {
                    ex.printStackTrace();
                    }
        }));
        
    }
    
        private void muestraAlerta (String titulo, String mssg) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mssg);
            alert.showAndWait();
    }
    
}
