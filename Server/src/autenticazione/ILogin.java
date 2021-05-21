package autenticazione;

public interface ILogin {
	public String autentica(String username, String hash_passwd);
}
