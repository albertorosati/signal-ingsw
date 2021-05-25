package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import account.Sospendi;
import dominio.CartaVirtuale;
import dominio.Comune;
import dominio.Multimedia;
import dominio.Posizione;
import dominio.Profilo;
import dominio.RuoloUtente;
import dominio.Segnalazione;
import dominio.Stato;
import dominio.TipoBacheca;
import dominio.TipoFile;
import gestioneSegnalazioni.InserimentoController;
import segnalazione.EffettuaSegnalazioneController;
import segnalazione.Produttore;
import userHomePage.UserHomePageController;
import verifica.VerificaController;

public class TestController {

	Comune comune;

	Profilo produttore;
	Profilo consumatore;
	Profilo moderatore;
	Profilo gestore;
	Profilo pro_conv;

	Segnalazione mock_seg;

	@BeforeEach
	public void setUp() {
		Multimedia stemma = new Multimedia(new File("/bologna_stemma.png"), TipoFile.FOTO);
		Multimedia foto = new Multimedia(new File("/bologna_foto.jpg"), TipoFile.FOTO);
		comune = new Comune("Città Metropolitana di Bologna", stemma, foto);
		CartaVirtuale carta = new CartaVirtuale(100, comune);

		produttore = new Profilo(UUID.randomUUID(), "dangelo.harrison@mail.com", "D'Angelo", "Harrison",
				"CODICEFISCALE", false, 5, RuoloUtente.BASE, comune, List.of(carta));
		carta.setProfilo(produttore);

		moderatore = new Profilo(UUID.randomUUID(), "darius.thompson@mail.com", "Darius", "Thompson", "CODICEFISCALE",
				false, 5, RuoloUtente.MODERATORE, comune, null);

		gestore = new Profilo(UUID.randomUUID(), "derek.willis@mail.com", "Derek", "Willis", "CODICEFISCALE", false, 5,
				RuoloUtente.GESTORE, comune, null);

		consumatore = new Profilo(UUID.randomUUID(), "nick.perkins@mail.com", "Nick", "Perkins", "CODICEFISCALE", false,
				5, RuoloUtente.PRO, comune, null);

		pro_conv = new Profilo(UUID.randomUUID(), "frank.vitucci@mail.com", "Frank", "Vitucci", "CODICEFISCALE", false,
				5, RuoloUtente.PRO_CONVENZIONATO, comune, null);

		mock_seg = new Segnalazione("mock", "mock description", LocalDateTime.now(), null, false, null, null,
				produttore, null, null, null, true);
	}

	// Verifica della sospensione di un utente
	// se l'utente viene sospeso non deve poter
	// effettuare una nuova segnalazione
	@Test
	public void testSospendi() throws SQLException {
		Sospendi sospendiController = new Sospendi();
		EffettuaSegnalazioneController es = new EffettuaSegnalazioneController();
		Produttore prodController = new Produttore();

		sospendiController.sospendi(produttore);

		es.effettuaSegnalazione(mock_seg);
		Segnalazione[] mySeg = prodController.getMieSegnalazioni(produttore);

		assertFalse(Arrays.asList(mySeg).contains(mock_seg));
	}

	// Test del controller InserimentoController
	@Test
	public void testBacheca() throws SQLException {
		InserimentoController ins = new InserimentoController();

		TipoBacheca[] dest = new TipoBacheca[2];
		dest[0] = TipoBacheca.PRO;
		dest[1] = TipoBacheca.PRO_CONVENZIONATO;

		List<String> tags = List.of("panchina", "parco");

		Posizione pos = new Posizione(44.540670, 11.353173);

		Segnalazione segnalazione = new Segnalazione("panchina rotta",
				"Lo schienale di una panchina al parco dei Laghetti e' rotto", LocalDateTime.now(), tags, true,
				Stato.APPROVATA, pos, produttore, null, null, null, true);

		ins.inserisciInBacheca(segnalazione, dest);

		UserHomePageController home = new UserHomePageController();
		Segnalazione[] bacheca = home.getBacheca(pro_conv);

		assertTrue(Arrays.asList(bacheca).contains(segnalazione));
	}

	// Test del funzionamento del controller VerificaController
	// in particolare della funzione accetta, che deve accreditare
	// 50 punti all'utente la cui segnalazione è stata accettata
	@Test
	public void testPunti() throws SQLException {
		int points_Before, points_After;
		VerificaController verify = new VerificaController();

		points_Before = produttore.getTotalPoints();
		verify.accetta(mock_seg);
		points_After = produttore.getTotalPoints();

		assertEquals(points_Before + 50, points_After);
	}

}
