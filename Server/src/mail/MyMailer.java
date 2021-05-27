package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyMailer implements AutoCloseable {

	private final String HOST = "smtp.gmail.com";
	private final String PORT = "587";
	private final String USERNAME = "devmail.ingsft@gmail.com";
	private final String PASSWORD = "Password123@";

	private final String MAIN_URL = "https://ingsft-signal.duckdns.org/";

	private Session session;
	private Transport transport;
	
	public static MyMailer instance;

	private MyMailer() throws MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.user", USERNAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth", "true");
		this.session = Session.getDefaultInstance(props);
		this.transport = session.getTransport("smtp");
		transport.connect(HOST, USERNAME, PASSWORD);
	}

	public void sendMail(String destinatario, String oggetto, String html) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(oggetto);
			message.setContent(html, "text/html; charset=utf-8");
			transport.sendMessage(message, message.getAllRecipients());
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public void sendMailVerifica(String destinatario, String hash) {
		String url = MAIN_URL + "confermaRegistrazione?m=" + destinatario + "&h=" + hash;
		TemplateMail template = TemplateMail.VERIFICA_UTENTE;
		String html = template.getHtml(url);
		this.sendMail(destinatario, template.getOggetto(), html);
	}
	
	public void sendMailSospeso(String destinatario, String hash) {
		String url = MAIN_URL + "Ciao " + destinatario + ",\n il tuo profilo è stato sospeso.";
		TemplateMail template = TemplateMail.UTENTE_SOSPESO;
		String html = template.getHtml(url);
		this.sendMail(destinatario, template.getOggetto(), html);
	}

	@Override
	public void close() throws Exception {
		transport.close();
	}

	public static MyMailer getIstance() {
		if (instance == null)
			try {
				instance = new MyMailer();
			} catch (MessagingException e) {
				e.printStackTrace();
				return null;
			}
		return instance;
	}

}
