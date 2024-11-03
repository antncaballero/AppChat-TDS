package umu.tds.dominio;

import java.util.Set;
import javax.swing.ImageIcon;
import umu.tds.gui.VentanaPrincipal;

public class Grupo extends Contacto {

	private Set<ContactoIndividual> participantes;
	
	public Grupo(String nombre, Set<ContactoIndividual> participantes) {
		super(nombre);
		this.participantes = participantes;
	}
	
	@Override
	public ImageIcon getFoto() {	
		return VentanaPrincipal.getScaledIcon("src/main/resources/group.png", 50, 50);
	}
	
	@Override
	public String getEstado() {
		
		return "";
	}
	
	
}
