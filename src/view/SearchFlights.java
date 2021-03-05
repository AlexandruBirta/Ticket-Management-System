package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import controller.GenerateCountries;
import model.DBConnection;
import model.JTextFieldLimit;
import model.User;

public class SearchFlights extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6345230936275590279L;

	private User user;

	private JLabel fromLabel;
	private JComboBox fromField;
	private JLabel toLabel;
	private JComboBox toField;
	private JLabel departureLabel;
	private JDateChooser departureDate;
	private JDateChooser arrivalDate;
	private JRadioButton returnButton;
	private JRadioButton oneWayButton;
	private JLabel classLabel;
	private JComboBox classBox;
	private JButton searchButton;
	private JButton backButton;
	private JLabel priceLabel;
	private JLabel currencyLabel;
	private JTextField priceField;
	private JLabel textIndicationLabel;
	private ButtonGroup returnTypeButtonGroup;

	private BufferedImage backgroundImage;
	
	private static final String[] CLASSES = { "First", "Business", "Economy", "Premium Economy" };

	private static final String SEARCH_STATEMENT = "SELECT from_location, to_location, CAST(departure AS DATE), CAST(arrival AS DATE), return_option, one_way_option, class, price FROM tickets";
	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\search_flights_background.jpg";
	
	public SearchFlights(User user) {
		super("Search flights");
		
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setContentPane(new ImagePanel(backgroundImage));

		this.user = user;

		ArrayList<String> countryList = new ArrayList<>();

		countryList = GenerateCountries.getCountryList();

		fromLabel = new JLabel("From");
		fromField = new JComboBox(countryList.toArray());
		toLabel = new JLabel("To");
		toField = new JComboBox(countryList.toArray());
		departureLabel = new JLabel("Depart");
		departureDate = new JDateChooser();
		returnButton = new JRadioButton("return");
		oneWayButton = new JRadioButton("one way");
		arrivalDate = new JDateChooser();
		classLabel = new JLabel("Class");
		classBox = new JComboBox(CLASSES);
		searchButton = new JButton("Search flight");
		backButton = new JButton("Back");
		priceLabel = new JLabel("Price");
		currencyLabel = new JLabel("$");
		priceField = new JTextField(10);
		textIndicationLabel = new JLabel("(Enter 0 for all tickets)");

		returnTypeButtonGroup = new ButtonGroup();
		returnTypeButtonGroup.add(returnButton);
		returnTypeButtonGroup.add(oneWayButton);

		
		
		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(670, 330);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		///////////// row /////////////

		fromLabel.setBounds(50, 30, 250, 25);
		fromLabel.setForeground(Color.black);
		add(fromLabel);

		fromField.setBounds(50, 60, 262, 25);
		add(fromField);

		toLabel.setBounds(350, 30, 250, 25);
		toLabel.setForeground(Color.black);
		add(toLabel);

		toField.setBounds(350, 60, 262, 25);
		add(toField);

		///////////// row /////////////

		departureLabel.setBounds(50, 140, 250, 25);
		departureLabel.setForeground(Color.black);
		add(departureLabel);

		departureDate.setBounds(50, 170, 105, 25);
		departureDate.setDate(new Date());
		add(departureDate);

		returnButton.setBounds(208, 115, 100, 25);
		returnButton.setSelected(true);
		add(returnButton);

		oneWayButton.setBounds(208, 140, 100, 25);
		add(oneWayButton);

		arrivalDate.setBounds(210, 170, 105, 25);
		arrivalDate.setDate(new Date());
		add(arrivalDate);

		classLabel.setBounds(350, 140, 250, 25);
		classLabel.setForeground(Color.black);
		add(classLabel);

		classBox.setBounds(350, 170, 140, 25);
		add(classBox);

		searchButton.setBounds(350, 230, 140, 40);
		add(searchButton);

		priceLabel.setBounds(510, 120, 250, 25);
		priceLabel.setForeground(Color.black);
		add(priceLabel);
		
		textIndicationLabel.setBounds(510, 140, 250, 25);
		textIndicationLabel.setForeground(Color.black);
		add(textIndicationLabel);

		currencyLabel.setBounds(500, 170, 70, 25);
		currencyLabel.setForeground(Color.black);
		add(currencyLabel);
		priceField.setBounds(510, 170, 70, 25);
		add(priceField);
		priceField.setDocument(new JTextFieldLimit(9));
		priceField.setText("000000.00");

		backButton.setBounds(500, 230, 140, 40);
		add(backButton);
	}

	public void layoutFunctionality() {

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new Login();

					}

				});

			}

		});

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = null;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				String fromDestination = fromField.getSelectedItem().toString();
				String toDestination = toField.getSelectedItem().toString();
				String departure = sdf.format(departureDate.getDate().getTime());
				String arrival = sdf.format(arrivalDate.getDate().getTime());
				boolean returnOption = returnButton.isSelected();
				boolean oneWayOption = oneWayButton.isSelected();
				String classSelection = classBox.getSelectedItem().toString();
				double price = Double.parseDouble(priceField.getText());

				int returnValue;
				int oneWayValue;

				if (returnOption == true)
					returnValue = 1;
				else
					returnValue = 0;

				if (oneWayOption == true)
					oneWayValue = 1;
				else
					oneWayValue = 0;

				boolean flightFound = false;

				try (Connection conn = DBConnection.getConnection();
						PreparedStatement stmt = conn.prepareStatement(SEARCH_STATEMENT,
								ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

					rs = stmt.executeQuery();

					while (rs.next()) {
						if (rs.getString(1).equals(fromDestination) && rs.getString(2).equals(toDestination)
								&& rs.getString(3).equals(departure) && rs.getString(4).equals(arrival)
								&& rs.getInt(5) == returnValue && rs.getInt(6) == oneWayValue
								&& rs.getString(7).contentEquals(classSelection)) {
							flightFound = true;
						}
					}

					rs.close();

				} catch (SQLException ex) {
					System.out.println("SQL Exception!");
					ex.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				dispose();

				if (flightFound) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							FlightSearchResults searchResults = new FlightSearchResults(true, user, fromDestination,
									toDestination, departure, arrival, returnOption, oneWayOption, classSelection,
									price);
						}
					});
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							FlightSearchResults searchResults = new FlightSearchResults(false, user, null, null, null,
									null, false, false, null, 0.0);
						}
					});
				}

			}
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}