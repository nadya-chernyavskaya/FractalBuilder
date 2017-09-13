package main_interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.filechooser.*;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.border.*;


import polishNotation.PolishNotation;

import fracPackage.AbleToDraw;
import fracPackage.ColorScheme;
import fracPackage.Fractal;
import fracPackage.JuliaFractal;
import fracPackage.FractalPoint;
import fracPackage.SynchrMenu;

public class MainFrame extends JFrame implements AbleToDraw {
	private DefaultListModel<String> list_model;
	private DefaultListModel<String> list_model_favorite;
	private JList<String> list2;
	private JList<String> list1;
	private JpgFilesFilter jpg;
	private PngFilesFilter png;
	private TextFilesFilter txt;
	private JMenuItem delete;
	private JMenuItem removeAll;
	private JPopupMenu pm;
	private JButton[] buttons;
	public JPanel graph;
	public JPanel picture;
	private JFileChooser fc1;
	private JFileChooser fcp;
	private JMenuItem savePic;
	public final JTextField t1,t2,t3;
	private int i;
	private JButton param;
	private int ScreenWidth;
	private int ScreenHeight;
	public Fractal fractal;
	public SynchrMenu synchrMenu;
	
	public MainFrame() {
		super("main_frame");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	//	setContentPane(new JLabel(new ImageIcon("3.jpg")));
		getScreenSize();
		setSize((ScreenWidth*25/40),(ScreenHeight*35/40));
		setTitle("Mandelbrot ABC");
		setLocationRelativeTo(null);		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Файл");
		JMenuItem open = new JMenuItem("Открыть избранные");
		//make hot key
		open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		JMenuItem saveObj = new JMenuItem("Cохранить избранные");
		saveObj.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		savePic = new JMenuItem("Cохранить изображение");	
		
		savePic.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		file.add(open);
		file.addSeparator();
		file.add(saveObj);
		file.add(savePic);
		//the rigth order is important!
		menuBar.add(file);
		
		JButton help = new JButton("Справка");
		help.setContentAreaFilled(false);
		help.setOpaque(false);
		help.setBorderPainted(false);
		help.setFocusable(false);
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpWindow u = new helpWindow();
				u.editor0 = new JEditorPane();
				u.editor = new JEditorPane();
				u.MakeAll(u.editor0, u.editor);
				}
			});
		param = new JButton("Параметры");
		param.setOpaque(false);
		param.setContentAreaFilled(false);
		param.setBorderPainted(false);
		param.setEnabled(false);
		param.setFocusable(false);
		param.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParametersWin paramWin = new ParametersWin();
				paramWin.MakeParamWin(synchrMenu);
			}
		});
		menuBar.add(param);
		menuBar.add(help);
		setJMenuBar(menuBar); 
		

	
	final JTextField smallField;
	smallField = new JTextField(13);
	t1 = new JTextField(10);
	t1.setToolTipText("Re(c)");
	t2 = new JTextField(10);
	t2.setToolTipText("Im(c)");
	t3 = new JTextField(3);	
	t3.setToolTipText("n - количество итераций до бесконечности");
	
	//табличное колво строк, столбцов, расстояния
	JPanel grid = new JPanel(new GridLayout(1,1,0,0));
	grid.setOpaque(false);
	// добавляем компоненты

	JPanel text_button = new JPanel(new FlowLayout(FlowLayout.LEFT));
	text_button.setOpaque(false);
	smallField.setToolTipText("Введите функцию");
	text_button.add(new JLabel("f(z) = "));
	text_button.add( smallField);
	JButton draw = new JButton("ОК");
	draw.setToolTipText("Нарисовать множество Мандельброта");

	text_button.add(draw);
	ImageIcon favImage  = new ImageIcon("bookmarks2.png"); 
	JButton fav = new JButton(favImage);
	fav.setToolTipText("Добавить в избранное");
	fav.setPreferredSize(new Dimension(22,22));
	fav.setOpaque(false);
	fav.setFocusPainted(false);
    fav.setContentAreaFilled(false);
    fav.setRolloverIcon(new ImageIcon("bookmarks.png"));
	fav.setBorderPainted(false);
	text_button.add(fav);
	fav.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		check(smallField.getText(),list_model_favorite);
	}
	});
	
	grid.add(text_button);

