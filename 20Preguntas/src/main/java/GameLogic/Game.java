package GameLogic;

import TDAs.TreeG4;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Grupo 4
 */

public class Game {
    private TreeBuilder builder;
    private TreeG4 <String> gameTree;
    private ArrayList<String> preguntasTxt;
    
    public void buildDecisionsTree (boolean subioArchivo) {
        builder = new TreeBuilder (subioArchivo);
        builder.setAnimals();
        preguntasTxt = builder.setQuestions();
        builder.addQuestions();
        builder.addAnimals();
        gameTree = builder.getProduct();
    }
    
    public TreeG4 <String> getTree () {
        return gameTree;
    }

    public ArrayList<String> getPreguntasTxt() {
        return preguntasTxt;
    }
    
    
    
    public void testDemo () {
        Scanner scanner = new Scanner(System.in);
        Stack <TreeG4 <String>> stack = new Stack <> ();
        stack.push(gameTree);
        
        while (!stack.isEmpty()) {
            TreeG4 <String> tempTree = stack.pop();
            
            if (tempTree != null) {
                if (!tempTree.isLeaf()) {
                    System.out.println(tempTree.getContent());
                    String answer = scanner.nextLine().toLowerCase();

                    if (answer.equals("sí") || answer.equals("si")) {
                        stack.push(tempTree.getYesBranch());
                    } else {
                        stack.push(tempTree.getNoBranch());
                    }

                } else {
                    System.out.println("Tu animal es: " + tempTree.getContent());
                    return;
                }
            }
        }
    }
}