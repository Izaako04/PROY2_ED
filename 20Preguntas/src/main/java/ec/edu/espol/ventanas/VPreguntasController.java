package ec.edu.espol.ventanas; 

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import GameLogic.Question;
import GameLogic.TreeBuilder;
import TDAs.TreeG4;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
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
    @FXML
    private Button btnDes;
    
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
    private boolean botonSiPresionado = false, botonNoPresionado = false, botonDesPresionado = false;
    @FXML
    private StackPane StackPane;
    private boolean archivosSubidos;
    private PriorityQueue <Question> pqPreguntas = new PriorityQueue <> (Comparator.comparingDouble(Question::getEntropy));
    private HashMap <String, ArrayList<Integer>> animales;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void home (int nPreguntas, TreeG4 <String> tree, ArrayList<String> prets, boolean subioArchivos){
        
        muestraAlerta("Inicio de Juego", "Piensa en un animal!");
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
                float prob = Math.round(calcularProbabilidad (tempTree) * 100.0f) / 100.0f;
                String textoPregunta = tempTree.getContent();
                
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
                float prob = Math.round(calcularProbabilidad (tempTree) * 100.0f) / 100.0f;
                String textoPregunta = tempTree.getContent();
                
                if (prob != 0) {
                    textoPregunta += "\n \n" + (prob * 100) + "% de que sea " + lConjuntoAnimales.get(0);
                }
                
                txPregunta.setText(textoPregunta);
                ArrayList <String> s = (tempTree.recorridoPorNiveles());
                
                for (String ss : s) {
                    System.out.println(ss);
                }
                
                procesarPregunta (tempTree, false);
                
            } else {
                procesarHoja (tempTree);
            }
        }
        
    }
    
    private void reorganizarAnimales() {
    }
    
    private void prepararJuegoRapido() {
        
        btnDes.setVisible(false);
        TreeBuilder builder = new TreeBuilder(archivosSubidos);
        builder.setAnimals();
        preguntas = builder.setQuestions();
        pqPreguntas = builder.getQuestions();
 
        animales = builder.getAnimals();
        reorganizarAnimales();
        builder.reset();
    }

    public void modoJuegoRapido(boolean primeraVez, boolean subidoArchivos) {
        
        
        
        if (primeraVez) {
            archivosSubidos = subidoArchivos;
            prepararJuegoRapido();
        }

        if (!pqPreguntas.isEmpty()) {
            if (animales.size() == 1) {
                // Aquí mostraría el animal si ya no hay más preguntas
                respuesta = animales.keySet().iterator().next();
                lConjuntoAnimales = new ArrayList <> ();
                lConjuntoAnimales.add(respuesta);
                ventanaMostrarRespuesta(respuesta,lConjuntoAnimales, botonesSelects);
                return;
            }
            
            String pregunta = pqPreguntas.poll().toString();

            txPregunta.setText(pregunta);

            btnSi.setOnAction(event -> {
                respuestaSi(event);
                procesarPreguntaJuegoRapido(1, pregunta);
                modoJuegoRapido(false, true);
            });

            btnNo.setOnAction(event -> {
                respuestaNo(event);
                procesarPreguntaJuegoRapido(0, pregunta);
                modoJuegoRapido(false, true);
            });
        } else {
            if (animales.size() == 1) {
                // Aquí mostraría el animal si ya no hay más preguntas
                respuesta = animales.keySet().iterator().next();
                lConjuntoAnimales = new ArrayList <> ();
                lConjuntoAnimales.add(respuesta);
                ventanaMostrarRespuesta(respuesta,lConjuntoAnimales, botonesSelects);
                return;
            }
            lConjuntoAnimales = new ArrayList <> ();
            lConjuntoAnimales.add(respuesta);
            ventanaMostrarRespuesta(null,lConjuntoAnimales, botonesSelects);
        }
    }


    private void procesarPreguntaJuegoRapido(int respuesta, String pregunta) { 
        HashMap<String, ArrayList<Integer>> animalesActualizados = new HashMap<>();
        int index = preguntas.indexOf(pregunta);
        
        for (Map.Entry<String, ArrayList<Integer>> entry : animales.entrySet()) {
            if (entry.getValue().get(index) == respuesta) {
                animalesActualizados.put(entry.getKey(), entry.getValue());
            }
        }

        preguntas.remove(index);
        animales = animalesActualizados;
                
        for (Map.Entry<String, ArrayList<Integer>> entry : animales.entrySet()) {
            entry.getValue().remove(index);
        }
        
        if (animales.size() == 1) {
            return;
        }
        
        ArrayList<Integer> indicesAEliminar = new ArrayList<>();
        int sizePreguntas = pqPreguntas.size();
        
        for (int i = 0; i < sizePreguntas; i++) {
            int cont = 0;
            for (ArrayList<Integer> respuestas : animales.values()) {
                if (respuestas.get(i) == 0) {
                    cont++;
                }
            }

            if (cont == 0 || cont == animales.size()) {
                indicesAEliminar.add(i);
            }
        }

        Collections.sort(indicesAEliminar, Collections.reverseOrder());
        
        for (int indexToRemove : indicesAEliminar) {
            preguntas.remove(indexToRemove);
            for (ArrayList<Integer> respuestas : animales.values()) {
                respuestas.remove(indexToRemove);
            }
        }
        
        PriorityQueue <Question> tempPQ = new PriorityQueue <> (Comparator.comparingDouble(Question::getEntropy));
        while (!pqPreguntas.isEmpty()) {
            Question q = pqPreguntas.poll();
            if (preguntas.contains(q.getStatement())) {
                tempPQ.offer(q);
            }
        }
        
        while (!tempPQ.isEmpty()) {
            pqPreguntas.offer(tempPQ.poll());
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
        
        btnDes.setOnAction(event -> {
            if(botonesSelects.size() == 1) try {
                regresar(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            else{
                respuestaDes(event);
                TreeG4 nuevoTempTree = retrocederPregunta();
                stack.push(nuevoTempTree);
                continuar(nuevoTempTree, esHoja);
            }
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
    
    private void respuestaDes(Event event) {
        conteoPregunta-=1;
        botonesSelects.removeLast();
        botonDesPresionado = true;
    }
    
    
    private void muestraAlerta(String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
    
    public void regresar(Event event) throws IOException{
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
    
    public TreeG4 <String> retrocederPregunta(){
        
        TreeG4 <String> nuevoArbol = treeGame;
        
        for(int i = 1; i < botonesSelects.size() -1;i++){
            if(botonesSelects.get(i).equals(" sí")){
                nuevoArbol = nuevoArbol.getYesBranch();
            }else if(botonesSelects.get(i).equals(" no")) nuevoArbol = nuevoArbol.getNoBranch();
            
        }
        return nuevoArbol;
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