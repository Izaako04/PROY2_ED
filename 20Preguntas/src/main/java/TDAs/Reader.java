package TDAs;

import java.io.BufferedReader;
import java.io.FileReader;

public class Reader {

    
    public static void read(){
        FileReader archivo;
        BufferedReader lector;
        try {
        archivo = new FileReader("C:\\PROY2_ED\\20Preguntas\\preguntas.txt");
        if (archivo.ready()) {
            lector = new BufferedReader (archivo);
            String cadena;
            while ((cadena = lector.readLine()) != null) {
                System.out.println(cadena);
            }
        }
        else {
            System.out.println("El archivo no está listo para ser leido...");
        }
        } catch (Exception e) {
        System.out.println("Error: "+e.getMessage());
        }

    }
    
}
