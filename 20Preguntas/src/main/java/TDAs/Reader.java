package TDAs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    
    
    public static List<String> leerTxt(String nfilev){ //direccion del archivo
        
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
    
    public static void crearArchivo(String nfile){
        try{
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(nfile),true));
            pw.close();
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
}
