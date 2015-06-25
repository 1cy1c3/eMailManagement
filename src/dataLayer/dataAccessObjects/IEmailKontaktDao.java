package dataLayer.dataAccessObjects;

import java.util.List;

import exceptions.NoEmailKontaktFoundException;
import exceptions.NoNextEmailKontaktFoundException;
import exceptions.NoPreviousEmailKontaktFoundException;
import businessObjects.IEmailKontakt;

/**
 * Interface for declaring the methods of a persistence
 * 
 * @author Rune Krauss
 * 
 */
public interface IEmailKontaktDao {
	/**
	 * Creates a business object
	 * 
	 * @return business object
	 */
	public IEmailKontakt create();

	/**
	 * Deletes a business object
	 * 
	 * @param emailKontakt
	 */
	public void delete(IEmailKontakt emailKontakt);

	/**
	 * Searches the first business object
	 * 
	 * @return business object
	 * @throws NoEmailKontaktFoundException
	 *             if no email contact was found
	 */
	public IEmailKontakt first() throws NoEmailKontaktFoundException;

	/**
	 * Searches the last business object
	 * 
	 * @return business object
	 * @throws NoEmailKontaktFoundException
	 *             if no email contact was found
	 */
	public IEmailKontakt last() throws NoEmailKontaktFoundException;

	/**
	 * Searches the next business object
	 * 
	 * @param emailKontakt
	 * @return business object
	 * @throws NoNextEmailKontaktFoundException
	 *             if no next email contact was found
	 */
	public IEmailKontakt next(IEmailKontakt emailKontakt)
			throws NoNextEmailKontaktFoundException;

	/**
	 * Searches the previous business object
	 * 
	 * @param emailKontakt
	 * @return business object
	 * @throws NoPreviousEmailKontaktFoundException
	 *             if no previous email contact was found
	 */
	public IEmailKontakt previous(IEmailKontakt emailKontakt)
			throws NoPreviousEmailKontaktFoundException;

	/**
	 * Saves a business object
	 * 
	 * @param emailKontakt
	 */
	public void save(IEmailKontakt emailKontakt);

	/**
	 * Selects business objects
	 * 
	 * @return business object
	 */
	public List<IEmailKontakt> select();

	/**
	 * Selects a business object
	 * 
	 * @param id
	 * @return business object
	 * @throws NoEmailKontaktFoundException
	 *             if no email contact was found
	 */
	public IEmailKontakt select(int id) throws NoEmailKontaktFoundException;
}
