package umu.tds.persistencia;

import java.util.Optional;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Mensaje;

public class AdaptadorContactoIndividualTDS {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorContactoIndividualTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorContactoIndividualTDS();
		return unicaInstancia;
	}
	
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Optional<Entidad> e = Optional.ofNullable(servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()));
		if (e.isPresent()) return;
		
		for (Mensaje m : contactoIndividual.getListaMensajes()) {
			//AdaptadorMensajeTDS.getUnicaInstancia().registrarMensaje(m);
		}
		
		
		
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
