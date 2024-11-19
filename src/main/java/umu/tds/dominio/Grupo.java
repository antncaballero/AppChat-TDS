package umu.tds.dominio;

import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import umu.tds.gui.VentanaPrincipal;

public class Grupo extends Contacto {

	private List<ContactoIndividual> participantes;
	
	public Grupo(String nombre, List<ContactoIndividual> participantes) {
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
	
	public List<ContactoIndividual> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }
	
	
}
