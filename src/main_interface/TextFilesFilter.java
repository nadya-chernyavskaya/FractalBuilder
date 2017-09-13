package main_interface;

import javax.swing.filechooser.FileFilter;


public class TextFilesFilter extends FileFilter {
		public boolean accept(java.io.File file) {
			if ( file.isDirectory() ) return true;
			return ( file.getName().endsWith(".frac") );
		}
		public String getDescription() {
		
			return "Фрактал (*.frac)";
		}
	}
