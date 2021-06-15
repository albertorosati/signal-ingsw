package userHomePage;

import java.sql.SQLException;

import dominio.Segnalazione;
import exceptions.EmailNotExistingException;

public interface IUserHomePage {
	public Segnalazione[] getBacheca(String email) throws SQLException, EmailNotExistingException;
}
