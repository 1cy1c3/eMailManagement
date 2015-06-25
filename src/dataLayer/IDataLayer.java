package dataLayer;

import dataLayer.dataAccessObjects.IEmailKontaktDao;

/**
 * Interface for data layer
 * 
 * @author Rune Krauss
 * 
 */
public interface IDataLayer {
	public IEmailKontaktDao getEmailKontaktDao();
}
