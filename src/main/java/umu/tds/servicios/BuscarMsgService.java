package umu.tds.servicios;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.dominio.Contacto;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public enum BuscarMsgService {

	INSTANCE;

	private List<Mensaje> mensajes = new LinkedList<>();
	
	public List<Mensaje> buscarMensajes(Usuario usuario, String texto, Contacto contactoTlf, Contacto contactoNombre) {
		buscarMensajePorNombre(usuario, contactoNombre);
		buscarMensajePorTlf(usuario, contactoTlf);
		if (mensajes.isEmpty()) {
			usuario.getContactos().forEach(contacto -> contacto.getTodosLosMensajes(usuario).forEach(mensaje -> mensajes.add(mensaje)));
		}

		if (!texto.isEmpty()) {
		    mensajes = mensajes.stream()
		            .filter(mensaje -> mensaje.getTexto().contains(texto))
		            .distinct()
		            .collect(Collectors.toList());
		}

		return mensajes;
	}
	
	private void buscarMensajePorTlf(Usuario usuario, Contacto contactoTlf) {
		if (contactoTlf != null) {
			contactoTlf.getTodosLosMensajes(usuario).forEach(mensaje -> mensajes.add(mensaje));
		}
	}
	
	private void buscarMensajePorNombre(Usuario usuario, Contacto contactoNombre) {
		if (contactoNombre != null) {
			contactoNombre.getTodosLosMensajes(usuario).forEach(mensaje -> mensajes.add(mensaje));
		}
		
	}
}
