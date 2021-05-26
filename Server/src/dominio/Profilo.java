package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.Connector;
import exceptions.EmailNotExistingException;

public class Profilo {

	private int id;
	private String email;
	private String nome;
	private String cognome;
	private String identificatore;
	private boolean sospeso;
	private float valutazione;
	private RuoloUtente ruolo;
	private Comune comuneResidenza;
	private List<CartaVirtuale> carteVirtuali;

	public Profilo(int id, String email, String nome, String cognome, String identificatore, boolean sospeso,
			float valutazione, RuoloUtente ruolo, Comune comuneResidenza, List<CartaVirtuale> carteVirtuali) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.identificatore = identificatore;
		this.sospeso = sospeso;
		this.valutazione = valutazione;
		this.ruolo = ruolo;
		this.comuneResidenza = comuneResidenza;
		this.carteVirtuali = carteVirtuali;
	}

	public static Profilo getProfiloByEmail(Connector conn, String email)
			throws SQLException, EmailNotExistingException {
		PreparedStatement ps = conn.prepare("SELECT * FROM Utenti WHERE email = ?");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if (!rs.first())
			throw new EmailNotExistingException("L'utente non esiste");

		return new Profilo(rs.getInt("id"), rs.getString("email"), rs.getString("nome"), rs.getString("cognome"),
				rs.getString("identificatore"), rs.getBoolean("sospeso"), rs.getFloat("reputazione"),
				RuoloUtente.values()[rs.getInt("tipoUtente")], null, null);
	}
	
	public static Profilo getProfiloById(Connector conn, int id)
			throws SQLException, EmailNotExistingException {
		PreparedStatement ps = conn.prepare("SELECT * FROM Utenti WHERE id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (!rs.first())
			throw new EmailNotExistingException("L'utente non esiste");

		return new Profilo(rs.getInt("id"), rs.getString("email"), rs.getString("nome"), rs.getString("cognome"),
				rs.getString("identificatore"), rs.getBoolean("sospeso"), rs.getFloat("reputazione"),
				RuoloUtente.values()[rs.getInt("tipoUtente")], null, null);
	}

	public static Profilo of(Connector conn, String email, String password, String nome, String cognome,
			String identificatore, String comune, int tipoUtente) throws SQLException {
		PreparedStatement ps = conn
				.prepare("INSERT INTO Utenti (email, password, nome, cognome, identificatore) VALUES (?,?,?,?,?)");
		ps.setString(1, email);
		ps.setString(2, password);
		ps.setString(3, nome);
		ps.setString(4, cognome);
		ps.setString(5, identificatore);
		ps.execute();
		try {
			return Profilo.getProfiloByEmail(conn, email);
		} catch (EmailNotExistingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getIdentificatore() {
		return identificatore;
	}

	public boolean isSospeso() {
		return sospeso;
	}

	public float getValutazione() {
		return valutazione;
	}

	public RuoloUtente getRuolo() {
		return ruolo;
	}

	public void sospendi() {
		this.sospeso = true;
	}

	public float inserisciValutazione(int valutazione) {
		// nel dominio è un float ma per me essendo stelline ha senso
		// metterla come int ¯\__(ツ)__/¯
		this.valutazione = (this.valutazione + valutazione) / (float) 2;
		return this.valutazione;
	}

	public Comune getComuneResidenza() {
		return comuneResidenza;
	}

	public List<CartaVirtuale> getCarteVirtuali() {
		return carteVirtuali;
	}

	public int getTotalPoints() {
		int res = 0;
		for (CartaVirtuale card : this.carteVirtuali)
			res += card.getSaldo();

		return res;
	}

	public boolean addPoint(Comune comune, int points) {
		boolean res = false;

		for (CartaVirtuale card : this.carteVirtuali)
			if (card.getComune().equals(comune)) {
				card.aggiungiPunti(points);
				res = true;
			}

		return res;
	}

}
