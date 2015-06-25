package dataLayer.dataAccessObjects.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Binder;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import businessObjects.IEmailKontakt;
import dataLayer.businessObjects.EmailKontakt;
import dataLayer.dataAccessObjects.IEmailKontaktDao;
import exceptions.NoEmailKontaktFoundException;
import exceptions.NoNextEmailKontaktFoundException;
import exceptions.NoPreviousEmailKontaktFoundException;

/**
 * Handles the data layer and provides the CRUD - Operations
 * 
 * @author Rune Krauss
 * 
 */
public class EmailKontaktDaoXml implements IEmailKontaktDao {

	private static final String EMAILCONTACTS_XML = "email-contacts.xml";

	/**
	 * If EmailKontatkList does not exists then creates it, read out
	 * email-contacts.xml
	 * 
	 * @return EmailKontatkList
	 */
	private EmailKontaktList readContacts() {
		JAXBContext context = null;
		if (!new File(EMAILCONTACTS_XML).exists())
			return new EmailKontaktList();
		try {
			context = JAXBContext.newInstance(EmailKontaktList.class);
			Unmarshaller um = context.createUnmarshaller();
			EmailKontaktList emailContacts = (EmailKontaktList) um
					.unmarshal(new File(EMAILCONTACTS_XML));
			return emailContacts;
		} catch (JAXBException e) {
			e.printStackTrace();
			return new EmailKontaktList();
		}
	}

	@Override
	public IEmailKontakt create() {
		EmailKontakt ek = new EmailKontakt();
		return ek;
	}

	@Override
	public void delete(IEmailKontakt emailKontakt) {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(EmailKontaktList.class);
			EmailKontaktList emailContacts = readContacts();
			for (int i = 0; i < emailContacts.getContactList().size(); i++) {
				if (emailContacts.getContactList().get(i).getId() == emailKontakt
						.getId())
					emailContacts.getContactList().remove(i);
			}

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(emailContacts, new File(EMAILCONTACTS_XML));

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public IEmailKontakt first() throws NoEmailKontaktFoundException {
		EmailKontaktList emailContacts = readContacts();
		if (emailContacts.getContactList().size() == 0) {
			throw new NoEmailKontaktFoundException("Kein Kontakt gefunden!");
		} else
			return emailContacts.getContactList().get(0);
	}

	@Override
	public IEmailKontakt last() throws NoEmailKontaktFoundException {
		EmailKontaktList emailContacts = readContacts();
		if (emailContacts.getContactList().size() == 0) {
			throw new NoEmailKontaktFoundException("Kein Kontakt gefunden!");
		} else
			return emailContacts.getContactList().get(
					emailContacts.getContactList().size() - 1);
	}

	@Override
	public IEmailKontakt next(IEmailKontakt emailKontakt)
			throws NoNextEmailKontaktFoundException {
		EmailKontaktList emailContacts = readContacts();
		if (emailContacts.getContactList().size() == 0) {
			throw new NoNextEmailKontaktFoundException("Kein Kontakt gefunden!");
		} else {
			EmailKontakt ek = new EmailKontakt();
			for (EmailKontakt contact : emailContacts.getContactList()) {
				if (contact.getId() > emailKontakt.getId()
						&& (ek.getId() < emailKontakt.getId()))
					ek = contact;
			}
			if (ek.getId() == -1) {
				throw new NoNextEmailKontaktFoundException(
						"Kein Kontakt gefunden!");
			}
			return ek;
		}
	}

	@Override
	public IEmailKontakt previous(IEmailKontakt emailKontakt)
			throws NoPreviousEmailKontaktFoundException {
		EmailKontaktList emailContacts = readContacts();
		if (emailContacts.getContactList().size() == 0) {
			throw new NoPreviousEmailKontaktFoundException(
					"Kein Kontakt gefunden!");
		} else {
			EmailKontakt ek = new EmailKontakt();
			for (EmailKontakt contact : emailContacts.getContactList()) {
				if (contact.getId() < emailKontakt.getId()
						&& ek.getId() < emailKontakt.getId()) {
					ek = contact;
				}
				if (ek.getId() == -1)
					throw new NoPreviousEmailKontaktFoundException(
							"Kein Kontakt gefunden!");
			}
			return ek;
		}
	}

	/**
	 * If id == -1 of the email contact then insert else update
	 */
	@Override
	public void save(IEmailKontakt emailKontakt) {
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(EmailKontaktList.class);
			EmailKontaktList emailContacts = readContacts();

			EmailKontakt ek = (EmailKontakt) emailKontakt;

			List<Integer> list = new ArrayList<Integer>();
			int id = 0;

			if (emailKontakt.getId() < 0) {
				for (int i = 0; i < emailContacts.getContactList().size(); i++) {
					list.add(emailContacts.getContactList().get(i).getId());
				}
				if (emailContacts.getContactList().size() > 0)
					id = Collections.max(list) + 1;
				ek.setId(id);
				emailContacts.getContactList().add(ek);

				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				m.marshal(emailContacts, new File(EMAILCONTACTS_XML));
			} else {
				Document doc = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse("email-contacts.xml");
				Binder<Node> binder = context.createBinder();
				binder.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				Node xmlNode = doc.getDocumentElement();
				emailContacts = (EmailKontaktList) binder.updateJAXB(xmlNode);

				for (int i = 0; i < emailContacts.getContactList().size(); i++) {
					if (emailContacts.getContactList().get(i).getId() == emailKontakt
							.getId())
						id = i;
				}

				emailContacts.getContactList().get(id)
						.setEmail(emailKontakt.getEmail());
				emailContacts.getContactList().get(id)
						.setVorname(emailKontakt.getVorname());
				emailContacts.getContactList().get(id)
						.setNachname(emailKontakt.getNachname());

				xmlNode = binder.updateXML(emailContacts);
				doc.setNodeValue(xmlNode.getNodeValue());

				Transformer t = TransformerFactory.newInstance()
						.newTransformer();
				t.transform(new DOMSource(doc), new StreamResult(new File(
						EMAILCONTACTS_XML)));
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<IEmailKontakt> select() {
		EmailKontaktList emailContacts = readContacts();
		List<IEmailKontakt> list = new ArrayList<IEmailKontakt>(emailContacts
				.getContactList().size());
		for (EmailKontakt contact : emailContacts.getContactList()) {
			list.add(contact);
		}
		return list;
	}

	@Override
	public IEmailKontakt select(int id) throws NoEmailKontaktFoundException {
		EmailKontaktList emailContacts = readContacts();
		for (EmailKontakt contact : emailContacts.getContactList()) {
			if (contact.getId() == id)
				return contact;
		}
		throw new NoEmailKontaktFoundException("Kein Kontakt gefunden!");
	}

}
