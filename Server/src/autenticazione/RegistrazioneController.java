package autenticazione;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import database.Connector;
import dominio.Profilo;
import mail.MyMailer;

public class RegistrazioneController implements IRegistrazione {

	private MyMailer mailer;
	private Connector conn;

	private List<Character> mesiCF = List.of('A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T');

	public RegistrazioneController() throws SQLException {
		mailer = MyMailer.getIstance();
		conn = Connector.getInstance();
	}

	// Verifica del Codice Fiscale
	@Override
	public boolean verificaID(String id) {
		// esempio: RSSMRA85T10A562S
		// il codice fiscale è lungo esattamente 16 caratteri
		if (id.length() != 16)
			return false;

		int birthDay;
		Month birthMonth;
		int birthYear;
		try {
			birthDay = Integer.parseInt(id.substring(9, 11));
			birthYear = Integer.parseInt(id.substring(6, 8));
		} catch (NumberFormatException e) {
			return false;
		}

		// se è una donna, sottraiamo i 40
		if (birthDay > 40)
			birthDay -= 40;

		// sistemo l'anno
		if (birthYear < 5)
			birthYear += 2000;
		else
			birthYear += 1900;

		// get del mese
		if (!mesiCF.contains(id.substring(8, 9).charAt(0)))
			return false;

		birthMonth = Month.of(mesiCF.indexOf(id.substring(8, 9).charAt(0)) + 1);

		LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
		
		/*
		 * Il carattere di controllo viene determinato nel modo seguente: si sommano i
		 * valori di ciascuna delle cinque cifre di ordine dispari, partendo da
		 * sinistra; si raddoppia ogni cifra di ordine pari e, se il risultato è un
		 * numero di due cifre, esso si riduce ad una sola sommando la cifra relativa
		 * alle decine e quella relativa alle unità; si sommano quindi tutti i
		 * precedenti risultati; si determina il totale delle due somme di cui sopra; si
		 * sottrae da dieci la cifra relativa alle unità del precedente totale. Il
		 * carattere di controllo è la cifra relativa alle unità del risultato.
		 * 
		 */
		
		return birthDate.isBefore(LocalDate.now().minusYears(18));
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
			new URL("https://www.isvat.eu/IT/" + piva).openStream();
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
		System.out.print(rc.verificaID("RSTLRT99P07F158B"));
	}

}
