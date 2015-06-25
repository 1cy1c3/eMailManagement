package businessObjects;

/**
 * Interface for email contacts
 *
 * @author Rune Krauss
 */
public interface IEmailKontakt {
	public int getId();

	public String getVorname();

	public void setVorname(String vorname);

	public String getNachname();

	public void setNachname(String nachname);

	public String getEmail();

	public void setEmail(String email);
}
