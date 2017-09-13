package main_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class helpWindow extends JFrame{
public JEditorPane editor;
public JEditorPane editor0;

public void MakeAll(JEditorPane e1, JEditorPane e2) {
	JFrame frame = new JFrame();
	frame.setName("Help");
	frame.pack();
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	JPanel icon = new JPanel();
	icon.setLayout(new BorderLayout());
	JLabel us = new JLabel(new ImageIcon("fill-color.png"));
	us.setOpaque(false);
	icon.setPreferredSize(new Dimension(400,50));
	icon.add(us,BorderLayout.WEST);
	
	JPanel other = new JPanel();
	other.setLayout(new BorderLayout());
	JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	JPanel tab1 = new JPanel(new BorderLayout(0,0));
	try{
		File homePage0 = new File("programm.html");
		String path0 = homePage0.getAbsolutePath();
		e1 = new JEditorPane("file://"+path0);
	} catch (Exception ex) {
		}
	e1.setContentType("text/html");
	e1.setEditable(false);
	tab1.add(e1);

	tab1.setPreferredSize(new Dimension(400,650));
	tabPanel.addTab("О программе",tab1);
	
	JPanel tab2 = new JPanel(new BorderLayout(0,0));
	try{
	File homePage = new File("instuction.html");
	String path = homePage.getAbsolutePath();
	e2 = new JEditorPane("file://"+path);
	} catch (Exception ex) {
	}
	e2.setContentType("text/html");
	e2.setEditable(false);
	JScrollPane scroll = new JScrollPane(e2);
 	tab2.add(scroll);

	tabPanel.addTab("Инструкция",tab2);
	other.add(tabPanel,BorderLayout.CENTER);
	frame.add(icon, BorderLayout.NORTH);
    frame.add(other,BorderLayout.CENTER);
	frame.setSize(420,470);
	frame.setTitle("Help");
	//frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	frame.setAlwaysOnTop(true);
	frame.setVisible(true);
}
}