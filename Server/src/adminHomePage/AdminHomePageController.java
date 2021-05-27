package adminHomePage;

import dominio.Comune;
import dominio.Stats;

import database.Connector;
import java.sql.SQLException;

public class AdminHomePageController implements IAdminHomePage {

	private Connector connector;
	
	public AdminHomePageController() throws SQLException {
		this.connector=Connector.getInstance();
	}
	
	@Override
	public Stats getTotalStats(Comune comune) {
		Stats res;
		
		res=Stats.of(connector, comune.getNome());
		res.setComune(comune);
		
		return res;
	}

}
