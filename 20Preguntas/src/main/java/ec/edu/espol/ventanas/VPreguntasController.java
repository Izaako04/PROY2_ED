package ec.edu.espol.ventanas;
//hola :D Holaaaaaaa, sale video llamada en whatsapp
// okas
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import GameLogic.Game;
import TDAs.TreeG4;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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

    private String pregunta;
    private TreeG4 <String> treeGame;
    private Stack<TreeG4<String>> stack;
    private boolean botonSiPresionado = false, botonNoPresionado = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void home (int nPreguntas, TreeG4 <String> tree){
        treeGame = tree;
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
            } else {
                txPregunta.setText("Tu animal es: " + tempTree.getContent());
            }
        }
    }

    private void respuestaSi(Event event) {
        botonSiPresionado = true;
    }

    private void respuestaNo(Event event) {
        botonNoPresionado = true;
    }
}