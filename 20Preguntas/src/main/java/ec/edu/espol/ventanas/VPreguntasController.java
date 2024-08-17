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
import java.util.Collections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    
    private ArrayList<String> botonesSelects = new ArrayList<>(Collections.singletonList(" "));
    private String respuesta;
    private int cantPreguntas;
    private int conteoPregunta = 1;
    private int modoJuego;
    private TreeG4 <String> treeGame;
    private  ArrayList<String> preguntas;
    private ArrayList<String> conjuntos;
    private ArrayList<String> lConjuntoAnimales;
    private Stack<TreeG4<String>> stack;
    private boolean botonSiPresionado = false, botonNoPresionado = false;
    @FXML
    private StackPane StackPane;
    private boolean archivosSubidos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void home (int nPreguntas, TreeG4 <String> tree, ArrayList<String> prets, boolean subioArchivos){
        archivosSubidos = subioArchivos;
        preguntas = prets;
        treeGame = tree;
        cantPreguntas = nPreguntas +1;
        txPregunta.setWrappingWidth(600);
        empezarJuego(nPreguntas);
    }   

    private void empezarJuego(int n) {
        stack = new Stack<>();
        stack.push(treeGame);
        if(n!=0) {
            procesarNodo();
            modoJuego = 1;
        }
        else{
            procesarNodoInfinito();
            modoJuego = 0;
        }
    }
    
    private void procesarNodo () {
        if (stack.isEmpty()) {
            return;
        }

        TreeG4<String> tempTree = stack.pop();
        
        conjuntos = new ArrayList <> ();
        
        if(conteoPregunta==cantPreguntas){
            ArrayList <String> lConjuntoAnimales = new ArrayList <>();
            
            if (tempTree != null) {
                conjuntos = tempTree.recorridoPorNiveles();
                    for(String s: conjuntos){
                        if(!preguntas.contains(s)) {
                            lConjuntoAnimales.add(s);
                        }
                    }
                ventanaMostrarRespuesta(respuesta, lConjuntoAnimales, botonesSelects);  
            } else {
                ventanaMostrarRespuesta(null, lConjuntoAnimales, botonesSelects);
            }
        }
        
        if (tempTree != null) {
            if (!tempTree.isLeaf()) {
                float prob = calcularProbabilidad (tempTree);
                String textoPregunta = tempTree.getContent();
                System.out.println(prob);
                
                if (prob != 0) {
                    textoPregunta += "\n " + (prob * 100) + "% de que sea " + lConjuntoAnimales.get(0);
                }
                
                txPregunta.setText(textoPregunta);
                procesarPregunta (tempTree, false);
            } else {
                procesarHoja (tempTree);
            }
        }
    }
    
     
    private void procesarNodoInfinito () {
        if (stack.isEmpty()) {
            return;
        }

        TreeG4<String> tempTree = stack.pop();
                    
        if (tempTree != null) {
            if (!tempTree.isLeaf()) {
                float prob = calcularProbabilidad (tempTree);
                String textoPregunta = tempTree.getContent();
                System.out.println(prob);
                
                if (prob != 0) {
                    textoPregunta += "\n " + (prob * 100) + "% de que sea " + lConjuntoAnimales.get(0);
                }
                
                txPregunta.setText(textoPregunta);
                procesarPregunta (tempTree, false);
                
            } else {
                procesarHoja (tempTree);
            }
        }
        
    }
    
    private float calcularProbabilidad (TreeG4 <String> tempTree) {
        cargarLConjuntoAnimales(tempTree);
        
        float porcentaje = 0;
        
        if (!lConjuntoAnimales.isEmpty()) {
            String animal = lConjuntoAnimales.get(0);
            porcentaje = (float) 1 / lConjuntoAnimales.size();
        }

        return porcentaje;
    } 
    
    private void procesarHoja (TreeG4 <String> tempTree) {
        respuesta = tempTree.getContent();
        try {
            cargarLConjuntoAnimales (tempTree);
            
            if(lConjuntoAnimales.contains(respuesta)) ventanaMostrarRespuesta(respuesta,lConjuntoAnimales, botonesSelects);
            else {
                txPregunta.setText(tempTree.getContent());
                
                procesarPregunta (tempTree, true);
            }
                    
        } catch (Exception e){}
    }
    
    private void cargarLConjuntoAnimales (TreeG4 <String> tempTree) {
        lConjuntoAnimales = new ArrayList <>();
        conjuntos = new ArrayList <> ();
        conjuntos = tempTree.recorridoPorNiveles();
                    
        for(String s: conjuntos){
            if(!preguntas.contains(s)) {
                lConjuntoAnimales.add(s);
            }
        }
    }
    
    private ArrayList <String> cargarTodosAnimales () {
        ArrayList <String> returnList = new ArrayList <>();
        ArrayList <String> todoArbol = treeGame.recorridoPorNiveles();
                    
        for(String s: todoArbol){
            if(!preguntas.contains(s)) {
                returnList.add(s);
            }
        }
        return returnList;
    }
    
    private void procesarPregunta (TreeG4 <String> tempTree, boolean esHoja) {
        btnSi.setOnAction(event -> {
            respuestaSi(event);
            stack.push(tempTree.getYesBranch());
            continuar(tempTree, esHoja);
        });
        
        btnNo.setOnAction(event -> {
            respuestaNo(event);
            stack.push(tempTree.getNoBranch());
            continuar(tempTree, esHoja);
        });
    }
    
    private void continuar (TreeG4 <String> tempTree, boolean esHoja) {
        if (!esHoja) {
            if (modoJuego == 0)  procesarNodoInfinito();
            else procesarNodo();
        } else {
            ventanaMostrarRespuesta (null,lConjuntoAnimales, botonesSelects);
        }
    }
    
    private void respuestaSi(Event event) {
        botonSiPresionado = true;
        conteoPregunta+=1;
        botonesSelects.add(" sí");
    }

    private void respuestaNo(Event event) {
        botonNoPresionado = true;
        conteoPregunta+=1;
        botonesSelects.add(" no");
    }
    
    
    public void ventanaMostrarRespuesta (String animal, ArrayList<String> respuestasDadas, ArrayList<String> botonesSelects) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vResultado.fxml"));
        
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        VResultadoController vRespuesta = loader.getController();
        vRespuesta.home(animal, respuestasDadas, modoJuego, botonesSelects, archivosSubidos);
        
        stage = (Stage) txPregunta.getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}