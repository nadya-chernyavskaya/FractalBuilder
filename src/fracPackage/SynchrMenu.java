package fracPackage;

import polishNotation.Iteration;
import polishNotation.PolishNotation;
import java.util.*;


public class SynchrMenu 
{
	public static final int NOTHING        = -1;
	public static final int MAKENEWFRACTAL = 0;
	public static final int CHANGESCALE    = 1;
	public static final int MOVEX          = 2;
	public static final int MOVEY          = 3;
	public static final int CHANGEDATA     = 4;
	public static final int CHANGEITER     = 5;
	public static final int CHANGECOLOR    = 6;
	public static final int DRAW           = 7;
	public static final int GETPOINT       = 8;
	public static final int RENEWDATA      = 9;
	public static final int CHANGEAB       = 10;
	
	private LinkedList<Message> MessageQueue;
	
	private int dx;
	private int dy;
	private double chSize;
	
	private FractalPoint fPoint;
	private int i;
	private int j;
	
	private double xLeft;
	private double xRight;
 	private double yLow;
	private double yHigh;
	private int nX;
	private int nY;
	private int nIter;
	private Iteration iter;
	private ColorScheme cschm;
	
	private double a, b;
	private double ceiling;
	private int gorizontal;
	private int vertical;
	
    boolean isMessage;
    boolean isBusy;
	
	public SynchrMenu()
	{
		xLeft  = Fractal.xLeftDefault;		xRight = Fractal.xRightDefault;
		yLow   = Fractal.yLowDefault;		yHigh  = Fractal.yHighDefault;
		nX     = Fractal.nXDefault;			nY     = Fractal.nYDefault;

		gorizontal = 600;
		vertical   = 600;
		
		a = Fractal.aDefault;
		b = Fractal.bDefault;
		
		ceiling = Fractal.ceilingDefault;
		nIter  = Fractal.nIterDefault;
		
		PolishNotation pont = new PolishNotation(Fractal.iterDefault);
		pont.polish();
	    iter = new Iteration(pont.outline);
		
		cschm  = new ColorScheme(0);
		MessageQueue = new LinkedList<Message>();
		
		isMessage = false;
	}
	
	public void makeNewFractal()
	{
		putMessage(MAKENEWFRACTAL);
	}
	
	synchronized private void putMessage(int MessageType)
	{
		Message mess = new Message();
		
		mess.type = MessageType;
		
		mess.a = a;
		mess.b = b;
		mess.i = i;
		mess.j = j;
		mess.xLeft   = xLeft;
		mess.xRight  = xRight;
		mess.yLow    = yLow;
		mess.yHigh   = yHigh;
		mess.nX      = nX;
		mess.nY      = nY;
		mess.iter    = iter;
		mess.nIter   = nIter;
		mess.cschm   = cschm;
		mess.dx      = dx;
		mess.dy      = dy;
		mess.chSize  = chSize;
		mess.ceiling = ceiling;
		
		MessageQueue.offer(mess);
		notify();
	}
		
	synchronized public Message getMessage()
	{
		if(MessageQueue.isEmpty())
			try                           { wait(); }
			catch(InterruptedException e) {         }
			
		return MessageQueue.poll();
	}

	synchronized public boolean watchMessage()
	{		
		for( Message msg : MessageQueue )
		{
			if(msg.type == MAKENEWFRACTAL || msg.type == CHANGESCALE) return true;
		}
		
		return false;
	}
	
//===============================BEFORE, IT WAS USERMENU=================================================================
	
	public int setIter(String input)
	{
		PolishNotation pnot = new PolishNotation(input);
		pnot.polish();
	    iter = new Iteration(pnot.outline);
	    
	    return 0;
	}
	
	public void setColor(double param)
	{
		cschm = new ColorScheme(param); 
		putMessage(CHANGECOLOR);
	}
		
	public void setAB(double A, double B)
	{
		a = A;
		b = B;		
	}
	
