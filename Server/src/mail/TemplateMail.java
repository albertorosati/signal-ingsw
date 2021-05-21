package mail;

public enum TemplateMail {

	// Variabile {{url}}
	VERIFICA_UTENTE("Verifica Email", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"> <head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> <title>Verify your email address</title> <style type=\"text/css\" rel=\"stylesheet\" media=\"all\"> /*<![CDATA[*/ *:not(br):not(tr):not(html){font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; -webkit-box-sizing: border-box; box-sizing: border-box}.bottone a{color: #000000!important; text-decoration: underline!important;}body{width: 100%% !important; height: 100%%; margin: 0; line-height: 1.4; background-color: #F5F7F9; color: #839197; -webkit-text-size-adjust: none}a{color: #414EF9}.email-wrapper{width: 100%%; margin: 0; padding: 0; background-color: #F5F7F9}.email-content{width: 100%%; margin: 0; padding: 0}.email-masthead{padding: 25px 0; text-align: center}.email-masthead_logo{max-width: 400px; border: 0}.email-masthead_name{font-size: 16px; font-weight: bold; color: #839197; text-decoration: none; text-shadow: 0 1px 0 white}.email-body{width: 100%%; margin: 0; padding: 0; border-top: 1px solid #E7EAEC; border-bottom: 1px solid #E7EAEC; background-color: #FFF}.email-body_inner{width: 570px; margin: 0 auto; padding: 0}.email-footer{width: 570px; margin: 0 auto; padding: 0; text-align: center}.email-footer p{color: #839197}.body-action{width: 100%%; margin: 30px auto; padding: 0; text-align: center}.body-sub{margin-top: 25px; padding-top: 25px; border-top: 1px solid #E7EAEC}.content-cell{padding: 35px}.align-right{text-align: right}h1{margin-top: 0; color: #292E31; font-size: 19px; font-weight: bold; text-align: left}h2{margin-top: 0; color: #292E31; font-size: 16px; font-weight: bold; text-align: left}h3{margin-top: 0; color: #292E31; font-size: 14px; font-weight: bold; text-align: left}p{margin-top: 0; color: #839197; font-size: 16px; line-height: 1.5em; text-align: left}p.sub{font-size: 12px}p.center{text-align: center}.button{display: inline-block; width: 200px; background-color: #FFF; border-radius: 3px;border: 1px solid;border-color: #414EF9; color: #414EF9; font-size: 15px; line-height: 45px; text-align: center; text-decoration: none; -webkit-text-size-adjust: none; mso-hide: all}@media only screen and (max-width: 600px){.email-body_inner, .email-footer{width: 100%% !important}}@media only screen and (max-width: 500px){.button{width: 100%% !important}}/*]]>*/ </style> </head> <body> <table class=\"email-wrapper\" width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td align=\"center\"> <table class=\"email-content\" width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td class=\"email-masthead\"><a class=\"email-masthead_name\">Signal</a></td></tr><tr> <td class=\"email-body\" width=\"100%%\"> <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td class=\"content-cell\"> <h1>Verifica il tuo indirizzo Email</h1> <p>Grazie per esserti registrato sulla nostra piattaforma. Il prossimo passo è quello di verificare il tuo indirizzo Email. Premi il bottone sottostante per concludere la procedura di registrazione.</p><table class=\"body-action\" align=\"center\" width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td align=\"center\"> <div><a href=\"%s\" class=\"button\"><span class=\"bottone\">Conferma Registrazione</span></a></div></td></tr></table> </td></tr></table> </td></tr></table> </td></tr></table> </body></html>");
	
	private String oggetto, html;

	private TemplateMail(String oggetto, String html) {
		this.oggetto = oggetto;
		this.html = html;
	}

	public String getOggetto() {
		return oggetto;
	}
	
	public String getHtml() {
		return html;
	}

	//Sostituisce le variabili %s con quelle passate come argument
	public String getHtml(String... vars) {
		return String.format(this.getHtml(), (Object[]) vars);
	}

}
