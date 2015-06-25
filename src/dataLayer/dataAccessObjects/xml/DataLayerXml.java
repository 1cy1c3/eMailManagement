package dataLayer.dataAccessObjects.xml;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.IEmailKontaktDao;

/**
 * Starts the DaoXml
 * 
 * @author Rune Krauss
 * 
 */
public class DataLayerXml implements IDataLayer {

	@Override
	public IEmailKontaktDao getEmailKontaktDao() {
		EmailKontaktDaoXml ekdx = new EmailKontaktDaoXml();
		return ekdx;
	}
}