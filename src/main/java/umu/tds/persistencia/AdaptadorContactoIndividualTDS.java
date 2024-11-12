package umu.tds.persistencia;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.ContactoIndividual;

public class AdaptadorContactoIndividualTDS {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {
		// TODO Auto-generated method stub
	}

	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {
		// TODO Auto-generated method stub
	}

	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {
		// TODO Auto-generated method stub
	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}
}
