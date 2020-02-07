/*@Author Joesta aka Skullhack
 *ButtonListener.class
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ButtonListener implements ActionListener{
	
	private JTextArea infoArea = new JTextArea(100, 50);
	private Runtime rt = Runtime.getRuntime();
	private Process  p = null;
	private String appSpecs = "";
	private Scanner sc = null;
	private String input, d_letter;
	static String data = "";
	private char drive_letter;
	private File rimPath = null;
	private final File bin = new File("C:\\engine.exe");
	private final String titleMessage = "Unable To Open Port To The Device!!";
	private final String msg = "NO DEVICE DETECTED. MAKE SURE THAT YOUR BLACKBERRY DEVICE " + "\n" + "IS ATTACHED TO THE USB PORT!!!!";
	private BufferedReader buffReader = null;
	static String[] listOfInstalledOS = null;
	private ApplicationGUI appInstance;
		

	public ButtonListener(ApplicationGUI appInstance){
		super();
		this.appInstance = appInstance;
	}
	
	public void actionPerformed(ActionEvent paramE){


		boolean isDriveLetter = true;
		String removePathQuotes;
		String confirm;
	    String listOfDirs[] = null;
		String listDirs = "";
		String error = "";
		String promptMsg = "Enter the word: BLACKBERRY to continue!";

//------->load modules
		if(paramE.getActionCommand().equals("Load Modules")){

			do
			{
				input = JOptionPane.showInputDialog(null,"Enter the drive letter whose BlackBerry OS is installed","DRIVE LETTER", JOptionPane.PLAIN_MESSAGE);
				drive_letter = input.toUpperCase().charAt(0);


				//checking if drive letter is valid
				if((Character.isLetter(drive_letter) && (!Character.isWhitespace(drive_letter)))){

					d_letter = Character.toString(drive_letter);
					isDriveLetter = true;

					rimPath = new File("\"" + d_letter + ":\\Program Files\\Common Files\\Research In Motion\\Shared\\Loader Files\"");
					removePathQuotes = rimPath.getPath().substring(1, rimPath.toString().length() - 1);
					File _file = new File(removePathQuotes);

					//checking if module folder exists
					if((_file.isDirectory()) && _file.exists()){
						
						appInstance.osCombo.addItem(((Object)"Select Installed OS"));
						//storing list of existing operating system into comboBox
						listOfDirs = _file.list();
						for(String osInstalled : listOfDirs){
							appInstance.osCombo.addItem(getOS(osInstalled));
						}//end


						
						String currentOS = appInstance.osCombo.getSelectedItem().toString();
						if(currentOS.contains("Select")){
							appInstance.osOutput.setText("");
						}else{

							try{
				                                                   
									p = rt.exec("cmd /Q /C cd " + rimPath + " && cd " + currentOS + "\\java" + " && " + bin + " -u load *.cod");				 

									buffReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
									sc = new Scanner(buffReader);

									while(sc.hasNext()){
										data += sc.nextLine() + "\n";
										appInstance.outputArea.append(data);
									}
									//closing streams
									sc.close();
									buffReader.close();

									if(data.equals("")){
										JOptionPane.showMessageDialog(null, msg, titleMessage, JOptionPane.ERROR_MESSAGE);
										sc = new Scanner(p.getErrorStream());
										while(sc.hasNext()){
											error += sc.nextLine() + "\n";
										}
										sc.close();

										appInstance.outputArea.setText(error);
										return;
									}else{
										//appInstance.outputArea.setText(data);
									}
									

						    }catch(Exception e){
									JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR OCCURED WHILE PROCESSING DEVICE", JOptionPane.ERROR_MESSAGE);
						  		}
					      }
						
					   }else{

						      JOptionPane.showMessageDialog(null, " No BlackBerry installation folder was found in drive " + drive_letter +":\\" + "! \n Please Install BlackBerry OS and Retry!" + "\n" +
							  "","NO BLACKBERRY OS WAS FOUND!", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "The drive letter " + "( " + drive_letter + " ) " + " is not valid. PRESS (OK) to RETRY","ERROR", JOptionPane.ERROR_MESSAGE);
					isDriveLetter = false;
				}

			}while(!isDriveLetter);

//--------> wipe device applications
		}else if(paramE.getActionCommand().equals("Wipe dev Apps")){
			//Reminder, validate password prompt
			confirm = JOptionPane.showInputDialog(null, promptMsg, "CONFIRM TO WIPDE DEVICE APPLICATIONS", JOptionPane.WARNING_MESSAGE);
			
			if(confirm.toUpperCase().equals("BLACKBERRY")){
				try{
					p = rt.exec(bin + " -u wipe -a");
					buffReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					sc = new Scanner(buffReader);
					
					while(sc.hasNext()){
						appInstance.outputArea.append(p.getErrorStream() + "\n");
					}
					sc.close();
					buffReader.close();
					
					//checking for valid operation
					if(data.equals("")){
						JOptionPane.showMessageDialog(null, msg, titleMessage, JOptionPane.ERROR_MESSAGE);
						sc = new Scanner(p.getErrorStream());
						while(sc.hasNext()){
							error += sc.nextLine() + "\n";
						}
						sc.close();

						//appInstance.outputArea.setText(error);
					}else{
						//appInstance.outputArea.setText(data);
					}
					

				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR WHILE WIPING DEVICE APPLICATIONS", JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, confirm + " is an invalid input!", "INVALID INPUT ENTERED", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

//--------> reset device to factory
		}else if(paramE.getActionCommand().equals("Reset To Factory")){

			confirm = JOptionPane.showInputDialog(null, promptMsg, "CONFIRM DEVICE FACTORY RESET", JOptionPane.WARNING_MESSAGE);
			if(confirm.toUpperCase().equals("BLACKBERRY")){
				try{

					p = rt.exec(bin + " -u resettofactory");
					buffReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					sc = new Scanner(buffReader);

					while(sc.hasNext()){
						data += sc.nextLine() + "\n";
					}
					sc.close();
					buffReader.close();

					
					//checking for valid operation in data
					if(data.equals("")){
						JOptionPane.showMessageDialog(null, msg, titleMessage, JOptionPane.ERROR_MESSAGE);
						sc = new Scanner(p.getErrorStream());
						while(sc.hasNext()){
							error += sc.nextLine() + "\n";
						}
						sc.close();

						appInstance.outputArea.setText(error);
					}else{
						appInstance.outputArea.setText(data);
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}else{
				JOptionPane.showMessageDialog(null, confirm + " is an invalid input!", "INVALID INPUT ENTERED", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

//---------> wipe file system
		}else if(paramE.getActionCommand().equals("Wipe File System")){

			confirm = JOptionPane.showInputDialog(null, promptMsg, "CONFIRM FILE SYSTEM WIPE", JOptionPane.WARNING_MESSAGE);
			if(confirm.toUpperCase().equals("BLACKBERRY")){
				try{

					p = rt.exec(bin + " -u wipe -f");
					buffReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					sc = new Scanner(buffReader);

					while(sc.hasNext()){
						data += sc.nextLine() + "\n";
					}
					sc.close();
					buffReader.close();

					//checking for valid operation in data
					if(data.equals("")){
						JOptionPane.showMessageDialog(null, msg, titleMessage, JOptionPane.ERROR_MESSAGE);
						sc = new Scanner(p.getErrorStream());

						while(sc.hasNext()){
							error += sc.nextLine() +  "\n";
						}
						sc.close();

						appInstance.outputArea.setText(error);

					}else{
						appInstance.outputArea.setText(data);
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}else{
				JOptionPane.showMessageDialog(null, confirm + " is an invalid input!", "INVALID INPUT ENTERED", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

//---------->take screenshot
		}else if(paramE.getActionCommand().equals("Take Screenshot")){
			
			JOptionPane.showMessageDialog(null, "The default screenshot mode is set to ACTIVE.", "SCREENSHOT MODE", JOptionPane.INFORMATION_MESSAGE);
			String imageName;

			imageName = JOptionPane.showInputDialog(null, "Give your snap a name\n Default naming will be  -> screenshot <- if you continue with the OK Button");
			if(imageName.equals("")){
				try{
					//p = rt.exec("cmd /Q /C " + bin.getName() + " -u screenshot active screenshot.png");
					p = rt.exec(bin + " -u screenshot active screenshot.png");

					JOptionPane.showMessageDialog(null, "Screenshot taken!","DONE", JOptionPane.INFORMATION_MESSAGE);
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());

				}
			}else{

				try{
					//p = rt.exec("cmd /Q /C " + bin.getName() + " -u screenshot active " + imageName +".png");
					p = rt.exec(bin + " -u screenshot active " + imageName + ".png");
					JOptionPane.showMessageDialog(null, "Screenshot taken!","DONE", JOptionPane.INFORMATION_MESSAGE);

				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}

//------------> show information about device
		}else if(paramE.getActionCommand().equals("Device Info")){
			JOptionPane.showMessageDialog(null, "This option lets you view your device infomation including" + "\n" +
				"Hardware ID" + "\n" +
				"PIN (in hexadecimal)" + "\n" +
				"VM Version" + "\n" +
				"Radio ID, Vendor ID, Avtice WAF" + "\n" +
				"OS METRICS etc");
			try{
				p = rt.exec(bin + " -u deviceinfo");
				buffReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				sc = new Scanner(buffReader);

				while(sc.hasNext()){
					data += sc.nextLine() + "\n";
				}
				sc.close();
				buffReader.close();

				if(data.equals("")){
					JOptionPane.showMessageDialog(null, msg, titleMessage, JOptionPane.ERROR_MESSAGE);

					sc = new Scanner(p.getErrorStream());
					while(sc.hasNext()){
						error += sc.nextLine() + "\n";
					}
					sc.close();

					appInstance.outputArea.setText(error);

					return;
				}else{
					appInstance.outputArea.setText(data);
				}

			}catch(Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

//------------> Load JAD files to device
		}else if(paramE.getActionCommand().equals("Load JAD")){
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose JAD File");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File jadFile = chooser.getSelectedFile();
				try{
					p = rt.exec("cmd /Q /C " + bin.getName() + " -u load " + jadFile.getName());

					String name = jadFile.getName().substring(0, jadFile.getName().toString().length() - 4);

				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		 }else if(paramE.getActionCommand().equals("About App")){

		 		 appSpecs = "    Application Requirements" + "\n" +
		 					  "-> JRE version 1.7 or higher" + "\n" +
		 					  " ->  BlackBerry drivers" + "\n" +
		 					  "  ->   Valid OS for your device" + "\n "+
		 					  "--<>><><><><><><>><><><><><>--" + "\n" +
		 					  " <>--MESS with the BEST\n       --_>DIE<_--\n      Like the REST--<>";
		 		 appInstance.osOutput.setText(appSpecs);

		}else if(paramE.getActionCommand().equals("About Author")){
				JFrame a = new JFrame();
				a.setTitle("About Author");
				a.setSize(720, 701);
				a.setLocationRelativeTo(null);
				Icon icon = new ImageIcon(getClass().getResource("/res/img/joesta.jpg"));
				JLabel disp = new JLabel();
				disp.setIcon(icon);
				a.add(disp);
				a.setVisible(true);
				a.setResizable(false);
				
		}
	}

	private Object getOS(final String os){
		return new Object() { 
			public String toString(){
			    return os; 
		   }
	  };
   }
}