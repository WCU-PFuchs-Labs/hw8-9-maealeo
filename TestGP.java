import binary.*; 
import tabular.*; 
import java.util.*; 

public class TestGP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //grab input
        System.out.print("Enter data file path: "); //ask file
        String fileName = sc.nextLine(); //store file

        int generationSize = 500; //500 trees
        int maxDepth = 5; //tree depth
        int numGenerations = 50; //how many gens to run

        Generation gen = new Generation(generationSize, maxDepth, fileName); //make first gen

        for (int genNum = 1; genNum <= numGenerations; genNum++) { //loop gens
            gen.evalAll(); //eval all trees fitness

            System.out.println("Generation " + genNum + ":"); //show gen num
            System.out.print("Best fitness: "); 
            gen.printBestFitness(); //print best fitness
            System.out.println("Best tree:");
            gen.printBestTree(); //print best tree

            gen.evolve(); //make next gen
        }
    }
}