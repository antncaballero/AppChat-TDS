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
import java.util.stream.Collectors;

import umu.tds.utils.Utils;

/**
 * Clase que representa un usuario.
 */
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

	/**
	 * Constructor de la clase Usuario.
	 * @param nombre
	 * @param apellidos
	 * @param numTlf
	 * @param password
	 * @param estado
	 * @param fechaNacimiento
	 * @param email
	 * @param fotoPerfilCodificada
	 * @param fechaRegistro
	 */
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
	/**
	 * constructor sin fecha de registro
	 * @param nombre
	 * @param apellidos
	 * @param numTlf
	 * @param password
	 * @param estado
	 * @param fechaNacimiento
	 * @param email
	 * @param fotoPerfilCodificada
	 */
	public Usuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {
		this(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada, LocalDate.now());

	}	
	/**
	 * Constructor sin estado ni fecha.
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param numTlf
	 * @param password
	 * @param fechaNacimiento
	 * @param email
	 * @param fotoPerfilCodificada
	 */
	public Usuario(String nombre, String apellidos, int numTlf, String password, LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {		
		this(nombre, apellidos, numTlf, password, ESTADO_POR_DEFECTO, fechaNacimiento, email, fotoPerfilCodificada);		
	}	

	/**
	 * Devuelve la foto de perfil codificada.
	 * @return fotoPerfilCodificada en base64
	 */
	public String getFotoPerfilCodificada() {
		return fotoPerfilCodificada;
	}
	/**
	 * Devuelve la foto de perfil.
	 * @return Image de la foto de perfil
	 */
	public Image getFotoPerfil() {		
		Optional<Image> imagen = Optional.ofNullable(Utils.convertBase64ToImage(fotoPerfilCodificada));	
		return (imagen.isPresent() ? imagen.get() : Utils.convertBase64ToImage(Utils.convertImageToBase64(new File("src/main/resources/user.png"))));	
	}

	/**
	 * Establece la foto de perfil codificada en base64 
	 * @param fotoPerfilCodificada en base64
	 */
	public void setFotoPerfilCodificada(String fotoPerfilCodificada) {
		this.fotoPerfilCodificada = fotoPerfilCodificada;
	}

	/**
	 * Devuelve el código del usuario. 
	 * @return código del usuario
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * Establece el código del usuario.
	 * @param codigo
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * Devuelve el nombre del usuario. 
	 * @return nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece el nombre del usuario.
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Devuelve los apellidos del usuario. 
	 * @return apellidos del usuario
	 */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * Establece los apellidos del usuario. 
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 * Devuelve el número de teléfono del usuario. 
	 * @return número de teléfono del usuario
	 */
	public int getNumTlf() {
		return numTlf;
	}
	/**
	 * Establece el número de teléfono del usuario. 
	 * @param numTlf
	 */
	public void setNumTlf(int numTlf) {
		this.numTlf = numTlf;
	}
	/**
	 * Devuelve la contraseña del usuario. 
	 * @return contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Establece la contraseña del usuario.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Devuelve el estado del usuario. 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Establece el estado del usuario.
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * Devuelve el email del usuario.
	 * @return email del usuario
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Establece el email del usuario.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario.
	 * @return fecha de nacimiento del usuario
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * Devuelve si el usuario es premium. 
	 * @return true si es premium, false en caso contrario
	 */
	public boolean isPremium() {
		return isPremium;
	}
	/**
	 * Establece si el usuario es premium. 
	 * @param isPremium
	 */
	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	/**
	 * Devuelve los contactos del usuario.
	 * @return lista de contactos
	 */
	public List<Contacto> getContactos() {
		return Collections.unmodifiableList(contactos);
	}

	/**
	 * Devuelve los contactos ordenados por el último mensaje recibido.
	 * @return lista de contactos ordenados
	 */
	public List<Contacto> getContactosOrdenados() {
		//Para que se ordenen los contactos por el último mensaje recibido
		return contactos.stream()
				.sorted((c1, c2) -> {
					List<Mensaje> listaC1 = c1.getTodosLosMensajes(this);
					List<Mensaje> listaC2 = c2.getTodosLosMensajes(this);
					if (listaC1.isEmpty() && listaC2.isEmpty()) {
						return 0; // Si ambos están vacíos, son considerados iguales
					} else if (listaC1.isEmpty()) {
						return 1; // Si solo el contacto actual tiene lista vacía, va al final
					} else if (listaC2.isEmpty()) {
						return -1; // Si solo el otro contacto tiene lista vacía, va al final
					}
					// Ambos contactos tienen mensajes, por lo tanto se compara el último mensaje
					Mensaje ultimoMensajeThis = listaC1.get(listaC1.size() - 1);
					Mensaje ultimoMensajeOtro = listaC2.get(listaC2.size() - 1);
					return ultimoMensajeOtro.compareTo(ultimoMensajeThis);
				})
				.collect(Collectors.toUnmodifiableList());
	}
	/**
	 * Establece los contactos del usuario.
	 * @param contactos
	 */
	public void setContactos(List<Contacto> contactos) {
		contactos.stream()
		.forEach(c -> this.contactos.add(c));	
	}
	/**
	 * Devuelve los descuentos aplicables al usuario.
	 * @return lista de descuentos aplicables al usuario
	 */
	public List<Descuento> getDescuentosAplicables() {
		return FactoriaDescuentos.INSTANCE.getDescuentosUsuario(this);
	}
	/**
	 * Devuelve la fecha de registro del usuario.
	 * @return fecha de registro del usuario
	 */
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * Devuelve el número de mensajes enviados por el usuario el último mes.
	 * @return número de mensajes enviados por el usuario
	 */
	public int getNumMensajesUltimoMes() {
		return (int) contactos.stream()
				.flatMap(c -> c.getTodosLosMensajes(this).stream())
				.filter(m-> m.getHora().isAfter(LocalDateTime.now().minusMonths(1)))
				.count();
	}
	/**
	 * Devuelve el contacto con el nombre pasado por parámetro.
	 * @param nombre del contacto
	 * @return contacto con el nombre pasado por parámetro o null si no existe
	 */
	public Contacto encontrarContactoPorNombre(String nombre) {
		return contactos.stream()
				.filter(c -> c.getNombre().equals(nombre))
				.findFirst()
				.orElse(null);
	}
	/**
	 * Devuelve el contacto con el número de teléfono pasado por parámetro.
	 * @param numTlf
	 * @return contacto con el número de teléfono pasado por parámetro o null si no existe
	 */
	public Contacto encontrarContactoPorNumTlf(int numTlf) {
		return contactos.stream()
				.filter(c -> c instanceof ContactoIndividual)
				.map(c -> (ContactoIndividual) c)
				.filter(c -> c.getUsuarioAsociado().getNumTlf() == numTlf)
				.findFirst()
				.orElse(null);
	}
	/**
	 * Añade un contacto al usuario.
	 * @param contacto
	 */
	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}
	/**
	 * Elimina un mensaje a un contacto.
	 * @param contacto
	 * @param mensaje
	 */
	public void enviarMensaje(Mensaje mensaje, Contacto contacto) {
		contacto.addMensaje(mensaje);
	}
	/**
	 * Devuelve si el usuario tiene agregado a otro usuario como contacto.
	 * @param usuario
	 * @return
	 */
	public boolean tieneAgregado(Usuario usuario) {
		return contactos.stream().
				anyMatch(c -> c instanceof ContactoIndividual && 
						((ContactoIndividual) c).getUsuarioAsociado().equals(usuario));
	}

	// Métodos equals y hashCode
	
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
