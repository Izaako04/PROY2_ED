/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.ventanas;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Grupo 4
 */

public class VResultadoController implements Initializable {

    @FXML
    private ImageView imgResultado;
    @FXML
    private Text txtRespuesta;
    private int modo;
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button btnRegresar;
    private boolean seleccionar;
    private ArrayList<String> btnsSelects; 
    private boolean archivosSubidos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void home(String animal, ArrayList<String> conjuntos, int modoJuego, ArrayList<String> botonesSelects, boolean subioArchivos){
        archivosSubidos = subioArchivos;
        btnsSelects = botonesSelects;
        modo = modoJuego;
        
        seleccionar = mostrarRespueta(animal, conjuntos);
        
        btnRegresar.setOnAction(event -> {
            try {
                if(seleccionar){
                Boolean valor = alertar("AVISO","Tu animal no ha sido encontrado", "¿Desea agregar su animal al Txt?");
                if(valor) reescribirArchivo(event, btnsSelects);
                else regresar(event);
                }
                else regresar(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                });   
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
    
    public boolean mostrarRespueta(String animal, ArrayList<String> conjuntos){
        if(animal == null && conjuntos.isEmpty()){
            txtRespuesta.setText("Animal no encontrado!");
            mostrarImagen("desconocido");

            if(modo == 0){ 
                return true;
            }
        }else if(animal == null && conjuntos.size() == 1){
            txtRespuesta.setText(conjuntos.get(0));
            mostrarImagen(conjuntos.get(0));
        }else if(conjuntos.size()!=1){
            String texto = "  ";
            for (String a : conjuntos) {
                texto += a + "\n";
            }
            
            txtRespuesta.setText(texto);
            mostrarImagen("varios");
        }else{
            txtRespuesta.setText(animal);
            mostrarImagen(animal);
        }
        return false;
     }
     
     public void mostrarImagen(String respuesta) {
        try{    
            String imagePath = "/imagenes/" + respuesta + ".png"; // Ruta a la imagen en el classpath
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imgResultado.setImage(image);
        }catch(NullPointerException  nl){
            String imagePath = "/imagenes/" + "desconocido" + ".png"; // Ruta a la imagen en el classpath
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imgResultado.setImage(image);
        }
     }
     
         private void muestraAlerta(String titulo, String mssg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mssg);
        alert.showAndWait();
    }
         
     private boolean alertar(String tittle, String Header, String cuerpo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(tittle);
        alert.setHeaderText(Header);
        alert.setContentText(cuerpo);

        ButtonType btnContinue = new ButtonType("Continuar");
        ButtonType btnCancel = new ButtonType("Cancelar");
        alert.getButtonTypes().setAll(btnContinue, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnContinue) {
            return true;
        } else {
            return false;
        }
    }
     
     public void reescribirArchivo(Event event, ArrayList<String> respuestasDadas){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vAgregarAnimal.fxml"));
        
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        VAgregarAnimalController vAgregarAnimal = loader.getController();
        vAgregarAnimal.home(respuestasDadas, archivosSubidos);
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}
