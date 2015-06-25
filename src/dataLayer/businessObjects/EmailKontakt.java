package dataLayer.businessObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import businessObjects.IEmailKontakt;

/**
 * Bean for respective business objects
 * 
 * @author Rune Krauss
 * 
 */
@XmlRootElement(name = "EmailKontakt")
public class EmailKontakt implements IEmailKontakt {
	
	private int id = -1;
	private String vorname;
	private String nachname;
	private String email;

	@Override
	@XmlElement(name="id")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	@XmlElement(name="vorname")
	public String getVorname() {
		return this.vorname;
	}

	@Override
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	@Override
	@XmlElement(name="nachname")
	public String getNachname() {
		return this.nachname;
	}

	@Override
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Override
	@XmlElement(name="email")
	public String getEmail() {
		return this.email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

}