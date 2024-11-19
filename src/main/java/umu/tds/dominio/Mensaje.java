package umu.tds.dominio;

import java.time.LocalDateTime;

public class Mensaje {
	
	private int codigo;
	private String texto;
	private LocalDateTime hora;
	private int emoticono;
	private int tlfEmisor;
	private int tlfReceptor;
	
	public Mensaje(String texto, LocalDateTime hora, int emoticono, int tlfEmisor, int tlfReceptor) {
		super();
		this.codigo = 0;
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.tlfEmisor = tlfEmisor;
		this.tlfReceptor = tlfReceptor;
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

}
