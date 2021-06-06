package userHomePage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import dominio.Profilo;
import dominio.RuoloUtente;
import dominio.Segnalazione;

public class UserHomePageController implements IUserHomePage {
	
	private Connector connector;
	
	public UserHomePageController() throws SQLException {
		this.connector = Connector.getInstance();
	}
	
	public Segnalazione[] getBacheca(Profilo profilo) {
		
		List<Segnalazione> res=new ArrayList<>();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps=connector.prepare("SELECT isSeg FROM ? WHERE visible = TRUE");
			ps.setString(1, getTable(profilo.getRuolo()));
			rs = ps.executeQuery();
			
			while(rs.next()) {
				res.add(Segnalazione.getById(connector,rs.getInt("idSeg")));
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return (Segnalazione[]) res.toArray();
	}
	
	
	private static String getTable(RuoloUtente role) {
		String res="";
	
		switch(role) {
		case PRO:
			res="BachecaPRO";
			break;
		case PRO_CONVENZIONATO :
			res="BachecaPROConv";
			break;
		default:
			res="BachecaBase";
			break;
		}
		
		return res;
	}
	
}