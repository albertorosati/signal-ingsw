package autenticazione;

public interface IRegistrazione {
	public boolean verificaID(String id);
	public boolean verificaP_IVA(String piva);
	public boolean verificaAccount(String id);
	public void inviaConferma(String email);
	public void convalidaEmail(String profile);
	public void registra(String profile, String hash_passwd);
}