//	JPanel har_button = new JPanel(new FlowLayout(FlowLayout.LEFT));
//	har_button.setOpaque(false);
	JPanel har = new JPanel(new FlowLayout(FlowLayout.CENTER ));
	har.setToolTipText("Характеристики выбранной точки");
	har.setOpaque(false);
	har.add(t1);
	har.add(t2);
	har.add(t3);
	text_button.add(har);

	JButton Julia = new JButton("Жюлиа"); /////Julia
	Julia.setToolTipText("Нарисовать для точки мн-во Жюлиа");
	Julia.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			
			if ((t1.getText().length() > 0) & (t2.getText().length() > 0)) {
				JuliaWindow julfr = new JuliaWindow();
				julfr.MakeJuliaFrame(Double.parseDouble(t1.getText()), Double.parseDouble(t2.getText()),smallField.getText());
			}
		}
		});
	text_button.add(Julia);
//	grid.add(har_button);

	picture = new JPanel();
	picture.setOpaque(false);
	picture.setLayout(new BorderLayout());
	graph = new JPanel();//// here we add picture of Mandelbrot made my Jenya
	graph.setOpaque(false);
	picture.add(graph);
	/*-----------From Jenya--------------------------------------------------------------------------------------*/

	
	synchrMenu  = new SynchrMenu();
	fractal = new Fractal(synchrMenu, graph, this);

