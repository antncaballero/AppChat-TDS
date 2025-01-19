package umu.tds.dominio;

import java.awt.Image;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Clase abstracta que representa un contacto de un usuario
 * 
 */
public abstract class Contacto {
	
	private int codigo;
	private String nombre;
	private List<Mensaje> listaMensajes;
	
	/**
	 * Constructor de la clase
	 * 
	 * @param nombre del contacto
	 */
	public Contacto(String nombre) {
		this.codigo = 0;
		this.nombre = nombre;
		this.listaMensajes = new LinkedList<Mensaje>();
	}

	/**
	 * Devuelve el nombre del contacto
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre del contacto
	 * 
	 * @param nombre del contacto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el código del contacto
	 */
	public int getCodigo() {
		return codigo;
	}
	
	/**
	 * Establece el código del contacto
	 * @param codigo
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Devuelve una lista de mensajes recibidos por el contacto, del usuario que lo tiene agregado
	 * 
	 * @return lista de mensajes
	 */
	public List<Mensaje> getMensajesRecibidos() {
        return Collections.unmodifiableList(listaMensajes);
    }
	
	/**
	 * Devuelve la foto del contacto
	 * @return
	 */
	public abstract Image getFoto();
	
	/**
	 * Devuelve el estado del contacto
	 * @return
	 */
	public abstract String getEstado();
	
	/**
	 * Añade un mensaje a la lista de mensajes
	 * @param mensaje
	 */
	public void addMensaje(Mensaje mensaje) {
		listaMensajes.add(mensaje);
	};
		
	/**
	 * Devuelve todos los mensajes entre el usuario que tiene agregado al contacto y el usuario asociado al contacto
	 * 
	 * @param usuario que tiene agregado al contacto
	 * @return lista de mensajes
	 */
	public abstract List<Mensaje> getTodosLosMensajes(Usuario usuario);

	@Override
	public int hashCode() {
		return Objects.hash(codigo, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return codigo == other.codigo && Objects.equals(nombre, other.nombre);
	}
}

