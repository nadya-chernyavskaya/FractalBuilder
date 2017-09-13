package main_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import polishNotation.PolishNotation;

import main_interface.MainFrame.swComponent;

import fracPackage.AbleToDraw;
import fracPackage.Fractal;
import fracPackage.JuliaFractal;
import fracPackage.SynchrMenu;


public class JuliaWindow implements AbleToDraw {
private JButton savePic;
private SynchrMenu synchrMenuJ;
JFrame frame= new JFrame();
private JFileChooser fcp;
private JpgFilesFilter jpg;
private PngFilesFilter png;
public JuliaFractal fractalJulia;
public JPanel graphJul;
private swComponent c;

public void MakeJuliaFrame(double a, double b,String s) {
	final JFrame frame= new JFrame();	frame.setName("Julia");
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	JMenuBar menuBar = new JMenuBar();
	savePic = new JButton("Cохранить");
	savePic.setFocusable(false);
	savePic.setOpaque(false);
	savePic.setContentAreaFilled(false);
	savePic.setBorderPainted(false);
	
	JButton param = new JButton("Параметры");
	param.setFocusable(false);
	param.setOpaque(false);
	param.setContentAreaFilled(false);
	param.setBorderPainted(false);
	
	param.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ParametersWin w = new ParametersWin();
			w.MakeParamWin(synchrMenuJ);
		}
	});
	
	jpg = new JpgFilesFilter();
	png = new PngFilesFilter();
	fcp = new JFileChooser();
	fcp.addChoosableFileFilter(jpg);
	fcp.addChoosableFileFilter(png);
	savePic.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fcp.setDialogTitle("Сохранение файла");
			fcp.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int res = fcp.showSaveDialog(frame);
			if ( res == JFileChooser.APPROVE_OPTION ){
				try{
					if (fcp.getFileFilter() == jpg) {
						ImageIO.write(fractalJulia.getImage(), "JPG", new File(fcp.getSelectedFile().getAbsolutePath()+".jpg"));
						System.out.println(fcp.getSelectedFile().getAbsolutePath());
					} else
					if (fcp.getFileFilter() == png) {
						ImageIO.write(fractalJulia.getImage(), "PNG", new File(fcp.getSelectedFile().getAbsolutePath()+".png"));
						System.out.println(fcp.getSelectedFile().getAbsolutePath());
					} else {
						ImageIO.write(fractalJulia.getImage(), "JPG", new File(fcp.getSelectedFile().getAbsolutePath()+".jpg"));
					}
				
				} catch (IOException ie) {
						ie.printStackTrace();
					}
			}	
		}
	});
	
	menuBar.add(savePic);
	menuBar.add(param);
	panel.add(menuBar,BorderLayout.NORTH);
	JPanel picture = new JPanel();

	c = new swComponent();
	
	synchrMenuJ  = new SynchrMenu();
	fractalJulia = new JuliaFractal (synchrMenuJ, c, this);
	synchrMenuJ.setAB(a,b);
	PolishNotation pol = new PolishNotation(s);
	synchrMenuJ.setIter(pol.s);
	synchrMenuJ.makeNewFractal();
	c.setPreferredSize(new Dimension(600, 600));
	picture.add(c, BorderLayout.CENTER);
	picture.add(new JLabel("   "), BorderLayout.NORTH);
	picture.add(new JLabel("   "), BorderLayout.SOUTH);
	picture.add(new JLabel("   "), BorderLayout.WEST);
	picture.setOpaque(false);
	picture.revalidate();

	JToolBar toolbar = new JToolBar("Toolbar",JToolBar.VERTICAL);
	toolbar.setOpaque(false);
	toolbar.setFloatable(false);
	String[] iconFiles = { "zoom-in.png", "zoom-out.png","arrow-left1.png","arrow-right1.png","arrow-up1.png","arrow-down1.png", "fill-color1.png" };
	String[] newiconFiles = { "zoom-in2.png", "zoom-out2.png","arrow-left.png","arrow-right.png","arrow-up.png","arrow-down.png", "fill-color.png" };
	String[] buttonLabels = { "����������", "��������", "�����","������","�����","����","����"};
	ImageIcon[] icons = new ImageIcon[iconFiles.length];
	JButton[] buttons = new JButton[buttonLabels.length];
	for (int i = 0; i < iconFiles.length; ++i) {
        icons[i] = new ImageIcon(iconFiles[i]);
        buttons[i] = new JButton(icons[i]);
        buttons[i].setFocusPainted(false);
        buttons[i].setContentAreaFilled(false);
        buttons[i].setToolTipText(buttonLabels[i]);
        buttons[i].setOpaque(false);
        buttons[i].setBorderPainted(false);
        buttons[i].setRolloverIcon(new ImageIcon(newiconFiles[i]));
        toolbar.add(buttons[i]);
	}
	buttons[1].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.ensmall();
				c.repaint();
		}
		});
	buttons[0].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.enlarge();
				c.repaint();
		}
		});
	
	buttons[2].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.moveLeft();
		}
		});
	buttons[4].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.moveUp();
		}
		});
	buttons[3].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.moveRight();
		}
		});
	buttons[5].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenuJ.moveDown();;
		}
		});
	buttons[6].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ColorWindow cw = new ColorWindow();
			int position = cw.getPosition(1);
			cw.MakeWindow(synchrMenuJ,position,1);
		}
		
	});
	
	panel.add(toolbar,BorderLayout.EAST);
	panel.add(picture, BorderLayout.CENTER);

	frame.add(panel);
	frame.setSize(650,658);
	frame.setLocationRelativeTo(null);
	
	frame.setResizable(false);
	frame.setVisible(true);
}


public class swComponent extends JComponent {
	public void paintComponent(Graphics g) {
		FractalObserver fObs = new FractalObserver();
		super.paintComponent(g);
		g.drawImage(fractalJulia.getImage(),0,0, c.getHeight(), c.getHeight(),fObs);

	}
}

	public void putIterXYdata(double x, double y, int nIter)
	{
		
	}

}
