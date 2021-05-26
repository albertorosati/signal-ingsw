package autenticazione;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import dominio.Profilo;
import json.Response;

public interface IRegistrazione {
	public boolean verificaID(String id);

	public boolean verificaP_IVA(String piva) throws MalformedURLException, IOException;

	public boolean verificaAccount(String id) throws SQLException;

	public void inviaConferma(Profilo utente) throws SQLException;

	public Response convalidaEmail(Profilo utente, String hash) throws SQLException;

	public void registra(Profilo utente, String hash_passwd) throws SQLException;
}
