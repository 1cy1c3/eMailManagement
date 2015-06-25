package presentationLayer;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import businessObjects.IEmailKontakt;
import dataLayer.DataLayerManager;
import dataLayer.dataAccessObjects.IEmailKontaktDao;
import exceptions.NoEmailKontaktFoundException;
import exceptions.NoNextEmailKontaktFoundException;
import exceptions.NoPreviousEmailKontaktFoundException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for drawing the GUI and implementing application code / listener
 * methods
 * 
 * @author Rune Krauss
 * 
 */
public class JFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private IEmailKontaktDao ekd;
	private IEmailKontakt ek;

	private JPanel contentPane;
	private String iconPath = "res/email.png";
	/**
	 * Text field for first name
	 */
	private JTextField textField;
	/**
	 * Text field for surname
	 */
	private JTextField textField_1;
	/**
	 * Text field for email
	 */
	private JTextField textField_2;
	/**
	 * Text field for KontaktId
	 */
	private JTextField textField_3;
	private JButton button;

	/**
	 * Launch the application
	 */
	public JFrame() {
		DataLayerManager dlm = DataLayerManager.getInstance();
		ekd = dlm.getDataLayer().getEmailKontaktDao();
		drawGUI();
		displayFirst();
	}

	public JFrame(String title) {
		super(title);
	}

	/**
	 * Refreshes the text fields with current data
	 * 
	 * @param contact
	 *            Current contact
	 */
	private void refresh(IEmailKontakt contact) {
		ek = contact;
		if (ek == null) {
			textField.setText("");
			textField_1.setText("");
			textField_2.setText("");
			textField_3.setText("");
			ek = ekd.create();
		} else {
			textField.setText(ek.getVorname());
			textField_1.setText(ek.getNachname());
			textField_2.setText(ek.getEmail());
			textField_3.setText(ek.getId() + "");
		}
	}

	/**
	 * Terminates the application
	 */
	private void beenden() {
		int response = JOptionPane.showConfirmDialog(null,
				"M\u00F6chten Sie das Programm wirklich beenden?");
		if (response == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Opens a new dialog for creating a new contact
	 */
	private void newContact() {
		final JDialog dialog = new JDialog();
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setTitle("Neuen Kontakt erstellen");
		dialog.setBounds(this.getLocationOnScreen().x + 350,
				this.getLocationOnScreen().y + 80, 230, 180);
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblVorname = new JLabel("Vorname");
		contentPanel.add(lblVorname);

		final JTextField textField = new JTextField();
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblNachname = new JLabel("Nachname");
		contentPanel.add(lblNachname);

		final JTextField textField_1 = new JTextField();
		contentPanel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		contentPanel.add(lblEmail);

		final JTextField textField_2 = new JTextField();
		contentPanel.add(textField_2);
		textField_2.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Speichern");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				Matcher m = p.matcher(textField_2.getText().trim());
				if (!(textField.getText().isEmpty())
						&& !(textField_1.getText().isEmpty())
						&& !(textField_2.getText().isEmpty())
						&& m.matches()) {
					ek = ekd.create();
					ek.setVorname(textField.getText().trim());
					ek.setNachname(textField_1.getText().trim());
					ek.setEmail(textField_2.getText().trim());
					ekd.save(ek);
					displayLast();
				} else if ((textField.getText().isEmpty())
						|| (textField_1.getText().isEmpty())
						|| (textField_2.getText().isEmpty())
						|| !m.matches()) {
					JOptionPane.showMessageDialog(null,
							"Die Felder wurden nicht korrekt ausgef\u00FCllt!");
				}
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		buttonPane.add(cancelButton);
		dialog.setVisible(true);
	}

	/**
	 * Saves a contact
	 */
	private void saveContact() {
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(textField_2.getText().trim());
		if (ek != null && !(textField.getText().isEmpty())
				&& !(textField_1.getText().isEmpty())
				&& !(textField_2.getText().isEmpty())
				&& m.matches()) {
			int response = JOptionPane.showConfirmDialog(this,
					"M\u00F6chten Sie die Daten wirklich \u00FCbernehmen?");
			if (response == JOptionPane.YES_OPTION) {
				ek.setVorname(textField.getText().trim());
				ek.setNachname(textField_1.getText().trim());
				ek.setEmail(textField_2.getText().trim());
				ekd.save(ek);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Die Felder wurden nicht korrekt ausgef\u00FCllt!");
		}
	}

	/**
	 * Deletes a contact
	 */
	private void deleteContact() {
		if (ek != null) {
			int response = JOptionPane.showConfirmDialog(this,
					"M\u00F6chten Sie den Kontakt wirklich l\u00F6schen?");
			if (response == JOptionPane.YES_OPTION) {
				ekd.delete(ek);
				displayFirst();
			}
		}
	}

	/**
	 * Searches a contact
	 */
	private void searchContact() {
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Kontakt suchen");
		dialog.setResizable(false);
		dialog.setBounds(this.getLocationOnScreen().x + 350,
				this.getLocationOnScreen().y - 40, 250, 110);
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblKontaktid = new JLabel("Kontaktnummer");
		contentPanel.add(lblKontaktid);

		final JTextField textField = new JTextField();
		contentPanel.add(textField);
		textField.setColumns(3);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refresh(ekd.select(Integer.parseInt(textField.getText()
							.trim())));
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				} catch (NoEmailKontaktFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		buttonPane.add(cancelButton);

		dialog.setVisible(true);
	}

	/**
	 * Displays an info about the application
	 */
	private void about() {
		JOptionPane
				.showMessageDialog(
						this,
						"Mit Hilfe von diesem Programm k\u00F6nnen Sie Ihre Kontakte verwalten.\n"
								+ "Es besitzt Aktionen zum Erstellen, L\u00F6schen und Editieren\n"
								+ "Ihrer Kontakte und hat Felder wie Vorname, Nachname und eMail.");
	}

	/**
	 * Displays the first record
	 */
	private void displayFirst() {
		try {
			refresh(ekd.first());
		} catch (NoEmailKontaktFoundException e) {
			refresh(null);
			e.printStackTrace();
		}
	}

	/**
	 * Displays the previous contact
	 */
	private void displayPrevious() {
		try {
			refresh(ekd.previous(ek));
		} catch (NoPreviousEmailKontaktFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Displays the next contact
	 */
	private void displayNext() {
		try {
			refresh(ekd.next(ek));
		} catch (NoNextEmailKontaktFoundException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Displays the last contact
	 */
	private void displayLast() {
		try {
			refresh(ekd.last());
		} catch (NoEmailKontaktFoundException e) {
			refresh(null);
			e.printStackTrace();
		}
	}

	/**
	 * Draws the GUI
	 */
	private void drawGUI() {
		setResizable(false);
		setTitle("Email-Management");
		setIconImage(Toolkit.getDefaultToolkit().getImage(iconPath));
		addWindowListener(new WindowAdapter(this));

		setBounds(100, 100, 331, 229);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnKontakt = new JMenu("Kontakt");
		menuBar.add(mnKontakt);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newContact();
			}
		});
		mnKontakt.add(mntmNeu);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveContact();
			}
		});
		mnKontakt.add(mntmSpeichern);

		JMenuItem mntmSuchen = new JMenuItem("Suchen");
		mntmSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchContact();
			}
		});
		mnKontakt.add(mntmSuchen);

		JMenuItem mntmLschen = new JMenuItem("L\u00F6schen");
		mntmLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteContact();
			}
		});
		mnKontakt.add(mntmLschen);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beenden();
			}
		});
		mnKontakt.add(mntmBeenden);

		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);

		JMenuItem mntmberEmailmanagement = new JMenuItem(
				"\u00DCber Email-Management");
		mntmberEmailmanagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});
		mnHilfe.add(mntmberEmailmanagement);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(32, 36, 70, 14);
		contentPane.add(lblVorname);

		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setBounds(32, 61, 70, 14);
		contentPane.add(lblNachname);

		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setBounds(32, 86, 70, 14);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(120, 33, 112, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(120, 58, 112, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(120, 86, 112, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		button = new JButton("|<");
		button.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button.setBounds(32, 125, 45, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayFirst();
			}
		});
		contentPane.add(button);

		JButton button_1 = new JButton("<");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button_1.setBounds(87, 125, 45, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPrevious();
			}
		});
		contentPane.add(button_1);

		textField_3 = new JTextField();
		textField_3.setBounds(142, 126, 35, 20);
		textField_3.setEditable(false);
		textField_3.setHorizontalAlignment(textField_3.CENTER);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JButton button_2 = new JButton(">");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button_2.setBounds(187, 125, 45, 23);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayNext();
			}
		});
		contentPane.add(button_2);

		JButton button_3 = new JButton(">|");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button_3.setBounds(242, 125, 45, 23);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLast();
			}
		});
		contentPane.add(button_3);
	}
}
