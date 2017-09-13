package fracPackage;

import java.awt.*;

public class ColorScheme 
{
 	public Color[] colorArr; 
 	public double alpha = 0;
 	public double betta = 0;
 	public double gamma = 0;
 	public ColorScheme()
 	{
 	}
 	public ColorScheme(double phi)
 	{
 		if(phi<10){
 			this.alpha = phi*0.628;
 		} else if(phi<20){
 			this.betta = phi*0.628;
 		} else {
 			this.gamma = phi*0.628;
 		}
 	}
	public Color color(int nIter)
	{
		
		int red   = (int)Math.round(-125*(Math.cos(nIter/6.+ alpha + 2* betta)) + 125);
		int green = (int)Math.round(-125*(Math.cos(nIter/6. + 1.04 + 2* alpha + gamma)) + 125);
		int blue  = (int)Math.round(-125*(Math.cos(nIter/6. + 2.08 + betta +2*gamma)) + 125);
		
		return new Color(red, green, blue);
	}
}
