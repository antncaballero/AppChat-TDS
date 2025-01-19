package umu.tds.servicios;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

/**
 * Clase que representa un servicio para buscar mensajes en la aplicación
 */
public enum BuscarMsgService {
	INSTANCE;
	
	/**
	 * Busca mensajes en la lista de mensajes de un usuario
	 * @param usuario
	 * @param mensajes sobre los que buscar
	 * @param filtro tlf
	 * @param filtro nombreContacto
	 * @param filtro texto
	 * @param filtro isEnviados
	 * @param filtro isRecibidos
	 * @return Lista de mensajes que cumplen los filtros, ordenados por fecha
	 */
	public List<Mensaje> buscarMensajes(Usuario usuario, List<Mensaje> mensajes, String tlf, String nombreContacto, String texto, boolean isEnviados, boolean isRecibidos) {				
		return mensajes.stream()
				.filter(filtroTelefono(tlf))					
				.filter(filtroNombreContacto(usuario, nombreContacto))
				.filter(filtroEmisor(usuario, isEnviados))
				.filter(filtroReceptor(usuario, isRecibidos))
				.filter(filtroTexto(texto))		         					
				.sorted()
				.collect(Collectors.toList());				
	}
	
	//Filtros como funciones que devuelven predicados
	
	/**
	 * Filtro que comprueba si el texto del mensaje contiene el texto introducido
	 * @param texto
	 * @return predicado que comprueba si el texto del mensaje contiene el texto introducido
	 */
	private Predicate<Mensaje> filtroTexto(String texto) {
		return m -> texto.isEmpty() || m.getTexto().contains(texto);
	}
	/**
	 * Filtro que comprueba si el emisor del mensaje es el usuario
	 * @param usuario
	 * @param isEnviados
	 * @return
	 */
	private Predicate<Mensaje> filtroEmisor(Usuario usuario, boolean isEnviados) {
		return m -> !isEnviados || m.getEmisor().equals(usuario);
	}
	/**
	 * Filtro que comprueba si el receptor del mensaje es el usuario
	 * @param usuario
	 * @param isRecibidos
	 * @return
	 */
	private Predicate<Mensaje> filtroReceptor(Usuario usuario, boolean isRecibidos) {
		return m -> {
			if (!isRecibidos) return true; // no se ha seleccionado recibidos
			return m.getReceptor() instanceof ContactoIndividual 
					&& ((ContactoIndividual) m.getReceptor()).getUsuarioAsociado().equals(usuario);
		};		
	}
	/**
	 * Filtro que comprueba si el mensaje está asociado (emisor o receptor) al teléfono introducido
	 * @param tlf
	 * @return
	 */
	private Predicate<Mensaje> filtroTelefono(String tlf) {
		return m -> {
			if (tlf.isEmpty()) return true; 												// no hay filtro por tlf
			if (m.getEmisor().getNumTlf() == Integer.parseInt(tlf)) return true; 			// tlf corresponde al emisor
			if (m.getReceptor() instanceof ContactoIndividual) {
				ContactoIndividual contacto = (ContactoIndividual) m.getReceptor();
				return contacto.getUsuarioAsociado().getNumTlf() == Integer.parseInt(tlf);	// tlf corresponde al receptor
			}
			return false;
		};
	}
	/**
	 * Filtro que comprueba si el nombre del contacto corresponde al emisor o receptor del mensaje
	 * @param u
	 * @param nombreContacto
	 * @return
	 */
	private Predicate<Mensaje> filtroNombreContacto(Usuario u, String nombreContacto) {
		return m -> {
			if (nombreContacto.isEmpty()) return true; 														// no se ha introducido nombre
			if (m.getReceptor().getNombre().equals(nombreContacto)) return true; 							// nombre Contacto corresponde al receptor			
			Contacto c = u.encontrarContactoPorNombre(nombreContacto);
			return (c instanceof ContactoIndividual && ((ContactoIndividual) c).getUsuarioAsociado().equals(m.getEmisor())); // nombre Contacto corresponde al emisor			
		};
	}	
}
