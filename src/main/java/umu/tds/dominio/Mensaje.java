package umu.tds.dominio;

import java.time.LocalDateTime;

public class Mensaje {
	
	private String texto;
	private LocalDateTime hora;
	public Mensaje(String texto, LocalDateTime hora, int emoticono, int tlfEmisor, int tlfReceptor) {
		super();
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.tlfEmisor = tlfEmisor;
		this.tlfReceptor = tlfReceptor;
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
	public int getTlfEmisor() {
		return tlfEmisor;
	}
	public void setTlfEmisor(int tlfEmisor) {
		this.tlfEmisor = tlfEmisor;
	}
	public int getTlfReceptor() {
		return tlfReceptor;
	}
	public void setTlfReceptor(int tlfReceptor) {
		this.tlfReceptor = tlfReceptor;
	}
	private int emoticono;
	private int tlfEmisor;
	private int tlfReceptor;

}
