package main_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fracPackage.SynchrMenu;

public class ParametersWin {
public JFrame frame;
public JPanel pane[];
public JTextField text[];
public void MakeParamWin(final SynchrMenu sm){
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(new Dimension(320,400));
		frame.setAlwaysOnTop(true);
		JPanel panel = new JPanel(); 
		JPanel pane0 = new JPanel();
		pane0.add(new JLabel("Задайте параметры:"));
		panel.add(pane0);
		
		final String label[] = {" Координата X  ","  Координата Y  ","       Размер        ","   Разрешение   ","Число итераций", " Бесконечность "};
		JLabel[] label0 = new JLabel[label.length];
		pane = new JPanel[label.length];
		text = new JTextField[label.length];
		for (int i=0;i < label.length;++i ){
			label0[i] = new JLabel();
			label0[i].setText(label[i]);
			pane[i] = new JPanel();
			pane[i].add(label0[i]);
			text[i] = new JTextField();
			text[i].setColumns(6);
			pane[i].add(text[i]);
			panel.add(pane[i]);
		}
		text[0].setText(""+sm.xLeft());
		text[1].setText(""+sm.yHigh());
		text[2].setText(""+(sm.xRight() - sm.xLeft()));
		text[3].setText(""+sm.nX());
		text[4].setText(""+sm.nIter());
		text[5].setText(""+sm.ceiling());
		JButton confirm = new JButton("Применить");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if (sm.setProperties(text[0].getText(),text[1].getText(),text[2].getText(),text[3].getText(),text[4].getText(),text[5].getText()) < 0 ){
					JOptionPane.showMessageDialog(frame,"Некорректно заполнены поля!", "Ошибка",JOptionPane.WARNING_MESSAGE);		
					return;
				}
				sm.makeNewFractal();
				frame.dispose();
			}
		});
		
	
		JButton asItWas = new JButton("По умолчанию");
		asItWas.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e){
				sm.setDefaultProperties();
				sm.makeNewFractal();
				frame.dispose();
			}
		});
		JPanel l = new JPanel();
		JPanel l1 = new JPanel();
		l.add(confirm);
		l1.add(asItWas);
		panel.add(l);
		panel.add(l1);
		frame.add(panel);
	
		frame.setTitle("Parameters");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

}
