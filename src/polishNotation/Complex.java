package polishNotation;

public class Complex {

   private static final double EPS = 1e-12; // accuracy 

   private double re, im;                   

   public Complex(double re, double im) { // constructors
      this.re = re; this.im = im;
   }

   public Complex(double re){
	   this(re, 0.0); 
   }

   public Complex(){
	   this(0.0, 0.0); 
   }

   public Complex(Complex z){
	   this(z.getRe(), z.getIm()) ; 
   }

      // access 

   public double getRe(){
	   return re;
   } 

   public double getIm(){
	   return im;
   } 

   public Complex getZ(){
	   return this;
   } 

   public void setRe(double re){this.re = re;} 

   public void setIm(double im){this.im = im;} 

   public void setZ(Complex z){re = z.getRe(); im = z.getIm();}

      // mod and arg

   public double mod(){
	   return Math.sqrt(re * re + im * im);
   } 

   public double arg(){
	   return Math.atan2(im, re);
   }

   public boolean isReal(){
	   return Math.abs(im) < EPS;
   }

      // methods of class Object

   public boolean equals(Complex z){ 
      return Math.abs(re -z.re) < EPS && 
             Math.abs(im - z.im) < EPS;
   }

   public String toString(){
      return "Complex: " + re + " " + im;
   }


   public void mul(Complex z){  //*=
      double t = re * z.re - im * z.im; 
            im = re * z.im + im * z.re; 
            re = t;
   }


   public Complex plus(Complex z){ // +
      return new Complex(re + z.re, im + z.im);
   } 

   public Complex minus(Complex z){ //-
      return new Complex(re - z.re, im - z.im); 
   }

   public Complex asterisk(Complex z){ // *
	   return new Complex(
       re * z.re - im * z.im, re * z.im + im * z.re);
   }

   public Complex slash(Complex z){ // /
	   double m = z.mod();
	   return new Complex( (re * z.re + im * z.im) / m /m, (im * z.re - re * z.im) / m /m);
   }
   public Complex pow(double n){ // ^ in real power
         if( ((int)n == n ) && n > 0){
        	Complex result = new Complex(this);
        	int i=1;
        	while(i < n){
        		result.mul(this);
        		++i;
        	}
        	return result;
        }else{ 
        	return new Complex();
       }
   }
   public Complex exp(){ // exponenta
	   return new Complex(Math.exp(re)* Math.cos(im) , Math.exp(re)* Math.sin(im));
   }
   
   public Complex cos(){ // cos
	   Complex a = new Complex(0.5);
	   Complex b = new Complex(-im, re);
	   Complex c = new Complex(im, -re);
	   if(((b.exp()).plus(c.exp())).asterisk(a).mod() <EPS ){
		   return new Complex();
	   } else  return new Complex( ((b.exp()).plus(c.exp())).asterisk(a));
   }
   public Complex sin(){ // sin
	   Complex a = new Complex(0, -0.5);
	   Complex b = new Complex(-im, re);
	   Complex c = new Complex(im, -re);
	   if(((b.exp()).minus(c.exp())).asterisk(a).mod() <EPS ){
		   return new Complex();
	   } else  return new Complex( ((b.exp()).minus(c.exp())).asterisk(a));
   }
   public Complex tan(){ // tan
	   return new Complex( this.sin().slash(this.cos()));
   }
   public Complex cotan(){ // cotan
	   return new Complex( this.cos().slash(this.sin()));
   }
}
