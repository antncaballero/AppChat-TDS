package umu.tds.dominio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class Contacto {
	
	private String nombre;
	private List<Mensaje> listaMensajes;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		this.listaMensajes = new LinkedList<Mensaje>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public List<Mensaje> getListaMensajes() {
        return Collections.unmodifiableList(listaMensajes);
    }
	
	public abstract ImageIcon getFoto();
	
	public abstract String getEstado();
	
	
}




