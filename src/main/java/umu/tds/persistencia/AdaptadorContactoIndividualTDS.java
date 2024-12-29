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
	
	private static final String PROPIEDAD_NOMBRE = "nombre";
	private static final String PROPIEDAD_USUARIO_ASOCIADO = "usuarioAsociado";
	private static final String PROPIEDAD_MENSAJES_RECIBIDOS = "listaMensajes";
	
	
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
		
		/* TODO revisar si habría que poner esto
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().registrarUsuario(contactoIndividual.getUsuarioAsociado());
		} catch (DAOException e) {
	
			e.printStackTrace();
		}
		*/
		
		AdaptadorUsuarioTDS.getUnicaInstancia().registrarUsuario(contactoIndividual.getUsuarioAsociado());	
		contactoIndividual.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::registrarMensaje);
	
		//Creamos la entidad y añadimos propiedades
		eContactoIndividual = Optional.of(new Entidad());
		eContactoIndividual.get().setNombre("contactoIndividual");
		eContactoIndividual.get().setPropiedades(
			    new ArrayList<Propiedad>(Arrays.asList(
			        new Propiedad(PROPIEDAD_NOMBRE, contactoIndividual.getNombre()),
			        new Propiedad(PROPIEDAD_USUARIO_ASOCIADO, String.valueOf(contactoIndividual.getUsuarioAsociado().getCodigo())),
			        new Propiedad(PROPIEDAD_MENSAJES_RECIBIDOS, obtenerCodigosMensajes(contactoIndividual.getMensajesRecibidos()))
			    ))
		);
		
		//Registramos la entidad
		eContactoIndividual = Optional.ofNullable(servPersistencia.registrarEntidad(eContactoIndividual.get()));
		contactoIndividual.setCodigo(eContactoIndividual.get().getId());		
	}

	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContact;		
		contactoIndividual.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::borrarMensaje);

		eContact = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		servPersistencia.borrarEntidad(eContact);
		
		// Si está en el pool, borramos del pool
		if (PoolDAO.INSTANCE.contains(contactoIndividual.getCodigo()))
			PoolDAO.INSTANCE.removeObject(contactoIndividual.getCodigo());
	}
	

	public void modificarContactoIndividual(ContactoIndividual contactoIndividual) {
		
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		
		for (Propiedad p : eContactoIndividual.getPropiedades()) {
			if (p.getNombre().equals(PROPIEDAD_NOMBRE)) {
				p.setValor(contactoIndividual.getNombre());
			} else if (p.getNombre().equals(PROPIEDAD_USUARIO_ASOCIADO)) {
				p.setValor(String.valueOf(contactoIndividual.getUsuarioAsociado().getCodigo()));
			} else if (p.getNombre().equals(PROPIEDAD_MENSAJES_RECIBIDOS)) {
				p.setValor(obtenerCodigosMensajes(contactoIndividual.getMensajesRecibidos()));
			}
			servPersistencia.modificarPropiedad(p);
		}	
	}
	

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		
		// Si esta en el pool, se devuelve
		if (PoolDAO.INSTANCE.contains(codigo)) return (ContactoIndividual) PoolDAO.INSTANCE.getObject(codigo);

		Usuario usuarioAsociado = null;
		
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_NOMBRE);
		
		ContactoIndividual contactoIndividual = new ContactoIndividual(nombre, usuarioAsociado);
		contactoIndividual.setCodigo(codigo);
		
		PoolDAO.INSTANCE.addObject(codigo, contactoIndividual);
		
		UsuarioDAO usuarioDAO = AdaptadorUsuarioTDS.getUnicaInstancia();
		usuarioAsociado = usuarioDAO.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_USUARIO_ASOCIADO)));
		contactoIndividual.setUsuarioAsociado(usuarioAsociado);
		
		obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_MENSAJES_RECIBIDOS))
			.forEach(contactoIndividual::addMensaje);

		return contactoIndividual;
	}
	
	@Override
	public List<ContactoIndividual> recuperarTodosLosContactosIndividuales() {
		List<ContactoIndividual> contactosIndividuales = new ArrayList<ContactoIndividual>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoIndividual");
		entidades.forEach(e -> contactosIndividuales.add(recuperarContactoIndividual(e.getId())));
		return contactosIndividuales;
	}
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		return Arrays.asList(codigos.split(" ")).stream()
				.filter(c -> !c.isEmpty())
				.map(Integer::parseInt)
				.map(AdaptadorMensajeTDS.getUnicaInstancia()::recuperarMensaje)
				.collect(Collectors.toList());
	}

	
}
