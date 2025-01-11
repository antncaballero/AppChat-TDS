package umu.tds.dominio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.awt.Image;
import java.io.File;

import umu.tds.utils.Utils;

public class Grupo extends Contacto {

	private List<ContactoIndividual> participantes;
	private String fotoGrupoCodificada;
	private String estado;
	
	public Grupo(String nombre, List<ContactoIndividual> participantes, String fotoGrupoCodificada, String estado) {
		super(nombre);
		this.participantes = participantes;
		this.fotoGrupoCodificada = fotoGrupoCodificada;
		this.estado = estado;
	}
	
	@Override
	public Image getFoto() {
		return getFotoGrupo();
	}
	
	public String getFotoGrupoCodificada() {
		return fotoGrupoCodificada;
	}
	
	public Image getFotoGrupo() {
		
		Optional<Image> imagen = Optional.ofNullable(Utils.convertBase64ToImage(fotoGrupoCodificada));	
		return (imagen.isPresent() ? imagen.get() : Utils.convertBase64ToImage(Utils.convertImageToBase64(new File("src/main/resources/group.png"))));	
	}

	public void setFotoGrupoCodificada(String fotoGrupoCodificada) {
		this.fotoGrupoCodificada = fotoGrupoCodificada;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String getEstado() {	//TODO revisar
		return estado;
	}
	
	public List<ContactoIndividual> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }
	
	public void setParticipantes(List<ContactoIndividual> participantes) {
        this.participantes = participantes;
    }
	
	public boolean addParticipante(ContactoIndividual contacto) {
		return participantes.add(contacto);
	}
	
	public boolean removeParticipante(ContactoIndividual contacto) {
		return participantes.remove(contacto);
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
	
	public void editarGrupo(String nombre, List<ContactoIndividual> participantes, String fotoGrupoCodificada,
			String estado) {
		setNombre(nombre);
		this.participantes = participantes;
		this.fotoGrupoCodificada = fotoGrupoCodificada;
		this.estado = estado;
	}
	
	
	
}