/*-----------------------------------------------------------------------------------------------------------*/
	
	
	graph.setLayout(new BorderLayout());
	graph.addMouseListener(new MouseAdapter(){
		 public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2)
		        {
		        	synchrMenu.getFPoint(e.getX(), e.getY());    		        	
		        }
		 }
	});
	graph.setFocusable(true);
	graph.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			graph.grabFocus();
		}
	});
	addKeyBindingLeft(graph);
	addKeyBindingRight(graph);
	addKeyBindingUp(graph);
	addKeyBindingDown(graph);
	
	draw.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		if ((smallField.getText()).length() > 0 ) {
			PolishNotation input = new PolishNotation(smallField.getText());
			if (input.analyse() < 0){ 
				JOptionPane.showMessageDialog(MainFrame.this,
						"Проверьте коректность функционального выражения", "Ошибка",
						JOptionPane.WARNING_MESSAGE);
			} else {
				
				for (int i = 0; i < buttons.length; i++) {
					buttons[i].setEnabled(true);
				}
				param.setEnabled(true);
				synchrMenu.setDimension(graph.getHeight());
				synchrMenu.setIter(input.s);
				synchrMenu.makeNewFractal();
				swComponent sw1 = new swComponent();
				graph.add(sw1, BorderLayout.CENTER);
				picture.revalidate();
				
			}
			
		} else
			JOptionPane.showMessageDialog(MainFrame.this,
					"Задайте функцию!", "Ошибка",
					JOptionPane.WARNING_MESSAGE);
	}});
	fc1 = new JFileChooser();
	fcp = new JFileChooser();
	jpg = new JpgFilesFilter();
	png = new PngFilesFilter();
	txt = new TextFilesFilter();
	fcp.addChoosableFileFilter(jpg);
	fcp.addChoosableFileFilter(png);
	savePic.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fcp.setDialogTitle("Сохранение файла");
			fcp.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int res = fcp.showSaveDialog(getContentPane());
			if ( res == JFileChooser.APPROVE_OPTION ){
				try{
					if (fcp.getFileFilter() == jpg) {
						ImageIO.write(fractal.getImage(), "JPG", new File(fcp.getSelectedFile().getAbsolutePath()+".jpg"));
						System.out.println(fcp.getSelectedFile().getAbsolutePath());
					} else
					if (fcp.getFileFilter() == png) {
						ImageIO.write(fractal.getImage(), "PNG", new File(fcp.getSelectedFile().getAbsolutePath()+".png"));
						System.out.println(fcp.getSelectedFile().getAbsolutePath());
					} else{
						ImageIO.write(fractal.getImage(), "PNG", new File(fcp.getSelectedFile().getAbsolutePath()+".png"));
					System.out.println(fcp.getSelectedFile().getAbsolutePath());
					}
					
				} catch (IOException ie) {
						ie.printStackTrace();
					}
			}	
		}
	});
	
	fc1.addChoosableFileFilter(txt);
	saveObj.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fc1.setDialogTitle("Сохранение избранных");
			fc1.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int res = fc1.showSaveDialog(getContentPane());
			if ( res == JFileChooser.APPROVE_OPTION ){
				if (list2.getModel().getSize() != 0 ) {
					try {
						if (fc1.getFileFilter() == txt ) {
							FileWriter ryt = new FileWriter(fc1.getSelectedFile().getAbsolutePath()+ ".frac");
							BufferedWriter out = new BufferedWriter(ryt);
							for (int i=0; i < list2.getModel().getSize();i++) {
								out.write((String)list_model_favorite.get(i));
								out.newLine();
							}
							out.close();
						} else {
							FileWriter ryt = new FileWriter(fc1.getSelectedFile().getAbsolutePath());
							BufferedWriter out = new BufferedWriter(ryt);
							for (int i=0; i < list2.getModel().getSize();i++) {
								out.write((String)list_model_favorite.get(i));
								out.newLine();
							}
							out.close();
						}
						} catch (IOException ie) {
							ie.printStackTrace();
						}
				}
			}
		}});
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fc1.setDialogTitle("Открыть");
				fc1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int res = fc1.showOpenDialog(getContentPane());
				if (res == JFileChooser.APPROVE_OPTION) {
					File set_file = fc1.getSelectedFile();
						try{
							String selected_func;
							FileReader selected_file = new FileReader(set_file);
							BufferedReader reader = new BufferedReader(selected_file);                                        
							while ((selected_func = reader.readLine()) != null) {
								check(selected_func,list_model_favorite);
				
							}
						} 
						catch (FileNotFoundException o) {
							o.printStackTrace();
						} 
						catch (IOException o) {
							o.printStackTrace();
						}
				}
			}});


	smallField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if ((smallField.getText()).length() > 0 ) {
				PolishNotation input = new PolishNotation(smallField.getText());
				if (input.analyse() < 0){ 
					JOptionPane.showMessageDialog(MainFrame.this,
							"Проверьте коректность функционального выражения", "Ошибка",
							JOptionPane.WARNING_MESSAGE);
				} else {
					synchrMenu.setDimension(graph.getHeight());					
					synchrMenu.setIter(input.s);
					synchrMenu.makeNewFractal();
					graph.add(new swComponent(), BorderLayout.CENTER);
					picture.add(graph);
					picture.revalidate();
				}
				
			}
			
		}});

	
	picture.add(new JLabel("     "), BorderLayout.WEST);
	picture.add(new JLabel("   "), BorderLayout.SOUTH);
	picture.add(new JLabel("   "), BorderLayout.NORTH);
