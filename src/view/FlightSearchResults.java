package view;

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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import model.DBConnection;
import model.Ticket;
import model.User;

public class FlightSearchResults extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2015348138689547201L;

	private User user;
	private boolean flightFound;

	private JLabel foundLabel;
	private JButton backButton;

	private String fromDestination;
	private String toDestination;
	private String departure;
	private String arrival;
	private boolean returnOption;
	private boolean oneWayOption;
	private String classSelection;
	private double price;

	private JLabel backgroundImageLabel;
	private JLabel picLabel;

	private BufferedImage backgroundImage;
	private BufferedImage myPicture;

	private static final String IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\error.png";
	private static final String BACKGROUND_IMAGE_PATH_1 = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\results_frame_background.jpg";
	private static final String BACKGROUND_IMAGE_PATH_2 = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\search_flights_background.jpg";

	public FlightSearchResults(boolean flightFound, User user, String fromDestination, String toDestination,
			String departure, String arrival, boolean returnOption, boolean oneWayOption, String class_selection,
			double price) {
		super("Search results");

		this.user = user;
		this.flightFound = flightFound;
		this.fromDestination = fromDestination;
		this.toDestination = toDestination;
		this.departure = departure;
		this.arrival = arrival;
		this.returnOption = returnOption;
		this.oneWayOption = oneWayOption;
		this.classSelection = class_selection;
		this.price = price;

		foundLabel = new JLabel();
		backButton = new JButton("Back");

		try {
			myPicture = ImageIO.read(new File(IMAGE_PATH));
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH_1));
		} catch (IOException e) {
			e.printStackTrace();
		}

		picLabel = new JLabel(new ImageIcon(myPicture));
		backgroundImageLabel = new JLabel(new ImageIcon(backgroundImage));

		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {

		if (this.flightFound == true) {
			foundComponents();
		} else
			notFoundComponents();

	}

	public void foundComponents() {

		String FLIGHT_SEARCH_SPECIFIC = "SELECT * FROM tickets " + "WHERE from_location = '" + this.getFromDestination()
				+ "' && " + "to_location = '" + this.getToDestination() + "' && " + "CAST(departure AS DATE) = '"
				+ this.getDeparture() + "' && " + "CAST(arrival AS DATE) = '" + this.getArrival() + "' && "
				+ "return_option = " + this.getReturnOption() + " && " + "one_way_option = " + this.getOneWayOption()
				+ " && " + "class = '" + this.getClassSelection() + "'";

		if (this.getPrice() != 0.0)
			FLIGHT_SEARCH_SPECIFIC = FLIGHT_SEARCH_SPECIFIC + " && price <=" + this.getPrice() + " ORDER BY price";
		else
			FLIGHT_SEARCH_SPECIFIC = FLIGHT_SEARCH_SPECIFIC + " ORDER BY price";

		List<Ticket> ticketList = new ArrayList<Ticket>();

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(FLIGHT_SEARCH_SPECIFIC,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				ticketList.add(new Ticket(rs.getInt("id"), rs.getString("airline_company_code"),
						rs.getString("flight_number"), rs.getString("airline_company"), rs.getString("from_location"),
						rs.getString("from_airport"), rs.getString("to_location"), rs.getString("to_airport"),
						rs.getString("boarding"), rs.getString("departure"), rs.getString("arrival"),
						rs.getString("class"), rs.getString("gate"), rs.getDouble("price"), rs.getInt("return_option"),
						rs.getInt("one_way_option")));
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel panel = new JPanel();
		JScrollPane scrollBar = new JScrollPane(panel);
		int panelHeight = 167;
		int ticketPanelHeight = 95;
		int numberOfTickets;

		for (numberOfTickets = 0; numberOfTickets < ticketList.size() - 1; numberOfTickets++)
			panelHeight += ticketPanelHeight;

		panel.setPreferredSize(new Dimension(585, panelHeight + 9));
		panel.setLayout(null);

		backgroundImageLabel.setBounds(0, -19, 588, 60);
		add(backgroundImageLabel);

		backButton.setBounds(30, 9, 100, 25);
		panel.add(backButton);

		for (numberOfTickets = 0; numberOfTickets < ticketList.size(); numberOfTickets++)
			new TicketPanel(this, panel, numberOfTickets * 100, ticketList.get(numberOfTickets), user);

		getContentPane().add(scrollBar);

		setSize(620, panelHeight + 50);
		setMaximumSize(new Dimension(620, 800));
		setMinimumSize(new Dimension(620, 200));
	}

	public void notFoundComponents() {
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH_2));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setContentPane(new ImagePanel(backgroundImage));

		setLayout(null);

		foundLabel.setBounds(137, 10, 500, 25);
		add(foundLabel);

		foundLabel.setText("There are no available flights based on the data you have entered!");

		backButton.setBounds(245, 240, 150, 25);
		add(backButton);

		picLabel.setBounds(220, 30, 200, 200);
		add(picLabel);

		setSize(680, 330);
	}

	public void layoutFunctionality() {
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new SearchFlights(user);
					}
				});

			}
		});
	}

	public String getFromDestination() {
		return fromDestination;
	}

	public void setFromDestination(String fromDestination) {
		this.fromDestination = fromDestination;
	}

	public String getToDestination() {
		return toDestination;
	}

	public void setToDestination(String toDestination) {
		this.toDestination = toDestination;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public boolean getReturnOption() {
		return returnOption;
	}

	public void setReturnOption(boolean returnOption) {
		this.returnOption = returnOption;
	}

	public boolean getOneWayOption() {
		return oneWayOption;
	}

	public void setOneWayOption(boolean oneWayOption) {
		this.oneWayOption = oneWayOption;
	}

	public String getClassSelection() {
		return classSelection;
	}

	public void setClassSelection(String classSelection) {
		this.classSelection = classSelection;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
