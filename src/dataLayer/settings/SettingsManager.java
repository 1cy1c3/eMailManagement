package dataLayer.settings;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Determines the data layer type
 * 
 * @author Rune Krauss
 * 
 */
public class SettingsManager {
	private static SettingsManager instance;
	private PersistenceSettings persistenceSettings;
	private final String xmlFilePath = "raw/pref.xml";

	private SettingsManager() {
	}

	public static SettingsManager getInstance() {
		if (instance == null)
			instance = new SettingsManager();
		System.out.println("Read persistence settings...");
		instance.readPersistenceSettings();
		return instance;
	}

	public String getPersistenceType() {
		return persistenceSettings.getPersistenceType();
	}

	/**
	 * Reads in the pref.xml and iterates it to find out the respective data
	 * layer type
	 * 
	 * @return data layer type
	 */
	private PersistenceSettings readPersistenceSettings() {
		if (persistenceSettings == null)
			persistenceSettings = new PersistenceSettings();
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(new FileInputStream(xmlFilePath));
			doc.getDocumentElement().normalize();
			NodeList nL = doc.getElementsByTagName("persistence");
			for (int i = 0; i < nL.getLength(); i++) {
				Node n = nL.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) n;
					nL = elem.getElementsByTagName("type").item(0)
							.getChildNodes();
					n = (Node) nL.item(0);
					String type = n.getNodeValue();
					if (type.isEmpty()) {
						persistenceSettings.setPersistenceType("sqlite");
					}
					persistenceSettings.setPersistenceType(type);
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
			persistenceSettings.setPersistenceType("sqlite");
		} catch (IOException e) {
			e.printStackTrace();
			persistenceSettings.setPersistenceType("sqlite");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			persistenceSettings.setPersistenceType("sqlite");
		}
		return persistenceSettings;

	}
}
