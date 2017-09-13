/*package fracPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class rawDraw extends JFrame implements KeyListener
{
	UserMenu usrMenu;
	public rawDraw()
	{
		super("RawMandelbrot");
		
		addKeyListener(this);
		usrMenu = new UserMenu(this);

		SwingComponent swComp = new SwingComponent(usrMenu);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		getContentPane().add(swComp);
		setSize(600, 600);
		setVisible(true);
	
	}
	class SwingComponent extends JComponent
	{
		UserMenu scFrac;
		public SwingComponent(UserMenu frac)
		{
			scFrac = frac;
		}
		public void paintComponent(Graphics g)
		{
			FractalObserver frObs = new FractalObserver();
			super.paintComponent(g);
			g.drawImage(scFrac.getImage(),0,0, 600, 600, frObs);									
		}
	}
	public static void main(String[] args)
	{
		new rawDraw();	
	}
	class FractalObserver implements ImageObserver
	{
		public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int heigth)
		{
			return true;
		}
	}
	
	public void keyPressed(KeyEvent ke)
	{
		int key = ke.getKeyCode();
		switch(key)
		{
			case KeyEvent.VK_LEFT:
				usrMenu.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				usrMenu.moveRight();
				break;
			case KeyEvent.VK_UP:
				usrMenu.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				usrMenu.moveDown();
				break;
			case KeyEvent.VK_Z:
				usrMenu.enlarge();
				break;
			case KeyEvent.VK_X:
				usrMenu.ensmall();
				break;
		}
		
	}
	
	public void keyReleased(KeyEvent kEv)
	{
	}
	
	public void keyTyped(KeyEvent kEv)
	{	
	}
}*/