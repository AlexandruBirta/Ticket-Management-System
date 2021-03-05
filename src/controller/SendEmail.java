package controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;  
  
public class SendEmail 
{  
	public static void sendMail(String recipient, String code) {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "ticketsystemhelper@gmail.com";
		String passoword = "Ticketpassword@123";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, passoword);
			}
		});
		
		Message message = prepareMessage(session, myAccountEmail, recipient, code);
		
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	
	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recipient, String code) {
		
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(myAccountEmail));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				message.setSubject("Reset your password");
				message.setText("Hello user!\n\nHere's your reset code: " + code);
				return message;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	
		return null;
	}
} 
