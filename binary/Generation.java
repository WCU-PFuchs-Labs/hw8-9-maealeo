package binary;

import java.util.*; 
import tabular.DataSet; 

public class Generation {
    private GPTree[] trees; 
    private DataSet data; 
    private int size; 

    public Generation(int size, int maxDepth, String fileName){
        this.size = size; 
        this.data = new DataSet(fileName);

        Binop[] ops = {new Plus(), new Minus(), new Mult(), new Divide()};
        NodeFactory nf = new NodeFactory(ops, data.getNumIndep());
        Random rand = new Random();
        trees = new GPTree[size]; 
        for(int i = 0; i < size; i++){
            trees[i] = new GPTree(nf, maxDepth, rand);
        }
    }

        public void evalAll() {
        for (GPTree t : trees) {
            t.evalFitness(data);
        }
        Arrays.sort(trees); // uses GPTree.compareTo
    }

    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> topTen = new ArrayList<>();
        for (int i = 0; i < Math.min(10, trees.length); i++) {
            topTen.add(trees[i]);
        }
        return topTen;
    }

    public void printBestFitness() {
        System.out.printf("%.2f\n", trees[0].getFitness());
    }

    public void printBestTree() {
        System.out.println(trees[0]);
    }

    public void evolve() {
        Random rand = new Random();
        GPTree[] nextGen = new GPTree[size];
        for (int i = 0; i < size; i += 2) {
            GPTree parent1 = (GPTree) trees[rand.nextInt(size/2)].clone();
            GPTree parent2 = (GPTree) trees[rand.nextInt(size/2)].clone();
            parent1.crossover(parent2, rand);
            nextGen[i] = parent1;
            if(i + 1 < size){
                nextGen[i + 1] = parent2; 
            }
        }
        trees = nextGen;
    }

    @Override 
    public GPTree clone() {
        GPTree copy = (GPTree)super.clone(); 
        copy.root = root.clone(); 
        return copy; 
    }
}
