package TDAs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Grupo 4
 */

public class TreeG4 <E> {
    private Node <E> root;
    
    protected class Node <E> {
        private E content;
        private TreeG4 <E> yesBranch, noBranch;
        
        public Node (E e) {
            content = e;
            yesBranch = noBranch = null;
        }
    }
    
    public TreeG4 () {
        root = null;
    }
    
    public TreeG4 (E e) {
        root = new Node (e);
    }

    public void addLeft (TreeG4 <E> tree) {
        if (root.yesBranch != null) return;
        root.yesBranch = tree;
    }
    
    public void addRight (TreeG4 <E> tree) {
        if (root.noBranch != null) return;
        root.noBranch = tree;
    }
    
    public boolean isEmpty () {
        return root == null;
    }
    
    public int height (){
        if (isEmpty()) return 0;
        if (isLeaf()) return 1;
        int alturaIzq = (root.yesBranch!=null) ? root.yesBranch.height() : 0;
        int alturaDer = 0;
        if (root.noBranch!=null) alturaDer = root.noBranch.height();

        return 1 + Math.max( alturaDer, alturaIzq);
    }
    
    public boolean isLeaf () {
        //if(isEmpty()) return false;
        return root.yesBranch == null && root.noBranch == null;
    }
    
    public void setRoot (E e) {
        root = new Node (e);  
    }
    
    public TreeG4 <E> getYesBranch () {
        return root.yesBranch;
    }
    
    public TreeG4 <E> getNoBranch () {
        return root.noBranch;
    }
    
    public E getContent () {
        return root.content;
    }
    
     public ArrayList <E> recorridoPreOrden (){
        if(isEmpty()) return null;
        ArrayList<E> recorrido = new ArrayList<>();
        recorrido.add(root.content);

        if(root.yesBranch!=null) recorrido.addAll(root.yesBranch.recorridoPreOrden());
        if(root.noBranch!=null) recorrido.addAll(root.noBranch.recorridoPreOrden());
        return recorrido;
    }
     
     public ArrayList<E> recorridoPorNiveles() {
        ArrayList<E> recorrido = new ArrayList<>();
        
        if (isEmpty()) return recorrido;
        
        Queue<TreeG4<E>> queue = new LinkedList<>();
        queue.add(this); // Empezamos con la raíz
        
        while (!queue.isEmpty()) {
            TreeG4<E> currentTree = queue.poll();
            recorrido.add(currentTree.getContent());
            
            if (currentTree.getYesBranch() != null) {
                queue.add(currentTree.getYesBranch());
            }
            
            if (currentTree.getNoBranch() != null) {
                queue.add(currentTree.getNoBranch());
            }
        }
        
        return recorrido;
    }
}