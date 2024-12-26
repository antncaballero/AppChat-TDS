package umu.tds.dominio;

import javax.swing.ImageIcon;

import umu.tds.utils.Utils;

public class ContactoIndividual extends Contacto {
	
	private Usuario usuarioAsociado;
	
	public ContactoIndividual(String nombre, Usuario usuarioAsociado) {
		super(nombre);
		this.usuarioAsociado = usuarioAsociado;
	}
	
	@Override
	public ImageIcon getFoto() {	
		return Utils.imageToImageIcon(usuarioAsociado.getFotoPerfil(), 50, 50);
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
}
