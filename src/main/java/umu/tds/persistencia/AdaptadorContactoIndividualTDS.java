package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import beans.Entidad;
import beans.Propiedad;
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
		
		//Comprobar si ya esta registrado
		Optional<Entidad> eContactoIndividual = Optional.ofNullable(servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()));
		if (eContactoIndividual.isPresent()) return;
		
		//Registramos sus objetos asociados
		AdaptadorUsuarioTDS.getUnicaInstancia().registrarUsuario(contactoIndividual.getUsuarioAsociado());	
		contactoIndividual.getListaMensajes().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::registrarMensaje);
	
		//Creamos la entidad y añadimos propiedades
		eContactoIndividual = Optional.of(new Entidad());
		eContactoIndividual.get().setNombre("contactoIndividual");
		eContactoIndividual.get().setPropiedades(
			    new ArrayList<Propiedad>(Arrays.asList(
			        new Propiedad("nombre", contactoIndividual.getNombre()),
			        new Propiedad("usuarioAsociado", String.valueOf(contactoIndividual.getUsuarioAsociado().getCodigo())),
			        new Propiedad("listaMensajes", obtenerCodigosMensajes(contactoIndividual.getListaMensajes()))
			    ))
		);
		
		//Registramos la entidad
		eContactoIndividual = Optional.ofNullable(servPersistencia.registrarEntidad(eContactoIndividual.get()));
		contactoIndividual.setCodigo(eContactoIndividual.get().getId());		
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
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
}
