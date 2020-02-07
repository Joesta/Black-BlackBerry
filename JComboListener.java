import java.awt.*;
import java.awt.event.*;

public class JComboListener implements ItemListener{
	ApplicationGUI appGUI;
	ButtonListener bl = null;

	public JComboListener(ApplicationGUI appGUI){
		this.appGUI = appGUI;

	}

	public void itemStateChanged(ItemEvent e){
		Object selectedOS = appGUI.osCombo.getSelectedItem().toString();
		//Oject selectedOS = e.getSelectedItem();
		if(selectedOS.toString().contains("Select")){
			appGUI.osOutput.setText("Choose OS to load to your device");
		}else{
			String info = "You have selected to install " + "\n" +
					  "Modules for BlackBerry " + selectedOS.toString().substring(0, 4) + "\n" +
					  "OS Version : " + "\n" + 
					  selectedOS.toString();
					  appGUI.osOutput.setText(info);
		}
		
					  
		
	}
}