package dataLayer.settings;

/**
 * Saves the type of the data layer
 * 
 * @author Rune Krauss
 * 
 */
public class PersistenceSettings {
	/**
	 * Data layer type
	 */
	private String type;

	public String getPersistenceType() {
		return type;
	}

	public void setPersistenceType(String type) {
		this.type = type;
	}
}