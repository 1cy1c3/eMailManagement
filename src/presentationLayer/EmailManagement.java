package presentationLayer;

import java.awt.EventQueue;

/**
 * Main class of the program, includes the main method for executing
 * 
 * @author Rune Krauss
 */
public class EmailManagement {

	/**
	 * Program starts here
	 * 
	 * @param args
	 *            Responsible for the command line
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}