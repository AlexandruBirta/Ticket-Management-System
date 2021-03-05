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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import model.DBConnection;

public class Reports extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2417456358627815539L;

	private JButton backButton;
	private JButton transactionsButton;
	private JButton ticketsButton;
	private JButton usersButton;
	private JButton numberOfTicketsButton;

	private JScrollPane transactionsScrollPane;
	private JTable transactionsTable;

	private JScrollPane ticketsScrollPane;
	private JTable ticketsTable;

	private JScrollPane usersScrollPane;
	private JTable usersTable;

	private JScrollPane numberOfTicketsScrollPane;
	private JTable numberOfTicketsTable;

	private BufferedImage backgroundImage;

	private static final String SHOW_TRANSACTIONS_STATEMENT = "SELECT * FROM transactions";
	private static final String SHOW_USERS_STATEMENT = "SELECT * FROM users";
	private static final String SHOW_TICKETS_STATEMENT = "SELECT * FROM tickets";
	private static final String SHOW_NUMBER_OF_TICKETS_STATEMENT = "SELECT  t1.user_id, t1.user_username, t1.user_email, t1.user_first_name, t1.user_last_name, count(DISTINCT t1.transaction_id), sum(t1.ticket_price)"
			+ " FROM transactions t1" + " INNER JOIN transactions t2" + " ON t1.transaction_id = t2.transaction_id"
			+ " GROUP BY  t1.user_id" + " ORDER BY count(DISTINCT t1.transaction_id) DESC";
	private static final String BACKGROUND_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\reports_background.jpg";

	public Reports() {
		super("Reports");

		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setContentPane(new ImagePanel(backgroundImage));

		backButton = new JButton("Back");
		transactionsButton = new JButton("Show transactions");
		usersButton = new JButton("Show users");
		ticketsButton = new JButton("Show tickets");
		numberOfTicketsButton = new JButton("<html>Show number of <br/> tickets bought</html>");

		layoutComponents();
		layoutFunctionality();

		setResizable(false);
		setSize(1200, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void layoutComponents() {
		setLayout(null);

		int gridX = 80;
		int gridY = 60;

		makeTransactionsTable();
		makeUsersTable();
		makeTicketsTable();
		makeNumberOfTicketsTable();

		///////////// row /////////////

		transactionsButton.setBounds(gridX, gridY, 150, 25);
		add(transactionsButton);

		gridY += 60;

		///////////// row /////////////

		usersButton.setBounds(gridX, gridY, 150, 25);
		add(usersButton);

		gridY += 60;

		///////////// row /////////////

		ticketsButton.setBounds(gridX, gridY, 150, 25);
		add(ticketsButton);

		gridY += 60;

		///////////// row /////////////

		numberOfTicketsButton.setBounds(gridX, gridY - 12, 150, 50);
		add(numberOfTicketsButton);

		gridY += 60;

		///////////// row /////////////

		backButton.setBounds(gridX, gridY, 150, 25);
		add(backButton);

		gridY += 60;

	}

	private void layoutFunctionality() {
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new AdminPanel();
					}
				});
			}

		});

		transactionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				transactionsScrollPane.setVisible(true);
				usersScrollPane.setVisible(false);
				ticketsScrollPane.setVisible(false);
				numberOfTicketsScrollPane.setVisible(false);

			}

		});

		usersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				transactionsScrollPane.setVisible(false);
				usersScrollPane.setVisible(true);
				ticketsScrollPane.setVisible(false);
				numberOfTicketsScrollPane.setVisible(false);

			}

		});

		ticketsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				transactionsScrollPane.setVisible(false);
				usersScrollPane.setVisible(false);
				ticketsScrollPane.setVisible(true);
				numberOfTicketsScrollPane.setVisible(false);
			}

		});

		numberOfTicketsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				transactionsScrollPane.setVisible(false);
				usersScrollPane.setVisible(false);
				ticketsScrollPane.setVisible(false);
				numberOfTicketsScrollPane.setVisible(true);
			}

		});

	}

	public void makeTransactionsTable() {

		String[] transactionsColumns = { "ID", "Transaction date", "User ID", "Username", "First name", "Last name",
				"Country", "Email", "Phone number", "Ticket ID", "Airline company", "Flight number", "From location",
				"From airport", "To location", "To airport", "Class", "Ticket price" };

		Vector<String> transactions = new Vector<String>();

		for (String column : transactionsColumns)
			transactions.add(column);

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SHOW_TRANSACTIONS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			transactionsTable = new JTable(buildTableModel(rs, transactions));

			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(7).setPreferredWidth(160);
		transactionsTable.getColumnModel().getColumn(8).setPreferredWidth(140);
		transactionsTable.getColumnModel().getColumn(9).setPreferredWidth(50);
		transactionsTable.getColumnModel().getColumn(10).setPreferredWidth(160);
		transactionsTable.getColumnModel().getColumn(11).setPreferredWidth(60);
		transactionsTable.getColumnModel().getColumn(12).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(13).setPreferredWidth(50);
		transactionsTable.getColumnModel().getColumn(14).setPreferredWidth(100);
		transactionsTable.getColumnModel().getColumn(15).setPreferredWidth(50);
		transactionsTable.getColumnModel().getColumn(16).setPreferredWidth(70);
		transactionsTable.getColumnModel().getColumn(17).setPreferredWidth(70);
		transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		transactionsScrollPane = new JScrollPane(transactionsTable);
		transactionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		transactionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		transactionsScrollPane.setBounds(300, 60, 840, 480);
		add(transactionsScrollPane);
		transactionsScrollPane.setVisible(true);
	}

	public void makeUsersTable() {

		String[] usersColumns = { "ID", "Username", "Password", "First name", "Last name", "Email", "Country",
				"Phone number", "Administrator", "Date of birth" };

		Vector<String> users = new Vector<String>();

		for (String column : usersColumns)
			users.add(column);

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SHOW_USERS_STATEMENT, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			usersTable = new JTable(buildTableModel(rs, users));

			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		usersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		usersTable.getColumnModel().getColumn(1).setPreferredWidth(140);
		usersTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		usersTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		usersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		usersTable.getColumnModel().getColumn(5).setPreferredWidth(150);
		usersTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		usersTable.getColumnModel().getColumn(7).setPreferredWidth(80);
		usersTable.getColumnModel().getColumn(8).setPreferredWidth(40);
		usersTable.getColumnModel().getColumn(9).setPreferredWidth(80);
		usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		usersScrollPane = new JScrollPane(usersTable);
		usersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		usersScrollPane.setBounds(300, 60, 840, 480);
		add(usersScrollPane);
		usersScrollPane.setVisible(false);
	}

	public void makeTicketsTable() {

		String[] ticketsColumns = { "ID", "Airline company code", "Flight number", "Airline company", "From location",
				"From airport", "To location", "To airport", "Boardig", "Departure", "Arrival", "Class", "Gate",
				"Price", "Return option", "One-way option" };

		Vector<String> tickets = new Vector<String>();

		for (String column : ticketsColumns)
			tickets.add(column);

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SHOW_TICKETS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			ticketsTable = new JTable(buildTableModel(rs, tickets));

			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		ticketsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		ticketsTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		ticketsTable.getColumnModel().getColumn(2).setPreferredWidth(60);
		ticketsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		ticketsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		ticketsTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		ticketsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		ticketsTable.getColumnModel().getColumn(7).setPreferredWidth(50);
		ticketsTable.getColumnModel().getColumn(8).setPreferredWidth(140);
		ticketsTable.getColumnModel().getColumn(9).setPreferredWidth(140);
		ticketsTable.getColumnModel().getColumn(10).setPreferredWidth(140);
		ticketsTable.getColumnModel().getColumn(11).setPreferredWidth(60);
		ticketsTable.getColumnModel().getColumn(12).setPreferredWidth(50);
		ticketsTable.getColumnModel().getColumn(13).setPreferredWidth(70);
		ticketsTable.getColumnModel().getColumn(14).setPreferredWidth(50);
		ticketsTable.getColumnModel().getColumn(15).setPreferredWidth(50);
		ticketsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		ticketsScrollPane = new JScrollPane(ticketsTable);
		ticketsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		ticketsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		ticketsScrollPane.setBounds(300, 60, 840, 480);
		add(ticketsScrollPane);
		ticketsScrollPane.setVisible(false);
	}

	public void makeNumberOfTicketsTable() {

		String[] numberOfTicketsColumns = { "User ID", "Username", "Email", "First name", "Last name",
				"Number of tickets bought", "Total spent" };

		Vector<String> numberOfTickets = new Vector<String>();

		for (String column : numberOfTicketsColumns)
			numberOfTickets.add(column);

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SHOW_NUMBER_OF_TICKETS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			numberOfTicketsTable = new JTable(buildTableModel(rs, numberOfTickets));

			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		numberOfTicketsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		numberOfTicketsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		numberOfTicketsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		numberOfTicketsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		numberOfTicketsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		numberOfTicketsTable.getColumnModel().getColumn(5).setPreferredWidth(120);
		numberOfTicketsTable.getColumnModel().getColumn(6).setPreferredWidth(240);

		numberOfTicketsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		numberOfTicketsScrollPane = new JScrollPane(numberOfTicketsTable);
		numberOfTicketsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		numberOfTicketsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		numberOfTicketsScrollPane.setBounds(300, 60, 840, 480);
		add(numberOfTicketsScrollPane);
		numberOfTicketsScrollPane.setVisible(false);
	}

	public static DefaultTableModel buildTableModel(ResultSet rs, Vector<String> columnNames) throws SQLException {

		ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();

		int columnCount = metaData.getColumnCount();

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);
	}
}
