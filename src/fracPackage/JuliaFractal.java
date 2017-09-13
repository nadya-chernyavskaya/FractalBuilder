package fracPackage;

import java.awt.Component;

import polishNotation.Iteration;

public class JuliaFractal extends Fractal 
{
	public JuliaFractal(SynchrMenu synchr, Component Graph, AbleToDraw Window)
	{
		super(synchr, Graph, Window);
		a = synchr.a();
		b = synchr.b();
	}
	
	public FractalPoint makeField(double xi, double yj, Iteration iter)
	{
	 	return new FractalPoint(xi, yj, a, b, iter, ceiling);
	}
}