package ricerca;

import java.sql.SQLException;
import java.util.List;

import dominio.Profilo;
import dominio.Segnalazione;

public interface IRicerca {
		public List<Segnalazione> cercaSegnalazione(String key) throws SQLException;
}
