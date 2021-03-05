package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mysql.cj.util.StringUtils;
import com.toedter.calendar.JDateChooser;

import controller.GenerateCountries;
import controller.InsertUser;
import model.DBConnection;
import model.JTextFieldLimit;
import model.RSAEncryption;

public class RegisterForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7416893416172526686L;
	
	private JLabel usernameLabel;
	private JTextField usernameField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JLabel confirmPasswordLabel;
	private JPasswordField confirmPasswordField;
	private JLabel firstNameLabel;
	private JTextField firstNameField;
	private JLabel lastNameLabel;
	private JTextField lastNameField;
	private JLabel emailLabel;
	private JTextField emailField;
	private JLabel countryLabel;
	private JComboBox countryBox;
	private JLabel phoneNumberLabel;
	private JTextField phoneNumberField;
	private JLabel dateOfBirthLabel;
	private JDateChooser dateOfBirthChooser;
	private JButton registerButton;
	private JLabel incorrectDetailsLabel;
	private JButton backButton;
	
	private BufferedImage backgroundImage;

	private static final String USER_CHECK_STATEMENT = "SELECT username, email FROM users";
	private static final String EMAIL_VALIDATION_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\"
			+ "x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\"
			+ ".)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]"
			+ ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\register_background.jpg";

	
	public RegisterForm() {
		super("Register");
		
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setContentPane(new ImagePanel(backgroundImage));

		ArrayList<String> countryList = new ArrayList<>();

		countryList = GenerateCountries.getCountryList();

		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(30);
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField(30);
		confirmPasswordLabel = new JLabel("Confirm password:");
		confirmPasswordField = new JPasswordField(30);
		firstNameLabel = new JLabel("First name:");
		firstNameField = new JTextField(30);
		lastNameLabel = new JLabel("Last name:");
		lastNameField = new JTextField(30);
		emailLabel = new JLabel("Email:");
		emailField = new JTextField(30);
		countryLabel = new JLabel("Country");
		countryBox = new JComboBox(countryList.toArray());
		phoneNumberLabel = new JLabel("Phone number:");
		phoneNumberField = new JTextField(10);
		dateOfBirthLabel = new JLabel("Date of birth:");
		dateOfBirthChooser = new JDateChooser();
		registerButton = new JButton("Register");
		incorrectDetailsLabel = new JLabel("Incorrect login details!");
		backButton = new JButton("Back");

		layoutComponents();

		layoutFunctionality();

		setResizable(false);
		setSize(400, 850);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		int gridY = 20;

		///////////// row /////////////

		usernameLabel.setBounds(10, gridY, 250, 25);
		usernameLabel.setForeground(Color.black);
		add(usernameLabel);

		gridY += 40;

		usernameField.setBounds(10, gridY, 250, 25);
		add(usernameField);

		gridY += 40;

		///////////// row /////////////

		passwordLabel.setBounds(10, gridY, 250, 25);
		passwordLabel.setForeground(Color.black);
		add(passwordLabel);

		gridY += 40;

		passwordField.setBounds(10, gridY, 250, 25);
		add(passwordField);

		gridY += 40;

		///////////// row /////////////

		confirmPasswordLabel.setBounds(10, gridY, 250, 25);
		confirmPasswordLabel.setForeground(Color.black);
		add(confirmPasswordLabel);

		gridY += 40;

		confirmPasswordField.setBounds(10, gridY, 250, 25);
		add(confirmPasswordField);

		gridY += 40;

		///////////// row /////////////

		firstNameLabel.setBounds(10, gridY, 250, 25);
		firstNameLabel.setForeground(Color.black);
		add(firstNameLabel);

		gridY += 40;

		firstNameField.setBounds(10, gridY, 250, 25);
		add(firstNameField);

		gridY += 40;

		///////////// row /////////////

		lastNameLabel.setBounds(10, gridY, 250, 25);
		lastNameLabel.setForeground(Color.black);
		add(lastNameLabel);

		gridY += 40;

		lastNameField.setBounds(10, gridY, 250, 25);
		add(lastNameField);

		gridY += 40;

		///////////// row /////////////

		emailLabel.setBounds(10, gridY, 250, 25);
		emailLabel.setForeground(Color.black);
		add(emailLabel);

		gridY += 40;

		emailField.setBounds(10, gridY, 250, 25);
		add(emailField);

		gridY += 40;

		///////////// row /////////////

		countryLabel.setBounds(10, gridY, 250, 25);
		countryLabel.setForeground(Color.black);
		add(countryLabel);

		gridY += 40;

		countryBox.setBounds(10, gridY, 250, 25);
		add(countryBox);

		gridY += 40;

		///////////// row /////////////

		phoneNumberLabel.setBounds(10, gridY, 250, 25);
		phoneNumberLabel.setForeground(Color.black);
		add(phoneNumberLabel);

		gridY += 40;

		phoneNumberField.setBounds(10, gridY, 80, 25);
		add(phoneNumberField);
		phoneNumberField.setDocument(new JTextFieldLimit(10));

		gridY += 40;

		///////////// row /////////////

		dateOfBirthLabel.setBounds(10, gridY, 250, 25);
		dateOfBirthLabel.setForeground(Color.black);
		add(dateOfBirthLabel);

		gridY += 40;

		dateOfBirthChooser.setBounds(10, gridY, 105, 25);
		dateOfBirthChooser.setDate(new Date());
		add(dateOfBirthChooser);

		gridY += 40;

		///////////// row /////////////

		registerButton.setBounds(90, gridY + 10, 100, 30);
		add(registerButton);

		backButton.setBounds(200, gridY + 10, 100, 30);
		add(backButton);

		gridY += 40;

		///////////// row /////////////

		incorrectDetailsLabel.setBounds(122, gridY - 5, 400, 30);
		incorrectDetailsLabel.setForeground(Color.red);
		add(incorrectDetailsLabel);
		incorrectDetailsLabel.setVisible(false);
	}

	public void layoutFunctionality() {

		registerButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				boolean userCheck = true;

				String username;
				String pwd;
				String first_name;
				String last_name;
				String email;
				String country;
				String phone_number;
				String date_of_birth;

				username = usernameField.getText();
				pwd = passwordField.getText();
				first_name = firstNameField.getText();
				last_name = lastNameField.getText();
				email = emailField.getText();
				country = countryBox.getSelectedItem().toString();
				phone_number = phoneNumberField.getText();
				date_of_birth = sdf.format(dateOfBirthChooser.getDate().getTime());

				ResultSet rs = null;

				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(USER_CHECK_STATEMENT,
								ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

					rs = stmt.executeQuery();

					// Check if email is unique and valid and if username is unique

					while (rs.next()) {
						if (rs.getString("username").equals(username)  ||  rs.getString("email").equals(email) || isValidEmail(email) == false)
							userCheck = false;
					}

					rs.close();

				} catch (SQLException ex) {
					System.out.println("SQL Exception!");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// Register user if details are correct

				if (passwordField.getText().equals(confirmPasswordField.getText())
						&& StringUtils.isStrictlyNumeric(phoneNumberField.getText()) && !username.equals("")
						&& !pwd.equals("") && !first_name.equals("") && !last_name.equals("") && !email.equals("")
						&& !country.equals("") && !phone_number.equals("") && !date_of_birth.equals("")
						&& userCheck == true) {

					PrivateKey privateKey = RSAEncryption.getPrivateKey();

					try {
						pwd = RSAEncryption.encryptMessage(pwd, privateKey);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					InsertUser.insertUser(username, pwd, first_name, last_name, email, country, phone_number,
							date_of_birth);

					dispose();

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new Login();
						}
					});
				} else
					incorrectDetailsLabel.setVisible(true);
			}
		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Login();
					}
				});
			}
		});
	}

	public static boolean isValidEmail(String email) {
		return email.matches(EMAIL_VALIDATION_REGEX);
	}
}