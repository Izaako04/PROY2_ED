package ec.edu.espol.ventanas; 
//hola :D Holaaaaaaa, sale video llamada en whatsapp
// okas
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import GameLogic.Game;
import TDAs.Reader;
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
import javafx.scene.layout.StackPane;
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
    private  ArrayList<String> preguntas;
    private ArrayList<String> conjuntos;
    private Stack<TreeG4<String>> stack;
    private boolean botonSiPresionado = false, botonNoPresionado = false;
    @FXML
    private StackPane StackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void home (int nPreguntas, TreeG4 <String> tree, ArrayList<String> prets){
        preguntas = prets;
        treeGame = tree;
        cantPreguntas = nPreguntas +1;
         txPregunta.setWrappingWidth(600);
        System.out.println(treeGame.recorridoPorNiveles());
        empezarJuego(nPreguntas);
    }   

    private void empezarJuego(int n) {
        stack = new Stack<>();
        stack.push(treeGame);
        if(n!=0) {
            procesarNodo();
        }
        else{
            procesarNodoInfinito();
        }
    }
    
    private void procesarNodo () {
        if (stack.isEmpty()) {
            return;
        }
        
        TreeG4<String> tempTree = stack.pop();
        System.out.println("Cantidad de preguntas> "+cantPreguntas);
        System.out.println("Cantidad de pregutnas recorridas>> "+conteoPregunta);
        
        conjuntos = tempTree.recorridoPorNiveles();

        if(conteoPregunta==cantPreguntas){
            ArrayList <String> lConjuntoAnimales = new ArrayList <>();
                for(String s: conjuntos){
                    if(!preguntas.contains(s)) {
                        lConjuntoAnimales.add(s);
                    }
                }
                    System.out.println(respuesta);
                    System.out.println(lConjuntoAnimales.toString());
                    System.out.println(lConjuntoAnimales.isEmpty());
                ventanaMostrarRespuesta(respuesta, lConjuntoAnimales);
        }
        
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
            }else {
                respuesta = tempTree.getContent();
               // txPregunta.setText("Tu animal es: " + tempTree.getContent());
                try {
                    ventanaMostrarRespuesta(respuesta,conjuntos);
                } catch (Exception e) {
                    
                }
            }
        }
    }
    
    
        private void procesarNodoInfinito () {
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
            }else {
                respuesta = tempTree.getContent();
               // txPregunta.setText("Tu animal es: " + tempTree.getContent());
                try {
                    ventanaMostrarRespuesta(respuesta,conjuntos);
                } catch (Exception e) {                   
                }
            }
        }
    }

    private void respuestaSi(Event event) {
        botonSiPresionado = true;
        conteoPregunta+=1;
    }

    private void respuestaNo(Event event) {
        botonNoPresionado = true;
        conteoPregunta+=1;
    }
    
    
    public void ventanaMostrarRespuesta (String animal, ArrayList<String> respuestasDadas) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("vResultado.fxml"));
        
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        VResultadoController vRespuesta = loader.getController();
        vRespuesta.home(animal, respuestasDadas);
        
        stage = (Stage) txPregunta.getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}