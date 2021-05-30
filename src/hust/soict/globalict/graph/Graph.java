package hust.soict.globalict.graph;

import static java.awt.Event.CTRL_MASK;

import java.awt.BorderLayout ;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import hust.soict.globalict.graph.RightClick;





public class Graph extends JFrame implements ActionListener {
		//declare frame
	private JFrame frameAbout;
	private String data[][], head[];
	private String[] listDirected = {"Khong chi so" , "Co chi so"} ;
	private JComboBox<String> cbbBeginPoint = new JComboBox<String>();
	private JComboBox<String> cbbEndPoint = new JComboBox<String>();
	private JComboBox<String> cbbMapType = new JComboBox<String>() ;
	
	private JRadioButton radUndirected, radDirected, radBFS, radDFS, radDijkstra ;
	private JButton btnRunAll, btnRunStep;
	
	private JTable tableMatrix;
	private JTable tableLog;
	
	// declare menu draw
	private JPanel drawPanel = new JPanel();
	private JButton btnPoint, btnLine, btnUpdate, btnMove, btnOpen, btnSave,
	btnNew;
    
	//draw
	private Draw myDraw = new Draw();
	
	//declare log
	private JTextArea textLog;
	private JTextArea textMatrix;
	
	private JTextField textNumerPoint;

	private RightClick mouseRight;
	
	private int indexBeginPoint = 0, indexEndPoint = 0;
	private int step = 0;
	private boolean mapType = false;
	private boolean MapType = false; //ko co chi so


	private int HEIGHT_SELECT;

	private int WIDTH_SELECT;
	
	
	public Graph(String title) {
		setTitle(title);
		setLayout(new BorderLayout(5, 5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //set up exit application default window
		
		add(creatMenu(), BorderLayout.NORTH);	      // creat menu bar
         // creat content panel
		add(creatSelectPanel(), BorderLayout.WEST);
		add(creatPaintPanel(), BorderLayout.CENTER);
		add(creatLogPanel(), BorderLayout.SOUTH);
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		}
	
	
	






	private JPanel creatLogPanel() {
		textLog = new JTextArea("Path: ");
		textLog.setRows(3);
		textLog.setEditable(false);
		JScrollPane scrollPath = new JScrollPane(textLog);
		JScrollPane scroll = new JScrollPane(tableLog = createTable());

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("Log"));
		panel.add(scrollPath, BorderLayout.PAGE_START);
		panel.add(scroll, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(WIDTH_SELECT * 7 / 2,
				HEIGHT_SELECT / 2));
		return panel;
	}




	private JPanel creatPaintPanel() {
		
		drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.Y_AXIS));
		drawPanel.setBorder(new TitledBorder(""));
		drawPanel.setBackground(null);
		Icon icon;
		// String link = File.separator + "icon" + File.separator;
		String link = "/icon/";

		icon = getIcon(link + "iconOk.png");
		drawPanel.add(btnUpdate = createButtonImage(icon, "Update Graph"));

		icon = getIcon(link + "iconPoint.png");
		drawPanel.add(btnPoint = createButtonImage(icon, "Draw Point"));

		icon = getIcon(link + "iconLine.png");
		drawPanel.add(btnLine = createButtonImage(icon, "Draw line"));

		icon = getIcon(link + "iconMove.png");
		drawPanel.add(btnMove = createButtonImage(icon, "Move Point"));

		icon = getIcon(link + "iconOpen.png");
		drawPanel.add(btnOpen = createButtonImage(icon, "Open graph"));

		icon = getIcon(link + "iconSave.png");
		drawPanel.add(btnSave = createButtonImage(icon, "Save graph"));

		icon = getIcon(link + "iconNew.png");
		drawPanel.add(btnNew = createButtonImage(icon, "New graph"));

		mouseRight = createRightClick();
		myDraw.setComponentPopupMenu(mouseRight);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(drawPanel, BorderLayout.WEST);
		JScrollPane MyDraw = new JScrollPane(myDraw);
//		MyDraw.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
//	    MyDraw.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

