/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VBienvenidaController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void iniciarJuegoM(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VSubirArchivo.fxml"));
            root = loader.load();
            
            VSubirArchivoController vSubirArchivo = loader.getController();
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
