package ec.edu.espol.ventanas;
//hola :D Holaaaaaaa, sale video llamada en whatsapp
// okas
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import GameLogic.Game;
import TDAs.TreeG4;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author davsu
 */
public class VPreguntasController implements Initializable {
    @FXML
    private ImageView img1;
    @FXML
    private Text txPregunta;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private Button btnSi;
    @FXML
    private Button btnNo;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    private String respuesta;
    private int cantPreguntas;
    private int conteoPregunta = 1;
    private TreeG4 <String> treeGame;
    private Stack<TreeG4<String>> stack;
    private boolean botonSiPresionado = false, botonNoPresionado = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void home (int nPreguntas, TreeG4 <String> tree){
        treeGame = tree;
        cantPreguntas = nPreguntas +1;
        System.out.println(treeGame.recorridoPorNiveles());
        if (nPreguntas == 0) empezarJuego();
    }   

    private void empezarJuego() {
        stack = new Stack<>();
        stack.push(treeGame);
        
        procesarNodo();
    }
    
    private void procesarNodo () {
        if (stack.isEmpty()) {
            return;
        }
        
        TreeG4<String> tempTree = stack.pop();
        ArrayList<String> conjuntos = new ArrayList<>();
        
        if (tempTree != null) {
            if (!tempTree.isLeaf()) {
                txPregunta.setText(tempTree.getContent());
                btnSi.setOnAction(event -> {
                    respuestaSi(event);
                    stack.push(tempTree.getYesBranch());
                    procesarNodo();
                });

                btnNo.setOnAction(event -> {
                    respuestaNo(event);
                    stack.push(tempTree.getNoBranch());
                    procesarNodo();
                });   
//            } else if(conteoPregunta==cantPreguntas){
//                
//                conjuntos =tempTree.recorridoPreOrden();
//                System.out.println(conjuntos.toString());
            }else {
                respuesta = tempTree.getContent();
               // txPregunta.setText("Tu animal es: " + tempTree.getContent());
                try {
                    ventanaMostrarRespuesta(respuesta);
                } catch (Exception e) {
                    
                }
            }
        }
        conteoPregunta+=1;
    }

    private void respuestaSi(Event event) {
        botonSiPresionado = true;
    }

    private void respuestaNo(Event event) {
        botonNoPresionado = true;
    }
    
    
    public void ventanaMostrarRespuesta (String animal) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("vResultado.fxml"));
        root = loader.load();

        VResultadoController vRespuesta = loader.getController();
        vRespuesta.home(animal);
        
        stage = (Stage) txPregunta.getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}