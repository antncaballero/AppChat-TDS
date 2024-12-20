package umu.tds.dominio;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import umu.tds.utils.Utils;

public class Usuario {
	
	private final static String ESTADO_POR_DEFECTO = "Hey, I'm using AppChat";
	
	private int codigo;
	private String nombre;
	private String apellidos;
	private int numTlf;
	private String password;
	private String fotoPerfilCodificada;
	private String estado;
	private final LocalDate fechaNacimiento;
	private String email;
	private boolean isPremium;
	private List<Contacto> contactos;
	
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {
		super();
		this.codigo = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.numTlf = numTlf;
		this.password = password;
		this.estado = estado;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.isPremium = false;
		this.contactos = new LinkedList<>();
		this.fotoPerfilCodificada = fotoPerfilCodificada;
		
	}
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {		
		this(nombre, apellidos, numTlf, password, ESTADO_POR_DEFECTO, fechaNacimiento, email, fotoPerfilCodificada);		
	}	
	
	public String getFotoPerfilCodificada() {
		return fotoPerfilCodificada;
	}
	
	public Image getFotoPerfil() {
		
		Optional<Image> imagen = Optional.ofNullable(Utils.convertBase64ToImage(fotoPerfilCodificada));	
		return (imagen.isPresent() ? imagen.get() : Utils.convertBase64ToImage(Utils.convertImageToBase64(new File("src/main/resources/user.png"))));	
	}


	public void setFotoPerfilCodificada(String fotoPerfilCodificada) {
		this.fotoPerfilCodificada = fotoPerfilCodificada;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getNumTlf() {
		return numTlf;
	}
	public void setNumTlf(int numTlf) {
		this.numTlf = numTlf;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	
	public List<Contacto> getContactos() {
		return Collections.unmodifiableList(contactos);
	}
	
	public void setContactos(List<Contacto> contactos) {
		contactos.stream()
			.forEach(c -> this.contactos.add(c));
		
	}
	

}
