package handshake;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import dominio.Chat;
import dominio.Messaggio;
import dominio.Profilo;
import dominio.Segnalazione;
import exceptions.EmailNotExistingException;
import json.Mess;
import json.Response;

public class HandshakeController implements IHandshake {

	private Connector conn;
	
	public HandshakeController(){
		try {
			this.conn = Connector.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response sendMessage(int idSeg, String emailMittente, String mex) throws SQLException {
		Chat chat;
		Profilo mittente=null;
		boolean esito;
		Response res=new Response();
		
		try {
			mittente = Profilo.getProfiloByEmail(conn, emailMittente);
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		chat=Chat.getChat(idSeg, mittente.getId(), conn);
		esito=chat.inviaMessaggio(mex, mittente.getId(), conn);
		
		res.setEsito(esito);
		return res;
	}

	@Override
	public void sendMedia(Chat chat, Profilo mittente, File file) {
		// ...
	}

	@Override
	public Response getMessages(int  idSegnalazione, String userEmail) throws SQLException {
		Mess[] res;
		Response result;
		
		
		Chat ch;
		
		Profilo user=null;
		Segnalazione segnalazione=Segnalazione.getById(conn, idSegnalazione);
		
		try {
			user = Profilo.getProfiloByEmail(conn, userEmail);
		} catch (SQLException | EmailNotExistingException e) {
			e.printStackTrace();
		}
		
		ch=Chat.getChat(segnalazione.getId(),user.getId(),conn);
		res=ch.returnOldMessages(user.getId(), conn);
		
		result=new Response();
		result.setMessages(res);
		
		return result;		
	}

}