//////////////////////////////////////////////TOOLBAR	
	JToolBar toolbar = new JToolBar("Toolbar",JToolBar.VERTICAL);
	toolbar.setOpaque(false);
	toolbar.setFloatable(false);
	
	String[] iconFiles = { "zoom-in.png", "zoom-out.png","arrow-left1.png","arrow-right1.png","arrow-up1.png","arrow-down1.png", "fill-color1.png" };
	String[] newiconFiles = { "zoom-in2.png", "zoom-out2.png","arrow-left.png","arrow-right.png","arrow-up.png","arrow-down.png", "fill-color.png" };
	String[] buttonLabels = { "Приблизить", "Отдалить", "Влево","Вправо","Вверх","Вниз","Цвет"};
	ImageIcon[] icons = new ImageIcon[iconFiles.length];
	buttons = new JButton[buttonLabels.length];
	for (i = 0; i < iconFiles.length; ++i) {
        icons[i] = new ImageIcon(iconFiles[i]);
        buttons[i] = new JButton(icons[i]);
        buttons[i].setEnabled(false);
        buttons[i].setFocusPainted(false);
        buttons[i].setContentAreaFilled(false);
        buttons[i].setToolTipText(buttonLabels[i]);
        buttons[i].setOpaque(false);
        buttons[i].setBorderPainted(false);
        buttons[i].setRolloverIcon(new ImageIcon(newiconFiles[i]));
        toolbar.add(buttons[i]);
	}
	buttons[0].addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
			synchrMenu.enlarge();
			graph.repaint();
	}
	});
	buttons[1].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenu.ensmall();
				graph.repaint();
		}
		});
	buttons[2].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenu.moveLeft();
		}
		});
	buttons[4].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenu.moveUp();
		}
		});
	buttons[3].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenu.moveRight();
		}
		});
	buttons[5].addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				synchrMenu.moveDown();;
		}
		});

	buttons[6].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			ColorWindow cw = new ColorWindow();
			int position = cw.getPosition(0);
			cw.MakeWindow(synchrMenu, position,0);
		}
		
	});
	

	
	picture.add(toolbar, BorderLayout.EAST);
//////////////////////////////////////////////////	
	JPanel right = new JPanel(new BorderLayout());
	right.setOpaque(false);
	JPanel left = new JPanel(new GridLayout(2,1));
	left.setOpaque(false);
	
	list_model = new DefaultListModel<String>();
	list_model.add(0,"z*(sin(z))^2 + c");
	list_model.add(0,"z*tg(z) + c");
	list_model.add(0,"z*(sin(z)-cos(z)) + c");
	list_model.add(0,"z*(cos(z))^3 + c");
	list_model.add(0,"exp(-z) + c");
	list_model.add(0,"z*tg(z) + c");
	list_model.add(0,"z*sin(z) + c");
	list_model.add(0,"z^8 + z^3 + c");
	list_model.add(0,"z^4 + c");
	list_model.add(0,"z^3 + c");
	list_model.add(0,"z^2 + c");
	list_model.add(0,"z + c");	
		
	list1 = new JList<String>(list_model);
	JScrollPane scroll_list1 = new JScrollPane(list1);
	list1.setBorder(new CompoundBorder(new TitledBorder("Примеры"), new EmptyBorder(1, 1, 1, 1)));
	scroll_list1.setOpaque(false);
	scroll_list1.getViewport().setOpaque(false);
	left.add(scroll_list1);
	
	list_model_favorite = new DefaultListModel<String>();
	list2 = new JList<String>(list_model_favorite);
	
	pm = new JPopupMenu();
	delete = new JMenuItem("Удалить");
	removeAll = new JMenuItem("Очистить список");
	pm.add(delete);
	pm.add(removeAll);
	list2.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent me) {
			if (SwingUtilities.isRightMouseButton(me) && !list2.isSelectionEmpty() && list2.locationToIndex(me.getPoint()) == list2.getSelectedIndex()) {
				pm.show(list2, me.getX(), me.getY());   
		    }
			if(me.getClickCount()==2){
	        	if (!list2.isSelectionEmpty() && list2.locationToIndex(me.getPoint()) == list1.getSelectedIndex()) {
	        		smallField.setText((String) list_model_favorite.get(list2.getSelectedIndex()));
	        	}  	
	        }
		}
	});
	list1.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
	        if(e.getClickCount()==2){
	        	if (!list1.isSelectionEmpty() && list1.locationToIndex(e.getPoint()) == list1.getSelectedIndex()) {
	        		smallField.setText((String) list_model.get(list1.getSelectedIndex()));
	        	}  	
	        }
		}
	});
	list2.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
	        if(e.getClickCount()==2){
	        	if (!list2.isSelectionEmpty() && list2.locationToIndex(e.getPoint()) == list2.getSelectedIndex()) {
	        		smallField.setText((String) list_model_favorite.get(list2.getSelectedIndex()));
	        	}  	
	        }
		}
	});
	
	
	delete.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			System.out.println(list2.getSelectedIndex());
			list_model_favorite.remove(list2.getSelectedIndex());
  		}
    });
	removeAll.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			list_model_favorite.removeAllElements();
		}
	});
	
	JScrollPane scroll_list2 = new JScrollPane(list2);
	list2.setBorder(new CompoundBorder(new TitledBorder("Избранные"), new EmptyBorder(1, 1, 1, 1)));
	scroll_list2.setOpaque(false);
	scroll_list2.getViewport().setOpaque(false);
	left.add(scroll_list2);
	
	
	JSplitPane splitVertic = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	splitVertic.setOpaque(false);
	splitVertic.setDividerSize(6);
	splitVertic.setResizeWeight(0.99);
	splitVertic.setEnabled(false);
	splitVertic.isContinuousLayout();
	splitVertic.setTopComponent(picture);
	splitVertic.setBottomComponent(grid);
	
	JSplitPane splitHorizont = new JSplitPane();
	splitHorizont.setOpaque(false);
	splitHorizont.setEnabled(false);
	//splitHorizont.setOneTouchExpandable(true);
	splitHorizont.setDividerSize(8);
	splitHorizont.isContinuousLayout();
	splitHorizont.setDividerLocation(0.2);
	splitHorizont.setResizeWeight(0.2);
	splitHorizont.setLeftComponent(left);
	splitHorizont.setRightComponent(right);
	right.add(splitVertic);

	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(splitHorizont, BorderLayout.CENTER);
