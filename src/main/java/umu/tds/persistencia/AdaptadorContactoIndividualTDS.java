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

/**
 * Clase que implementa el Adaptador de ContactoIndividual para el tipo de
 * persistencia TDS
 */
public class AdaptadorContactoIndividualTDS implements ContactoIndividualDAO{
	
	private static final String PROPIEDAD_NOMBRE = "nombre";
	private static final String PROPIEDAD_USUARIO_ASOCIADO = "usuarioAsociado";
	private static final String PROPIEDAD_MENSAJES_RECIBIDOS = "listaMensajes";
		
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS unicaInstancia = null;
	
	/**
	 * Constructor para obtener el servicio de persistencia
	 */
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	/**
	 * Obtener la única instancia de la clase
	 * @return la instancia del adaptador
	 */
	public static AdaptadorContactoIndividualTDS getInstancia() {
		if (unicaInstancia == null) unicaInstancia = new AdaptadorContactoIndividualTDS();
		return unicaInstancia;
	}
	/**
	 * Registrar un contacto individual en el servicio de persistencia
	 * @param el contacto individual a registrar
	 */
	public void registrarContactoIndividual(ContactoIndividual contactoIndividual) {		
		//Comprobar si ya esta registrado
		Optional<Entidad> eContactoIndividual = Optional.ofNullable(servPersistencia.recuperarEntidad(contactoIndividual.getCodigo()));
		if (eContactoIndividual.isPresent()) return;
		
		//Registramos sus objetos asociados		
		AdaptadorUsuarioTDS.getInstancia().registrarUsuario(contactoIndividual.getUsuarioAsociado());	
		contactoIndividual.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getInstancia()::registrarMensaje);
	
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
		PoolDAO.INSTANCE.addObject(contactoIndividual.getCodigo(), contactoIndividual);
	}
	/**
	 * Método para borrar un contacto individual del servicio de persistencia
	 * @param contactoIndividual el contacto individual a borrar
	 */
	public void borrarContactoIndividual(ContactoIndividual contactoIndividual) {				
		contactoIndividual.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getInstancia()::borrarMensaje);
		Entidad eContact = servPersistencia.recuperarEntidad(contactoIndividual.getCodigo());
		servPersistencia.borrarEntidad(eContact);	
		// Si está en el pool, borramos del pool
		if (PoolDAO.INSTANCE.contains(contactoIndividual.getCodigo()))
			PoolDAO.INSTANCE.removeObject(contactoIndividual.getCodigo());
	}
	/**
	 * Método para modificar un contacto individual en el servicio de persistencia
	 * @param contactoIndividual el contacto individual a modificar
     */
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

	/**
	 * Método para recuperar un contacto individual del servicio de persistencia
	 * @param codigo el código del contacto individual a recuperar
	 * @return el contacto individual recuperado
	 */
	public ContactoIndividual recuperarContactoIndividual(int codigo) {	
		// Si esta en el pool, se devuelve
		if (PoolDAO.INSTANCE.contains(codigo)) return (ContactoIndividual) PoolDAO.INSTANCE.getObject(codigo);
		Usuario usuarioAsociado = null;	
		
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_NOMBRE);	
		ContactoIndividual contactoIndividual = new ContactoIndividual(nombre, usuarioAsociado);
		contactoIndividual.setCodigo(codigo);
		PoolDAO.INSTANCE.addObject(codigo, contactoIndividual);

		usuarioAsociado = AdaptadorUsuarioTDS.getInstancia().recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_USUARIO_ASOCIADO)));
		contactoIndividual.setUsuarioAsociado(usuarioAsociado);
		
		obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPIEDAD_MENSAJES_RECIBIDOS))
			.forEach(contactoIndividual::addMensaje);

		return contactoIndividual;
	}
	/**
     * Método para recuperar todos los contactos individuales del servicio de persistencia
     * @return la lista de contactos individuales recuper
     */
	public List<ContactoIndividual> recuperarTodosLosContactosIndividuales() {
		List<ContactoIndividual> contactosIndividuales = new ArrayList<ContactoIndividual>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoIndividual");
		entidades.forEach(e -> contactosIndividuales.add(recuperarContactoIndividual(e.getId())));
		return contactosIndividuales;
	}

	/**
	 * Método para obtener los códigos de los mensajes recibidos por un contacto
	 * individual 
	 * @param mensajesRecibidos la lista de mensajes recibidos
	 * @return los códigos de los mensajes recibidos
	 */
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
	/**
	 * Método para obtener los mensajes a partir de sus códigos
	 * @param codigos
	 * @return la lista de mensajes
	 */
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		return Arrays.asList(codigos.split(" ")).stream()
				.filter(c -> !c.isEmpty())
				.map(Integer::parseInt)
				.map(AdaptadorMensajeTDS.getInstancia()::recuperarMensaje)
				.collect(Collectors.toList());
	}
}
