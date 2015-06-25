package presentationLayer;

import java.awt.event.WindowEvent;

/**
 * Class for better controlling the window switches
 * 
 * @author Rune Krauss
 * 
 */
public class WindowAdapter extends java.awt.event.WindowAdapter {
	private JFrame frame = null;
	
	/**
	 * Initializes the current frame
	 * @param frame Current frame of the application
	 */
	public WindowAdapter(JFrame frame) {
		this.frame = frame;
	}
	
	public void windowActivated(WindowEvent e) {
		System.out.println("Die Anwendung wurde aktiviert...");
	}
	public void windowClosed(WindowEvent e) {
		System.out.println("Die Anwendung wurde geschlossen...");
	}
	public void windowClosing(WindowEvent e) {
		System.out.println("Die Anwendung wird geschlossen...");
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent e) {
		System.out.println("Die Anwendung wurde deaktiviert...");
	}
	public void windowDeiconified(WindowEvent e) {
		System.out.println("Die Anwendung wurde minimiert...");
	}
	public void windowIconified(WindowEvent e) {
		System.out.println("Die Anwendung wurde maximiert...");
	}
	public void windowOpened(WindowEvent e) {
		System.out.println("Die Anwendung wurde ge\u00F6ffnet...");
	}
}
