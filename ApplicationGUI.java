/*
 *@Author Joesta
 *Purpose	: Fix BlackBerry Issues
 *AppName	: Black BlackBerry 
 *Black_BlackBerry.class
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class ApplicationGUI extends JFrame{

	ButtonListener buttonListener = new ButtonListener(this);
	JComboListener comboListener = new JComboListener(this);
	private final LoadBinary loadBinary = new LoadBinary();

	//panels
	private JPanel pnlMain;
	private JPanel pnlOperations;
	private JPanel pnlButtonInfo;
	private JPanel pnlButtonsOperation;
	private JPanel pnlComboBox;

	//text area
	JTextArea outputArea;
	JTextArea osOutput;

	//combo box
	JComboBox osCombo;

	//1st column - buttons
	private JButton btnAboutApp;
	private JButton btnAboutAuthor;
	private JButton btnLoadModules;
	private JButton btnWipeApps;
	private JButton btnResetToFactory;
	private JButton btnScreenshot;

	//2nd column - buttons
	private JButton btnWipeFileSystem;
	private JButton btnDeviceInfo;
	private JButton btnLoadJad;
	private JButton btnRadioUse;

	//scrollpane
	private JScrollPane scrollPane;

	//font
	private Font localFont = new Font("Courier new", Font.PLAIN , 15);
	private Font localFont2 = new Font("Courier new", Font.PLAIN , 12);
	//title border
	private TitledBorder infoBorder;
	private TitledBorder oprButtonBorder;
	private TitledBorder installedOSBorder;
	private TitledBorder osSelectionBorder;

	//frame icon
	private final ImageIcon skull = new ImageIcon(getClass().getResource("/res/img/skull2.gif"));

	//label
	private final JLabel iconDisp = new JLabel();
	//private String[] os = new String[4];

	public ApplicationGUI(){
		//setting frame preferrances
		super("Black_BlackBerry | bY skullhack");
		setSize(609, 596);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setResizable(false);

		//title border
		infoBorder = new TitledBorder("[Output Information]");
		infoBorder.setTitleFont(localFont);
		//instanciating main panel
		pnlMain = new JPanel();
		GridLayout gridLayout = new GridLayout(1, 2, 5, 5);
		pnlMain.setLayout(gridLayout);

		//instanciating info panel buttons
		btnAboutApp 	= new JButton("About App");
		btnAboutAuthor 	= new JButton("About Author");
		btnAboutApp.setPreferredSize(new Dimension(146, 20));
		btnAboutAuthor.setPreferredSize(new Dimension(146, 20));
		btnAboutApp.setFont(localFont);
		btnAboutAuthor.setFont(localFont);

		//instanciating action button panel for 1st column
		btnLoadModules 		= new JButton("Load Modules");
		btnWipeApps			= new JButton("Wipe dev Apps");
		btnResetToFactory 	= new JButton("Reset To Factory");
		btnScreenshot 		= new JButton("Take Screenshot");

		//instaciating acttion button panel for 2nd column
		btnWipeFileSystem 	= new JButton("Wipe File System");
		btnDeviceInfo		= new JButton("Device Info");
		btnLoadJad 			= new JButton("Load JAD");
		btnRadioUse 		= new JButton("Radio Use ON/OFF");

		//title border for operation buttons
		oprButtonBorder = new TitledBorder("Operations");
		oprButtonBorder.setTitleFont(localFont);

		pnlButtonsOperation = new JPanel();
		pnlButtonsOperation.setBackground(new Color(0, 120, 120));
		pnlButtonsOperation.setBorder(oprButtonBorder);
		pnlButtonsOperation.setLayout(new GridLayout(4, 1, 5, 5));
		pnlButtonsOperation.add(btnLoadModules);
		pnlButtonsOperation.add(btnWipeFileSystem);
		pnlButtonsOperation.add(btnWipeApps);
		pnlButtonsOperation.add(btnResetToFactory);
		pnlButtonsOperation.add(btnLoadJad);
		pnlButtonsOperation.add(btnScreenshot);
		pnlButtonsOperation.add(btnDeviceInfo);
		pnlButtonsOperation.add(btnRadioUse);

		//registering buttons to listerners
		btnAboutApp.addActionListener(buttonListener);
		btnAboutAuthor.addActionListener(buttonListener);
		btnLoadModules.addActionListener(buttonListener);
		btnWipeApps.addActionListener(buttonListener);
		btnResetToFactory.addActionListener(buttonListener);
		btnScreenshot.addActionListener(buttonListener);
		btnWipeFileSystem.addActionListener(buttonListener);
		btnDeviceInfo.addActionListener(buttonListener);
		btnLoadJad.addActionListener(buttonListener);
		btnRadioUse.addActionListener(buttonListener);

		
		//setting new dimensions to operable buttons
		btnLoadModules.setPreferredSize(btnAboutApp.getPreferredSize());
		btnLoadModules.setFont(localFont2);
		btnWipeFileSystem.setPreferredSize(btnAboutApp.getPreferredSize());
		btnWipeFileSystem.setFont(localFont2);
		btnResetToFactory.setPreferredSize(btnAboutApp.getPreferredSize());
		btnResetToFactory.setFont(localFont2);
		btnScreenshot.setPreferredSize(btnAboutApp.getPreferredSize());
		btnScreenshot.setFont(localFont2);
		btnDeviceInfo.setPreferredSize(btnAboutApp.getPreferredSize());
		btnDeviceInfo.setFont(localFont2);
		btnRadioUse.setPreferredSize(btnAboutApp.getPreferredSize());
		btnRadioUse.setFont(localFont2);
		btnLoadJad.setPreferredSize(btnAboutApp.getPreferredSize());
		btnLoadJad.setFont(localFont2);
		btnWipeApps.setPreferredSize(btnAboutApp.getPreferredSize());
		btnWipeApps.setFont(localFont2);

		//adding buttons to respective panels
		pnlButtonInfo = new JPanel();
		pnlButtonInfo.setBackground(new Color(0, 51, 0));
		pnlButtonInfo.add(btnAboutApp);
		pnlButtonInfo.add(btnAboutAuthor);

		//operations panel
		pnlOperations = new JPanel();
		pnlOperations.setBackground(Color.BLACK);
		pnlOperations.add(pnlButtonInfo);

		//instanciating text area
		outputArea = new JTextArea(600, 590);
		outputArea.setBackground(new Color(238, 238, 238));
		outputArea.setFont(localFont);
		outputArea.setBorder(infoBorder);
		outputArea.setToolTipText("Output: verbosity of current process of modules being loaded to device");
		outputArea.setLineWrap(true);
		outputArea.setEditable(false);

		//scrollpane
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(outputArea);

		//os selection border
		osSelectionBorder = new TitledBorder("Select OS Version");
		osSelectionBorder.setTitleFont(localFont);

		//comboxBox panel
		pnlComboBox = new JPanel();
		pnlComboBox.setBackground(new Color(0, 125, 125));
		pnlComboBox.setBorder(osSelectionBorder);
		
		//combox box
		osCombo = new JComboBox();
		osCombo.setFont(localFont);
		osCombo.addItemListener(comboListener);
		pnlComboBox.add(osCombo);
		osCombo.setPreferredSize(new Dimension(292, 22));
		pnlOperations.add(pnlComboBox, BorderLayout.NORTH);



		//label
		osOutput = new JTextArea();
		osOutput.setLineWrap(true);
		osOutput.setBackground(new Color(192, 192, 192));
		osOutput.setFont(localFont);
		osOutput.setPreferredSize(new Dimension(292, 170));
		osOutput.setEditable(false);
		pnlOperations.add(osOutput);

		pnlOperations.add(pnlButtonsOperation);

		
		iconDisp.setIcon(skull);
		pnlOperations.add(iconDisp);

		//adding panels to main panel
		pnlMain.add(pnlOperations);
		pnlMain.add(scrollPane);
		
		getContentPane().add(pnlMain);
		this.setVisible(true);

		loadBinary.loadBin();
	}
}