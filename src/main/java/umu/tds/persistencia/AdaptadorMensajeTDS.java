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
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public class AdaptadorMensajeTDS implements MensajeDAO {
	
	private static final String PROPIEDAD_EMISOR = "emisor";
	private static final String PROPIEDAD_RECEPTOR = "receptor";
	private static final String PROPIEDAD_HORA = "hora";
	private static final String PROPIEDAD_TEXTO = "texto";
	private static final String PROPIEDAD_EMOTICONO = "emoticono";
	
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	public static AdaptadorMensajeTDS getInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else {
			return unicaInstancia;
		}
	}
	
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarMensaje(Mensaje mensaje) {
		//Comprobamos si ya esta registrado
		Optional<Entidad> eMensaje = Optional.ofNullable(servPersistencia.recuperarEntidad(mensaje.getCodigo()));
		if (eMensaje.isPresent()) return;
		
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
			        new Propiedad(PROPIEDAD_EMISOR, Integer.toString(mensaje.getEmisor().getCodigo())),
			        new Propiedad(PROPIEDAD_RECEPTOR, Integer.toString(mensaje.getReceptor().getCodigo()))
			     ))
		);
		//Registramos la entidad
		eMensaje = Optional.ofNullable(servPersistencia.registrarEntidad(eMensaje.get()));
		
		//Asignamos el codigo unico, aprovechamos el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.get().getId());
		
		//Añadimos al pool
		PoolDAO.INSTANCE.addObject(mensaje.getCodigo(), mensaje);
	}

	public void borrarMensaje(Mensaje mensaje) {
		//Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		
		//Borramos la entidad
		servPersistencia.borrarEntidad(eMensaje);
		
		//Si esta en el pool, lo borramos
		if (PoolDAO.INSTANCE.contains(mensaje.getCodigo())) 
			PoolDAO.INSTANCE.removeObject(mensaje.getCodigo());
		
	}

	public void modificarMensaje(Mensaje mensaje) {
		// Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		//Se recorren sus propiedades y se actualiza su valor
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

	public Mensaje recuperarMensaje(int codigo) {
		//Si esta en el pool, lo devolvemos
		if (PoolDAO.INSTANCE.contains(codigo))
			return (Mensaje) PoolDAO.INSTANCE.getObject(codigo);
		
		//Sino, recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		//Recuperamos sus propiedades
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		LocalDateTime fechaHora = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora"));		
		int emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_EMOTICONO));		
		Usuario emisor = null;
		ContactoIndividual receptor = null;
		
		//Creamos el mensaje
		Mensaje mensaje = new Mensaje(texto, fechaHora, emoticono, emisor, receptor);
		mensaje.setCodigo(codigo);
		
		//Añadimos al pool antes de llamar a otros adaptadores
		PoolDAO.INSTANCE.addObject(codigo, mensaje);
		
		emisor = AdaptadorUsuarioTDS.getInstancia().recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_EMISOR)));
		receptor = AdaptadorContactoIndividualTDS.getInstancia().recuperarContactoIndividual(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_RECEPTOR)));
		mensaje.setEmisor(emisor);
		mensaje.setReceptor(receptor);
		
		//Devolvemos el mensaje
		return mensaje;
	}

	@Override
	public List<Mensaje> recuperarTodosLosMensajes() {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");
		eMensajes.forEach(e -> mensajes.add(recuperarMensaje(e.getId())));
		return mensajes;
	}

	

}














