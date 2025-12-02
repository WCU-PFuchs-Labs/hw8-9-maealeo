package tabular;

import java.io.*; 
import java.util.*; 

public class DataSet {
    //list of each data row
    private ArrayList<DataRow> rows; 

    //constructor reads csv + builds dataset
    public DataSet(String fileName){
        rows = new ArrayList<>(); 
        try(Scanner sc = new Scanner(new File(fileName))){
            //skip header
            if(sc.hasNextLine()) {
                sc.nextLine(); 
            }
            //read each line
            while(sc.hasNextLine()){
                String line = sc.nextLine(); 
                //split by commas
                String[] tokens = line.split(",");
                //first val is y
                double y = Double.parseDouble(tokens[0].trim());
                //rest are x vals
                double[] xVals = new double[tokens.length - 1]; 
                for(int i = 1; i < tokens.length; i++){
                    xVals[i - 1] = Double.parseDouble(tokens[i].trim());
                }
                //add to list
                rows.add(new DataRow(y, xVals));
            }
        } catch(FileNotFoundException e){
            System.err.println("data file not found: " + fileName);
        }
    }

    //return all rows
    public ArrayList<DataRow> getRows(){
        return rows; 
    }

    //num indep vars
    public int getNumIndep(){
        if(rows.isEmpty()) {
            return 0; 
        }
        return rows.get(0).getXValues().length; 
    }
    
}
