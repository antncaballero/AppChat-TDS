package umu.tds.persistencia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;
/**
 * Clase que implementa el Adaptador de Mensaje para el servicio de persistencia TDS
 */
public class AdaptadorMensajeTDS implements MensajeDAO {
	
	private static final String PROPIEDAD_EMISOR = "emisor";
	private static final String PROPIEDAD_RECEPTOR = "receptor";
	private static final String PROPIEDAD_HORA = "hora";
	private static final String PROPIEDAD_TEXTO = "texto";
	private static final String PROPIEDAD_EMOTICONO = "emoticono";
	private static final String PROPIEDAD_TIPO_RECEPTOR = "tipoReceptor";	
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	/**
	 * Devuelve la unica instancia de AdaptadorMensajeTDS
	 * @return unicaInstancia
	 */
	public static AdaptadorMensajeTDS getInstancia() {
		if (unicaInstancia == null) return new AdaptadorMensajeTDS();
		else return unicaInstancia;	
	}
	/**
	 * Constructor de AdaptadorMensajeTDS
	 */
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	/**
	 * Registra un mensaje en la base de datos
	 * @param mensaje
	 */
	public void registrarMensaje(Mensaje mensaje) {
		Optional<Entidad> eMensaje = Optional.ofNullable(servPersistencia.recuperarEntidad(mensaje.getCodigo()));
		if (eMensaje.isPresent()) return;
		
		//Registramos sus objetos asociados
		AdaptadorUsuarioTDS.getInstancia().registrarUsuario(mensaje.getEmisor());
		if (mensaje.getReceptor() instanceof ContactoIndividual) AdaptadorContactoIndividualTDS.getInstancia().registrarContactoIndividual((ContactoIndividual) mensaje.getReceptor());
	    else AdaptadorGrupoTDS.getInstancia().registrarGrupo((Grupo) mensaje.getReceptor());
	    		
		//Creamos una entidad mensaje
		eMensaje = Optional.of(new Entidad());
		//Asignamos tipo
		eMensaje.get().setNombre("mensaje");
		//Asignamos atributos
		eMensaje.get().setPropiedades(
			    new ArrayList<Propiedad>(Arrays.asList(
			        new Propiedad(PROPIEDAD_TEXTO, mensaje.getTexto()),
			        new Propiedad(PROPIEDAD_HORA, mensaje.getHora().toString()),
			        new Propiedad(PROPIEDAD_EMOTICONO, Integer.toString(mensaje.getEmoticono())),
			        new Propiedad(PROPIEDAD_TIPO_RECEPTOR, mensaje.getReceptor() instanceof ContactoIndividual ? "individual" : "grupo"),
			        new Propiedad(PROPIEDAD_EMISOR, Integer.toString(mensaje.getEmisor().getCodigo())),
			        new Propiedad(PROPIEDAD_RECEPTOR, Integer.toString(mensaje.getReceptor().getCodigo()))
			        
			     ))
		);
		eMensaje = Optional.ofNullable(servPersistencia.registrarEntidad(eMensaje.get()));		
		mensaje.setCodigo(eMensaje.get().getId());		
		PoolDAO.INSTANCE.addObject(mensaje.getCodigo(), mensaje);
	}
	/**
	 * Borra un mensaje de la base de datos
	 * @param mensaje
	 */
	public void borrarMensaje(Mensaje mensaje) {
		//Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		
		//Borramos la entidad
		servPersistencia.borrarEntidad(eMensaje);
		
		//Si esta en el pool, lo borramos
		if (PoolDAO.INSTANCE.contains(mensaje.getCodigo())) 
			PoolDAO.INSTANCE.removeObject(mensaje.getCodigo());
		
	}
	/**
	 * Modifica un mensaje en la base de datos
	 */
	public void modificarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		for (Propiedad prop : eMensaje.getPropiedades()) {
			if (prop.getNombre().equals(PROPIEDAD_TEXTO)) {
				prop.setValor(mensaje.getTexto());
			} else if (prop.getNombre().equals(PROPIEDAD_HORA)) {
				prop.setValor(mensaje.getHora().toString());
			} else if (prop.getNombre().equals(PROPIEDAD_EMOTICONO)) {
				prop.setValor(Integer.toString(mensaje.getEmoticono()));
			} else if (prop.getNombre().equals(PROPIEDAD_EMISOR)) {
				prop.setValor(Integer.toString(mensaje.getEmisor().getCodigo()));
			} else if (prop.getNombre().equals(PROPIEDAD_RECEPTOR)) {
				prop.setValor(Integer.toString(mensaje.getReceptor().getCodigo()));
			}
			servPersistencia.modificarEntidad(eMensaje);
		}		
	}
	/**
	 * Recupera un mensaje de la base de datos
	 * @param codigo
	 */
	public Mensaje recuperarMensaje(int codigo) {
		//Si esta en el pool, lo devolvemos
		if (PoolDAO.INSTANCE.contains(codigo)) return (Mensaje) PoolDAO.INSTANCE.getObject(codigo);		
		//Sino, recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		//Recuperamos sus propiedades
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_TEXTO);
		LocalDateTime fechaHora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_HORA));		
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_EMOTICONO));		
		Usuario emisor = null;
		Contacto receptor = null;
		String tipo = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_TIPO_RECEPTOR);
		
		//Creamos el mensaje
		Mensaje mensaje = new Mensaje(texto, fechaHora, emoticono, emisor, receptor);
		mensaje.setCodigo(codigo);
		
		//Añadimos al pool antes de llamar a otros adaptadores
		PoolDAO.INSTANCE.addObject(codigo, mensaje);
		
		emisor = AdaptadorUsuarioTDS.getInstancia().recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_EMISOR)));		
		if (tipo.equals("grupo")) receptor = AdaptadorGrupoTDS.getInstancia().recuperarGrupo(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_RECEPTOR)));
		else receptor = AdaptadorContactoIndividualTDS.getInstancia().recuperarContactoIndividual(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_RECEPTOR)));
		
		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
		
		//Devolvemos el mensaje
		return mensaje;
	}
	/**
	 * Recupera todos los mensajes de la base de datos
	 */
	public List<Mensaje> recuperarTodosLosMensajes() {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");
		eMensajes.forEach(e -> mensajes.add(recuperarMensaje(e.getId())));
		return mensajes;
	}

	

}














