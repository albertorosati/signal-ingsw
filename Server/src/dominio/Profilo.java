package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import exceptions.EmailNotExistingException;
import json.RespState;
import json.Response;

public class Profilo {

	private int id;
	private String email;
	private String nome;
	private String cognome;
	private String identificatore;
	private boolean sospeso;
	private float valutazione;
	private int numVal;
	private RuoloUtente ruolo;
	private Comune comuneResidenza;
	private List<CartaVirtuale> carteVirtuali;
	
	private Connector connector;
	
	//----------------------------------------------COSTRUTTORI-----------------------------------------------------

	public Profilo(int id, String email, String nome, String cognome, String identificatore, boolean sospeso,
			float valutazione, RuoloUtente ruolo, Comune comuneResidenza, List<CartaVirtuale> carteVirtuali) throws SQLException {
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
		this.numVal=0;
		connector = Connector.getInstance();
	}
	
	//null Comune di residenza --> must per limitazione di conservazione dei dati
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
		
		int idUser=rs.getInt("id");
		
		return new Profilo(idUser, rs.getString("email"), rs.getString("nome"), rs.getString("cognome"),
				rs.getString("identificatore"), rs.getBoolean("sospeso"), rs.getFloat("reputazione"),
				RuoloUtente.values()[rs.getInt("tipoUtente")], null, getCarte(conn,idUser));
	}
	
	
	public Profilo(String email, String password, String nome, String cognome,
			String identificatore, String comune, int tipoUtente) {
		
		try {
			this.connector=Connector.getInstance();
		
			PreparedStatement ps = connector.prepareReturn("INSERT INTO Utenti (email, password, nome, cognome, identificatore) "
					+ "VALUES (?,?,?,?,?)");
			
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, nome);
			ps.setString(4, cognome);
			ps.setString(5, identificatore);
			ps.execute();
			
			ResultSet rs=ps.getGeneratedKeys();
			this.id=rs.getInt("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}

	public static Profilo of(Connector conn, String email, String password, String nome, String cognome,
			String identificatore, String comune, int tipoUtente) throws SQLException {
		PreparedStatement ps = conn.prepareReturn("INSERT INTO Utenti (email, password, nome, cognome, identificatore) VALUES (?,?,?,?,?)");
		ps.setString(1, email);
		ps.setString(2, password);
		ps.setString(3, nome);
		ps.setString(4, cognome);
		ps.setString(5, identificatore);
		ps.execute();
		
		ResultSet rs = ps.getGeneratedKeys();
		
		
		try {
			return Profilo.getProfiloByEmail(conn, email);
		} catch (EmailNotExistingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//----------------------------------------------METODI-----------------------------------------------------
	
	public Response toResponse() {
		Response res=new Response();
			
		res.setState(RespState.SUCCESS);
		res.setEmail(this.getEmail());
		res.setNome(this.getNome());
		res.setCognome(this.cognome);
		res.setIdentificatore(this.identificatore);
		res.setSospeso(this.sospeso);
		res.setReputazione(this.valutazione);
		res.setTipoUtente(this.getRuolo());
		//carteVirtuali
		
		return res;
	}	
	
	private static List<CartaVirtuale> getCarte(Connector conn,int idUtente) {
		List<CartaVirtuale> list = new ArrayList<>();

		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = conn.prepare("SELECT * FROM Carte WHERE utente = ?");
			ps.setInt(1, idUtente);
			rs = ps.executeQuery();

			while (rs.next()) {
				Comune c = new Comune(rs.getString("name"), rs.getString("stemma"), rs.getString("foto"));
				list.add(new CartaVirtuale(rs.getInt("id"), c));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return list;
	}
	
	
	
	public Segnalazione[] getMySegnalazioni() {
		List<Segnalazione> list= new ArrayList<>();

		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = connector.prepare("SELECT id FROM Seganlazioni WHERE autore = ?");
			ps.setInt(1, this.id);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(Segnalazione.getById(connector, rs.getInt("id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return list.toArray(new Segnalazione[0]);
	}
	
	
	public int getTotalSegnalazioniEffettute() {
		int res=0;
		PreparedStatement ps;
		ResultSet rs;

		try {
			ps = connector.prepare("SELECT COUNT(*) AS Total FROM Segnalazioni WHERE autore = ? ;");	
			ps.setInt(1, this.id);
			rs=ps.executeQuery();
			
			res=rs.getInt("Total");
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public int getTotalSegnalazioniRisolte() {
		int res=0;
		PreparedStatement ps;
		ResultSet rs;

		try {
			//query da rivedere
			ps = connector.prepare("SELECT COUNT(*) AS Total FROM Segnalazioni WHERE assegnatario = ? AND stato = 7 ;");	
			ps.setInt(1, this.id);
			rs=ps.executeQuery();
			
			res=rs.getInt("Total");
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	

	public void sospendi() {
		this.sospeso = true;
		
		PreparedStatement ps;

		try {
			//update Utenti
			ps = connector.prepare("UPDATE Utenti SET sospeso = ? WHERE id = ? ;");	
			ps.setBoolean(1, true);
			ps.setInt(2, this.id);
			ps.execute();
			
			//update visibile
			ps = connector.prepare("UPDATE Segnalazioni SET visibile = false WHERE autore = ? AND stato < 5;");	
			ps.setInt(1,this.id);
			ps.execute();
			
			//CacheSospeso -- dovremmo fare un insert or replace ma facciamo finta di no
			ps = connector.prepare("INSERT into CacheSospensione (email,sospeso) VALUES (?,?) ;");	
			ps.setString(1, this.email);
			ps.setBoolean(2, true);
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public float inserisciValutazione(int valutazione) {
		// nel dominio è un float ma per me essendo stelline ha senso		 
		// metterla come int ¯\__(ツ)__/¯		
		// --> int input  ; output --> float media()
		this.valutazione=this.calculateVal(valutazione);
		
		PreparedStatement ps;

		try {
			ps = connector.prepare("UPDATE Utenti SET (reputazione,numVal) = (?,?) WHERE id = ? ;");	
			ps.setFloat(1,this.valutazione);
			ps.setInt(2,this.numVal);
			ps.setInt(3, this.id);
			ps.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.valutazione;
	}
	
	private float calculateVal(int valutazione) {
		return ((this.valutazione*this.numVal)+valutazione)/++this.valutazione;
	}


	public int getTotalPoints() {
		int res = 0;
		
		//update val
		this.carteVirtuali=Profilo.getCarte(connector, this.id);

		for (CartaVirtuale card : this.carteVirtuali)
			res += card.getSaldo();
	
		return res;
	}
	
	public int getPoint(Comune comune) {
		int res=0;
		
		//update val
		this.carteVirtuali=Profilo.getCarte(connector, this.id);
				
		for (CartaVirtuale c : this.carteVirtuali)
			if(c.getComuneComune().getNome().compareTo(comune.getNome())==0)
				return c.getSaldo();
				
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
	
	//--------------------------------------GETTERS-----------------------------------------------------
	
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
	
	public Comune getComuneResidenza() {
		return comuneResidenza;
	}

	public List<CartaVirtuale> getCarteVirtuali() {
		return carteVirtuali;
	}

}
