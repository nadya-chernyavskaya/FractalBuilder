package fracPackage;

import java.awt.*;
import polishNotation.Iteration;
import polishNotation.PolishNotation;

import java.awt.image.*;
//---
public class Fractal implements Runnable
{
	Thread computation;
	
	static double xLeftDefault  = -2;
	static double xRightDefault =  2;
	static double yLowDefault   = -2;
	static double yHighDefault  =  2;
	
	static double aDefault = 0.35;
	static double bDefault = 0.1;
	
	static double ceilingDefault = 2;
	
	static int nXDefault     = 600;
	static int nYDefault     = 600;
	static int nIterDefault  = 100;
	static String iterDefault = "z^2 + c";
	
	private SynchrMenu synchr;
	
	private double xLeft;
	private double xRight;
 	private double yLow;
	private double yHigh;
	private int nX;
	private int nY;
	private int nIter;
	private ColorScheme cSchm;
	private Iteration iter;
	
	protected double ceiling;
	protected double a;
	protected double b;
	
	private FractalPoint[][] field;
	private int nIterCurrent;
	
	private BufferedImage img;
	private Component comp;
	private AbleToDraw mainWindow;
/*---------------------------------------------------------------------------------------------------------------------------------------*/
	
	public Fractal(SynchrMenu Synchr, Component Graph, AbleToDraw mWindow)
	{
		computation = new Thread(this, "computation");
		
		xLeft  = xLeftDefault;		xRight = xRightDefault;
		yLow   = yLowDefault;		yHigh  = yHighDefault;
		nX     = nXDefault;			nY     = nYDefault;
		
		nIterCurrent  = 0; cSchm = new ColorScheme();
		
		PolishNotation pnot = new PolishNotation(iterDefault);
		pnot.polish();
	    iter = new Iteration(pnot.outline);
		
		nIter   = nIterDefault;
		ceiling = ceilingDefault;
		
		synchr = Synchr;
		mainWindow = mWindow; 
		comp  = Graph;
		
		newFracField();
		img = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
		
		computation.start();
	}
/*--------------------------------------------------------------------------------------------------------------*/

	public void newFracField()
	{
		field = new FractalPoint[nX][nY];
		for(int i = 0; i < nX; i++)
		{
			double xi = ItoX(i, nX, xLeft, xRight);
		
			for(int j = 0; j < nY; j++)
			{
				double yj = JtoY(j, nY, yLow, yHigh);				
				field[i][j] = makeField(xi, yj, iter);
			}
		}		
	}

	public FractalPoint makeField(double xi, double yj, Iteration iter)
	{
	 	return new FractalPoint(xi, yj, iter, ceiling);
	}
	
	public void makeNewFractal()
	{
		newFracField();		
		iterateAndDraw();
	}
	
	public void changeScale (double chSizeParam )
	{
		double  xl = xLeft; double xr = xRight;
		double yl = yLow;  double yh = yHigh;
		
		xLeft   = (xl + xr)/2 - (xr - xl)/2 * chSizeParam;
		xRight  = (xl + xr)/2 + (xr - xl)/2 * chSizeParam;
	
		
		yLow   = (yl + yh)/2 - (yh - yl)/2 * chSizeParam;
		yHigh  = (yl + yh)/2 + (yh - yl)/2 * chSizeParam;

		makeNewFractal();
	}

	
/*--------------------------------------------------------------------------------------------------------------*/
	public void iterate(int n)
	{
		for(int i = 0; i < nX; i++)
			for(int j = 0; j < nY; j++)
				field[i][j].iterate(n);
		nIterCurrent += n;
	}
	
	public void iterateAndDraw()
	{		
		for(int j = 0; j < nY; j++)
		{
			if(synchr.watchMessage()) return;
			
			for(int i = 0; i < nX; i++)
			{
				field[i][j].iterate(nIter);		
			}
			if(j % (nY/6 + 1) == 0 || j == nY - 1) draw(j + 1);			
		}
		
		nIterCurrent = nIter;
	}
/*--------------------------------------------------------------------------------------------------------------*/
	public FractalPoint pointXY(double x, double y)
	{
		int i, j; 
		i = XtoI(x, nX, xLeft, xRight);
		j = YtoJ(y, nY, yLow , yHigh );
		return field[i][j];
	}
	
/*--------------------------------------------------------------------------------------------------------------*/
	public void draw()
	{
		draw(nY);
	}
	
	public void draw(int height)
	{
		img = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = img.createGraphics();
		
		for(int i = 0; i < nX; i ++)
			for(int j = 0; j < height; j++)
			{
				graph.setColor(field[i][j].color(cSchm));
				graph.drawLine(i,j,i,j);
			}
		
		graph.setColor(Color.LIGHT_GRAY);
		graph.fillRect(0, height, nX, nY - height);
				
		comp.repaint();
	}
	
