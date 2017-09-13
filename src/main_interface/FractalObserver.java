package main_interface;

import java.awt.Image;
import java.awt.image.ImageObserver;

public class FractalObserver implements ImageObserver {
	public boolean imageUpdate(Image img,int infoflags,int x,int y, int width, int heith){
		return true;
	}
}