		panel.add(MyDraw, BorderLayout.CENTER);
		return panel;	}

	private ImageIcon getIcon(String link) {
		return new ImageIcon(getClass().getResource(link));
	}
	
	private JRadioButton createRadioButton(String lable, Boolean select) {
		JRadioButton rad = new JRadioButton(lable);
		rad.addActionListener(this);
		rad.setSelected(select);
		return rad;
	}
	
	private JButton createButton(String lable) {
		JButton btn = new JButton(lable);
		btn.addActionListener(this);
		return btn;
	}

	// create buttonImage and add to panel
	private JButton createButtonImage(Icon icon, String toolTip) {
			JButton btn = new JButton(icon);
			btn.setMargin(new Insets(0, 0, 0, 0));
			btn.addActionListener(this);
			btn.setToolTipText(toolTip);
			return btn;
		}

	private JComboBox<String> createComboxBox(String title) {
				String list[] = { title };
		 		JComboBox<String> cbb = new JComboBox<String>(list);
		 		cbb.addActionListener(this);
		 		cbb.setEditable(false);
		 		cbb.setMaximumRowCount(5);
		 		return cbb;
		 	}
	private JTable createTable() {
		JTable table = new JTable();
		return table;
	}
	
	
	private JPanel creatSelectPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new GridLayout(4, 1, 5, 5));
		JPanel panelBottom = new JPanel(new BorderLayout());

		JPanel panelMapTypeTemp = new JPanel(new GridLayout(1, 2, 5, 5));
		panelMapTypeTemp.setBorder(new EmptyBorder(0, 10, 0, 5));
		panelMapTypeTemp.add(radUndirected = createRadioButton("Undirected",true));
		
		JPanel panelMapListDirected = new JPanel(new BorderLayout()); 
		panelMapListDirected.add(radDirected = createRadioButton("Directed", false), BorderLayout.WEST); 		
		panelMapListDirected.add(cbbMapType = createComboBox("Co chi so"), BorderLayout.CENTER);
		cbbMapType.setEnabled(false);
		cbbMapType.setModel(new DefaultComboBoxModel<String>(listDirected));
		cbbMapType.setMaximumRowCount(2);
		panelMapTypeTemp.add(panelMapListDirected);
		ButtonGroup groupMapType = new ButtonGroup();
		groupMapType.add(radUndirected);
		groupMapType.add(radDirected);
		JPanel panelMapType = new JPanel(new BorderLayout());
		panelMapType.setBorder(new TitledBorder("Map Type"));
		panelMapType.add(panelMapTypeTemp);

		JPanel panelInputMethodTemp = new JPanel(new GridLayout(1, 2, 5, 5));
		panelInputMethodTemp.setBorder(new EmptyBorder(0, 10, 0, 5));
		panelInputMethodTemp.add(radBFS = createRadioButton("BFS", true));
		panelInputMethodTemp.add(radDFS = createRadioButton("DFS", false));
		panelInputMethodTemp.add(radDijkstra = createRadioButton("Dijkstra", false));
		radDijkstra.setEnabled(false);
     	ButtonGroup groupInputMethod = new ButtonGroup();
		groupInputMethod.add(radBFS);
		groupInputMethod.add(radDFS); 
		groupInputMethod.add(radDijkstra);
		
		JPanel panelInputMethod = new JPanel(new BorderLayout());
		panelInputMethod.setBorder(new TitledBorder("Algorithms"));
		panelInputMethod.add(panelInputMethodTemp); 

		JPanel panelSelectPointTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelSelectPointTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelSelectPointTemp.add(cbbBeginPoint = createComboxBox("Begin"));
		panelSelectPointTemp.add(cbbEndPoint = createComboxBox("End"));
		JPanel panelSelectPoint = new JPanel(new BorderLayout());
		panelSelectPoint.setBorder(new TitledBorder("Point"));
		panelSelectPoint.add(panelSelectPointTemp);

		JPanel panelRunTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelRunTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelRunTemp.add(btnRunAll = createButton("Run All"));
		panelRunTemp.add(btnRunStep = createButton("Run Step"));
		JPanel panelRun = new JPanel(new BorderLayout());
		panelRun.setBorder(new TitledBorder("Run"));
		panelRun.add(panelRunTemp);

		panelTop.add(panelMapType);
		panelTop.add(panelSelectPoint);
		panelTop.add(panelInputMethod);
		panelTop.add(panelRun);

		JScrollPane scroll = new JScrollPane(tableMatrix = createTable());
		scroll.setPreferredSize(panelTop.getPreferredSize());
		panelBottom.add(scroll);

		panel.add(panelTop, BorderLayout.PAGE_START);
		panel.add(panelBottom, BorderLayout.CENTER);
		panel.setBorder(new EmptyBorder(0, 5, 0, 0));
		WIDTH_SELECT = (int) panel.getPreferredSize().getWidth();
		HEIGHT_SELECT = (int) panel.getPreferredSize().getHeight();
		return panel;
	}

	




	private JComboBox<String> createComboBox(String title) {
		String list[] = {title};
		JComboBox<String> cbb = new JComboBox<String>(list);
		cbb.addActionListener(this);
		cbb.setEditable(false);
		cbb.setMaximumRowCount(2);
		return cbb;
	}


	private JMenuBar creatMenu() {
		
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		// menuFile.add(menuFileNew);
		menuFile.add(createMenuItem("New", KeyEvent.VK_N, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Open", KeyEvent.VK_O, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Save", KeyEvent.VK_S, Event.CTRL_MASK));
		menuFile.addSeparator();
		menuFile.add(createMenuItem("Exit", KeyEvent.VK_X, Event.CTRL_MASK));

		JMenu menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);
		menuEdit.add(createMenuItem("ScreenShot", KeyEvent.VK_T, Event.CTRL_MASK));
		
		JMenu menuAbout = new JMenu("About");
		menuAbout.setMnemonic(KeyEvent.VK_A);
		menuAbout.add(createMenuItem("Information", KeyEvent.VK_I, CTRL_MASK));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuAbout);
		return menuBar;
	}




	private JMenuItem createMenuItem(String title, int keyEvent, int ctrlMask) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setMnemonic(keyEvent);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, ctrlMask));
		menuItem.addActionListener(this);
 
		return menuItem;
	}
	
	// creat Right Mouse Click
	private RightClick createRightClick() {
		RightClick mouseR = new RightClick();
		if(mapType && MapType) {
		mouseR.add(createMenuItem("Change cost", 0, 0));
		}
		mouseR.add(createMenuItem("Delete", 0, 0));
		
		return mouseR;
	}
	