///////////////////////////

	setVisible(true);
	}


	
	void addKeyBindingLeft(JComponent jc) {
        jc.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left pressed");
        jc.getActionMap().put("left pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	synchrMenu.moveLeft();
            }
        });
	}
	
	void addKeyBindingRight(JComponent jc) {
        jc.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right pressed");
        jc.getActionMap().put("right pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	synchrMenu.moveRight();
            }
        });
	}
	void addKeyBindingUp(JComponent jc) {
        jc.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up pressed");
        jc.getActionMap().put("up pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
            	synchrMenu.moveUp();
            }
        });
	}
	void addKeyBindingDown(JComponent jc) {
        jc.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down pressed");
        jc.getActionMap().put("down pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	synchrMenu.moveDown();
            }
        });
	}
	
	public SynchrMenu getFrac(){
		return synchrMenu;
	}
	
//====================================A FUNCTION GETTING THE SCREEN RESOLUTION===========================================
	public void getScreenSize()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		ScreenWidth  = screenSize.width;
		ScreenHeight = screenSize.height;
		
	}
//======================================================================================================================	
	public void putIterXYdata(double x, double y, int nIter)
	{
    	t1.setText("" + x);		
    	t2.setText("" + y);		
    	t3.setText("" + nIter);
    	t1.setCaretPosition(0);
    	t2.setCaretPosition(0);
    	t3.setCaretPosition(0);		
	}
	
	
	public void check(String a,DefaultListModel<String> list ) {
		int i;
		if ( a.length() > 0 ) {
			int p = list2.getModel().getSize();
			if (p == 0 ) 
				list.add(0,a);
			
				System.out.println(p);
			for (i=0;i<p;++i){
				if (a.equals((String) list.get(i))){
					break;
				}
			}
			if ((i == p) & (p!= 0)) list_model_favorite.add((p),a);
		}
	}
	
//=======================================================================================================================
	public class swComponent extends JComponent {
			public void paintComponent(Graphics g) {
				FractalObserver fObs = new FractalObserver();
				super.paintComponent(g);
				g.drawImage(fractal.getImage(),0,0,graph.getSize().height,graph.getSize().height,fObs);
			}
		}
	
	///////////////////////
	
	 public static void main(String[] args) {
		 Locale[] l = Locale.getAvailableLocales();
		 for (int i = 0; i < l.length; i++) {
			if(l[i].toString().equals("ru")){
				Locale.setDefault(l[i]);
				break;
			}
			
		}
		MainFrame win = new MainFrame();
		win.setResizable(false);
	}
}
