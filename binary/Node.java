package binary;

import java.util.Random;

/**
 * Code Template Author: David G. Cooper
 * Purpose: A Binary Tree class for Arithmetic evaluation
 */
public class Node implements Cloneable{
    private Node left;
    private Node right;
    private Op operation;
    protected int depth; 

    public Node(Unop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public Node(Binop operation) {
        this.operation = operation;
        this.depth = 0;
        this.left = null;
        this.right = null;
    }

    public Node(Binop operation,Node left, Node right) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
    public double eval(double[] values) {
        if (operation instanceof Unop) {
              return ((Unop)operation).eval(values);
        } else if (operation instanceof Binop) {
            //evaluate left and right subtrees recursively
            double leftValue = left.eval(values);
            double rightValue = right.eval(values);
              return ((Binop)operation).eval(leftValue, rightValue);
        } else {
              System.err.println("Error operation is not a Unop or a Binop!");
              return 0.0;
        }
    }

        //set left child of node
        public void setLeft(Node left) {
            this.left = left;
        }

        //set right child of node
        public void setRight(Node right) {
            this.right = right;
        }


        public double eval() {
            return eval(null);
        }

        public void addRandomKids(NodeFactory nf, int maxDepth, Random rand){
            //if its a terminal node, do nothing 
            if(operation instanceof Unop){
                return; 
            } 

            //if we are at max depth, add terminal nodes
            if(depth >= maxDepth){
                left = nf.getTerminal(rand);
                left.depth = this.depth + 1; 
                right = nf.getTerminal(rand);
                right.depth = this.depth + 1;
                return; 
            }

            //left child 
            int choiceLeft = rand.nextInt(nf.getNumOps() + nf.getNumIndepVars());
            if (choiceLeft < nf.getNumOps()) {
                //create a new operator Node
                left = nf.getOperator(rand);
                left.depth = this.depth + 1;
                left.addRandomKids(nf, maxDepth, rand);  // recursive expansion
            } else {
                //create a terminal Node
                left = nf.getTerminal(rand);
                left.depth = this.depth + 1;
            }

            //right child
            int choiceRight = rand.nextInt(nf.getNumOps() + nf.getNumIndepVars());
            if (choiceRight < nf.getNumOps()) {
                right = nf.getOperator(rand);
                right.depth = this.depth + 1;
                right.addRandomKids(nf, maxDepth, rand); //recursive expansion
            } else {
                right = nf.getTerminal(rand);
                right.depth = this.depth + 1;
            }
        }
    /** 
     * Visit this node, then left, then right 
     * calls c.collect(this) for every node visited
     */
    public void traverse(Collector c){
        c.collect(this);

        if(left != null){
            left.traverse(c);
        }

        if(right != null){
            right.traverse(c);
        }
    }

    /** 
     * returns true if the node is a terminal node 
     */
    public boolean isLeaf(){
        return (left == null && right == null);
    }

    /** 
     * swap this node's left child with the left child of trunk 
     */
    public void swapLeft(Node trunk){
        //temporarily store the node's left child 
        Node temp = this.left; 

        //swap 
        this.left = trunk.left; 
        trunk.left = temp; 
    }

    /** 
     * swap this node's right child with the right child of trunk 
     */
    public void swapRight(Node trunk){
        Node temp = this.right; 

        this.right = trunk.right; 
        trunk.right = temp; 
    }

    @Override 
    public Object clone(){
        try{
            Node copy = (Node) super.clone(); 
            if(left != null) copy.left = (Node) left.clone(); 
            if(right != null) copy.right = (Node) right.clone(); 
            return copy; 
            
        }catch(CloneNotSupportedException e) {
        return null; 
    }

    @Override
    public String toString(){
        if(operation instanceof Unop){ 
            // leaf node: just return value or variable
            return operation.toString();
        } else if(operation instanceof Binop){ 
            // binary operator: wrap entire expression in one parentheses pair
            return "(" + left.toString() + " " + operation.toString().trim() + " " + right.toString() + ")";
        } else {
            return "Error";
        }
    }
}
