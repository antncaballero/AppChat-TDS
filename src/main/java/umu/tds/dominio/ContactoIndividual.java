package umu.tds.dominio;

import javax.swing.ImageIcon;

public class ContactoIndividual extends Contacto {
	
	private Usuario usuarioAsociado;
	
	public ContactoIndividual(String nombre) {
		super(nombre);
	}
	
	@Override
	public ImageIcon getFoto() {	
		return usuarioAsociado.getFotoPerfil();
	}
	@Override
	public String getEstado() {
		return usuarioAsociado.getEstado();
	}
}
