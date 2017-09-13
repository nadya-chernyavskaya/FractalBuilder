package main_interface;

import javax.swing.filechooser.FileFilter;

public class PngFilesFilter extends FileFilter {
	public boolean accept(java.io.File file) {
		if ( file.isDirectory() ) 
			return true;
		return ( file.getName().endsWith("png")) ;
	}
	public String getDescription() {
	return "Изображения (*.png)";
	}
}