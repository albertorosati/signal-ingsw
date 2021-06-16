package dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

public class Comune {
	private int id;
	private String nome;
	private Multimedia stemma;
	private Multimedia foto;
	
	private Connector conn;

	public Comune(String nome, Multimedia stemma, Multimedia foto) {
		super();
		this.nome = nome;
		this.stemma = stemma;
		this.foto = foto;
	}
	
	public Comune(String nome, String stemma, String foto) {
		super();
		this.nome = nome;
		this.stemma = new Multimedia(stemma);
		this.foto = new Multimedia(foto);
	}
	
	public Comune(String nome) {
		super();
		this.nome = nome;
		this.stemma = null;
		this.foto = null;
		
		PreparedStatement ps;
		ResultSet rs;
		
		//check if already exists and load stemma,foto
		try {
			this.conn=Connector.getInstance();
			
			ps=conn.prepare("SELECT * FROM Comuni WHERE name = ? ; ");
			ps.setString(1, nome);
			rs=ps.executeQuery();
			
			if(!rs.first()) {
				//create
				ps=conn.prepareReturn("INSERT INTO Comuni (name) VALUES (?) ; ");
				ps.setString(1, nome);
				ps.execute();
				
				rs=ps.getGeneratedKeys();
				this.id=rs.getInt("id");
				
			}else {
				this.stemma.setPath(rs.getString("stemma"));
				this.foto.setPath(rs.getString("foto"));
				this.id=rs.getInt("id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getId() {
		return this.id;
	}

	public Multimedia getStemma() {
		return stemma;
	}

	public Multimedia getFoto() {
		return foto;
	}

	public String getNome() {
		return this.nome;
	}
}
