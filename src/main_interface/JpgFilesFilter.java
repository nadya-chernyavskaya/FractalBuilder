package main_interface;

import javax.swing.filechooser.FileFilter;

public class JpgFilesFilter extends FileFilter {
	public boolean accept(java.io.File file) {
		if ( file.isDirectory() ) 
			return true;
		return ( file.getName().endsWith("jpg")) ;
	}
	public String getDescription() {
	return "Images (*.jpg)";
	}
}
