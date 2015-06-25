package dataLayer.dataAccessObjects.sqlite;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.IEmailKontaktDao;

/**
 * Starts the DaoSqlite
 * 
 * @author Rune Krauss
 * 
 */
public class DataLayerSqlite implements IDataLayer {

	@Override
	public IEmailKontaktDao getEmailKontaktDao() {
		EmailKontaktDaoSqlite ekds = new EmailKontaktDaoSqlite();
		return ekds;
	}

}
