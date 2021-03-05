package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class WelcomeScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5871796930630663608L;
	private JLabel loginLabel;
	private JLabel aboutUsLabel;
	private JLabel homeLabel;
	private JLabel planeIconLabel;
	private JLabel dividerLabel;
	private JLabel popularDestinations;
	private JLabel destination1Label;
	private JLabel destination2Label;
	private JLabel destination3Label;
	private JLabel aboutUsText;
	private JLabel logoLabel;

	private BufferedImage backgroundImage;
	private BufferedImage planeIconImage;
	private BufferedImage dividerImage;
	private BufferedImage destination1;
	private BufferedImage destination2;
	private BufferedImage destination3;

	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\welcome_background.jpg";
	private static final String PLANE_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\plane_icon.png";
	private static final String DIVIDER_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\divider.png";
	private static final String DESTINATION_1_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\destination1.jpg";
	private static final String DESTINATION_2_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\destination2.jpg";
	private static final String DESTINATION_3_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\destination3.jpg";
    private static final String ABOUT_US_TEXT = "Welcome to the Great Ticketing System, your number one source for cheap flight tickets. We're dedicated to giving you the very best tickets we have, with a focus on fair prices.\r\n" + 
    		"\r\n" + 
    		"\r\n" + 
    		"Founded in 2020 by Birta Alexandru, Great Ticketing System has come a long way from its beginnings in Romania. When Birta Alexandru first started out, his passion for Java programming drove them to study so that this Great Ticketing System can offer you the best tickets. We now serve customers all over the world, and are thrilled that we're able to turn our passion into the best ticketing system out there.\r\n" + 
    		"\r\n" + 
    		"\r\n" + 
    		"We hope you enjoy our products as much as we enjoy offering them to you. If you have any questions or comments, please don't hesitate to contact us.\r\n" + 
    		"\r\n" + 
    		"\r\n" + 
    		"Sincerely,\r\n" + 
    		"\r\n" + 
    		"Birta Alexandru";

	public WelcomeScreen() {
		super("Welcome to the Great Ticketing System");

		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
			planeIconImage = ImageIO.read(new File(PLANE_IMAGE_PATH));
			dividerImage = ImageIO.read(new File(DIVIDER_PATH));
			destination1 = ImageIO.read(new File(DESTINATION_1_PATH));
			destination2 = ImageIO.read(new File(DESTINATION_2_PATH));
			destination3 = ImageIO.read(new File(DESTINATION_3_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setContentPane(new ImagePanel(backgroundImage));

		planeIconLabel = new JLabel(new ImageIcon(planeIconImage));
		dividerLabel = new JLabel(new ImageIcon(dividerImage));
		destination1Label = new JLabel(new ImageIcon(destination1));
		destination2Label = new JLabel(new ImageIcon(destination2));
		destination3Label = new JLabel(new ImageIcon(destination3));
		homeLabel = new JLabel("Home");
		aboutUsLabel = new JLabel("About us");
		loginLabel = new JLabel("Login");
		popularDestinations = new JLabel("Popular destinations:");
		aboutUsText = new JLabel();
		makeCustomLabel(aboutUsText, ABOUT_US_TEXT);
		logoLabel = new JLabel();
		makeCustomLabel(logoLabel, "Great\nTicketing\nSystem");

		

		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(1200, 850);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		int gridX = 200;

		Font menuFont = new Font("Helvetica Neue", Font.BOLD, 40);
		Font textFont = new Font("Helvetica Neue", Font.ITALIC, 20);

		planeIconLabel.setBounds(30, 5, 200, 200);
		add(planeIconLabel);

		logoLabel.setBounds(270, 5, 200, 200);
		logoLabel.setFont(menuFont);
		logoLabel.setForeground(Color.black);
		add(logoLabel);
		
		homeLabel.setBounds(400 + gridX, 100, 150, 50);
		homeLabel.setFont(menuFont);
		homeLabel.setForeground(Color.black);
		homeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(homeLabel);

		aboutUsLabel.setBounds(575 + gridX, 100, 200, 50);
		aboutUsLabel.setFont(menuFont);
		aboutUsLabel.setForeground(Color.black);
		aboutUsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(aboutUsLabel);

		loginLabel.setBounds(800 + gridX, 100, 150, 50);
		loginLabel.setFont(menuFont);
		loginLabel.setForeground(Color.black);
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(loginLabel);

		popularDestinations.setBounds(350, 210, 500, 200);
		popularDestinations.setFont(menuFont);
		popularDestinations.setForeground(Color.black);
		add(popularDestinations);

		destination1Label.setBounds(50, 400, 300, 300);
		add(destination1Label);

		destination2Label.setBounds(430, 400, 300, 300);
		add(destination2Label);

		destination3Label.setBounds(800, 400, 300, 300);
		add(destination3Label);

		dividerLabel.setBounds(0, 205, 1500, 15);
		add(dividerLabel);
		
		aboutUsText.setBounds(50, 200, 1000, 580);
		aboutUsText.setForeground(Color.black);
		aboutUsText.setFont(textFont);
		

	}

	public void layoutFunctionality() {
		loginLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Login();
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginLabel.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				loginLabel.setForeground(Color.black);
			}
		});

		aboutUsLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			
				remove(popularDestinations);
				remove(destination1Label);
				remove(destination2Label);
				remove(destination3Label);
				add(aboutUsText);
				
				repaint();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				aboutUsLabel.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				aboutUsLabel.setForeground(Color.black);
			}
		});
		
		
		
		homeLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
			
				add(popularDestinations);
				add(destination1Label);
				add(destination2Label);
			    add(destination3Label);
			    remove(aboutUsText);
				
				repaint();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				homeLabel.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				homeLabel.setForeground(Color.black);
			}
			
		
		});
	}


	public void makeCustomLabel(JLabel label, String string) {
		label.setText("<html>" + string.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
	}
}
