import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//working



public class SendEmail {

	public static void main(String[] args) throws IOException {
		//authentication info
		
		try(InputStream ip=SendEmail.class.getClassLoader().getResourceAsStream("config.properties")){
			Properties prop = new Properties(); 
			   prop.load(ip);
				  String host =prop.getProperty("host");//hostname of the mail server
				  String from = prop.getProperty("from"); //from internet address
				  String to = prop.getProperty("to"); //to internet address
				  String password=prop.getProperty("password");
				  System.out.println(" prop "+host+" from "+from+"to"+to);
				 
				  prop.put("mail.smtp.auth", "true");
					prop.put("mail.smtp.starttls.enable", "true");
					//properties.put("mail.smtp.host", "outlook.office365.com");
					prop.put("mail.smtp.host", host);
					prop.put("mail.smtp.port", "587");
					Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(from,password);
						}
					});
			
	
		
		
		
	
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject("Subject Line");
			
			
			msg.setText("Hi, The test mail is working.");
			
			Transport.send(msg);
			System.out.println("Sent message");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
