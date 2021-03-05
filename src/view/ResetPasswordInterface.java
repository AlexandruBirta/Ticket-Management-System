package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SecureRandom;
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

import controller.ResetPassword;
import controller.SendEmail;
import model.DBConnection;
import model.JTextFieldLimit;
import model.RSAEncryption;

public class ResetPasswordInterface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -998180134702966323L;
	
	private JLabel emailLabel;
	private JTextField emailField;
	private JButton sendCodeButton;
	private JButton backButton;
	private JLabel verificationLabel1;
	private JLabel verificationLabel2;
	private JLabel codeLabel;
	private JTextField codeField;
	private JLabel wrongCodeLabel;
	private JButton nextButton;
	private JLabel newPasswordLabel;
	private JPasswordField newPasswordField;
	private JLabel confirmNewPasswordLabel;
	private JPasswordField confirmNewPasswordField;
	private JButton finishButton;
	private JLabel invalidEmailLabel;
	private JLabel invalidPasswordLabel;
	
	private BufferedImage backgroundImage;
	
	private static final String EMAIL_CHECK_STATEMENT = "SELECT email FROM users";
	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\reset_password_background.jpg";

	private String code;
	private Font f;

	public ResetPasswordInterface() {
		super("Reset Password");
		
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setContentPane(new ImagePanel(backgroundImage));

		f = new Font("Verdana",Font.BOLD,30);
		
		emailLabel = new JLabel("Email:");
		emailField = new JTextField(50);
		sendCodeButton = new JButton("Send code");
		backButton = new JButton("Back");
		verificationLabel1 = new JLabel("We will send you a 6-digit verification code to reset your password");
		verificationLabel2 = new JLabel("at your email address!");
		invalidEmailLabel = new JLabel("Invalid email address!");
		codeLabel = new JLabel("Insert your verification code here:");
		codeField = new JTextField(10);
		nextButton = new JButton("Next");
		wrongCodeLabel = new JLabel("Incorrect code!");
		newPasswordLabel = new JLabel("New password:");
		newPasswordField = new JPasswordField(30);
		confirmNewPasswordLabel = new JLabel("Confirm new password:");
		confirmNewPasswordField = new JPasswordField(30);
		finishButton = new JButton("Finish");
		invalidPasswordLabel = new JLabel("Invalid passwords!");

		firstLayoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(500, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void firstLayoutComponents() {
		setLayout(null);

		int gridY = 20;
		
		

		///////////// row /////////////

		verificationLabel1.setBounds(50, gridY, 600, 25);
		verificationLabel1.setForeground(Color.black);
		add(verificationLabel1);

		gridY += 20;

		///////////// row /////////////

		verificationLabel2.setBounds(50, gridY, 600, 25);
		verificationLabel2.setForeground(Color.black);
		add(verificationLabel2);

		gridY += 40;

		///////////// row /////////////

		emailLabel.setBounds(150, gridY, 80, 25);
		emailLabel.setForeground(Color.black);
		add(emailLabel);

		invalidEmailLabel.setBounds(150, gridY + 16, 80, 25);
		invalidEmailLabel.setForeground(Color.red);
		add(invalidEmailLabel);
		invalidEmailLabel.setVisible(false);

		gridY += 40;

		emailField.setBounds(150, gridY, 200, 25);
		add(emailField);

		gridY += 40;

		///////////// row /////////////

		sendCodeButton.setBounds(150, gridY, 100, 25);
		add(sendCodeButton);

		gridY += 40;

		backButton.setBounds(150, gridY, 80, 25);
		add(backButton);

	}

	public void secondLayoutComponents() {
		getContentPane().removeAll();
		repaint();

		int gridY = 30;

		///////////// row /////////////

		codeLabel.setBounds(142, gridY, 600, 25);
		codeLabel.setForeground(Color.black);
		add(codeLabel);

		gridY += 55;

		///////////// row /////////////

		wrongCodeLabel.setBounds(194, gridY - 25, 600, 25);
		wrongCodeLabel.setForeground(Color.red);
		add(wrongCodeLabel);
		wrongCodeLabel.setVisible(false);

		codeField.setBounds(164, gridY - 4, 140, 50);
		codeField.setFont(f);
		codeField.setDocument(new JTextFieldLimit(6));
		add(codeField);

		gridY += 60;

		///////////// row /////////////

		nextButton.setBounds(181, gridY, 100, 25);
		add(nextButton);

	}

	public void thirdLayoutComponents() {
		getContentPane().removeAll();
		repaint();

		int gridY = 20;

		///////////// row /////////////

		newPasswordLabel.setBounds(30, gridY, 200, 25);
		newPasswordLabel.setForeground(Color.black);
		add(newPasswordLabel);
		
		gridY += 40;

		newPasswordField.setBounds(30, gridY - 10, 200, 25);
		add(newPasswordField);

		gridY += 30;

		///////////// row /////////////

		confirmNewPasswordLabel.setBounds(30, gridY, 200, 25);
		confirmNewPasswordLabel.setForeground(Color.black);
		add(confirmNewPasswordLabel);
		
		gridY += 40;

		confirmNewPasswordField.setBounds(30, gridY - 10, 200, 25);
		add(confirmNewPasswordField);

		gridY += 40;

		///////////// row /////////////

		
		
		invalidPasswordLabel.setBounds(30, gridY - 25, 200, 25);
		invalidPasswordLabel.setForeground(Color.red);
		add(invalidPasswordLabel);
		invalidPasswordLabel.setVisible(false);
		
		finishButton.setBounds(30, gridY, 90, 25);
		add(finishButton);

	}

	public void layoutFunctionality() {
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

		sendCodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ResultSet rs = null;
				boolean emailFound = false;

				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(EMAIL_CHECK_STATEMENT,
								ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

					rs = stmt.executeQuery();

					while (rs.next()) {
						if (rs.getString("email").equals(emailField.getText()))
							emailFound = true;
					}

					rs.close();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}

				if (emailFound) {
					code = generateCode();

					SendEmail.sendMail(emailField.getText(), code);

					secondLayoutComponents();

				} else {
					invalidEmailLabel.setVisible(true);
				}

			}
		});

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (codeField.getText().equals(code))
					thirdLayoutComponents();
				else
					wrongCodeLabel.setVisible(true);
			}
		});
		
		finishButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				String pwd = newPasswordField.getText();
				
				if(!newPasswordField.getText().equals("") && !confirmNewPasswordField.getText().equals("") && newPasswordField.getText().equals(confirmNewPasswordField.getText())) {
					PrivateKey privateKey = RSAEncryption.getPrivateKey();

					try {
						pwd = RSAEncryption.encryptMessage(pwd, privateKey);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					ResetPassword.resetPassword(pwd, emailField.getText());

					dispose();

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new Login();
						}
					});
				} else {
				   invalidPasswordLabel.setVisible(true);
				}
				
			}
		});
	}

	public static String generateCode() {
		final String codeCharacters = "0123456789";

		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 6; i++)
			sb.append(codeCharacters.charAt(rnd.nextInt(codeCharacters.length())));

		return sb.toString();
	}

}
