package polishNotation;

public class Operand extends Complex{
    private boolean isNumber;
    private boolean isZ;
    private boolean isC;
	private Complex value;
	
	Operand(){ //constructors 
		this.isNumber = true;
		this.isZ = false;
		this.isC = false;
		this.value = new Complex();
	}
	public Operand(boolean n, boolean z, boolean c, Complex val){ //constructors 
		this.isNumber = n;
		this.isZ = z;
		this.isC = c;
		this.value = new Complex(val);
	}
	public Operand(Operand op){ //constructors 
		this.isNumber = op.isNumber;
		this.isZ = op.isZ;
		this.isC = op.isC;
		this.value = new Complex(op.value);
	}
	public Operand(double re, double im){ //constructors 
		this.isNumber = true;
		this.isZ = false;
		this.isC = false;
		this.value.setRe(re);
		this.value.setIm(im);
	}
	public Operand(Complex z){ //constructors 
		this.isNumber = true;
		this.isZ = false;
		this.isC = false;
		this.value = new Complex(z);
	}
	public Operand(Complex z, Character c){
		isNumber = false; 
		if(c == 'c' || c == 'C') {
			isC = true;
			isZ = false;
		}
		if(c == 'z' || c == 'Z') {
			isZ = true;
			isC = false;
		}
		value = new Complex(z);
	}
	public Operand(Character c){
		this(new Complex(),c);
	}
	public boolean isZ(){
		return isZ;
	}
	public boolean isC(){
		return isC;
	}
	public boolean isNumber(){
		return isNumber;
	}
	public void setValue(Complex a){
		value.setZ(a);
	}
	public Complex getValue(){
		return value;
	}
	
	
	public void setOperand(boolean n, boolean z, boolean c, Complex val){ //constructors 
		this.isNumber = n;
		this.isZ = z;
		this.isC = c;
		this.value = new Complex(val);
	}
	
	public void setOperand(Operand o){ //constructors 
		this.setOperand(o.isNumber, o.isZ, o.isC, o.value);
	}
	
	
	
	// +, -, *, /, ^
	public Operand plus(Operand z){ // +
		  return new Operand(this.value.plus(z.value));
	   } 

	public Operand minus(Operand z){ //-
	      return new Operand(this.value.minus(z.value)); 
	   }
	
	public Operand asterisk(Operand z){ //*
	      return new Operand(this.value.asterisk(z.value)); 
	   }

	public Operand slash(Operand z){ // *
	      return new Operand(this.value.slash(z.value)); 
	   }
	public Operand pow(Operand z){ //-
		return new Operand(this.value.pow(z.value.getRe())); 
	    }
	public Operand exp(){ // exponenta
		return new Operand(this.value.exp());
	}
	   
	public Operand cos(){ // cos
		return new Operand(this.value.cos());
		   
	}
	public Operand sin(){ // sin
		return new Operand(this.value.sin());
	}
	public Operand tan(){ // tan
		return new Operand(this.value.tan());
	}
	public Operand cotan(){ // cotan
		return new Operand(this.value.cotan());
	}
	
	public String toString(){
		if(isNumber) return value.toString();
		else if(isZ) return "z";
		else if(isC) return "c";
		else return "" + isNumber + isZ + isC + value;		
	   }
}