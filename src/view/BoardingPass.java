package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.zxing.WriterException;

import controller.QRCodeGenerator;
import model.Ticket;
import model.User;

public class BoardingPass extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3858778252860273701L;

	private Ticket ticket;
	private User user;

	private JLabel backgroundImageLabel;
	private JLabel airCompanyLabel;
	private JLabel fromAirportLabel;
	private JLabel boardingTimeLabel;
	private JLabel gateLabel;
	private JLabel fromDestinationLabel;
	private JLabel toDestinationLabel;
	private JLabel seatLabel;
	private JLabel departureTimeLabel;
	private JLabel fromDestinationRightLabel;
	private JLabel toDestinationRightLabel;
	private JLabel departureTimeRightLabel;
	private JLabel airCompanyRightLabel;
	private JLabel flightNumberLabel;
	private JLabel flightNumberRightLabel;
	private JLabel classLabel;
	private JLabel qrCodeLabel;
	private JLabel qrCodeRightLabel;
	private JLabel dateLabel;
	private JLabel userLabel;
	private JLabel userRightLabel;

	private BufferedImage backgroundImage;
	private BufferedImage qrCode;

	private static final String IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\boarding_pass.png";

	private static final String QR_CODE_IMAGE_PATH = "D:\\Facultate\\Licenta\\Proiect de licenta\\img\\qr_code.png";

	public BoardingPass(Ticket ticket, User user) {
		super("Your boarding pass");
		
		this.ticket = ticket;
		this.setUser(user);

		try {
			QRCodeGenerator.generateQRCodeImage(Integer.toString(ticket.hashCode()), 100, 100, QR_CODE_IMAGE_PATH);
		} catch (WriterException e) {
			System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
		}

		try {
			backgroundImage = ImageIO.read(new File(IMAGE_PATH));
			qrCode = ImageIO.read(new File(QR_CODE_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat systemDateFormat = new SimpleDateFormat("dd MMMM yyyy");

		Date arrivalDate;
		Date departureDate;
		Date boardingDate;
		String arrivalTime;
		String departureTime;
		String boardingTime;
		String systemDate = systemDateFormat.format(new Date());

		try {
			arrivalDate = dateFormat.parse(ticket.getArrival());
			arrivalTime = timeFormat.format(arrivalDate);
			departureDate = dateFormat.parse(ticket.getDeparture());
			departureTime = timeFormat.format(departureDate);
			boardingDate = dateFormat.parse(ticket.getBoarding());
			boardingTime = timeFormat.format(boardingDate);

			backgroundImageLabel = new JLabel(new ImageIcon(backgroundImage));
			qrCodeLabel = new JLabel(new ImageIcon(qrCode));
			qrCodeRightLabel = new JLabel(new ImageIcon(qrCode));
			airCompanyLabel = new JLabel(ticket.getAirline_company());
			airCompanyRightLabel = new JLabel(ticket.getAirline_company());
			flightNumberRightLabel = new JLabel(ticket.getFlight_number());
			classLabel = new JLabel(ticket.getClass_selection().toUpperCase() + " CLASS");
			fromAirportLabel = new JLabel(ticket.getFrom_airport());
			boardingTimeLabel = new JLabel(boardingTime);
			gateLabel = new JLabel(ticket.getGate());
			fromDestinationRightLabel = new JLabel(ticket.getFrom_location() + " " + ticket.getFrom_airport());
			departureTimeRightLabel = new JLabel(departureTime);
			toDestinationRightLabel = new JLabel(ticket.getTo_location());
			flightNumberLabel = new JLabel(ticket.getFlight_number());
			fromDestinationLabel = new JLabel(ticket.getFrom_location() + " " + ticket.getFrom_airport());
			toDestinationLabel = new JLabel(ticket.getTo_location());
			departureTimeLabel = new JLabel(departureTime);
			seatLabel = new JLabel("B15");
			dateLabel = new JLabel(systemDate);
			userLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
			userRightLabel = new JLabel(user.getFirstName() + " " + user.getLastName());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		layoutComponents();

		setSize(560, 280);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void layoutComponents() {
		setLayout(null);

		airCompanyLabel.setBounds(10, 0, 200, 50);
		add(airCompanyLabel);

		flightNumberLabel.setBounds(40, 85, 200, 50);
		add(flightNumberLabel);

		fromDestinationLabel.setBounds(25, 115, 200, 50);
		add(fromDestinationLabel);

		toDestinationLabel.setBounds(240, 115, 200, 50);
		add(toDestinationLabel);

		departureTimeLabel.setBounds(215, 165, 200, 50);
		add(departureTimeLabel);

		seatLabel.setBounds(27, 165, 200, 50);
		add(seatLabel);

		fromAirportLabel.setBounds(135, 85, 200, 50);
		add(fromAirportLabel);

		boardingTimeLabel.setBounds(250, 85, 200, 50);
		add(boardingTimeLabel);

		fromDestinationRightLabel.setBounds(395, 85, 200, 50);
		add(fromDestinationRightLabel);

		departureTimeRightLabel.setBounds(490, 85, 200, 50);
		add(departureTimeRightLabel);

		toDestinationRightLabel.setBounds(395, 120, 200, 50);
		add(toDestinationRightLabel);

		gateLabel.setBounds(335, 85, 200, 50);
		add(gateLabel);

		classLabel.setBounds(165, 202, 200, 50);
		add(classLabel);

		dateLabel.setBounds(250, 45, 200, 50);
		add(dateLabel);

		userLabel.setBounds(25, 45, 200, 50);
		add(userLabel);

		airCompanyRightLabel.setBounds(390, 0, 200, 50);
		add(airCompanyRightLabel);

		userRightLabel.setBounds(395, 53, 200, 50);
		add(userRightLabel);

		flightNumberRightLabel.setBounds(485, 0, 200, 50);
		add(flightNumberRightLabel);

		qrCodeLabel.setBounds(305, 132, 70, 70);
		add(qrCodeLabel);

		qrCodeRightLabel.setBounds(465, 132, 70, 70);
		add(qrCodeRightLabel);

		backgroundImageLabel.setBounds(-7, -19, 560, 280);
		add(backgroundImageLabel);

	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
