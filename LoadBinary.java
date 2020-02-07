/*@Author Joesta
 *LoadBinary.class
 */

import java.io.*;
import javax.swing.JOptionPane;

final public class LoadBinary{

	public LoadBinary(){
		super();
	}

	public final void loadBin(){

		OutputStream os = null;
		final int offset = 0;
		File file = null;
		final InputStream in = LoadBinary.class.getClassLoader().getResourceAsStream("res/binary/JavaLoader.exe");
		try{
			os = new FileOutputStream("C:\\engine.exe");
			file = new File("C:\\engine.exe");

			final byte[] readBytes = new byte[2048];

			int len = in.read(readBytes);
			while(len != -1){
				os.write(readBytes, offset, len);
				len = in.read(readBytes);
			}

		}catch(IOException io){
			JOptionPane.showMessageDialog(null, "Sorry! An error occured while writting the binary file!" + "\n" +
				" if you are running Windows 7, 8+ or 10 - Run this program as"  + "\n" +
				" an Administrator.", "COULD NOT WRITE BINIRAY FILE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		//closing stream resources
		finally{
			if(os != null){
				try{
					os.close();
					in.close();
					file.deleteOnExit();

				}catch(IOException io_1){
					JOptionPane.showMessageDialog(null, "Error closing stream resources", "ERROR OCCURED",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}