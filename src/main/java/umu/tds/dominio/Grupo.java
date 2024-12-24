package umu.tds.dominio;

import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import umu.tds.gui.VentanaPrincipal;
import umu.tds.utils.Utils;

public class Grupo extends Contacto {

	private List<ContactoIndividual> participantes;
	
	public Grupo(String nombre, List<ContactoIndividual> participantes) {
		super(nombre);
		this.participantes = participantes;
	}
	
	@Override
	public ImageIcon getFoto() {	
		return Utils.getScaledIcon("src/main/resources/group.png", 50, 50);
	}
	
	@Override
	public String getEstado() {	
		return "";
	}
	
	public List<ContactoIndividual> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }
	
	public boolean addParticipante(ContactoIndividual contacto) {
		return participantes.add(contacto);
	}
	
}
