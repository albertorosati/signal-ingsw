package json;

import dominio.CartaVirtuale;

public class Card {

	private String titolo;
	private String img; //base64
	private int punti;
	
	public Card() {
		super();
	}
	
	public static Card toCard(CartaVirtuale c) {
		Card res=new Card();
		
		res.setPunti(c.getSaldo());
		res.setTitolo(c.getComune());
		res.setImg(c.getComuneComune().getFoto().getBase64());
		
		return res;
	}

	

	//GETTERS&SETTERS
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}
	
}
