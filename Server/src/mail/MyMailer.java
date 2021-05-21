package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
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

	public MyMailer() throws MessagingException {
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

	@Override
	public void close() throws Exception {
		transport.close();
	}

	public static void main(String[] args) {
		System.out.println(TemplateMail.VERIFICA_UTENTE.getHtml("hashio"));
		System.exit(-1);
		try (MyMailer mm = new MyMailer()) {
			mm.sendMailVerifica("alberto.rosati99@gmail.com", "udhfih2398hj9afjhsd9fj");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
