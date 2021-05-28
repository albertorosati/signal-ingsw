package account;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Connector;
import dominio.Profilo;
import mail.MyMailer;

public class SospendiController implements ISospendi {
	
	private Connector connector;
	private MyMailer mailer;
	
	public SospendiController() throws SQLException {
		mailer = MyMailer.getIstance();
		connector= Connector.getInstance();
	}

	@Override
	public void sospendi(Profilo p) {
		
		PreparedStatement ps;
		try {
			ps = connector.prepare("UPDATE CacheSospensione SET Sospeso=true WHERE email=?");
			ps.setString(1, p.getEmail());
			ps.execute();
			
			//foreach seg.state<5 --> set visible FALSE
			ps=connector.prepare("UPDATE Segnalazioni SET stato = 9 WHERE autore = ? AND stato < 5");
			ps.setString(1, p.getIdentificatore());
			ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		inviaEmail(p);
	}

	@Override
	public void inviaEmail(Profilo p) {
		mailer.sendMailSospeso(p.getEmail());		
	}

}
