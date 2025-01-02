package umu.tds.dominio;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ContactoIndividual extends Contacto {
	
	private Usuario usuarioAsociado;
	
	public ContactoIndividual(String nombre, Usuario usuarioAsociado) {
		super(nombre);
		this.usuarioAsociado = usuarioAsociado;
	}
	
	@Override
	public Image getFoto() {	
		return usuarioAsociado.getFotoPerfil();
	}
	@Override
	public String getEstado() {
		return usuarioAsociado.getEstado();
	}
	
	public Usuario getUsuarioAsociado() {
		return usuarioAsociado;
	}
	
	public void setUsuarioAsociado(Usuario usuarioAsociado) {
		this.usuarioAsociado = usuarioAsociado;
	}
	
	public int getNumTlf() {
        return usuarioAsociado.getNumTlf();
    }
	
	public boolean nombreEsIgualNumTlf() {
		return this.getNombre().equals(String.valueOf(this.getNumTlf()));
	}

	@Override
	public List<Mensaje> getTodosLosMensajes(Usuario usuario) {
		return Stream.concat(getMensajesRecibidos().stream(), getMensajesEnviados(usuario).stream())
				.sorted()
				.collect(Collectors.toList());
	}

	
	//MÉTODOS AUXILIARES
	
	
	private List<Mensaje> getMensajesEnviados(Usuario usuario) {		
		Optional<ContactoIndividual> contacto = encontrarContactoIndividualPorUsuario(usuario);		
		return contacto.isPresent() ? contacto.get().getMensajesRecibidos() : new LinkedList<>();
	}
	
	private Optional<ContactoIndividual> encontrarContactoIndividualPorUsuario(Usuario usuario) {
		return usuarioAsociado.getContactos().stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(c -> c.getUsuarioAsociado().equals(usuario))
				.findFirst();				
	}
	
	
}
