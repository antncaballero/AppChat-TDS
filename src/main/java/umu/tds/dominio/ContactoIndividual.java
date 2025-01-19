package umu.tds.dominio;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Clase que representa un contacto individual
 */
public class ContactoIndividual extends Contacto {
	
	private Usuario usuarioAsociado;
	
	/**
	 * Constructor de la clase
	 * @param nombre
	 * @param usuarioAsociado
	 */
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
	
	/**
	 * Devuelve el usuario asociado al contacto
	 * @return usuario asociado al contacto
	 */
	public Usuario getUsuarioAsociado() {
		return usuarioAsociado;
	}
	
	/**
	 * Establece el usuario asociado al contacto
	 * @param usuarioAsociado
	 */
	public void setUsuarioAsociado(Usuario usuarioAsociado) {
		this.usuarioAsociado = usuarioAsociado;
	}
	
	/**
	 * Devuelve el numero de telefono del contacto
	 * @return teléfono del contacto
	 */
	public int getNumTlf() {
        return usuarioAsociado.getNumTlf();
    }
	
	/**
	 * Devuelve si un contacto es ficticio/falso
	 * @return
	 */
	public boolean isContactoFicticio() {
		return this.getNombre().equals(String.valueOf(this.getNumTlf()));
	}

	@Override
	public List<Mensaje> getTodosLosMensajes(Usuario usuario) {
		return Stream.concat(getMensajesRecibidos().stream(), getMensajesEnviados(usuario).stream())
				.sorted()
				.collect(Collectors.toList());
	}

	
	//MÉTODOS AUXILIARES
	
	/**
	 * Devuelve los mensajes enviados por el usuario asociado al contacto al usuario actual 
	 * @param usuario
	 * @return mensajes recibidos por usuario asociado al contacto
	 */
	private List<Mensaje> getMensajesEnviados(Usuario usuario) {		
		Optional<ContactoIndividual> contacto = encontrarContactoIndividualPorUsuario(usuario);		
		return contacto.isPresent() ? contacto.get().getMensajesRecibidos() : new LinkedList<>();
	}
	
	
	/**
	 * Devuelve el contacto individual asociado a un usuario
	 * 
	 * @param usuario
	 * @return contacto individual asociado al usuario
	 */
	private Optional<ContactoIndividual> encontrarContactoIndividualPorUsuario(Usuario usuario) {
		return usuarioAsociado.getContactos().stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(c -> c.getUsuarioAsociado().equals(usuario))
				.findFirst();				
	}

	// MÉTODOS HEREDADOS DE OBJECT
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(usuarioAsociado);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoIndividual other = (ContactoIndividual) obj;
		return Objects.equals(usuarioAsociado, other.usuarioAsociado);
	}
}
