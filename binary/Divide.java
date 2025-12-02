package binary;

public class Divide extends Binop{

    @Override
    public double eval(double left, double right){
        if(Math.abs(right) < 0.0001){
            return 1.0;
        }
        return left / right; 
    }

    @Override
    public String toString(){
        return " / ";
    }
}
