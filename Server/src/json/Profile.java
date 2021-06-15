package json;

import dominio.Profilo;

public class Profile {
	
	private String autore;
	private float reputazione;
	private String userType;
	
	public Profile() {
		super();
	}
	
	public static Profile toProfile(Profilo pr) {
		Profile res=new Profile();
		
		res.setAutore(pr.getNome().concat(" "+pr.getCognome()));
		res.setReputazione(pr.getValutazione());
		res.setUserType(pr.getRuolo().toString());
		
		return res;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public void setReputazione(float reputazione) {
		this.reputazione = reputazione;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public float getReputazione() {
		return reputazione;
	}

	
	
		

}
