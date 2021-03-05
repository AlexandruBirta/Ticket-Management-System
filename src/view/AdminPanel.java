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
import java.text.ParseException;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;

import com.mysql.cj.util.StringUtils;
import com.toedter.calendar.JDateChooser;

import controller.GenerateCountries;
import controller.InsertFlight;
import model.JTextFieldLimit;

public class AdminPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320318550998264372L;

	private JLabel airlineCodeLabel;
	private JTextField airlineCodeField;

	private JLabel flightNumberLabel;
	private JTextField flightNumberField;

	private JLabel airlineCompanyLabel;
	private JTextField airlineCompanyField;

	private JLabel fromLocationLabel;
	private JComboBox fromLocationField;

	private JLabel fromAirportLabel;
	private JTextField fromAirportField;

	private JLabel toLocationLabel;
	private JComboBox toLocationField;

	private JLabel toAirportLabel;
	private JTextField toAirportField;

	private JLabel boardingLabel;
	private JDateChooser boardingDate;
	private JSpinner boardingTime;

	private JLabel departureLabel;
	private JDateChooser departureDate;
	private JSpinner departureTime;

	private JLabel arrivalLabel;
	private JDateChooser arrivalDate;
	private JSpinner arrivalTime;

	private JLabel classSelectionLabel;
	private JComboBox classSelectionField;

	private JLabel gateLabel;
	private JTextField gateField;

	private JLabel priceLabel;
	private JLabel currencyLabel;
	private JTextField priceField;

	private JRadioButton returnButton;
	private JRadioButton oneWayButton;
	private ButtonGroup returnTypeButtonGroup;

	private JButton insertFlightButton;
	private JButton showReportsButton;
	private JButton backButton;

	private JLabel incorrectDetails1;
	private JLabel incorrectDetails2;
	private JLabel incorrectDetails3;
	private JLabel incorrectDetails4;
	private JLabel incorrectDetails5;
	private JLabel incorrectDetails6;

	private BufferedImage backgroundImage;

	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\admin_panel_background.jpg";

	private static final String[] CLASSES = { "First", "Business", "Economy", "Premium Economy" };

	public AdminPanel() {
		super("Admin Panel");

		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setContentPane(new ImagePanel(backgroundImage));

		ArrayList<String> countryList = new ArrayList<>();

		countryList = GenerateCountries.getCountryList();

		airlineCodeLabel = new JLabel("Airline code:");
		airlineCodeField = new JTextField(3);

		flightNumberLabel = new JLabel("Flight number:");
		flightNumberField = new JTextField(10);

		airlineCompanyLabel = new JLabel("Airline company:");
		airlineCompanyField = new JTextField(50);

		fromLocationLabel = new JLabel("From:");
		fromLocationField = new JComboBox(countryList.toArray());

		fromAirportLabel = new JLabel("From airport:");
		fromAirportField = new JTextField(3);

		toLocationLabel = new JLabel("To:");
		toLocationField = new JComboBox(countryList.toArray());

		toAirportLabel = new JLabel("To airport:");
		toAirportField = new JTextField(3);

		boardingLabel = new JLabel("Boarding date:");
		boardingDate = new JDateChooser();
		boardingTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor boardingEditor = new JSpinner.DateEditor(boardingTime, "HH:mm:ss");
		boardingTime.setEditor(boardingEditor);
		boardingTime.setValue(new Date());

		departureLabel = new JLabel("Departure date:");
		departureDate = new JDateChooser();
		departureTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor departureEditor = new JSpinner.DateEditor(departureTime, "HH:mm:ss");
		departureTime.setEditor(departureEditor);
		departureTime.setValue(new Date());

		arrivalLabel = new JLabel("Arrival date:");
		arrivalDate = new JDateChooser();
		arrivalTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor arrivalEditor = new JSpinner.DateEditor(arrivalTime, "HH:mm:ss");
		arrivalTime.setEditor(arrivalEditor);
		arrivalTime.setValue(new Date());

		classSelectionLabel = new JLabel("Class");
		classSelectionField = new JComboBox(CLASSES);

		gateLabel = new JLabel("Gate:");
		gateField = new JTextField(3);

		priceLabel = new JLabel("Price:");
		currencyLabel = new JLabel("$");
		priceField = new JTextField(10);

		returnButton = new JRadioButton("return");
		oneWayButton = new JRadioButton("one way");

		returnTypeButtonGroup = new ButtonGroup();
		returnTypeButtonGroup.add(returnButton);
		returnTypeButtonGroup.add(oneWayButton);

		insertFlightButton = new JButton("Insert new flight");
		showReportsButton = new JButton("Show reports");
		backButton = new JButton("Back");

		incorrectDetails1 = new JLabel("Incorrect details!");
		incorrectDetails2 = new JLabel("Airline code must be numeric!");
		incorrectDetails3 = new JLabel("Flight number must be numeric!");
		incorrectDetails4 = new JLabel("Aiports must be only in alphabetic letters!");
		incorrectDetails5 = new JLabel("Price must not be 0!");
		incorrectDetails6 = new JLabel(
				"Boarding date must be before departure date and departure date before arrival date!");

		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(700, 850);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		int gridY = 20;
		int gridX = 50;

		///////////// row /////////////

		airlineCodeLabel.setBounds(gridX, gridY, 250, 25);
		airlineCodeLabel.setForeground(Color.black);
		add(airlineCodeLabel);

		gridY += 40;

		airlineCodeField.setBounds(gridX, gridY, 35, 25);
		add(airlineCodeField);
		airlineCodeField.setDocument(new JTextFieldLimit(3));

		gridY += 40;

		///////////// row /////////////

		flightNumberLabel.setBounds(gridX, gridY, 250, 25);
		flightNumberLabel.setForeground(Color.black);
		add(flightNumberLabel);

		gridY += 40;

		flightNumberField.setBounds(gridX, gridY, 250, 25);
		add(flightNumberField);

		gridY += 40;

		///////////// row /////////////

		airlineCompanyLabel.setBounds(gridX, gridY, 250, 25);
		airlineCompanyLabel.setForeground(Color.black);
		add(airlineCompanyLabel);

		gridY += 40;

		airlineCompanyField.setBounds(gridX, gridY, 250, 25);
		add(airlineCompanyField);

		gridY += 40;

		///////////// row /////////////

		fromLocationLabel.setBounds(gridX, gridY, 250, 25);
		fromLocationLabel.setForeground(Color.black);
		add(fromLocationLabel);

		gridY += 40;

		fromLocationField.setBounds(gridX, gridY, 250, 25);
		add(fromLocationField);

		gridY += 40;

		///////////// row /////////////

		fromAirportLabel.setBounds(gridX, gridY, 250, 25);
		fromAirportLabel.setForeground(Color.black);
		add(fromAirportLabel);

		gridY += 40;

		fromAirportField.setBounds(gridX, gridY, 35, 25);
		add(fromAirportField);
		fromAirportField.setDocument(new JTextFieldLimit(3));

		gridY += 40;

		///////////// row /////////////

		toLocationLabel.setBounds(gridX, gridY, 250, 25);
		toLocationLabel.setForeground(Color.black);
		add(toLocationLabel);

		gridY += 40;

		toLocationField.setBounds(gridX, gridY, 250, 25);
		add(toLocationField);

		gridY += 40;

		///////////// row /////////////

		toAirportLabel.setBounds(gridX, gridY, 250, 25);
		toAirportLabel.setForeground(Color.black);
		add(toAirportLabel);

		gridY += 40;

		toAirportField.setBounds(gridX, gridY, 35, 25);
		add(toAirportField);
		toAirportField.setDocument(new JTextFieldLimit(3));

		gridY += 40;

		///////////// row /////////////

		gridX += 320;
		gridY = 20;

		boardingLabel.setBounds(gridX, gridY, 250, 25);
		boardingLabel.setForeground(Color.black);
		add(boardingLabel);

		gridY += 40;

		boardingDate.setBounds(gridX, gridY, 105, 25);
		boardingDate.setDate(new Date());
		add(boardingDate);

		boardingTime.setBounds(gridX + 115, gridY, 72, 25);
		add(boardingTime);

		gridY += 40;

		///////////// row /////////////

		departureLabel.setBounds(gridX, gridY, 250, 25);
		departureLabel.setForeground(Color.black);
		add(departureLabel);

		gridY += 40;

		departureDate.setBounds(gridX, gridY, 105, 25);
		departureDate.setDate(new Date());
		add(departureDate);

		departureTime.setBounds(gridX + 115, gridY, 72, 25);
		add(departureTime);

		gridY += 40;

		///////////// row /////////////

		arrivalLabel.setBounds(gridX, gridY, 250, 25);
		arrivalLabel.setForeground(Color.black);
		add(arrivalLabel);

		gridY += 40;

		arrivalDate.setBounds(gridX, gridY, 105, 25);
		arrivalDate.setDate(new Date());
		add(arrivalDate);

		arrivalTime.setBounds(gridX + 115, gridY, 72, 25);
		add(arrivalTime);

		gridY += 40;

		///////////// row /////////////

		classSelectionLabel.setBounds(gridX, gridY, 250, 25);
		classSelectionLabel.setForeground(Color.black);
		add(classSelectionLabel);

		gridY += 40;

		classSelectionField.setBounds(gridX, gridY, 140, 25);
		add(classSelectionField);

		gridY += 40;

		///////////// row /////////////

		gateLabel.setBounds(gridX, gridY, 250, 25);
		gateLabel.setForeground(Color.black);
		add(gateLabel);

		gridY += 40;

		gateField.setBounds(gridX, gridY, 35, 25);
		add(gateField);
		gateField.setDocument(new JTextFieldLimit(3));

		gridY += 40;

		///////////// row /////////////

		priceLabel.setBounds(gridX, gridY, 250, 25);
		priceLabel.setForeground(Color.black);
		add(priceLabel);

		gridY += 40;

		currencyLabel.setBounds(gridX, gridY, 70, 25);
		currencyLabel.setForeground(Color.black);
		add(currencyLabel);
		priceField.setBounds(gridX + 10, gridY, 70, 25);
		add(priceField);
		priceField.setDocument(new JTextFieldLimit(9));
		priceField.setText("000000.00");

		gridY += 40;

		///////////// row /////////////

		gridY += 40;

		returnButton.setBounds(gridX, gridY, 70, 25);
		add(returnButton);
		returnButton.setSelected(true);

		oneWayButton.setBounds(gridX + 80, gridY, 100, 25);
		add(oneWayButton);

		gridY += 20;

		///////////// row /////////////

		insertFlightButton.setBounds(gridX, gridY + 30, 250, 70);
		add(insertFlightButton);

		backButton.setBounds(gridX, gridY + 125, 250, 50);
		add(backButton);

		showReportsButton.setBounds(gridX - 320, gridY + 30, 250, 70);
		add(showReportsButton);

		///////////// row /////////////

		gridY += 60;

		incorrectDetails1.setBounds(gridX - 320, gridY + 30, 250, 70);
		incorrectDetails1.setFont(incorrectDetails1.getFont().deriveFont(Font.BOLD, 20f));
		incorrectDetails1.setForeground(Color.red);
		add(incorrectDetails1);

		gridY += 13;

		incorrectDetails2.setBounds(gridX - 320, gridY + 40, 250, 70);
		incorrectDetails2.setForeground(Color.red);
		add(incorrectDetails2);

		gridY += 13;

		incorrectDetails3.setBounds(gridX - 320, gridY + 40, 250, 70);
		incorrectDetails3.setForeground(Color.red);
		add(incorrectDetails3);

		gridY += 13;

		incorrectDetails4.setBounds(gridX - 320, gridY + 40, 250, 70);
		incorrectDetails4.setForeground(Color.red);
		add(incorrectDetails4);

		gridY += 13;

		incorrectDetails5.setBounds(gridX - 320, gridY + 40, 250, 70);
		incorrectDetails5.setForeground(Color.red);
		add(incorrectDetails5);

		gridY += 13;

		incorrectDetails6.setBounds(gridX - 320, gridY + 40, 500, 70);
		incorrectDetails6.setForeground(Color.red);
		add(incorrectDetails6);

		gridY += 13;

		setIncorrectDetailsVisible(false);

	}

	public void layoutFunctionality() {
		insertFlightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

				Date boardingDateStamp = (Date) boardingTime.getValue();
				String boardingTime = timeFormat.format(boardingDateStamp);

				Date departureDateStamp = (Date) departureTime.getValue();
				String departureTime = timeFormat.format(departureDateStamp);

				Date arrivalDateStamp = (Date) arrivalTime.getValue();
				String arrivalTime = timeFormat.format(arrivalDateStamp);

				String airline_company_code = airlineCodeField.getText().toUpperCase();
				String flight_number = flightNumberField.getText();
				String airline_company = airlineCompanyField.getText();
				String from_location = fromLocationField.getSelectedItem().toString();
				String from_airport = fromAirportField.getText().toUpperCase();
				String to_location = toLocationField.getSelectedItem().toString();
				String to_airport = toAirportField.getText().toUpperCase();
				String boarding = dateFormat.format(boardingDate.getDate().getTime()) + " " + boardingTime;
				String departure = dateFormat.format(departureDate.getDate().getTime()) + " " + departureTime;
				String arrival = dateFormat.format(arrivalDate.getDate().getTime()) + " " + arrivalTime;
				String class_selection = classSelectionField.getSelectedItem().toString();
				String gate = gateField.getText().toUpperCase();
				int return_option;
				int one_way_option;

				if (returnButton.isSelected()) {
					return_option = 1;
					one_way_option = 0;
				} else {
					return_option = 0;
					one_way_option = 1;
				}

				try {
					Date boardingCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(boarding);
					Date departureCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(departure);
					Date arrivalCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(arrival);

					double price = Double.parseDouble(priceField.getText());

					if (!airline_company_code.equals("") && !flight_number.equals("") && !airline_company.equals("")
							&& !from_airport.equals("") && !to_airport.equals("") && !gate.equals("")
							&& boardingCheck.before(departureCheck) && departureCheck.before(arrivalCheck)
							&& StringUtils.isStrictlyNumeric(airline_company_code)
							&& StringUtils.isStrictlyNumeric(flight_number) && isAlpha(from_airport)
							&& isAlpha(to_airport) && price != 0.0) {

						setIncorrectDetailsVisible(false);

						InsertFlight.insertFlight(airline_company_code, flight_number, airline_company, from_location,
								from_airport, to_location, to_airport, boarding, departure, arrival, class_selection,
								gate, price, return_option, one_way_option);

					} else {
						setIncorrectDetailsVisible(true);
					}

				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				}

			}

		});

		showReportsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new Reports();
					}
				});
			}

		});

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
	}

	public static boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public void setIncorrectDetailsVisible(boolean statement) {
		incorrectDetails1.setVisible(statement);
		incorrectDetails2.setVisible(statement);
		incorrectDetails3.setVisible(statement);
		incorrectDetails4.setVisible(statement);
		incorrectDetails5.setVisible(statement);
		incorrectDetails6.setVisible(statement);
	}
}
