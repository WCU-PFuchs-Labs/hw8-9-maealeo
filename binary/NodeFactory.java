package binary;
import java.util.Random; 

/** 
 * creates random nodes for expression trees with either terminal or 
 * binary operation nodes
 */
public class NodeFactory {
    private int numIndepVars; 
    private Binop[] currentOps; 

    /** 
     * constructs a node factory 
     * @param b array of available binary operations
     * @param numVars number of independent variables
     */
    public NodeFactory(Binop[] b, int numVars){
        this.currentOps = b; 
        this.numIndepVars = numVars;
    }

    /** 
     * returns a node that holds a random binop operator 
     * @param rand - random number generator
     * @return a node that holds a random binop operator
     */
    public Node getOperator(Random rand){
        int index = rand.nextInt(currentOps.length); //choose random index
        Binop opClone = (Binop) currentOps[index].clone(); //clone the operator
        return new Node(opClone);
    }

    /** 
     * returns the number of available operators 
     * @return number of operators 
     */
    public int getNumOps(){
        return currentOps.length;
    }

    /** 
     * returns a terminal node which is either a Const or a variable 
     * @param rand - random number generator
     * @return a new node that holds either a Const or variable
     */
    public Node getTerminal(Random rand){
        int choice = rand.nextInt(numIndepVars + 1);
        if(choice < numIndepVars){
            return new Node(new Variable(choice));
        } else {
            double constValue = rand.nextDouble(); //random constant between 0 and and 1
            return new Node(new Const(constValue));
        }
    }

    /** 
     * returns the number of independent variables 
     * @return number of independent variables
     */
    public int getNumIndepVars(){
        return numIndepVars;
    }


    
}
