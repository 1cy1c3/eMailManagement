package dataLayer.dataAccessObjects.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dataLayer.businessObjects.EmailKontakt;

/**
 * Contains a list of all elements in email-contacts.xml
 * 
 * @author Rune Krauss
 * 
 */
@XmlRootElement(name = "emailcontacts")
public class EmailKontaktList {

	private ArrayList<EmailKontakt> contactList;

	public EmailKontaktList() {
		contactList = new ArrayList<EmailKontakt>();
	}

	@XmlElementWrapper(name = "data")
	@XmlElement(name = "EmailKontakt")
	public ArrayList<EmailKontakt> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<EmailKontakt> contactList) {
		this.contactList = contactList;
	}

}
