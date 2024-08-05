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
 * @author Grupo 4
 */
public class VMenuController implements Initializable {
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    @FXML
    private Button btnStart;
    @FXML
    private Button btnConfiguracion;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnStart.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    

    public void home () {}
    
    @FXML
    private void iniciarJuegoM(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vCantidadPreguntas.fxml"));
        root = loader.load();
            
        VCantidadPreguntasController vCPC = loader.getController();
        vCPC.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
}
