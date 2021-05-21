package autenticazione;

import java.sql.SQLException;

import dominio.Profilo;

public interface IRegistrazione {
	public boolean verificaID(String id);

	public boolean verificaP_IVA(String piva);

	public boolean verificaAccount(String id) throws SQLException;

	public void inviaConferma(Profilo utente) throws SQLException;

	public void convalidaEmail(Profilo utente) throws SQLException;

	public void registra(Profilo utente, String hash_passwd) throws SQLException;
}
