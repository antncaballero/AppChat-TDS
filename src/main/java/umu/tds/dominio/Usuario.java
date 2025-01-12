package umu.tds.dominio;

import java.awt.Image;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
	private final LocalDate fechaRegistro;
	
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada, LocalDate fechaRegistro) {
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
		this.fechaRegistro = fechaRegistro;
		
	}
	
	public Usuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {
		this(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada, LocalDate.now());
		
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
	
	public List<Descuento> getDescuentosAplicables() {
		return FactoriaDescuentos.INSTANCE.getDescuentosUsuario(this);
	}
	
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	
	public int getNumMensajesUltimoMes() {
        return (int) contactos.stream()
                .flatMap(c -> c.getMensajesRecibidos().stream())
                .filter(m-> m.getHora().isAfter(LocalDateTime.now().minusMonths(1)))
                .count();
    }
	
	public Contacto encontrarContactoPorNombre(String nombre) {
		return contactos.stream()
				.filter(c -> c.getNombre().equals(nombre))
				.findFirst()
				.orElse(null);
	}
	
	public Contacto encontrarContactoPorNumTlf(int numTlf) {
		return contactos.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(c -> c.getUsuarioAsociado().getNumTlf() == numTlf)
				.findFirst()
				.orElse(null);
	}
	
	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}
	
	public void enviarMensaje(Mensaje mensaje, Contacto contacto) {
		contacto.addMensaje(mensaje);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numTlf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return numTlf == other.numTlf;
	}
	
}
