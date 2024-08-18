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
    private boolean archivosSubidos = false;
    @FXML
    private Button btnRegresar;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegresar.setOnMouseClicked(event -> {
            try {
                regresarBienvenida(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnStart.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnConfiguracion.setOnMouseClicked(event -> {
            try {
                vCargarArchivo(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }    

    public void home (boolean subioArchivos) {
        archivosSubidos = subioArchivos;
        
        btnRegresar.setOnMouseClicked(event -> {
            try {
                regresarBienvenida(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnStart.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnConfiguracion.setOnMouseClicked(event -> {
            try {
                vCargarArchivo(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    @FXML
    private void iniciarJuegoM(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vCantidadPreguntas.fxml"));
        root = loader.load();
            
        VCantidadPreguntasController vCPC = loader.getController();
        vCPC.home(archivosSubidos);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    private void regresarBienvenida(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vBienvenida.fxml"));
        root = loader.load();
            
        VBienvenidaController vbc = loader.getController();
        vbc.home(archivosSubidos);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    private void vCargarArchivo (Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vSubirArchivo.fxml"));
        root = loader.load();

        VSubirArchivoController vSubirArchivo = loader.getController();
        vSubirArchivo.home();

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
    
}
