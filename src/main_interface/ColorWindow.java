package main_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import fracPackage.SynchrMenu;

public class ColorWindow extends JFrame{
private static JSlider slider;
private static JFrame frame;
public static int result;
public static int result0;
public void MakeWindow(final SynchrMenu frac, int res,final int a) {
	frame = new JFrame();
	frame.setName("Color");
	frame.pack();
	JPanel panel = new JPanel();
	JLabel shrift = new JLabel("Выберите цветовую гамму");
	panel.add(new JLabel("         "));
	panel.add(shrift);
	JLabel bar = new JLabel(new ImageIcon("icons/ColorBar.png"));
	panel.add(bar);
	slider = new JSlider(0, 30, 0);
	slider.setMajorTickSpacing(500);
	slider.setPaintTicks(true);
	slider.setValue(res);
	slider.setValue(res);
	Dimension d = slider.getPreferredSize();  
	slider.setPreferredSize(new Dimension(d.width+117,d.height));
	panel.add(slider);
	JButton okey = new JButton("Применить"); 
	okey.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (a == 0 ) {
				result0 = slider.getValue();
				frac.setColor(result0);
			}
			else {
				result = slider.getValue();
				frac.setColor(result);
			}
			frame.dispose();
		}
	});
	panel.add(okey);
	frame.add(panel);
	frame.setSize(400,145);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	
}
public int getPosition(int a){
	if (a == 0) 
		return result0;
	else 
		return result;
}
}
