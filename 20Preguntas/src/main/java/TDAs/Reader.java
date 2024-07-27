package TDAs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Reader {
    
    
    public static List<String> readPreguntas(String nfilev){ //direccion del archivo
        
         ArrayList<String> preguntas = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(nfilev))){
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                preguntas.add(line);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return preguntas;
    }
    
    public static Map<String, String> readRespuestas(String nfilev){ //direccion del archivo
        
        HashMap<String,String> respuestas = new HashMap<>();
        try(Scanner sc = new Scanner(new File(nfilev))){
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tokens = line.split(" ",2);
                if (tokens.length >= 2) {
                    String key = tokens[0];
                    String value = tokens[1];
                    respuestas.put(key, value);
                } 
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return respuestas;
    }
    
}
