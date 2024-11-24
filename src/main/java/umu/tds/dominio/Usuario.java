package umu.tds.dominio;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Usuario {
	
	private final static String ESTADO_POR_DEFECTO = "Hey, I'm using AppChat";
	
	private int codigo;
	private String nombre;
	private String apellidos;
	private int numTlf;
	private String password;
	private ImageIcon fotoPerfil;
	private String estado;
	private final LocalDate fechaNacimiento;
	private String email;
	private boolean isPremium;
	private List<Contacto> contactos;
	
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email) {
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
		
		try {
			URL imageUrl = new URL("https://robohash.org/" + nombre + "?size=50x50");
			Image image = ImageIO.read(imageUrl);
			this.fotoPerfil = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));	

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, LocalDate fechaNacimiento, String email) {		
		this(nombre, apellidos, numTlf, password, ESTADO_POR_DEFECTO, fechaNacimiento, email);		
	}
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, ImageIcon imagen, String estado,
			LocalDate fechaNacimiento, String email) {
		this(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email);
		this.fotoPerfil = imagen;
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
	public ImageIcon getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(ImageIcon fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
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
