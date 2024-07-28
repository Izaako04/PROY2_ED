package ec.edu.espol.ventanas;

import GameLogic.Game;
import TDAs.Reader;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(loadFXML("vBienvenida"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("GuessTheAnimal");
        stage.getIcons().add(new Image("\\imagenes\\icon.png"));
        stage.show();
        
        //System.out.println(Reader.readerToHashMap("animales.txt"));
        
        // testeando la demo
//        Game jueguito = new Game ();
//        jueguito.buildDecisionsTree();
//        System.out.println(jueguito.getTree().recorridoPreOrden());
//        jueguito.testDemo();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}