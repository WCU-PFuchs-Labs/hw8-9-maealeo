package binary;

public class Variable extends Unop{
    private int index; //0 for X0, 1 for X1 etc

    public Variable(int index){
        this.index = index;
    }

    //evaluate using the values array
    @Override
    public double eval(double[] values){
        return values[index];
    }

    //convert to string
    @Override
    public String toString(){
        return "X" + index;
    }
}
