package exceptions;

/**
 * If the next contact is not found this exception will be activated
 * 
 * @author Rune Krauss
 */
public class NoNextEmailKontaktFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoNextEmailKontaktFoundException(String message) {
		super(message);
	}
}
