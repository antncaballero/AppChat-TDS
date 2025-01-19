package umu.tds.dominio;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.awt.Image;
import java.io.File;

import umu.tds.utils.Utils;

/**
 * Clase que representa un grupo de contactos individuales.
 * 
 */
public class Grupo extends Contacto {

	private List<ContactoIndividual> participantes;
	private String fotoGrupoCodificada;
	private String estado;
	
	/**
	 * Constructor de la clase Grupo.
	 * 
	 * @param nombre
	 * @param lista de participantes
	 * @param fotoGrupoCodificada
	 * @param estado     
	 */
	public Grupo(String nombre, List<ContactoIndividual> participantes, String fotoGrupoCodificada, String estado) {
		super(nombre);
		this.participantes = participantes;
		this.fotoGrupoCodificada = fotoGrupoCodificada;
		this.estado = estado;
	}
	
	@Override
	public Image getFoto() {
		Optional<Image> imagen = Optional.ofNullable(Utils.convertBase64ToImage(fotoGrupoCodificada));	
		return (imagen.isPresent() ? imagen.get() : Utils.convertBase64ToImage(Utils.convertImageToBase64(new File("src/main/resources/group.png"))));	
	}
	
	/**
	 * Método que devuelve la foto del grupo codificada.
	 * @return fotoGrupoCodificada en base64
	 */
	public String getFotoGrupoCodificada() {
		return fotoGrupoCodificada;
	}
	
	/**
	 * Método que establece la foto del grupo codificada. 
	 * @param fotoGrupoCodificada en base64
	 */
	public void setFotoGrupoCodificada(String fotoGrupoCodificada) {
		this.fotoGrupoCodificada = fotoGrupoCodificada;
	}
	
	/**
	 * Método que devuelve el estado del grupo.
	 * 
	 * @return estado del grupo
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String getEstado() {
		return estado;
	}
	
	/**
	 * Método que devuelve la lista de participantes del grupo.
	 * 
	 * @return lista de participantes
	 */
	public List<ContactoIndividual> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }
	
	/**
	 * Método que establece la lista de participantes del grupo.
	 * @param participantes
	 */
	public void setParticipantes(List<ContactoIndividual> participantes) {
        this.participantes = participantes;
    }
	
	/**
	 * Método que añade un participante al grupo.
	 * @param contacto
	 * @return true si se ha añadido correctamente, false en caso contrario
	 */
	public boolean addParticipante(ContactoIndividual contacto) {
		return participantes.add(contacto);
	}
	
	/**
	 * Método que elimina un participante del grupo.
	 * 
	 * @param contacto
	 * @return true si se ha eliminado correctamente, false en caso contrario
	 */
	public boolean removeParticipante(ContactoIndividual contacto) {
		return participantes.remove(contacto);
	}


	@Override
	public List<Mensaje> getTodosLosMensajes(Usuario usuario) {
		return this.getMensajesRecibidos();
	}
	
	/**
     * Método que edita un grupo.
     * 
     * @param nombre
     * @param participantes
     * @param fotoGrupoCodificada
     */
	public void editarGrupo(String nombre, List<ContactoIndividual> participantes, String fotoGrupoCodificada,
			String estado) {
		setNombre(nombre);
		this.participantes = participantes;
		this.fotoGrupoCodificada = fotoGrupoCodificada;
		this.estado = estado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(estado, fotoGrupoCodificada, participantes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fotoGrupoCodificada, other.fotoGrupoCodificada)
				&& Objects.equals(participantes, other.participantes);
	}
	
}