//        ACTION 


	private void actionUpdate() {
		updateListPoint();
//		resetDataDijkstra();
		setDrawResultOrStep(false);
		reDraw();
//		loadMatrix();
		clearLog();
	}

	private void actionDrawPoint() {
		myDraw.setDraw(1);
		setDrawResultOrStep(false);
	}

	private void actionDrawLine() {
		myDraw.setDraw(2);
		setDrawResultOrStep(false);
	}

	private void actionOpen() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open graph");
		int select = fc.showOpenDialog(this);
		if (select == 0) {
			String path = fc.getSelectedFile().getPath();
			System.out.println(path);
			myDraw.readFile(path);
			actionUpdate();
		}
	}

	private void actionSave() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save graph");
		int select = fc.showSaveDialog(this);
		if (select == 0) {
			String path = fc.getSelectedFile().getPath();
			System.out.println(path);
			myDraw.write(path);
		}
	}

	private void actionNew() {
		setDrawResultOrStep(false);
		myDraw.setResetGraph(true);
		myDraw.repaint();
		myDraw.init();
		updateListPoint();
		clearLog();
		clearMatrix();
	}

	private void actionChoosePoint() {
//		resetDataDijkstra();
		setDrawResultOrStep(false);
		reDraw();
		clearLog();
	}

	private void showDialogChangeCost() {
		int index = myDraw.indexLineContain(mouseRight.getPoint());
		if (index > 0) {
			myDraw.changeCost(index);
			actionUpdate();
		} else {
			JOptionPane.showMessageDialog(null, "Haven't line seleced!");
		}
	}

	private void showDialogDelete() {
		int index = myDraw.indexPointContain(mouseRight.getPoint());
		if (index <= 0) {
			index = myDraw.indexLineContain(mouseRight.getPoint());
			if (index > 0) {
				// show message dialog
				MyLine ml = myDraw.getData().getArrMyLine().get(index);
				String message = "Do you want delete the line from "
						+ ml.getIndexPointA() + " to " + ml.getIndexPointB();
				int select = JOptionPane.showConfirmDialog(this, message,
						"Delete line", JOptionPane.OK_CANCEL_OPTION);
				if (select == 0) {
					myDraw.deleteLine(index);
					actionUpdate();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Haven't point or line seleced!");
			}
		} else {
			// show message dialog
			String message = "Do you want delete the point " + index;
			int select = JOptionPane.showConfirmDialog(this, message,
					"Delete point", JOptionPane.OK_CANCEL_OPTION);
			if (select == 0) {
				myDraw.deletePoint(index);
				actionUpdate();
			}
		}
	}

	private void updateListPoint() {
		int size = myDraw.getData().getArrMyPoint().size();
		String listPoint[] = new String[size];
		listPoint[0] = "Begin";
		for (int i = 1; i < listPoint.length; i++) {
			listPoint[i] = String.valueOf(i);
		}

		cbbBeginPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
		cbbBeginPoint.setMaximumRowCount(5);

		if (size > 1) {
			listPoint = new String[size + 1];
			listPoint[0] = "End";
			for (int i = 1; i < listPoint.length; i++) {
				listPoint[i] = String.valueOf(i);
			}
			listPoint[listPoint.length - 1] = "All";
		} else {
			listPoint = new String[1];
			listPoint[0] = "End";
		}

		cbbEndPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
		cbbEndPoint.setMaximumRowCount(5);
	}

/*	private void setEnableDraw(boolean check, String matrix) {
		// btnLine.setEnabled(check);
		// btnPoint.setEnabled(check);
		// btnUpdate.setEnabled(check);

		// CardLayout cl = (CardLayout) (matrixPandl.getLayout());
		// cl.show(matrixPandl, matrix);
		cbbGraphDemo.setEnabled(!check);
	}
*/
	private void setEnableMapType(boolean mapType, boolean MapType) { // directed or not
		this.mapType = mapType;
		this.MapType = MapType;
		myDraw.setTypeMapDirected(MapType);
		myDraw.setTypeMap(mapType);
		setDrawResultOrStep(false);
		myDraw.repaint();
//		resetDataDijkstra();
//		loadMatrix();
	}

	private void setDrawResultOrStep(boolean check) {
		myDraw.setDrawResult(check);
		myDraw.setDrawStep(check);
	}

/*	private void resetDataDijkstra() {
		step = 0;
		dijkstra = new MyDijkstra();
		dijkstra.setMapType(mapType);
		dijkstra.setArrMyPoint(myDraw.getData().getArrMyPoint());
		dijkstra.setArrMyLine(myDraw.getData().getArrMyLine());
		dijkstra.input();
		dijkstra.processInput();
	}
*/
	private void reDraw() {
		myDraw.setReDraw(true);
		myDraw.repaint();
	}

	private void clearMatrix() {
		DefaultTableModel model = new DefaultTableModel();
		tableMatrix.setModel(model);
	}

	private void clearLog() {
		DefaultTableModel model = new DefaultTableModel();
		tableLog.setModel(model);
		clearPath();
	}

	private void clearPath() {
		textLog.setText("Path : ");
	}

/*	private void loadMatrix() {
		final int width = 35;
		final int col = WIDTH_SELECT / width - 1;
		int infinity = dijkstra.getInfinity();
		int a[][] = dijkstra.getA();
		head = new String[a.length - 1];
		data = new String[a[0].length - 1][a.length - 1];
		for (int i = 1; i < a[0].length; i++) {
			head[i - 1] = String.valueOf(i);
			for (int j = 1; j < a.length; j++) {
				if (a[i][j] == infinity) {
					data[i - 1][j - 1] = "∞";
				} else {
					data[i - 1][j - 1] = String.valueOf(a[i][j]);
				}
			}
		}
		DefaultTableModel model = new DefaultTableModel(data, head);
		tableMatrix.setModel(model);
		if (tableMatrix.getColumnCount() > col) {
			for (int i = 0; i < head.length; i++) {
				TableColumn tc = tableMatrix.getColumnModel().getColumn(i);
				tc.setPreferredWidth(width);
			}
			tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		} else {
			tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
	}
*/
/*	private void loadLog(boolean isStep) {
		final int width = 70;
		final int col = tableLog.getWidth() / width - 1;
		int infinity = dijkstra.getInfinity();
		int logLen[][] = dijkstra.getLogLen();
		int logP[][] = dijkstra.getLogP();
		head = new String[logLen.length - 1];
		data = new String[dijkstra.getNumberPointChecked()][logLen.length - 1];
		boolean check[] = new boolean[logLen.length - 1];

		for (int i = 0; i < logLen.length - 1; i++) {
			head[i] = String.valueOf(i + 1);
			check[i] = false;
			data[0][i] = "[∞, ∞]";
		}

		data[0][indexBeginPoint - 1] = "[0, " + indexBeginPoint + "]";

		for (int i = 1; i < data.length; i++) {
			int min = infinity, indexMin = -1;
			// // check "*" for min len
			for (int j = 1; j < logLen.length; j++) {
				if (min > logLen[i][j] && !check[j - 1]) {
					min = logLen[i][j];
					indexMin = j - 1;
				}
			}
			if (indexMin > -1) {
				check[indexMin] = true;
			}

			for (int j = 1; j < logLen.length; j++) {

				if (min > logLen[i][j] && !check[j - 1]) {
					min = logLen[i][j];
					indexMin = j - 1;
				}

				String p = "∞";
				if (logP[i][j] > 0) {
					p = logP[i][j] + "";
				}
				if (check[j - 1]) {
					data[i][j - 1] = "-";
				} else if (logLen[i][j] == infinity) {
					data[i][j - 1] = "[∞, " + p + "]";
				} else {
					data[i][j - 1] = "[" + logLen[i][j] + ", " + p + "]";
				}
			}

			if (indexMin > -1) {
				data[i - 1][indexMin] = "*" + data[i - 1][indexMin];
			}
		}

		// check "*" for min len of row last
		int min = infinity, indexMin = -1;
		for (int j = 1; j < logLen.length; j++) {
			if (min > logLen[data.length - 1][j] && !check[j - 1]) {
				min = logLen[data.length - 1][j];
				indexMin = j - 1;
			}
		}
		if (indexMin > -1) {
			check[indexMin] = true;
			data[data.length - 1][indexMin] = "*"
					+ data[data.length - 1][indexMin];
		}

		// update data for table log
		DefaultTableModel model = new DefaultTableModel(data, head);
		tableLog.setModel(model);
		if (tableLog.getColumnCount() > col) {
			for (int i = 0; i < head.length; i++) {
				TableColumn tc = tableLog.getColumnModel().getColumn(i);
				tc.setPreferredWidth(width);
			}
			// tableLog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		} else {
			// tableLog.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
	}
*/
/*	private void drawDemo() {
		int demo = cbbGraphDemo.getSelectedIndex();
		myDraw.readDemo(demo);
		actionUpdate();
	}
*/
	private void processInputMatrix() {
		int numberPoint = 0;
		boolean isSuccess = true;
		try {
			numberPoint = Integer.parseInt(textNumerPoint.getText());
			int a[][] = new int[numberPoint][numberPoint];
			String temp = textMatrix.getText();
			Scanner scan = new Scanner(temp);
			for (int i = 0; i < numberPoint; i++) {
				for (int j = 0; j < numberPoint; j++) {
					try {
						a[i][j] = scan.nextInt();
					} catch (InputMismatchException e) {
						JOptionPane.showMessageDialog(null,
								"Enter your matrix!");
						isSuccess = false;
						break;
					}
				}
				if (!isSuccess) {
					break;
				}
			}

			for (int i = 0; i < numberPoint; i++) {
				for (int j = 0; j < numberPoint; j++) {
					System.out.printf("%3d", a[i][j]);
				}
				System.out.println();
			}

			scan.close();

			myDraw.setA(a);
			myDraw.convertMatrixToData();
			myDraw.repaint();
//			dijkstra.setA(a);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Enter one integer number < 30!");
		}

	}

	private boolean checkRun() {
		int size = myDraw.getData().getArrMyPoint().size() - 1;
		indexBeginPoint = cbbBeginPoint.getSelectedIndex();
		indexEndPoint = cbbEndPoint.getSelectedIndex();
		if (indexEndPoint == size + 1) { // all Point
			indexEndPoint = -1;
		}

		if (size < 1 || indexBeginPoint == 0 || indexEndPoint == 0) {
			JOptionPane.showMessageDialog(null,
					"Error chose points or don't Update graph to chose points",
					"Error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void setBeginEndPoint() {
		myDraw.setIndexBeginPoint(indexBeginPoint);
		myDraw.setIndexEndPoint(indexEndPoint);
//		dijkstra.setBeginPoint(indexBeginPoint);
//		dijkstra.setEndPoint(indexEndPoint);
	}

/*	private void runAll() {
		if (checkRun()) {
			resetDataDijkstra();
			setBeginEndPoint();
			dijkstra.dijkstra();
			textLog.setText(dijkstra.tracePath());
			loadLog(false);

			myDraw.setDrawStep(false);
			myDraw.setDrawResult(true);
			myDraw.setA(dijkstra.getA());
			myDraw.setP(dijkstra.getP());
			myDraw.setInfinity(dijkstra.getInfinity());
			myDraw.setLen(dijkstra.getLen());
			myDraw.setCheckedPointMin(dijkstra.getCheckedPointMin());
			myDraw.repaint();
		}
	}

	private void runStep() {
		if (checkRun()) {
			setBeginEndPoint();
			dijkstra.dijkstraStep(++step);
			loadLog(true);
			textLog.setText(dijkstra.tracePathStep());

			myDraw.setDrawStep(true);
			myDraw.setDrawResult(false);
			myDraw.setA(dijkstra.getA());
			myDraw.setP(dijkstra.getP());
			myDraw.setArrPointResultStep(dijkstra.getArrPointResultStep());
			myDraw.setLen(dijkstra.getLen());
			myDraw.setCheckedPointMin(dijkstra.getCheckedPointMin());
			myDraw.setInfinity(dijkstra.getInfinity());
			myDraw.repaint();
		}
	}
*/

/*	private void showAbout() {
		if (frameAbout == null) {
			frameAbout = new HelpAndAbout(1, "Dijkstra - About");
		}
		frameAbout.setVisible(true);
	}
*/
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		// select button in paint
		if (e.getSource() == btnUpdate) {
			actionUpdate();
		}

		if (e.getSource() == btnPoint) {
			actionDrawPoint();
		}

		if (e.getSource() == btnLine) {
			actionDrawLine();
		}
		if (e.getSource() == btnMove) {
			myDraw.setDraw(3);
		}

		if (e.getSource() == btnNew) {
			actionNew();
		}

		
		// select Map type
		if (e.getSource() == radUndirected) {
			setEnableMapType(false,false);
			cbbMapType.setEnabled(false);
			radDijkstra.setEnabled(false);
		} else if (e.getSource() == radDirected) {
			setEnableMapType(true,false);
			cbbMapType.setEnabled(true);
		}
		if(e.getSource() == radBFS) {
			
		}
        if(e.getSource() == radDFS) {
			
		}
        if(e.getSource() == radDijkstra) {
			
		}

		if (e.getSource() == cbbMapType) {
			if(cbbMapType.getSelectedIndex() == 1) {
				
				radDijkstra.setEnabled(true);
				setEnableMapType(true,true);
			}else {radDijkstra.setEnabled(false);
			setEnableMapType(true,false);
			}
		}

		// select point
		if (e.getSource() == cbbBeginPoint || e.getSource() == cbbEndPoint) {
			actionChoosePoint();
		}
		// select run
/*		if (e.getSource() == btnRunStep) {
			runStep();
		}

		if (e.getSource() == btnRunAll) {
			runAll();
		}
*/
		// input matrix
		if (command == "Ok") {
			processInputMatrix();
		}

		// select menu bar
		if (command == "New") {
			actionNew();
		}
		if (command == "Open" || e.getSource() == btnOpen) {
			actionOpen();
		}
		if (command == "Save" || e.getSource() == btnSave) {
			actionSave();
		}
		if (command == "Exit") {
			System.exit(0);
		}
		if (command == "Information") {
			showInformation();
		}
				// select popup menu
		if (command == "Change cost") {
			showDialogChangeCost();
		}
		if (command == "Delete") {
			showDialogDelete();
		}
		
	}









private void showInformation() {
	// TODO Auto-generated method stub
	
}









private void actionChooseMapDirected() {
	
	
}



}
