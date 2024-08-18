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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView tortugaImg;
    private int cont = 0;
    @FXML
    private Label txtContador;
    private boolean archivosSubidos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIniciar.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        tortugaImg.setOnMouseClicked(event -> {
            cont++;
            txtContador.setText ("" + cont);
            
            if (cont == 4) {
                muestraAlerta ("Modo de juego rápido desbloqueado", "Ahora las preguntas serán más directas");
                try {
                    abrirJuegoRapido (event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }    
    
    public void home (boolean subidosArchivos) {
        archivosSubidos = subidosArchivos;
        
        btnIniciar.setOnMouseClicked(event -> {
            try {
                iniciarJuegoM(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        tortugaImg.setOnMouseClicked(event -> {
            cont++;
            txtContador.setText ("" + cont);
            
            if (cont == 4) {
                muestraAlerta ("Modo de juego rápido desbloqueado", "Ahora las preguntas serán más directas");
                try {
                    abrirJuegoRapido (event);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void abrirJuegoRapido (Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vPreguntas.fxml"));
        root = loader.load();            
        VPreguntasController preguntas = loader.getController();
        preguntas.modoJuegoRapido(true, archivosSubidos);
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
    
    
    private void muestraAlerta (String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
}