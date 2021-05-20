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
		String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"> <head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> <title>Verify your email address</title> <style type=\"text/css\" rel=\"stylesheet\" media=\"all\"> /*<![CDATA[*/ *:not(br):not(tr):not(html){font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; -webkit-box-sizing: border-box; box-sizing: border-box}.bottone a{color: #000000!important; text-decoration: underline!important;}body{width: 100% !important; height: 100%; margin: 0; line-height: 1.4; background-color: #F5F7F9; color: #839197; -webkit-text-size-adjust: none}a{color: #414EF9}.email-wrapper{width: 100%; margin: 0; padding: 0; background-color: #F5F7F9}.email-content{width: 100%; margin: 0; padding: 0}.email-masthead{padding: 25px 0; text-align: center}.email-masthead_logo{max-width: 400px; border: 0}.email-masthead_name{font-size: 16px; font-weight: bold; color: #839197; text-decoration: none; text-shadow: 0 1px 0 white}.email-body{width: 100%; margin: 0; padding: 0; border-top: 1px solid #E7EAEC; border-bottom: 1px solid #E7EAEC; background-color: #FFF}.email-body_inner{width: 570px; margin: 0 auto; padding: 0}.email-footer{width: 570px; margin: 0 auto; padding: 0; text-align: center}.email-footer p{color: #839197}.body-action{width: 100%; margin: 30px auto; padding: 0; text-align: center}.body-sub{margin-top: 25px; padding-top: 25px; border-top: 1px solid #E7EAEC}.content-cell{padding: 35px}.align-right{text-align: right}h1{margin-top: 0; color: #292E31; font-size: 19px; font-weight: bold; text-align: left}h2{margin-top: 0; color: #292E31; font-size: 16px; font-weight: bold; text-align: left}h3{margin-top: 0; color: #292E31; font-size: 14px; font-weight: bold; text-align: left}p{margin-top: 0; color: #839197; font-size: 16px; line-height: 1.5em; text-align: left}p.sub{font-size: 12px}p.center{text-align: center}.button{display: inline-block; width: 200px; background-color: #FFF; border-radius: 3px;border: 1px solid;border-color: #414EF9; color: #414EF9; font-size: 15px; line-height: 45px; text-align: center; text-decoration: none; -webkit-text-size-adjust: none; mso-hide: all}@media only screen and (max-width: 600px){.email-body_inner, .email-footer{width: 100% !important}}@media only screen and (max-width: 500px){.button{width: 100% !important}}/*]]>*/ </style> </head> <body> <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td align=\"center\"> <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td class=\"email-masthead\"><a class=\"email-masthead_name\">Signal</a></td></tr><tr> <td class=\"email-body\" width=\"100%\"> <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td class=\"content-cell\"> <h1>Verifica il tuo indirizzo Email</h1> <p>Grazie per esserti registrato sulla nostra piattaforma. Il prossimo passo è quello di verificare il tuo indirizzo Email. Premi il bottone sottostante per concludere la procedura di registrazione.</p><table class=\"body-action\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td align=\"center\"> <div><a href=\""
				+ url
				+ "\" class=\"button\"><span class=\"bottone\">Conferma Registrazione</span></a></div></td></tr></table> </td></tr></table> </td></tr></table> </td></tr></table> </body></html>";
		this.sendMail(destinatario, "Conferma Registrazione", html);
	}

	@Override
	public void close() throws Exception {
		transport.close();
	}

	public static void main(String[] args) {
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
