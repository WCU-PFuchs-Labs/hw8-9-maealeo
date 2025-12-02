package binary;
import java.util.ArrayList; 
import java.util.Random; 

import tabular.DataSet; 
import tabular.DataRow; 


public class GPTree implements Collector, Comparable<GPTree>, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness; 

    public void evalFitness(DataSet dataSet){
        fitness = 0.0; 
        for(DataRow row : dataSet.getRows()){
            double[] x = row.getXValues(); 
            double y = row.getY(); 
            double diff = eval(x) - y; 
            fitness += diff * diff; 
        }
    }

    public double getFitness(){
        return fitness; 
    }

    @Override 
    public int compareTo(GPTree t){
        return Double.compare(this.fitness, t.fitness);
    }

    @Override 
    public boolean equals(Object o){
        if(o == null || !(o instanceof GPTree)){
            return false; 
        }
        return this.compareTo((GPTree) o) == 0; 
    }

    @Override 
    public Object clone(){
        try{
            GPTree copy = (GPTree) super.clone(); 
            copy.root = (Node) this.root.clone();
            return copy; 
        } catch(CloneNotSupportedException e){
            return null; 
        }
    }
    
    
    /**
     * @param - node The node to be collected.
     * TODO: implement this method
     */
    @Override
    public void collect(Node node) {
        //add node if it isnt a leaf 
        if(!node.isLeaf()){
            crossNodes.add(node);
        }
        
    }
    
    
    // DO NOT EDIT code below for Homework 8. 
    // If you are doing the challenge mentioned in 
    // the comments above the crossover method
    // then you should create a second crossover
    // method above this comment with a slightly 
    // different name that handles all types
    // of crossover.
    
    
    /**
     * This initializes the crossNodes field and
     * calls the root Node's traverse method on this
     * so that this can collect the Binop Nodes.
     */
    public void traverse() {
        crossNodes = new ArrayList<Node>();
        root.traverse(this);
    }
    
    /**
     * This returns a String with all of the binop Strings
     * separated by semicolons
     */
    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }
   
    
    /**
     * this implements left child to left child
     * and right child to right child crossover.
     * Challenge: additionally implement left to 
     * right child and right to left child crossover.
     */
    public void crossover(GPTree tree, Random rand) {
        // find the points for crossover
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();
        // get the connection points
        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        
        if(left) {
            thisTrunk.swapLeft(treeTrunk);
            
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
        
    }

    GPTree() { 
        root = null; 
    }    
    
    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }
    
    public String toString() { 
        return root.toString(); 
    }
    
    public double eval(double[] data) { 
        return root.eval(data); 
    }
    
}
