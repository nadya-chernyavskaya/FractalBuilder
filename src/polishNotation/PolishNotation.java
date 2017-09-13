package polishNotation;
import java.util.LinkedList;
public class PolishNotation{
	
  public LinkedList<OperObject> outline; //fields
  public LinkedList<OperObject> stack;
  public String s;
  
  public PolishNotation(){   //constructor
	  s = "";
	  outline = new LinkedList<OperObject>() ;
	  stack = new LinkedList<OperObject>() ;
  }
  public PolishNotation(String arg){   //constructor 2
	  s = arg;
	  outline = new LinkedList<OperObject>() ;
	  stack = new LinkedList<OperObject>() ;
  }
  
  static boolean isDelim(char c) { 
    return c == ' ';
  }
  static boolean isZ(char c) { 
	    return (c == 'z')||(c == 'Z');
	  }
  static boolean isC(char c) { 
	    return (c == 'c')||(c == 'C');
	  }
  static boolean isBiOperator(char c) { 
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
  }
  static boolean isUniOperator(char c) { 
	    return c == 's' || c == 'o' || c == 't' || c == 'g' || c == 'e';
	  }
  static int priority(char op) {
    switch (op) {
    case '(':
      return 1;
    case '+':
    case '-':
      return 2;
    case '*':
    case '/':
      return 3;
    case '^':
      return 4;
    case 's':
    case 'o':
    case 'g':
    case 't':
    case 'e':
      return 5;
    default:
      return -1;
    }
  }
  
  public int analyse(){
	  
	  int count = 0;
	  int i = 0;
	  boolean biOperatorExpected = false;
	  boolean operandExpected = true;
	  boolean thereIsZ = false;
	  boolean thereIsC = false;
	  s = s.replace(" ", "");
	  s = s.replace("(-", "(0-");
	  s = s.replace("sin", "s");
	  s = s.replace("cos", "o");
	  s = s.replace("ctg", "g");
	  s = s.replace("tg", "t");
	  s = s.replace("exp", "e");  
	  while(i < s.length()) { 
	      char b = s.charAt(i);
	      if (! Character.isDigit(b) && !(b == '.')&& !(b == 'i') &&!isBiOperator(b) && !isUniOperator(b) && !(b == '(')  && !( b== ')') && !(isZ(b)) && !(isC(b))){
	    	  return -1;
	      }
	      ++i;
	  }
	  i = 0;
	  while(i < s.length()) { 
	      char b = s.charAt(i);
	      if (Character.isDigit(b)){
	    	  if(operandExpected == false){
	    		  return -1;
	    	  }
	    	  while(i < s.length()-1 && (Character.isDigit(s.charAt(i+1)) || (s.charAt(i+1) == '.') )){
	    		  ++i;
	    		  b = s.charAt(i);
	    	  }
	    	  biOperatorExpected = true;
	    	  operandExpected = false;
	      } 
	      if (b == 'i'){
	    	  if(i<=1 ||  ! Character.isDigit(s.charAt(i-1))){
	    		  return -1;
	    	  }
	      } 
	      if (isBiOperator(b)){
	      	  if(biOperatorExpected == false){
	    		  return -1;
	    	  }
	    	  biOperatorExpected = false;
	    	  operandExpected = true;
	    	  if (b == '^'){
	    		  int j = i+1;
	    		  char h = s.charAt(j);
	    		  if(h == '('){
	    			  int hCount = 1;
	    			  while(  hCount >0 && !isZ(h) && !isC(h) &&!(h == '/')&& !(h == '.') && j < s.length() ) {
	    				  j++;
	    				  h = s.charAt(j);
	    				  if(h == '(') hCount++;
	    				  if(h == ')') hCount--;
	    				  System.out.println(h);
	    				  
	    			  }
	    			  if (h == '.' || h == '/' || isZ(h) || isC(h)){
	    				  return -1;
	    			  }
	    		  } else {
	    			  while(  !isZ(h) && !isC(h) && !(h == '.')&& !(h == '/') && !isBiOperator(h) && !isUniOperator(h) && j < s.length() ) {
	    				  j++;
	    				  h = s.charAt(j);
	    			  }
	    			  if (h == '.' ||h == '/' || isZ(h) || isC(h)){
	    				  return -1;
	    			  }
	    		  }
	    	  }
	      }
	      if (isUniOperator(b)){
	    	  if(operandExpected == false){
	    		  return -1;
	    	  }
	      }
	      if(b == '('){ 
	    	  ++count; 
	    	  if(biOperatorExpected == true){
	    		  return -1;
	    	  }
	      } 
	      if( b== ')'){
	    	  --count;
	    	  if(operandExpected == true){
	    		  return -1;
	    	  }
	      } 
	      if (isZ(b)) {
	    	  if(operandExpected == false){
	    		  return -1;
	    	  }
	    	  biOperatorExpected = true;
	    	  operandExpected = false;
	    	  thereIsZ = true;
		  }
	      if (isC(b)) {
	    	  if(biOperatorExpected == true){
	    		  return -1;
	    	  }
	    	  biOperatorExpected = true;
	    	  operandExpected = false;
	    	  thereIsC = true;
		  } 
	      if(count<0){
	    	  return -1;
		  }
	      ++i;
	  }
	  if (!thereIsZ || !thereIsC){
	  	  return -1;
      }	  
	  if(count != 0){
		  return -1;
	  } else {
		  return 1;
	  }	  
  }
	  
  
  public int polish() {
	  for (int i = 0; i < s.length(); i++) { 
	      char b = s.charAt(i);
	      if(b== '('){
	    	  stack.add(new OperObject(b));
	      } else if( b== ')'){
	    	  while(!stack.isEmpty() && !(stack.getLast().getOperator() == '(')){
	  	    		  OperObject op = stack.removeLast();
	  	    		  if(!(op.getOperator() == '(')){
	  	    			  outline.add(op);
	  	    		  }
	    	  }
	    	  stack.removeLast();
	      } else if (isBiOperator(b)){
	    	  while(!stack.isEmpty() && (priority(stack.getLast().getOperator()) >= priority(b))){
	    		  OperObject op = stack.removeLast();
	    		  outline.add(op);
	    	  }
	    	  stack.add(new OperObject(b));
	      }  else if (isUniOperator(b)){
	    	  while(!stack.isEmpty() && (priority(stack.getLast().getOperator()) >= priority(b))){
	    		  OperObject op = stack.removeLast();
	    		  outline.add(op);
	    	  }
	    	  stack.add(new OperObject(b));
	      } else if (isZ(b)) {
		    	outline.add(new OperObject(new Operand('z')));
		  } else if (isC(b)) {
			    outline.add(new OperObject(new Operand('c')));
		  } else if(Character.isDigit(s.charAt(i))){
		        String number = "";
		        while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i)== '.') || (s.charAt(i)== 'i')))
		        	number += s.charAt(i++);
		        --i;
		        Complex a;
		        if(s.charAt(i)== 'i'){
		        	number = number.replace("i", "");
		        	a = new Complex(0,Double.parseDouble(number));
		        }else { 
		            a = new Complex(Double.parseDouble(number));
		        }
		        Operand oper = new Operand(a);
		        OperObject toadd = new OperObject(oper);
		        outline.add(toadd);
		  }
	  }
	  while(!stack.isEmpty()){
		  OperObject sign = stack.removeLast();
		  if(! (sign.getOperator() == '(') && !(sign.getOperator() == ')')){
			  outline.add(sign);		  
		  }
	  }
	  return 1;
  }  
}
