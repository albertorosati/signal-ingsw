package autenticazione;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import database.Connector;
import dominio.Profilo;
import mail.MyMailer;

public class RegistrazioneController implements IRegistrazione {

	private MyMailer mailer;
	private Connector conn;

	public RegistrazioneController() throws SQLException {
		mailer = MyMailer.getIstance();
		conn = Connector.getInstance();
	}

	// Verifica del Codice Fiscale
	@Override
	public boolean verificaID(String id) {
		
		return false;
	}

	@Override
	public boolean verificaP_IVA(String piva) throws IOException {
		// Una PIVA è formata solo da numeri
		for (char c : piva.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}

		// L'API endpoint restituisce un 404 se la VAT non è valida
		// quindi basta semplicemente verificare il codice HTTP
		// restituito dalla pagina
		try {
			URL url = new URL("https://www.isvat.eu/IT/" + piva);
			InputStreamReader reader = new InputStreamReader(url.openStream());
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	// Verifica che il codice fiscale non sia già presente all'interno del db
	@Override
	public boolean verificaAccount(String id) throws SQLException {
		PreparedStatement st = conn.prepare("SELECT * FROM Utenti WHERE identificativo = ?");
		st.setString(1, id);
		return st.executeQuery().first();
	}

	@Override
	public void inviaConferma(Profilo utente) throws SQLException {
		String hash = DigestUtils.sha256Hex(System.currentTimeMillis() + utente.getEmail() + "granellodisale");
		PreparedStatement st = conn.prepare("INSERT INTO ConfermeRegistrazioni (utente, hash) VALUES (?,?");
		st.setString(1, utente.getId().toString());
		st.setString(2, hash);
		mailer.sendMailVerifica(utente.getEmail(), hash);
	}

	@Override
	public void convalidaEmail(Profilo utente) throws SQLException {
		PreparedStatement ps = conn.prepare("DELETE FROM ConfermeRegistrazioni WHERE utente=?");
		ps.setString(1, utente.getId().toString());
		ps.execute();
		ps = conn.prepare("UPDATE Utenti SET confermato=1 WHERE id=?");
		ps.setString(1, utente.getId().toString());
		ps.execute();
	}

	@Override
	public void registra(Profilo utente, String hash_passwd) throws SQLException {
		PreparedStatement ps = conn
				.prepare("INSERT INTO Utenti (email, password, nome, cognome, identificatore) VALUES (?,?,?,?,?)");
		ps.setString(1, utente.getEmail());
		ps.setString(2, hash_passwd);
		ps.setString(3, utente.getNome());
		ps.setString(4, utente.getCognome());
		ps.setString(5, utente.getIdentificatore());
		ps.execute();
	}

	public static void main(String[] args) throws SQLException, IOException {
		RegistrazioneController rc = new RegistrazioneController();
		System.out.println(rc.verificaP_IVA("01131710376"));
	}

}
