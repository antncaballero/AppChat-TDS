package umu.tds.dominio;

import java.util.Collections;
import java.util.List;
import java.awt.Image;
import umu.tds.utils.Utils;

public class Grupo extends Contacto {

	private List<ContactoIndividual> participantes;
	
	public Grupo(String nombre, List<ContactoIndividual> participantes) {
		super(nombre);
		this.participantes = participantes;
	}
	
	@Override
	public Image getFoto() {	
		return Utils.getImage("src/main/resources/group.png");
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
	
	@Override
	public void addMensaje(Mensaje mensaje) {
		super.addMensaje(mensaje);
		participantes.forEach(p -> p.addMensaje(mensaje));
	}

	@Override
	public List<Mensaje> getTodosLosMensajes(Usuario usuario) {
		return this.getMensajesRecibidos();
	}

	
	
	
}
