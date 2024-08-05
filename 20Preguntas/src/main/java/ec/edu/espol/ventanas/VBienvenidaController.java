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

public class VBienvenidaController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnIniciar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIniciar.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    

    @FXML
    private void iniciarJuegoM(MouseEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenu.fxml"));
            root = loader.load();            
            VMenuController vSubirArchivo = loader.getController();
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
