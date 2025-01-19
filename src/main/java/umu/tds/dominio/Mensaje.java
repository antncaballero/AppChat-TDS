package umu.tds.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa un mensaje.
 * 
 */
public class Mensaje implements Comparable<Mensaje> {
	
	private int codigo;
	private String texto;
	private LocalDateTime hora;
	private int emoticono;
	private Usuario emisor;
	private Contacto receptor;
	
	/**
	 * Constructor de mensajes solo con texto
	 * @param texto
	 * @param emisor
	 * @param receptor
	 */
	public Mensaje(String texto, Usuario emisor, Contacto receptor) {
		this(texto, LocalDateTime.now(), 0, emisor, receptor);
	}
	
	/**
	 * Constructor de mensajes solo con emoticono
	 * 
	 * @param emoticono
	 * @param emisor
	 * @param receptor
	 */
	public Mensaje(int emoticono, Usuario emisor, Contacto receptor) {
		this("", LocalDateTime.now(), emoticono, emisor, receptor);
	}
	
	/**
	 * Constructor de mensajes con todos los parámetros
	 * 
	 * @param texto
	 * @param emoticono
	 * @param emisor
	 * @param receptor
	 * @param hora
	 */
	public Mensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor, Contacto receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.emisor = emisor;
		this.receptor = receptor;
	}
	/**
	 * Método que devuelve el código del mensaje.
	 * @return
	 */
	public int getCodigo() {
		return codigo;
	} 
	/**
	 * Método que establece el código del mensaje.
	 * @param codigo
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
     * Método que devuelve el texto del mensaje.
     * @return texto del mensaje
     */
	public String getTexto() {
		return texto;
	}
	/**
	 * Método que establece el texto del mensaje.
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	/**
	 * Método que devuelve la hora del mensaje.
	 * @return hora del mensaje
	 */
	public LocalDateTime getHora() {
		return hora;
	}
	/**
	 * Método que establece la hora del mensaje.
	 * @param hora
	 */
	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}
	/**
	 * Método que devuelve el emoticono del mensaje.
	 * @return emoticono del mensaje
	 */
	public int getEmoticono() {
		return emoticono;
	}
	/**
     * Método que establece el emoticono del mensaje.
     * @param emoticono
     */
	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}
	/**
	 * Método que devuelve el emisor del mensaje. 
	 * @return usuario emisor del mensaje
	 */
	public Usuario getEmisor() {
		return emisor;
	}
	/**
	 * Método que devuelve el receptor del mensaje.
	 * @return contacto receptor del mensaje
	 */
	public Contacto getReceptor() {
		return receptor;
	}
	/**
	 * Método que establece el receptor del mensaje.
	 * @param contacto receptor
	 */
	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}
	/**
	 * Método que establece el emisor del mensaje.
	 * @param usuario emisor
	 */
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	// Métodos sobreescritos de object
	
	@Override
	public int compareTo(Mensaje mensaje) {
		return hora.compareTo(mensaje.hora);
	}

	@Override
	public int hashCode() {
		return Objects.hash(emisor, emoticono, hora, receptor, texto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		return Objects.equals(emisor, other.emisor) && emoticono == other.emoticono && Objects.equals(hora, other.hora)
				&& Objects.equals(receptor, other.receptor) && Objects.equals(texto, other.texto);
	}	
}