	public void changeScale (double chSizeParam )
	{
		chSize = chSizeParam;
		
		double  xl = xLeft; double xr = xRight;
		double yl = yLow;  double yh = yHigh;
		
		xLeft   = (xl + xr)/2 - (xr - xl)/2 * chSizeParam;
		xRight  = (xl + xr)/2 + (xr - xl)/2 * chSizeParam;
	
		
		yLow   = (yl + yh)/2 - (yh - yl)/2 * chSizeParam;
		yHigh  = (yl + yh)/2 + (yh - yl)/2 * chSizeParam;
	
		putMessage(CHANGESCALE);
	}
	public void enlarge ()
	{
		changeScale(0.6);
	}
	public void ensmall ()
	{
		changeScale(1/0.6);
	}
	
	public void moveX(int dX)
	{
		dx = dX;
		
		double xLeftOld = xLeft;
		xLeft   = Fractal.ItoX(0    + dx, nX, xLeftOld, xRight);
		xRight  = Fractal.ItoX(nX + dx, nX, xLeftOld, xRight);
		
		putMessage(MOVEX);
	}
	
	public void moveY(int dY)
	{
		dy = dY;
		
		double yLowOld = yLow;
		yLow   = Fractal.JtoY(nY  - dy, nY, yLowOld, yHigh);
		yHigh  = Fractal.JtoY(0     - dy, nY, yLowOld, yHigh);
		
		putMessage(MOVEY);
	}

	public FractalPoint getFPoint(int p, int q)
	{
		if(p < 0 || p > gorizontal || q < 0 || q > gorizontal) return null;
		
		i = nX*p/gorizontal;
		j = nY*q/vertical;
		
		putMessage(GETPOINT);
		return fPoint;
	}
		
	public void setDimension(int Size)
	{
		gorizontal = vertical = Size;
	}
	
	public int setProperties(String X, String Y, String Size, String nXY, String NIter, String Ceiling)
	{
		 double x, y, size, ceil;
		 int nxy, niter;
		 
		 try { 
			 x    = Double .parseDouble(X); 
			 y    = Double .parseDouble(Y);
			 size = Double .parseDouble(Size);
			 nxy  = Integer.parseInt(nXY);
			 niter = Integer.parseInt(NIter);
			 ceil = Double .parseDouble(Ceiling);
		 } catch (NumberFormatException e) { return -1; }
		
   		 if(size <= 2.0E-14   || 
   		    nxy <= 0          || 
   		    ceil < 2          ||
   		    ceil > 1000) return -1;

		 xLeft  = x;
		 xRight = x + size;
		 yLow   = y - size;
		 yHigh  = y;
		 
		 nX = nxy;
		 nY = nxy;
		 
		 nIter   = niter;
		 ceiling = ceil;
		 
		 return 0;
	}
	
	public void setDefaultProperties()
	{	
		xLeft  = Fractal.xLeftDefault;		xRight = Fractal.xRightDefault;
		yLow   = Fractal.yLowDefault;		yHigh  = Fractal.yHighDefault;
		nX     = Fractal.nXDefault;			nY     = Fractal.nYDefault;

		ceiling = Fractal.ceilingDefault;		
		nIter  = Fractal.nIterDefault;
	}
	
	public void moveLeft()
	{		
		moveX(-nX/15 - 1);
	}
	public void moveRight()
	{
		moveX( nX/15 + 1);
	}

	public void moveUp()
	{
		moveY( nY/15 + 1);
	}
	public void moveDown()
	{
		moveY(-nY/15 - 1);
	}
	
	
	public double xLeft()
	{
		return xLeft;
	}
	public double xRight()
	{
		return xRight;
	}
 	public double yLow()
 	{
 		return yLow;
 	}
	public double yHigh()
	{
		return yHigh;
	}
	public int nX()
	{
		return nX;
	}
	public int nY()
	{
		return nY;
	}
	public int nIter() 
	{
		return nIter;
	}
	public Iteration iter()
	{
		return iter;
	}
	
	public ColorScheme cschm()
	{
		return cschm;
	}

	public int dx()
	{
		return dx;
	}
	public int dy()
	{
		return dy;
	}
	public double chSize()
	{
		return chSize;
	}

	public double a()
	{
		return a;
	}
	
	public double b()
	{
		return b;
	}
	public double ceiling()
	{
		return ceiling;
	}
}