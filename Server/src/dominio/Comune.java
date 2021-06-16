package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

public class Comune {
	private int id;
	private String nome;
	private String stemma;
	private String foto;

	private Connector conn;

	public Comune(String nome, String stemma, String foto) {
		super();
		this.nome = nome;
		this.stemma = stemma;
		this.foto = foto;
	}

	public Comune(String nome) {
		super();
		this.nome = nome;
		this.stemma = null;
		this.foto = null;

		PreparedStatement ps;
		ResultSet rs;

		// check if already exists and load stemma,foto
		try {
			this.conn = Connector.getInstance();

			ps = conn.prepare("SELECT * FROM Comuni WHERE name = ? ; ");
			ps.setString(1, nome);
			rs = ps.executeQuery();

			if (!rs.first()) {
				// create
				ps = conn.prepareReturn("INSERT INTO Comuni (name, stemma) VALUES (?, ?) ; ");
				ps.setString(1, nome);
				ps.setString(2, "https://upload.wikimedia.org/wikipedia/it/6/6e/Bologna-Stemma.png");
				ps.execute();

				rs = ps.getGeneratedKeys();
				this.id = rs.getInt("id");

			} else {
				this.stemma = rs.getString("stemma");
				this.foto = rs.getString("foto");
				this.id = rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private int getId() {
		return this.id;
	}

	public String getStemma() {
		return stemma;
	}

	public String getFoto() {
		return foto;
	}

	public String getNome() {
		return this.nome;
	}
}
