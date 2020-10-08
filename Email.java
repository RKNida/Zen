package sendemailbysite;
//working
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.BodyPart; 
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
import javax.mail.util.ByteArrayDataSource; 
public class Email {

 public void send() throws Exception { 
  try (InputStream ip=Email.class.getClassLoader().getResourceAsStream("config.properties")){
	  //  FileReader reader=new FileReader("config.properties");  

	   Properties prop = new Properties(); 
   
   prop.load(ip);
	  String host =prop.getProperty("host");// "smtp.gmail.com";//hostname of the mail server
	  String from = prop.getProperty("from"); //from internet address
	  String to = prop.getProperty("to"); //to internet address
	  String password=prop.getProperty("password");
	  System.out.println(" prop "+host+" from "+from+"to"+to);
	   prop.put("mail.host", host);   
	   prop.put("mail.smtp.auth", "true");
	   prop.put("mail.smtp.starttls.enable", "true");
	   prop.put("mail.smtp.host", host);
	   prop.put("mail.smtp.port", "587");


   Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(from,password);
		}
	});
   
   // Define message 
   MimeMessage message = new MimeMessage(session);
   message.addHeaderLine("method=REQUEST");
   message.addHeaderLine("charset=UTF-8"); 
   message.addHeaderLine("component=VEVENT"); 
   message.setFrom(new InternetAddress(from)); 
   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
   message.setSubject("Meeting Request Using JavaMail"); 
   StringBuffer sb = new StringBuffer();  
   StringBuffer buffer = sb.append("BEGIN:VCALENDARn"+
     "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//ENn"+
     "VERSION:2.0n" +
     "METHOD:REQUESTn" + 
     "BEGIN:VEVENTn" +
     "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx.xx.comn" + 
     "ORGANIZER:MAILTO:xx.xx.comn" + 
     "DTSTART:20201008T134500Zn" + 
     "DTEND:20201008T140000Zn" + 
     "LOCATION:3N1 Conference roomn" +
     "TRANSP:OPAQUEn" +
     "SEQUENCE:0n" +
     "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100n" +
     " 000004377FE5C37984842BF9440448399EB02n" +
     "DTSTAMP:20201008T120102Zn" + 
     "CATEGORIES:Meetingn" +
     "DESCRIPTION:This is a test meeting request.checking using JAVA .Please ingore itnn" +
     "SUMMARY:Test meeting requestn" + 
     "PRIORITY:1n" +
     "CLASS:PUBLICn" + 
     "BEGIN:VALARMn" + 
     "TRIGGER:PT1440Mn" +
     "ACTION:DISPLAYn" + 
     "DESCRIPTION:Remindern" +
     "END:VALARMn" +
     "END:VEVENTn" +
   "END:VCALENDAR");  // Create the message part
   BodyPart messageBodyPart = new MimeBodyPart(); 
   // Fill the message  
   messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
   messageBodyPart.setHeader("Content-ID","calendar_message"); 
   messageBodyPart.setDataHandler(new DataHandler( new ByteArrayDataSource(buffer.toString(), "text/calendar")));//very important
   // Create a Multipart  
   Multipart multipart = new MimeMultipart();  
   // Add part one 
   multipart.addBodyPart(messageBodyPart);    
   // Put parts in message
   message.setContent(multipart);  
   // send message 
   Transport.send(message);
  } catch (MessagingException me) {
   me.printStackTrace(); 
  } catch (Exception ex) {
   ex.printStackTrace(); 
  } 
 } 
 
 
 /* @param args */
 public static void main(String[] args) { 
  try {
   Email email = new Email(); 
   email.send();
   System.out.println("meeting request is sent");
  } catch (Exception e) {
   e.printStackTrace(); 
  }  
 } 
}