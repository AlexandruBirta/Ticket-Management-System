package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.DBConnection;
import model.RSAEncryption;
import model.User;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5994667425242401858L;

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton backButton;
	private JLabel registerLabel;
	private JLabel resetPasswordLabel;
	private JLabel failedLoginLabel;

	private BufferedImage backgroundImage;

	private static final String LOGIN_CHECK_STATEMENT = "SELECT * FROM users";
	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\login_background.jpg";

	public Login() {
		super("Login");

		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setContentPane(new ImagePanel(backgroundImage));

		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Passoword:");
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(30);
		loginButton = new JButton("Login");
		registerLabel = new JLabel("Don't have an account? Register here!");
		resetPasswordLabel = new JLabel("Forgot your password?");
		failedLoginLabel = new JLabel("Incorrect login details!");
		backButton = new JButton("Back");

		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(300, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		int gridY = 20;

		///////////// row /////////////

		usernameLabel.setBounds(25, gridY, 80, 25);
		usernameLabel.setForeground(Color.black);
		add(usernameLabel);

		usernameField.setBounds(100, gridY, 165, 25);
		add(usernameField);

		gridY += 40;

		///////////// row /////////////

		passwordLabel.setBounds(25, gridY, 80, 25);
		passwordLabel.setForeground(Color.black);
		add(passwordLabel);

		passwordField.setBounds(100, gridY, 165, 25);
		add(passwordField);

		gridY += 40;

		///////////// row /////////////

		registerLabel.setBounds(25, gridY, 400, 25);
		registerLabel.setForeground(Color.blue.darker());
		registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(registerLabel);

		gridY += 40;

		///////////// row /////////////

		resetPasswordLabel.setBounds(74, gridY, 400, 25);
		resetPasswordLabel.setForeground(Color.blue.darker());
		resetPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(resetPasswordLabel);

		gridY += 40;

		///////////// row /////////////

		loginButton.setBounds(57, gridY + 10, 80, 30);
		add(loginButton);

		backButton.setBounds(142, gridY + 10, 80, 30);
		add(backButton);

		gridY += 40;

		///////////// row /////////////

		failedLoginLabel.setBounds(74, gridY, 400, 30);
		failedLoginLabel.setForeground(Color.red);
		add(failedLoginLabel);
		failedLoginLabel.setVisible(false);

	}

	public void layoutFunctionality() {
		registerLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new RegisterForm();
					}
				});
			}
		});

		resetPasswordLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new ResetPasswordInterface();
					}
				});
			}
		});

		loginButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = null;

				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(LOGIN_CHECK_STATEMENT,
								ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

					rs = stmt.executeQuery();

					PublicKey publicKey = RSAEncryption.getPublicKey();

					while (rs.next()) {
						if (rs.getString("username").equals(usernameField.getText()) && RSAEncryption
								.decryptMessage(rs.getString("pwd"), publicKey).equals(passwordField.getText())) {
							if (rs.getInt("administrator") == 1) {
								// Login as an admin
								dispose();

								SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										new AdminPanel();
									}
								});
							} else {
								// Login as an user
								final User user = new User(rs.getInt("id"), rs.getString("username"),
										rs.getString("pwd"), rs.getString("first_name"), rs.getString("last_name"),
										rs.getString("email"), rs.getString("country"), rs.getString("phone_number"),
										rs.getInt("administrator"), rs.getDate("date_of_birth"));

								dispose();

								SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										new SearchFlights(user);
									}
								});

							}
						} else
							failedLoginLabel.setVisible(true);
					}

					rs.close();

				} catch (SQLException ex) {
					ex.printStackTrace();
				} catch (Exception e1) {
					failedLoginLabel.setVisible(true);
				}
			}
		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new WelcomeScreen();
					}

				});

			}

		});
	}
}
