package dataLayer;

import dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;
import dataLayer.dataAccessObjects.xml.DataLayerXml;
import dataLayer.settings.SettingsManager;

/**
 * Manages the data layer
 * 
 * @author Rune Krauss
 * 
 */
public class DataLayerManager {
	private static DataLayerManager instance;
	private IDataLayer dataLayer;

	private DataLayerManager() {
	}

	/**
	 * Singleton
	 * @return instance of DataLayerManager
	 */
	public static DataLayerManager getInstance() {
		if (instance == null)
			instance = new DataLayerManager();
		return instance;
	}

	/**
	 * Determines the data layer of the settings in raw/pref.xml
	 * @return respective data layer
	 */
	public IDataLayer getDataLayer() {
		SettingsManager sm = SettingsManager.getInstance();
		if (sm.getPersistenceType().equalsIgnoreCase("sqlite")) {
			System.out.println("Initialize Sqlite Datalayer...");
			DataLayerSqlite dls = new DataLayerSqlite();
			dataLayer = dls;
		} else if (sm.getPersistenceType().equalsIgnoreCase("xml")) {
			System.out.println("Initialize Xml Datalayer...");
			DataLayerXml dls = new DataLayerXml();
			dataLayer = dls;
		}
		return dataLayer;
	}
}
