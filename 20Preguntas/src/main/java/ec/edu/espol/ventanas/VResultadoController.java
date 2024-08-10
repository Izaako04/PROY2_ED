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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VResultadoController implements Initializable {

    @FXML
    private ImageView imgResultado;
    @FXML
    private Text txtRespuesta;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnRegresar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void home(String animal){
        
        txtRespuesta.setText(animal);
       String imagePath = "/imagenes/" + animal + ".png"; // Ruta a la imagen en el classpath
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imgResultado.setImage(image);
        
          btnRegresar.setOnAction(event -> {
            try {
                regresar(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                });   
    }

        private void regresar (Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenu.fxml"));
        root = loader.load();
        VMenuController vMenuController = loader.getController();
        vMenuController.home();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
