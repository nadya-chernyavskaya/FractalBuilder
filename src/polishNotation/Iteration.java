package polishNotation;

import java.util.LinkedList;

//import java.util.LinkedList;

public class Iteration{
	public LinkedList<OperObject> outline;
	public Complex z;
	public Complex c;
	
	public Iteration(){
		z = new Complex();
		c = new Complex();
		this.outline = new LinkedList<OperObject>();
	}
	public Iteration(Complex z, Complex c){
		this.z = new Complex(z);
		this.c = new Complex(c);
		this.outline = new LinkedList<OperObject>();
	}
	public Iteration(LinkedList<OperObject> iter, Complex z, Complex c){
		this.z = new Complex(z);
		this.c = new Complex(c);
		this.outline = new LinkedList<OperObject>(iter);
	}
	public Iteration(Iteration a){
		this.z = new Complex(a.z);
		this.c = new Complex(a.c);
		this.outline = new LinkedList<OperObject>(a.outline);
	}
	
	public Iteration(LinkedList<OperObject> iter){
		this.z = new Complex();
		this.c = new Complex();
		this.outline = new LinkedList<OperObject>(iter);
	}
	
	public void setZ(Complex z){
		this.z = new Complex(z);
	}
	public void setC(Complex c){
		this.c = new Complex(c);
	}
	
	
	public Complex iterate(){
		  LinkedList<OperObject> line = new LinkedList<OperObject>(outline);
		  LinkedList<Operand> st = new LinkedList<Operand>();
		  while(!line.isEmpty()){
			  OperObject op = line.removeFirst();
			  if (op.isOperand()){
				  if (op.getOperand().isZ()){
					  op.getOperand().setOperand(false, true, false, z);
					  st.add(new Operand(op.getOperand().getValue()));
				  } else if (op.getOperand().isC()){
					  op.getOperand().setOperand(false, false, true, c);
					  st.add(new Operand(op.getOperand().getValue()));
				  } else st.add(op.getOperand());
			  }
			  if (op.isOperator()){
				  if (PolishNotation.isBiOperator(op.getOperator())){
					  Operand r = st.removeLast(); 
					  Operand l = st.removeLast();  
					  switch (op.getOperator()) { 
					  case '+':
						  st.add(l.plus(r));
						  break;
					  case '-':
						  st.add(l.minus(r));
						  break;
					  case '*':
						  st.add(l.asterisk(r));
						  break;
					  case '/':
						  st.add(l.slash(r));
						  break;
					  case '^':
						  st.add(l.pow(r));
						  break;
					  } 
				  }
				  if (PolishNotation.isUniOperator(op.getOperator())){
					  Operand r = st.removeLast(); 
					  switch (op.getOperator()) { 
					  case 's':
						  st.add(r.sin());
						  break;
					  case 'o':
						  st.add(r.cos());
						  break;
					  case 't':
						  st.add(r.tan());
						  break;
					  case 'g':
						  st.add(r.cotan());
						  break;
					  case 'e':
						  st.add(r.exp());
						  break;
					  } 
				  }
			  }
		  }
		  Complex result = new Complex(st.removeLast().getValue());
		  return result;
	  }
}