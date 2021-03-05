package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.InsertTransaction;
import model.Ticket;
import model.User;

public class TicketPanel {

	private int gridY;
	private JPanel panel;
	private JFrame frame;

	private User user;
	private Ticket ticket;

	private JLabel airlineCompany;
	private JLabel departureLabel;
	private JLabel priceLabel;
	private JButton selectButton;
	private JLabel backgroundImageLabel;
	private JLabel classLabel;

	private BufferedImage backgroundImage;

	private static final String IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\ticket_result_background.jpg";

	public TicketPanel(JFrame frame, JPanel panel, int gridY, Ticket ticket, User user) {
		this.gridY = gridY;
		this.frame = frame;
		this.panel = panel;
		this.ticket = ticket;
		this.user = user;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		try {
			Date arrivalDate;
			Date departureDate;
			String arrivalTime;
			String departureTime;

			arrivalDate = dateFormat.parse(ticket.getArrival());
			arrivalTime = timeFormat.format(arrivalDate);
			departureDate = dateFormat.parse(ticket.getDeparture());
			departureTime = timeFormat.format(departureDate);

			try {
				backgroundImage = ImageIO.read(new File(IMAGE_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}

			airlineCompany = new JLabel(ticket.getAirline_company());
			departureLabel = new JLabel(departureTime + " " + ticket.getFrom_airport() + " - " + arrivalTime + " "
					+ ticket.getTo_airport());
			priceLabel = new JLabel("$" + ticket.getPrice());
			selectButton = new JButton("Select");
			backgroundImageLabel = new JLabel(new ImageIcon(backgroundImage));
			classLabel = new JLabel(ticket.getClass_selection().toUpperCase() + " CLASS");

			layoutComponents();

			layoutFunctionality();

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void layoutComponents() {
		airlineCompany.setBounds(30, 65 + this.getGridY(), 100, 25);
		// airlineCompany.setForeground(Color.WHITE);
		departureLabel.setBounds(200, 65 + this.getGridY(), 400, 25);
		classLabel.setBounds(235, 95 + this.getGridY(), 400, 25);
		// departureLabel.setForeground(Color.WHITE);
		priceLabel.setBounds(470, 65 + this.getGridY(), 100, 25);
		// priceLabel.setForeground(Color.WHITE);
		selectButton.setBounds(470, 100 + this.getGridY(), 100, 25);
		backgroundImageLabel.setBounds(0, 40 + this.getGridY(), 620, 110);

		panel.add(airlineCompany);
		panel.add(departureLabel);
		panel.add(classLabel);
		panel.add(priceLabel);
		panel.add(selectButton);
		panel.add(backgroundImageLabel);
	}

	public void layoutFunctionality() {
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InsertTransaction.insertTransaction(user, ticket);

				frame.dispose();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new BoardingPass(ticket, user);
					}
				});

			}
		});
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
