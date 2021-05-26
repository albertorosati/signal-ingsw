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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		inviaEmail(p);
	}

	@Override
	public void inviaEmail(Profilo p) {
		// TODO Auto-generated method stub
		
	}

}
