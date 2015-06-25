package dataLayer.dataAccessObjects.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
public class EmailKontaktDaoSqlite implements IEmailKontaktDao {
	private static String CLASSNAME = "org.sqlite.JDBC";
	private static String CONNECTIONSTRING = "jdbc:sqlite:"
			+ "email_kontakt.db";
	private static Connection con;


	/**
	 * Initializes connection to sqlite
	 * @return connection
	 * @throws SQLException if class was not found
	 */
	private Connection initDBConnection() throws SQLException {
		try {
			Class.forName(CLASSNAME);
			con = DriverManager.getConnection(CONNECTIONSTRING);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	@Override
	public IEmailKontakt create() {
		EmailKontakt ek = new EmailKontakt();
		return ek;
	}

	@Override
	public void delete(IEmailKontakt emailKontakt) {
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement("DELETE FROM Kontakt WHERE KontaktId = ?");
			ps.setInt(1, emailKontakt.getId());
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IEmailKontakt first() throws NoEmailKontaktFoundException {
		System.out.println("Display first contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		Statement stmt = null;
		EmailKontakt ek = new EmailKontakt();
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Kontakt (KontaktId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Vorname VARCHAR(32) NOT NULL, Nachname VARCHAR(32) NOT NULL, Email VARCHAR(64) NOT NULL)");
			rs = stmt
					.executeQuery("SELECT * FROM Kontakt ORDER BY KontaktId ASC LIMIT 1");
			if (rs.next()) {
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
			} else {
				throw new NoEmailKontaktFoundException("Kein Kontakt gefunden!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ek;
	}

	@Override
	public IEmailKontakt last() throws NoEmailKontaktFoundException {
		System.out.println("Display last contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		Statement stmt = null;
		EmailKontakt ek = new EmailKontakt();
		try {
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM Kontakt ORDER BY KontaktId DESC LIMIT 1");
			if (rs.next()) {
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
			} else {
				throw new NoEmailKontaktFoundException("Kein Kontakt gefunden!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ek;
	}

	@Override
	public IEmailKontakt next(IEmailKontakt emailKontakt)
			throws NoNextEmailKontaktFoundException {
		System.out.println("Display next contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		EmailKontakt ek = new EmailKontakt();
		try {
			ps = con.prepareStatement("SELECT * FROM Kontakt WHERE KontaktId > ? LIMIT 1");
			ps.setInt(1, emailKontakt.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
			} else {
				throw new NoNextEmailKontaktFoundException(
						"Kein weiterer Kontakt gefunden!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ek;
	}

	@Override
	public IEmailKontakt previous(IEmailKontakt emailKontakt)
			throws NoPreviousEmailKontaktFoundException {
		System.out.println("Display previous contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		EmailKontakt ek = new EmailKontakt();
		try {
			ps = con.prepareStatement("SELECT * FROM Kontakt WHERE KontaktId < ? ORDER BY KontaktId DESC LIMIT 1");
			ps.setInt(1, emailKontakt.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
			} else {
				throw new NoPreviousEmailKontaktFoundException(
						"Kein weiterer Kontakt gefunden!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ek;
	}

	/**
	 * If id == -1 of the email contact then insert else update
	 */
	@Override
	public void save(IEmailKontakt emailKontakt) {
		System.out.println("Save dataset...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			if (emailKontakt.getId() < 0) {
				ps = con.prepareStatement("INSERT INTO Kontakt (Vorname, Nachname, Email) VALUES(?, ?, ?)");
				ps.setString(1, emailKontakt.getVorname());
				ps.setString(2, emailKontakt.getNachname());
				ps.setString(3, emailKontakt.getEmail());
				ps.executeUpdate();
				con.commit();
				rs = ps.getGeneratedKeys();
				((EmailKontakt) emailKontakt).setId(rs.getInt(1));
				rs.close();
			} else {
				ps = con.prepareStatement("UPDATE Kontakt SET Vorname = ?, Nachname = ?, Email = ? WHERE KontaktId = ?");
				ps.setString(1, emailKontakt.getVorname());
				ps.setString(2, emailKontakt.getNachname());
				ps.setString(3, emailKontakt.getEmail());
				ps.setInt(4, emailKontakt.getId());
				ps.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<IEmailKontakt> select() {
		System.out.println("Search contact contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		Statement stmt = null;
		List<IEmailKontakt> ekl = new ArrayList<IEmailKontakt>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Kontakt");
			while (rs.next()) {
				EmailKontakt ek = new EmailKontakt();
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
				ekl.add(ek);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ekl;
	}

	@Override
	public IEmailKontakt select(int id) throws NoEmailKontaktFoundException {
		System.out.println("Search contact...");
		Connection con = null;
		try {
			con = initDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		EmailKontakt ek = new EmailKontakt();
		try {
			ps = con.prepareStatement("SELECT * FROM Kontakt WHERE KontaktId = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ek.setId(rs.getInt("KontaktId"));
				ek.setVorname(rs.getString("Vorname"));
				ek.setNachname(rs.getString("Nachname"));
				ek.setEmail(rs.getString("Email"));
			} else {
				throw new NoEmailKontaktFoundException(
						"Der Kontakt wurde nicht gefunden!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ek;
	}
}
