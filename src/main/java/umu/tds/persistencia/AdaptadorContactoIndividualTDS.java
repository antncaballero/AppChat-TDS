package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public class AdaptadorContactoIndividualTDS implements ContactoIndividualDAO{
	
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
		
		Entidad eContact;		
		contactoIndividual.getListaMensajes().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::borrarMensaje);

		eContact = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		servPersistencia.borrarEntidad(eContact);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.INSTANCE.contains(contactoIndividual.getCodigo()))
			PoolDAO.INSTANCE.removeObject(contactoIndividual.getCodigo());
	}
	

	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContactoIndividual;
		eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		
		for (Propiedad p : eContactoIndividual.getPropiedades()) {
			if (p.getNombre().equals("nombre")) {
				p.setValor(contactoIndividual.getNombre());
			} else if (p.getNombre().equals("usuarioAsociado")) {
				p.setValor(String.valueOf(contactoIndividual.getUsuarioAsociado().getCodigo()));
			} else if (p.getNombre().equals("listaMensajes")) {
				p.setValor(obtenerCodigosMensajes(contactoIndividual.getListaMensajes()));
			}
			servPersistencia.modificarPropiedad(p);
		}
			
	/*	
		//TODO Ver si esto funciona igual
		 
		eContactoIndividual.getPropiedades().get(0).setValor(contactoIndividual.getNombre());
		eContactoIndividual.getPropiedades().get(1).setValor(String.valueOf(contactoIndividual.getUsuarioAsociado().getCodigo()));
		eContactoIndividual.getPropiedades().get(2).setValor(obtenerCodigosMensajes(contactoIndividual.getListaMensajes()));

		servPersistencia.modificarEntidad(eContactoIndividual);
	*/	
	}
	

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		
		// Si esta en el pool, se devuelve
		if (PoolDAO.INSTANCE.contains(codigo)) return (ContactoIndividual) PoolDAO.INSTANCE.getObject(codigo);

		Usuario usuarioAsociado = null;
		
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "nombre");
		
		ContactoIndividual contactoIndividual = new ContactoIndividual(nombre, usuarioAsociado);
		contactoIndividual.setCodigo(codigo);
		
		PoolDAO.INSTANCE.addObject(codigo, contactoIndividual);
		
		UsuarioDAO usuarioDAO = AdaptadorUsuarioTDS.getUnicaInstancia();
		usuarioAsociado = usuarioDAO.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "usuarioAsociado")));
		contactoIndividual.setUsuarioAsociado(usuarioAsociado);
		
		obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "mensajesRecibidos"))
			.forEach(contactoIndividual::addMensaje);

		return contactoIndividual;
	}
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		return Arrays.asList(codigos.split(" ")).stream()
				.map(Integer::parseInt)
				.map(AdaptadorMensajeTDS.getUnicaInstancia()::recuperarMensaje)
				.collect(Collectors.toList());
	}
}
