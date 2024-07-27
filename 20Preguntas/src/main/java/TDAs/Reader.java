package TDAs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public static HashMap<String, ArrayList<Integer>> readerToHashMap (String filePath) {
        HashMap<String, ArrayList<Integer>> resultMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    String animalName = parts[0];
                    ArrayList<Integer> values = new ArrayList<>();
                    
                    // Process the rest of the parts
                    for (int i = 1; i < parts.length; i++) {
                        if ("sí".equals(parts[i])) {
                            values.add(1);
                        } else if ("no".equals(parts[i])) {
                            values.add(0);
                        }
                    }
                    
                    // Add to the HashMap
                    resultMap.put(animalName, values);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no fue encontrado en la ruta especificada: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        }
        
        return resultMap;
    }
    
    public static ArrayList <String> readToList (String path) {
        ArrayList <String> returnList = new ArrayList <> ();
        FileReader archivo;
        BufferedReader lector;
        try {
            archivo = new FileReader(path);
            if (archivo.ready()) {
                lector = new BufferedReader (archivo);
                String cadena;
                while ((cadena = lector.readLine()) != null) {
                    returnList.add(cadena);
                }
            }
            else {
                System.out.println("El archivo no está listo para ser leido...");
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        return returnList;
    }
    
    public static void createSampleFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Perro sí no sí");
            writer.newLine();
            writer.write("Gato sí sí no");
            writer.newLine();
            writer.write("Pájaro no sí no");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}