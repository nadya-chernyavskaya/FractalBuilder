package fracPackage;
import polishNotation.Iteration;
import java.awt.*;

public class FractalPoint extends pObject 
{
	private double x, y;
	private int nIter;

	public FractalPoint(double x0, double y0, Iteration iter, double ceiling) 
	{
		super(x0, y0, iter, ceiling);
		x = x0;
		y = y0;
		nIter = 0;
	}
	
	public FractalPoint(double x0, double y0, double a, double b, Iteration iter, double ceiling)
	{
		super(x0, y0, a, b, iter, ceiling);
		x = x0;
		y = y0;
		nIter = 0;		
	}
	
	public FractalPoint(FractalPoint fp)
	{
		super(0, 0, null, 5);
		if(fp!= null)
		{
			x     = fp.x;
			y     = fp.y;
			nIter = fp.nIter;
		}
	}
	
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}
	
	public int nIter() 
	{
		return nIter;
	}

	public boolean isOut() 
	{
		return super.isOut(x, y);
	}

	public void iterate(int n) 
	{
		int i = 0;
		while (i < n && !isOut())
		{
			i++;
			super.iterate();
		}
		nIter += i;
	}
	public Color color(ColorScheme cSchm) 
	{
		if (isOut()) return cSchm.color(nIter);
		return Color.black;
	}
	
	public String toString()
	{
		return "x: " + x + " y: " + y + " nIter: " + nIter; 
	}
}