package exceptions;

/**
 * If a contact is not found this exception will be activated
 * 
 * @author Rune Krauss
 */
public class NoEmailKontaktFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoEmailKontaktFoundException(String message) {
		super(message);
	}
}
