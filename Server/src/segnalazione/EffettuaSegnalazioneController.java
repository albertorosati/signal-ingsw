package segnalazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

import database.Connector;
import dominio.Segnalazione;

public class EffettuaSegnalazioneController implements IEffettuaSegnlazione {

	private Connector conn;

	public EffettuaSegnalazioneController() throws SQLException {
		conn = Connector.getInstance();
	}

	@Override
	public void effettuaSegnalazione(Segnalazione segnalazione) throws RuntimeException {
		
		//update cacheSegnalazioni
		PreparedStatement ps;
		ResultSet rs;
		LocalDateTime now=LocalDateTime.now();
		
		try {
			ps = conn.prepare("SELECT * FROM CacheSegnalazione WHERE email = ? ;");
			ps.setString(1, segnalazione.getAutore().getEmail());
			rs = ps.executeQuery();
			
			if(!rs.first() || Duration.between(LocalDateTime.parse(rs.getString("lastSeg")),now).toMinutes()>5 ) {
				//OK
				
				//INSERT SEGNALAZIONE
				Segnalazione.insert(conn, segnalazione);
				
				if(rs.first()) {
					//update cache
					ps = conn.prepare("UPDATE CacheSegnalazione SET lastSeg = ? WHERE email = ? ;");
					ps.setString(1, now.toString());
					ps.setString(2, segnalazione.getAutore().getEmail());
					ps.execute();
				}else {
					//new cache entry
					ps = conn.prepare("INSERT INTO CacheSegnalazione (lastSeg,email) VALUES (?,?) ;");
					ps.setString(1, now.toString());
					ps.setString(2, segnalazione.getAutore().getEmail());
					ps.execute();
				}
												
			}else {
				throw new RuntimeException();
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
		}		
	}

	@Override
	public double[] getPosizione(String indirizzo) throws SQLException {
		//...
		return null;
	}

}