	public void moveX(int dnX)
	{
		int i, j;
		
		if(dnX == 0) return;
		else if(dnX < 0)
		{
			for(i = nX - 1; i >= -dnX; i--)
				for(j = 0; j < nY; j++) 
					field[i][j] = field[i + dnX][j];
		
			for(i = 0; i < -dnX; i++)
				for(j = 0; j < nY; j++)
				{
					double x,y;
					x = ItoX(i + dnX, nX, xLeft, xRight);
					y = JtoY(j,       nY, yLow,  yHigh);
					field[i][j] = makeField(x,y, iter);
					field[i][j].iterate(nIter);
				}			
		}
		else 
		{
			for(i = 0; i < nX - dnX; i++)
				for(j = 0; j < nY; j++) 
					field[i][j] = field[i + dnX][j];
		
			for(i = nX - dnX; i < nX; i++)
				for(j = 0; j < nY; j++)
				{
					double x,y;
					x = ItoX(i + dnX, nX, xLeft, xRight);
					y = JtoY(j,       nY, yLow,  yHigh);
					field[i][j] = makeField(x,y, iter);
					field[i][j].iterate(nIter);
				}			
		}
		double xLeftOld = xLeft;
		xLeft   = ItoX(0  + dnX, nX, xLeftOld, xRight);
		xRight  = ItoX(nX + dnX, nX, xLeftOld, xRight);
		
		draw(nY);		
	}

	public void moveY(int dnY)
	{
		int i, j;
		
		if(dnY == 0) return;
		else if(dnY > 0)
		{
			for(i = 0; i < nX; i++)
				for(j = nY - 1; j >= dnY; j--) 
					field[i][j] = field[i][j - dnY];
		
			for(i = 0; i < nX; i++)
				for(j = 0; j < dnY; j++)
				{
					double x,y;
					x = ItoX(i      , nX, xLeft, xRight);
					y = JtoY(j - dnY, nY, yLow,  yHigh);
					field[i][j] = makeField(x,y, iter);
					field[i][j].iterate(nIter);
				}			
		}
		else 
		{
			for(i = 0; i < nX; i++)
				for(j = 0; j < nY + dnY; j++) 
					field[i][j] = field[i][j - dnY];
		
			for(i = 0; i < nX; i++)
				for(j = nY + dnY; j < nY; j++)
				{
					double x,y;
					x = ItoX(i      , nX, xLeft, xRight);
					y = JtoY(j - dnY, nY, yLow,  yHigh);
					field[i][j] = makeField(x,y, iter);
					field[i][j].iterate(nIter);
				}			
		}
		double yLowOld = yLow;
		yLow   = JtoY(nY  - dnY, nY, yLowOld, yHigh);
		yHigh  = JtoY(0   - dnY, nY, yLowOld, yHigh);
		
		draw(nY);		
	}
	
	
	public BufferedImage getImage()
	{
		return img;
	}
	
/*----------------------------------------------------------------------------------------------------------------------------------*/	
	public double getxLeft()
	{
		return xLeft;
	}
	public double getxRight()
	{
		return xRight;
	}
	public double getyLow()
	{
		return yLow;
	}
	public double getyHigh()
	{
		return yHigh;
	}
/*-------------------------------------------------------------------------------------------------------------------------
 *-------------------------------------------------------------------------------------------------------------------------
 */	
	public static double ItoX(int i, int NX, double xleft, double xright)
	{
		return (xleft*(NX - i) + xright*i)/NX;
	}
	
	public static double JtoY(int j, int NY, double ylow, double yhigh)
	{
		return (yhigh*(NY - j) + ylow*j)/NY;
	}
	
	public static int XtoI(double x, int NX, double xleft, double xright)
	{
		return (int)Math.round( (x - xleft)/(xright - xleft)*NX );
	}
	
	public static int YtoJ(double y, int NY, double ylow, double yhigh)
	{
		return (int)Math.round( (yhigh - y)/(yhigh  - ylow  )*NY );
	}
/*--------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------------*/
	
	public void run()
	{
		Message message;
		while( true )
		{
			 message = synchr.getMessage();

			 switch(message.type)
			 {
			 	case SynchrMenu.MAKENEWFRACTAL:
			 		xLeft  = message.xLeft;
			 		xRight = message.xRight;
			 		yLow   = message.yLow;
			 		yHigh  = message.yHigh;
			 		nX     = message.nX;
			 		nY     = message.nY;
			 		cSchm  = message.cschm;
			 		iter   = message.iter; 
			 		nIter  = message.nIter;
			 		ceiling= message.ceiling;
			 		a      = message.a;
			 		b      = message.b;
			 		makeNewFractal();
			 		break;
			 					 		
			 	case SynchrMenu.MOVEX:
			 		moveX(message.dx);
			 		break;
			 		
			 	case SynchrMenu.MOVEY:
			 		moveY(message.dy);
			 		break;
			 		
			 	case SynchrMenu.CHANGESCALE:
			 		changeScale(message.chSize);
			 		break;
			 		
			 	case SynchrMenu.CHANGECOLOR:
				    cSchm = message.cschm;		
				    draw();
				    break;
				    
			 	case SynchrMenu.DRAW:			 					 					 		
			 		draw();
			 		break;
			 		
			 	case SynchrMenu.GETPOINT:
			 		FractalPoint fp = new FractalPoint( field[message.i][message.j] );
			 		mainWindow.putIterXYdata(fp.x(), fp.y(), fp.nIter());
			 		break;
			 }
		}
	}	
}