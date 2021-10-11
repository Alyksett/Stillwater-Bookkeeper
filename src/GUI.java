import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	static JComboBox dateChooser = new JComboBox();
	
	static JComboBox artistChooser = new JComboBox();
	static JTextField checkNumberField = new JTextField();
	static String date;
	static String name;
	//buttons and such
	private JButton importButton = new JButton("Import File");
	private JButton exportBigWorkbookButton = new JButton("Export Selected Date");
	private JButton displayInputButton = new JButton("Display File");
	private JButton artistTotalButton = new JButton("Get Artist Total");
	private JButton updateCheckNoButton = new JButton("Set Check No. For Artist");	
	
	// creating console
	static JTextArea console = new JTextArea();
	static JScrollPane scroll = new JScrollPane(console);
	
	public static String selectedDate = null;
	public static String selectedName = null;

	//Constructor
	public GUI() {
		setTitle("Stillwater Art Guild Gallery Book Keeper");
		setSize(400,450);
		setLocation(new Point(300,200));
		
		
		setLayout(null);    
		setResizable(true);		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		initComponents(); 
		initEvent();
		
		if(Controller.getDates().length != 0){
			updateDateBox();
			initArtistChooser();
			selectedDate = Controller.getDates()[0].toString();
			selectedName = Controller.getArtistNames(selectedDate)[0].toString();
		}
	}

	public void initComponents(){
		/**
		 * Changing the bounds and setting location for each button
		 * first 2 params is the location in the frame, last 2 are the dimensions
		 * 
		 * Default button height = 25 pixels
		 */

		importButton.setBounds(10, 10, 175, 25);
		exportBigWorkbookButton.setBounds(10, 45, 175, 25);
		dateChooser.setBounds(200, 10, 175, 25);
		displayInputButton.setBounds(200, 45, 173, 25);
		artistChooser.setBounds(10, 90, 175, 25);
		artistTotalButton.setBounds(200, 90, 175, 25);
		checkNumberField.setBounds(10, 125, 175, 25);
		updateCheckNoButton.setBounds(200, 125, 175, 25);
		
		
		add(importButton);
		add(exportBigWorkbookButton);
		add(dateChooser);
		
		add(displayInputButton);
		
		add(artistChooser);
		add(artistTotalButton);
		add(checkNumberField);
		add(updateCheckNoButton);
		
		add(scroll);
		scroll.setBounds(0, 160, 385, 250);
		console.setEditable(false);
	}



	/*
	* Listeners
	*/
	
	public void initEvent(){
		// When the window closes, save the file and exit the application
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					Controller.saveFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(1);
			}
		});
		//Importing a workbook
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportFile.importWorkbook();
			}
		});
		//If the user highlights a new date, then update the currently selected date
		dateChooser.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					selectedDate = dateChooser.getSelectedItem().toString();
					updateArtistChooser();
				}
			}
		});

		//If the user highlights a new name, then update the currently selected name
		artistChooser.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					selectedName = artistChooser.getSelectedItem().toString();
					
				}
			}
		});		

		//Export workbook
		exportBigWorkbookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Controller.exportBigWorkbook();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		// Listener for getting the artists total
		//todo fix this, display the artist totals for a given date.
		artistTotalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendConsoleText(Controller.retrieveArtistTotal(selectedDate, selectedName));
			}
		});
		// Listener for displaying the pay period
		displayInputButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Controller.displayInput();
			}
		});
		
		// Listener for adding check number
		updateCheckNoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int number = Integer.parseInt(checkNumberField.getText());
				Controller.updateArtistCheckNo(selectedDate, selectedName, number);
			}
		});
	}
	public static void initArtistChooser(){
		Object[] names = Controller.initArtistNames();
		artistChooser.setModel(new DefaultComboBoxModel(names));
	}
	public static void updateArtistChooser(){
		//need to pass an actual value
		Object[] names = Controller.getArtistNames(selectedDate);
		artistChooser.setModel(new DefaultComboBoxModel(names));
	}

	//for initializing the date box with the database keys(months)
	public void updateDateBox(){
		Object[] dates = Controller.getDates();
		dateChooser.setModel(new DefaultComboBoxModel(dates));
	}
	//For adding a single date to the date chooser
	public static void addDate(String s){
		dateChooser.addItem(s);
	}

	public static String getDate(){
		return selectedDate;
	}

	public static void appendConsoleText(String text) {
		console.append(text);
	}

	public static String getTheText() {
		String text = checkNumberField.getText();
		return text;
	}
}
