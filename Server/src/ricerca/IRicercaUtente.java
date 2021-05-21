package ricerca;

import java.sql.SQLException;
import java.util.List;

import dominio.Profilo;

public interface IRicercaUtente {
	public List<Profilo> cercaUtente(String name) throws SQLException;
}
