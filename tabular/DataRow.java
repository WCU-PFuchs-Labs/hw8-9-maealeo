package tabular;

public class DataRow {
    //store y val
    private double y; 
    //store x val array
    private double[] xVals; 

    public DataRow(double y, double[] xVals){
        this.y = y; 
        this.xVals = xVals; 
    }

    public double getY(){
        return y; 
    }

    public double[] getXValues(){
        return xVals; 
    }
    
}
