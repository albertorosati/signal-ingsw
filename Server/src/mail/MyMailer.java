package mail;

import org.simplejavamail.api.email.CalendarMethod;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class MyMailer {
	
	private final String HOST = "smtp.gmail.com";
	private final int PORT = 465;
	private final String USERNAME = "devmail.ingsft@gmail.com";
	private final String PASSWORD = "Password123@";
	
	private Mailer mailer;

	public MyMailer() {
		this.mailer = MailerBuilder.withSMTPServer(HOST, PORT,  USERNAME, PASSWORD).buildMailer();
	}
	
	public void sendMail() {
		Email email = EmailBuilder.startingBlank()
		          .to("lolly.pop@somemail.com")
		          .withSubject("hey")
		          .withHTMLText("<img src='cid:wink1'><b>We should meet up!</b><img src='cid:wink2'>")
		          .buildEmail();

		mailer.sendMail(email);
	}
	
}
