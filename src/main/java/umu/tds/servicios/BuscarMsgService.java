package umu.tds.servicios;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public enum BuscarMsgService {
	INSTANCE;

	public List<Mensaje> buscarMensajes(Usuario usuario, List<Mensaje> mensajes, String tlf, String nombreContacto, String texto) {				
		return mensajes.stream()
				.filter(filtroTelefono(tlf))					
				.filter(filtroNombreContacto(usuario, nombreContacto))
				.filter(filtroTexto(texto))		         					
				.collect(Collectors.toList());				
	}
	
	//Filtros como funciones que devuelven predicados
	
	private Predicate<Mensaje> filtroTexto(String texto) {
		return m -> texto.isEmpty() || m.getTexto().contains(texto);
	}
	
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
	
	private Predicate<Mensaje> filtroNombreContacto(Usuario u, String nombreContacto) {
		return m -> {
			if (nombreContacto.isEmpty()) return true; 														// no se ha introducido nombre
			if (m.getReceptor().getNombre().equals(nombreContacto)) return true; 							// nombre Contacto corresponde al receptor			
			Contacto c = u.encontrarContactoPorNombre(nombreContacto);
			return (c instanceof ContactoIndividual && ((ContactoIndividual) c).getUsuarioAsociado().equals(m.getEmisor())); // nombre Contacto corresponde al emisor			
		};
	}	
}
