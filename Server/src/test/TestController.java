package test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import account.ISospendi;
import account.Sospendi;
import dominio.CartaVirtuale;
import dominio.Comune;
import dominio.Multimedia;
import dominio.Profilo;
import dominio.RuoloUtente;
import dominio.TipoFile;

public class TestController {

	Profilo profilo;
	Comune comune;

	@BeforeEach
	public void setUp() {
		Multimedia stemma = new Multimedia(new File("/bologna_stemma.png"), TipoFile.FOTO);
		Multimedia foto = new Multimedia(new File("/bologna_foto.jpg"), TipoFile.FOTO);
		comune = new Comune("Città Metropolitana di Bologna", stemma, foto);
		CartaVirtuale carta = new CartaVirtuale(100, comune);
		profilo = new Profilo(UUID.randomUUID(), "mario.rossi@mail.com", "Mario", "rossi", "CODICEFISCALE", false, 5,
				RuoloUtente.BASE, comune, List.of(carta));
		carta.setProfilo(profilo);
	}

	@Test
	void testSospendi() {
		ISospendi sospendiController = new Sospendi();
		sospendiController.sospendi(profilo);
	}

}
