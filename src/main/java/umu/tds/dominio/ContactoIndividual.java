package umu.tds.dominio;

import java.awt.Image;


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
		return this.getNombre().equals(String.valueOf(this.getNumTlf())) ? true : false;
	}
}
