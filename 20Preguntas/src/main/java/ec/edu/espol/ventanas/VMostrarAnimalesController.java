package ec.edu.espol.ventanas;

import GameLogic.Game;
import GameLogic.TreeBuilder;
import TDAs.Reader;
import TDAs.TreeG4;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grupo 4
 */

public class VMostrarAnimalesController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnIniciar;
    @FXML
    private ImageView tortugaImg;
    @FXML
    private TextArea txAreaAnimales;
    
    private boolean subioArchivo;
    private HashMap <String, ArrayList<Integer>> animales;
    @FXML
    private Label txtContador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void home (boolean sbArchivo) {
        subioArchivo = sbArchivo;
         guardarAnimales();
         Set<String> keySet = animales.keySet();

        // Construir el texto para mostrar las claves en forma de cascada
        StringBuilder sb = new StringBuilder();
        for (String key : keySet) {
            sb.append(key).append("\n");
        }

        // Establecer el texto en el TextArea
        txAreaAnimales.setText(sb.toString());
        
        btnIniciar.setOnMouseClicked(event -> {
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
        vMenuController.home(subioArchivo);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void guardarAnimales() {
        if (subioArchivo) animales = Reader.readerToHashMap("Txts/respuestas.txt");
        else animales = Reader.readerToHashMap("respuestas.txt");
    }

}