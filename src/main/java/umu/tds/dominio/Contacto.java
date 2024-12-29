package umu.tds.dominio;

import java.awt.Image;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public abstract class Contacto {
	
	private int codigo;
	private String nombre;
	//TODO revisar si tiene sentido que un grupo tenga una lista de mensajes
	private List<Mensaje> listaMensajes;
	
	public Contacto(String nombre) {
		this.codigo = 0;
		this.nombre = nombre;
		this.listaMensajes = new LinkedList<Mensaje>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	
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
	
	public abstract Image getFoto();
	
	public abstract String getEstado();	//TODO revisar si mover a contactor individual
	
	public boolean addMensaje(Mensaje mensaje) {
		return listaMensajes.add(mensaje);
	}
	
}




