package fracPackage;
import polishNotation.Complex;
import polishNotation.Iteration;

public class pObject 
{
	Iteration iter;
	private Complex z;
	private Complex c;
	private double ceiling;
	
	public pObject(double x, double y, Iteration Iter, double Ceiling) // MANDELBROT
	{
		z = new Complex(0,0); c = new Complex(x,y);
		iter = Iter;
		ceiling = Ceiling;
	}

	public pObject(double x, double y, double a, double b, Iteration Iter, double Ceiling)
	{
		z = new Complex(x,y); c = new Complex(a,b);    // JULIA
		iter = Iter;
		ceiling = Ceiling;
	}
	
	public void iterate ()
	{
		iter.setZ(z);
		iter.setC(c);
		z = new Complex(iter.iterate());
	}
	public boolean isOut   (double x, double y)
	{
		return z.mod() > ceiling;
	}
}