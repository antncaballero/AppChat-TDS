package umu.tds.dominio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

public abstract class Contacto {
	
	private int codigo;
	private String nombre;
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
	
	public List<Mensaje> getListaMensajes() {
        return Collections.unmodifiableList(listaMensajes);
    }
	
	public abstract ImageIcon getFoto();
	
	public abstract String getEstado();	
	
}




