package umu.tds.persistencia;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Grupo;

public class AdaptadorGrupoTDS {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;

	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarGrupo(Grupo grupo) {
		// TODO Auto-generated method stub
	}

	public void borrarGrupo(Grupo grupo) {
		// TODO Auto-generated method stub
	}

	public void modificarGrupo(Grupo grupo) {
		// TODO Auto-generated method stub
	}

	public Grupo recuperarGrupo(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}
}
