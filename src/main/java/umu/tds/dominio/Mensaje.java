package umu.tds.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mensaje implements Comparable<Mensaje> {
	
	private int codigo;
	private String texto;
	private LocalDateTime hora;
	private int emoticono;
	private Usuario emisor;
	private Contacto receptor;
	
	
	public Mensaje(String texto, Usuario emisor, Contacto receptor) {
		this(texto, LocalDateTime.now(), 0, emisor, receptor);
	}
	
	public Mensaje(int emoticono, Usuario emisor, Contacto receptor) {
		this("", LocalDateTime.now(), emoticono, emisor, receptor);
	}
	
	public Mensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor, Contacto receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.emisor = emisor;
		this.receptor = receptor;
	}
	
	public int getCodigo() {
		return codigo;
	}   		
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getHora() {
		return hora;
	}
	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}
	public int getEmoticono() {
		return emoticono;
	}
	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
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
