package autenticazione;

public interface IRegistrazione {
	public boolean verificaID(String id);
	public boolean verificaP_IVA(String piva);
	public boolean verificaAccount(String id);
	public void inviaConferma(Profilo utente);
	public void convalidaEmail(Profilo utente);
	public void registra(Profilo utente, String hash_passwd);
}
