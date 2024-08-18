package ec.edu.espol.ventanas;

import GameLogic.Game;
import GameLogic.TreeBuilder;
import TDAs.TreeG4;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grupo 4
 */

public class VAgregarAnimalController implements Initializable {

    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnAtras;
    private Game jueguito;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private ArrayList<String> respuestas;
    private int nPreguntas = 0;
    @FXML
    private TextField txtNombre;
    @FXML
    private ImageView imgPrevisualizer;
    private boolean archivosSubidos = false;
    private TreeG4 <String> tree;
    private ArrayList <String> preguntasEnOrdenReal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void home (ArrayList<String> rDadas, boolean subioArchivos) {
        archivosSubidos = subioArchivos;
        tree = generarArbol(subioArchivos);
        respuestas = rDadas;
        limpiarRespuestas ();
        
        imgPrevisualizer.setOnMouseClicked(event -> {try {
            escogerImg(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnContinuar.setOnMouseClicked(event -> {try {
            boolean escritura = escribirAnimalTxt(respuestas);
            if(escritura == true) regresar(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnAtras.setOnMouseClicked (event -> {
            try {
                regresar (event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private void limpiarRespuestas() {
        ArrayList<String> r = new ArrayList<>(respuestas.subList(1, respuestas.size()));
        ArrayList<String> preguntas = new ArrayList<>();
        Stack<TreeG4<String>> s = new Stack<>();
        s.push(tree);
        int i = 0;

        while (!s.isEmpty() && i < r.size()) {
            TreeG4<String> tempTree = s.pop();
            preguntas.add(tempTree.getContent());

            if (r.get(i).equals("sí")) {
                tempTree = tempTree.getYesBranch();
            } else {
                tempTree = tempTree.getNoBranch();
            }

            s.push(tempTree);
            i++;
        }

        ArrayList<String> respuestasOrdenadas = new ArrayList<>();
        respuestasOrdenadas.add(" ");

        for (String pregunta : preguntasEnOrdenReal) {
            int index = preguntas.indexOf(pregunta);
            if (index != -1) {
                respuestasOrdenadas.add(respuestas.get(index + 1));
            }
        }

        respuestas = respuestasOrdenadas;
    }
    
    private void muestraAlerta (String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
    
    private void regresar (Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vMenu.fxml"));
        root = loader.load();
        VMenuController vMenuController = loader.getController();
        vMenuController.home(archivosSubidos);
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public TreeG4 <String> generarArbol (boolean subioArchivos) {
        TreeG4 <String> arbol = new TreeG4 <> ();
        jueguito = new Game();
        jueguito.buildDecisionsTree(subioArchivos);
        arbol = jueguito.getTree();
        
        TreeBuilder builder = new TreeBuilder(archivosSubidos);
        builder.setAnimals();
        preguntasEnOrdenReal = builder.setQuestions();
        builder.reset();
        
        return arbol;
    }

    public boolean escribirAnimalTxt(ArrayList<String> respuestas){
        boolean campoVacio = txtNombre.getText().equals("");
        if (campoVacio) { // campos vacíos
            muestraAlerta ("Error al cargar tu animal", "Por favor intenta de nuevo");
            return false;
        }
        
        String rutaArchivo = "";
        
        if (archivosSubidos) rutaArchivo = "./Txts/respuestas.txt";
        else rutaArchivo = "respuestas.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo,true))) {
            // Escribir el string a
            writer.write("\n"+txtNombre.getText());
            
            // Escribir los elementos del ArrayList
            for (int i = 1; i < respuestas.size(); i++) {
                writer.write(respuestas.get(i));
            }
            
            muestraAlerta("Aviso", "Animal agregado con éxito!");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            muestraAlerta("ERROR", "Hubo un fallo al agregar el animal");
            return false;
        }

    }
        
    public void escogerImg(MouseEvent event) throws IOException {
      FileChooser fileChooser = new FileChooser();

        // filtro imágenes
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
           Path projectBasePath = Paths.get(System.getProperty("user.dir"));

        // Construir la ruta relativa
        Path targetFolderPath = projectBasePath.resolve("src/main/resources/imagenes");

        // Crear la carpeta de destino si no existe
        File targetFolder = targetFolderPath.toFile();
//        if (!targetFolder.exists()) {
//            targetFolder.mkdirs(); // Crear la carpeta si no existe
//        }

        // Crear el archivo de destino
        File targetFile = targetFolderPath.resolve(selectedFile.getName()).toFile();

        try {
            // Copiar la imagen seleccionada a la carpeta de destino
            Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Mostrar la imagen en la interfaz
            Image image = new Image(targetFile.toURI().toString());
            imgPrevisualizer.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores 
            }
        }    
    }
}