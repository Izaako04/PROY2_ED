package ec.edu.espol.ventanas;

import GameLogic.Game;
import TDAs.Reader;
import TDAs.TreeG4;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Grupo 4
 */

public class VSubirArchivoController implements Initializable {
    private Label lblArchivoSeleccionadoP;
    private File archivoSeleccionado;
    @FXML
    private Button btnIniciarsin;
    @FXML
    private Button btnContinuar;
    @FXML
    private Text simboloduda;
    private int cantPreguntas;
    @FXML
    private ImageView archivoPreguntas;
    @FXML
    private ImageView archivoRespuestas;
    private ArrayList<String> questionsTxt;
    private Game jueguito;
    private boolean archivoPreguntasSubido = false, archivoRespuestasSubido = false;
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void home () {
        configBtnArchivos();
        
        btnContinuar.setOnMouseClicked(event -> {try {
            regresar (event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }); 
   }
    
    private boolean handleCargarArchivo(MouseEvent event, String newNameFile) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));

        // Mostrar el cuadro de diálogo de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            File targetFolder = new File("Txts");
            if (!targetFolder.exists()) {
                targetFolder.mkdirs(); // Crea la carpeta si no existe
            }

            // Construir el nuevo archivo con el nombre proporcionado
            File targetFile = new File(targetFolder, newNameFile + ".txt");

            // Copiar el archivo seleccionado al nuevo archivo
            Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return true;
        } else {
            muestraAlerta("Archivo", "No se seleccionó ningún archivo");
            return false;
        }
    }

    public void ventanaEmpezarJuego (Event event) throws IOException {
        if (!archivoPreguntasSubido || !archivoRespuestasSubido) {
            muestraAlerta ("Empezando el juego", "Se usará la información del juego ya que no se seleccionó ningún archivo de texto");
        }
        
        TreeG4 <String> arbol = generarArbol (archivoPreguntasSubido && archivoRespuestasSubido);
//        questionsTxt = arbol.getContent();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vPreguntas.fxml"));
        root = loader.load();
        

        VPreguntasController vPreguntas = loader.getController();
        vPreguntas.home(cantPreguntas, arbol, jueguito.getPreguntasTxt(), archivoPreguntasSubido && archivoRespuestasSubido);

        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
    
    public TreeG4 <String> generarArbol (boolean subioArchivos) {
        TreeG4 <String> arbol = new TreeG4 <> ();
        jueguito = new Game();
        jueguito.buildDecisionsTree(subioArchivos);
        arbol = jueguito.getTree();
        return arbol;
    }
    
    // Método para obtener el archivo seleccionado
    public File getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    private boolean subirArchivoPreguntas(MouseEvent event) throws IOException {
        return handleCargarArchivo(event, "preguntas");
    }
    
    private boolean subirArchivoRespuestas(MouseEvent event) throws IOException {
        return handleCargarArchivo(event, "respuestas");
    }
    
    private void configBtnArchivos(){
        archivoPreguntas.setOnMouseClicked(event -> {
            try{
                if (subirArchivoPreguntas(event)) {
                    muestraAlerta("Archivo", "¡Su archivo de Preguntas se ha subido con Exito!");
                    archivoPreguntasSubido = true;
                } else {
                    archivoPreguntasSubido = false;
                }
            }catch (IOException ex) {
                ex.printStackTrace();
                }
        });
        
        archivoRespuestas.setOnMouseClicked((event -> {
            try{
                if (subirArchivoRespuestas(event)) {
                    muestraAlerta("Archivo", "¡Su archivo de Respuestas se ha subido con Exito!");
                    archivoRespuestasSubido = true;
                } else {
                    archivoRespuestasSubido = false;
                }
            }catch (IOException ex) {
                ex.printStackTrace();
                }
        }));
        
        simboloduda.setOnMouseClicked(event -> {
            try{
                if (subirArchivoPreguntas(event)) {
                    muestraAlerta("Archivo", "¡Su archivo de Preguntas se ha subido con Exito!");
                    archivoPreguntasSubido = true;
                } else {
                    archivoPreguntasSubido = false;
                }
            }catch (IOException ex) {
                ex.printStackTrace();
                }
        });
        
    }
    
    private void regresar (Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenu.fxml"));
        root = loader.load();
        VMenuController vMenuController = loader.getController();
        vMenuController.home(archivoPreguntasSubido && archivoRespuestasSubido);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    private void muestraAlerta (String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
    
}
