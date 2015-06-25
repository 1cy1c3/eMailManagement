package exceptions;

/**
 * If the previous contact is not found this exception will be activated
 * 
 * @author Rune Krauss
 */
public class NoPreviousEmailKontaktFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoPreviousEmailKontaktFoundException(String message) {
		super(message);
	}
}
