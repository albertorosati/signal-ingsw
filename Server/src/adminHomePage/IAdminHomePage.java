package adminHomePage;

import dominio.Comune;
import dominio.Stats;

public interface IAdminHomePage {
	public Stats getTotalStats(Comune comune);
}
