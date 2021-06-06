package adminHomePage;

import dominio.Comune;
import dominio.Stats;
import json.Response;

public interface IAdminHomePage {
	public Response getTotalStats(Comune comune);
}
