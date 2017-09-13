package polishNotation;

public class OperObject extends Operand{
    private boolean isOperand;
    private boolean isOperator;
	private Operand operand;
	private char operator;
	
	OperObject(boolean nd, boolean tor, Operand o, char c){ //constructors 
		this.isOperand = nd;
		this.isOperator = tor;
		this.operand = new Operand(o);
		this.operator = c;
	}
	OperObject(Operand o){ //constructors 
		this.isOperand = true;
		this.isOperator = false;
		this.operand = new Operand(o);
	}
	OperObject(char c){ //constructors 
		this.isOperand = false;
		this.isOperator = true;
		this.operator = c;
	}
	
	public boolean isOperand(){
		return isOperand;
	}
	public boolean isOperator(){
		return isOperator;
	}

	public void setOperand(Operand o){
		operand.setOperand(o);
	}
	public void setOperator(char c){
		this.operator = c;
	}
	public Operand getOperand(){
		return operand;
	}
	public char getOperator(){
		return operator;
	}
	public String toString(){
		if(isOperand)
	      return operand.toString();
		else if(isOperator)
		  return "operator " + operator;
		else return "0";
	}
	
}