import binary.*; 
import tabular.*; 
import java.util.*; 

public class TestGeneration {
    public static void main(String[] args) {
        //get input from user
        Scanner sc = new Scanner(System.in); 
        System.out.println("Enter the data file path: "); //prompt for the file 
        String fileName = sc.nextLine(); 

        int generationSize = 500; //number of trees in gen 
        int maxDepth = 5; //max depth of the tree 

        Generation gen = new Generation(generationSize, maxDepth, fileName);

        //evaluate the fitness for all trees
        gen.evalAll(); 

        System.out.println("Best tree: ");
        gen.printBestTree(); 

        System.out.print("Best fitness: "); 
        gen.printBestFitness();

        System.out.println("Top Ten Fitness Values: "); 
        ArrayList<GPTree> topTen = gen.getTopTen(); 
        for(int i = 0; i < topTen.size(); i++){
            System.out.printf("%.2f", topTen.get(i).getFitness());
            if (i != topTen.size() - 1) {
                System.out.print(", "); 
            }
        }
        System.out.println(); 

    }
}
