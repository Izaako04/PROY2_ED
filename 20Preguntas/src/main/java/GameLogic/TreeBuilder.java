package GameLogic;

import TDAs.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Grupo 4
 */

public class TreeBuilder {
    private TreeG4 <String> product;
    private HashMap <String, ArrayList <Integer>> animals = new HashMap <> ();
    private PriorityQueue <Question> questions;
    private ArrayList <Question> listQuestions;
    private boolean subioArchivo;
    
    public TreeBuilder (boolean subioArchivo) {
        this.subioArchivo = subioArchivo;
        reset ();
    }
    
    public void reset () {
        product = null;
        questions = new PriorityQueue <> (Comparator.comparingDouble(Question::getEntropy));
    }
    
    // step 1: read animals' file
    public void setAnimals () {
        if (subioArchivo) animals = Reader.readerToHashMap("Txts/respuestas.txt");
        else animals = Reader.readerToHashMap("respuestas.txt");
    }
    
    // step 2: read questions' files
    public ArrayList<String> setQuestions () {
        ArrayList <String> questionsTxt = new ArrayList <> ();
        
        if (subioArchivo) questionsTxt = Reader.readToList("Txts/preguntas.txt");
        else questionsTxt = Reader.readToList("preguntas.txt");
        
        int cont = 0;
        for (String s : questionsTxt) {
            Question q = new Question (s);
            double entropy = calculateEntropy (questionsTxt.indexOf(s));
            q.setEntropy(entropy);
            questions.offer(q);
            //cont++;
        }
        return questionsTxt;
    }
    
    // step 3
    public void addQuestions() {
        if (questions.isEmpty()) return;

        if (product == null) {
            // Inicializa la raíz del árbol con la primera pregunta
            Question q = questions.poll();
            product = new TreeG4<>(q.getStatement());
        }

        // Añadir el resto de las preguntas
        listQuestions = new ArrayList <> (questions);
        addSubQuestions(product, listQuestions);
    }


    // step 4
    public void addAnimals () {
        ArrayList <String> questionsTxt = new ArrayList <> ();
        
        if (subioArchivo) questionsTxt = Reader.readToList("Txts/preguntas.txt");
        else questionsTxt = Reader.readToList("preguntas.txt");
        
        for (Map.Entry<String, ArrayList <Integer>> entry: animals.entrySet()) {
            Stack <TreeG4 <String>> stackTree = new Stack <> ();
            stackTree.push(product);
            while (!stackTree.isEmpty()) {
                TreeG4 <String> tempTree = stackTree.pop();
                String qStatement = "";
                
                if (tempTree != null) {
                    qStatement = tempTree.getContent();
                    
                    int index = questionsTxt.indexOf(qStatement);
                    int answer = -1;
                    
                    if (index != -1) answer = entry.getValue().get(index);
                    
                    if (tempTree.isLeaf()) {
                        if (answer == 1) {
                            tempTree.addLeft(new TreeG4 <String> (entry.getKey()));
                        } else {
                            tempTree.addRight(new TreeG4 <String> (entry.getKey()));
                        }
                    } else {
                        if (answer == 1) {
                            if (tempTree.getYesBranch() != null) {
                                stackTree.push(tempTree.getYesBranch());
                            } else {
                                tempTree.addLeft(new TreeG4 <String> (entry.getKey()));
                            }
                        } else if (answer == 0) {
                            if (tempTree.getNoBranch() != null) {
                                stackTree.push(tempTree.getNoBranch());
                            } else {
                                tempTree.addRight(new TreeG4 <String> (entry.getKey()));
                            }
                        }
                    }
                }
            }
        }
    }

    // Final step: Return the product
    public TreeG4 getProduct () {
        return product;
    }
    
    // support methods
    private void addSubQuestions(TreeG4<String> node, List <Question> q) {
        if (node == null || questions.isEmpty()) return;

        // Si el nodo es una hoja, añade las siguientes preguntas
        if (node.isLeaf()) {
            // Crea dos nuevos nodos para la siguiente pregunta
            String nextQuestion = null;
            if (!q.isEmpty()) {
                nextQuestion = q.get(0).getStatement();
                node.addLeft(new TreeG4<>(nextQuestion));
                node.addRight(new TreeG4<>(nextQuestion));
            }
        }

        // Procesa las ramas izquierda y derecha si no están vacías
        if (node.getYesBranch() != null) {
            addSubQuestions(node.getYesBranch(), q.subList(1, q.size()));
        }
        if (node.getNoBranch() != null) {
            addSubQuestions(node.getNoBranch(), q.subList(1, q.size()));
        }
    }

    private double calculateEntropy (int n) {
        int yes = getYes(n);
        int no = getNo(n);

        if (animals.size() == 0) {
            return 0.0;
        }

        double yesEntropy = calculateYesEntropy(n);
        double noEntropy = calculateNoEntropy(n);

        double totalSize = animals.size();
        double probabilityYes = (double) yes / totalSize;
        double probabilityNo = (double) no / totalSize;

        double entropy = (probabilityYes * yesEntropy) + (probabilityNo * noEntropy);
        
        return entropy;
    }
    
    private int getYes (int n) {
        int yes = 0;
        
        for (Map.Entry <String, ArrayList<Integer>> entry : animals.entrySet()) {
            System.out.println(n + " " + entry.getValue().size());
            System.out.println(entry);
            yes += entry.getValue().get(n);
        }
        return yes;
    }
    
    private int getNo (int n) {
        int no = 0;
        
        for (Map.Entry <String, ArrayList<Integer>> entry : animals.entrySet()) {
            if (entry.getValue().get(n) == 0) no += 1;
        }
        
        return no;
    }
    
    private double calculateYesEntropy (int n) {
        double yesEntropy = 0.0;
        int yes = getYes(n);

        if (animals.size() == 0) {
            return yesEntropy;
        }

        double probabilityYes = (double) yes / animals.size();

        double log = probabilityYes > 0 ? Math.log(probabilityYes) / Math.log(2) : 0.0;

        yesEntropy = probabilityYes * log;

        return yesEntropy;
    }
    
    private double calculateNoEntropy (int n) {
        double noEntropy = 0.0;
        int no = getNo(n);

        if (animals.size() == 0) {
            return noEntropy;
        }

        double probabilityNo = (double) no / animals.size();

        double log = probabilityNo > 0 ? Math.log(probabilityNo) / Math.log(2) : 0.0;

        noEntropy = probabilityNo * log;

        return noEntropy;
    }

    public ArrayList<Question> getListQuestions() {
        return listQuestions;
    }
    
    
}