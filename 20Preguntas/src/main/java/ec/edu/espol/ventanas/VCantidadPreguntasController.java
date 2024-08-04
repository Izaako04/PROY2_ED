/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Grupo 4
 */

public class VCantidadPreguntasController implements Initializable {

    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnAtras;
    @FXML
    private TextField tfNumero;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    private int nPreguntas = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void home () {
        TextFormatter<Integer> textFormatterNPreguntas = new TextFormatter<Integer> (
            new IntegerStringConverter(),
            null,
            change -> {
                if (change.isAdded() && !change.getText().matches("\\d*\\.?\\d*")) {
                    return null;
                }
                return change;
            }
        );
        
        tfNumero.setTextFormatter(textFormatterNPreguntas);
        
        btnContinuar.setOnMouseClicked(event -> {try {
            validarNPreguntas (event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnAtras.setOnMouseClicked (event -> {
            try {
                System.out.println(":v");
                regresar (event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private void validarNPreguntas (Event event) throws IOException {
        if (tfNumero.getText().isEmpty()) {
            muestraAlerta ("Alerta", "No puedes dejar este campo vacío");
            return;
        }
        
        nPreguntas = Integer.parseInt(tfNumero.getText());
        vCargarArchivo (event);
    }
    
    private void muestraAlerta (String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
    
    private void vCargarArchivo (Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vSubirArchivo.fxml"));
        root = loader.load();

        VSubirArchivoController vSubirArchivo = loader.getController();
        vSubirArchivo.home(nPreguntas);

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
    
    private void regresar (Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenu.fxml"));
        root = loader.load();
        System.out.println("xd");
        VMenuController vMenuController = loader.getController();
        vMenuController.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}