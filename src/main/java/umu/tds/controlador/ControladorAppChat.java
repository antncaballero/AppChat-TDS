package umu.tds.controlador;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import umu.tds.persistencia.ContactoIndividualDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.GrupoDAO;
import umu.tds.persistencia.MensajeDAO;
import umu.tds.persistencia.UsuarioDAO;
import umu.tds.servicios.BuscarMsgService;
import umu.tds.servicios.PDFService;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.RepositorioUsuarios;
import umu.tds.dominio.Usuario;

/**
 * Controlador de la aplicación
 * 
 */
public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia = null;
	private Usuario usuarioActual;

	//Adaptadores
	private UsuarioDAO adaptadorUsuario;
	private ContactoIndividualDAO adaptadorContactoIndividual;
	private GrupoDAO adaptadorGrupo;
	private MensajeDAO adaptadorMensaje;

	//Repositorios
	private RepositorioUsuarios repositorioUsuarios;

	//Servicios
	private PDFService pdfService;
	private BuscarMsgService buscadorMensajes;

	/**
	 * Constructor privado de la clase
	 */
	private ControladorAppChat() {
		inicializarAdaptadores();
		inicializarRepositorios();
		inicializarServicios();

	}

	/**
	 * Método para inicializar los adaptadores
	 */
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			//por defecto se crea FactoriaTDS, se le podría especificar el tipo de factoría
			factoria = FactoriaDAO.getInstancia();
			adaptadorUsuario = factoria.getUsuarioDAO();
			adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
			adaptadorGrupo = factoria.getGrupoDAO();
			adaptadorMensaje = factoria.getMensajeDAO();
		} catch (DAOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Método para inicializar los repositorios
	 */
	private void inicializarRepositorios() {
		repositorioUsuarios = RepositorioUsuarios.INSTANCE;
	}

	/**
	 * Método para inicializar los servicios
	 */
	private void inicializarServicios() {
		pdfService = PDFService.INSTANCE;
		buscadorMensajes = BuscarMsgService.INSTANCE;
	}

	/**
	 * Método para obtener la instancia única de la clase
	 * @return unicaInstancia
	 */
	public static ControladorAppChat getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}

	//Métodos de la clase

	/**
	 * Método para obtener el usuario actual
	 * @return usuarioActual
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * Método para iniciar sesión 
	 * @param numTlf
	 * @param password
	 * @return true si se ha iniciado sesión correctamente, false en caso contrario
	 */
	public boolean iniciarSesion(int numTlf, String password) {
		Optional<Usuario> usuario = Optional.ofNullable(repositorioUsuarios.getUsuarioPorTlf(numTlf));
		if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
			usuarioActual = usuario.get();
			cargarMensajesDeNoAgregados();
			return true;
		}
		return false;
	}

	/**
	 * Método para registrar un usuario
	 * @param nombre
	 * @param apellidos
	 * @param numTlf
	 * @param password
	 * @param estado
	 * @param fechaNacimiento
	 * @param email
	 * @param fotoPerfilCodificada
	 * @return true si se ha registrado correctamente, false en caso contrario
	 */
	public boolean registrarUsuario(String nombre, String apellidos, int numTlf, String password, String estado,LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {
		Usuario user = new Usuario(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada);
		if (!repositorioUsuarios.contains(user)) { //Comprobamos que no exista el usuario
			adaptadorUsuario.registrarUsuario(user);
			repositorioUsuarios.add(user);			
			return true;
		}
		return false;
	}

	/**
	 * Método hacer un usuario premium
	 * @return descuento
	 */
	public void setPremiumUsuarioActual(boolean exitoPago) {
		usuarioActual.setPremium(exitoPago);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Método para buscar un contacto del usuario actual
	 * @param nombre
	 * @return contacto
	 */
	public Contacto buscarContactoDeUsuario(String nombre) {		
		return usuarioActual.encontrarContactoPorNombre(nombre);
	}

	/**
	 * Método para buscar un contacto del usuario actual
	 * 
	 * @param numTlf
	 * @return contacto
	 */
	public Contacto buscarContactoDeUsuario(int numTlf) {
		return usuarioActual.encontrarContactoPorNumTlf(numTlf);
	}

	/**
	 * Metodo que devuelve si un contacto es ficticio o no 
	 * @param contacto
	 */
	public boolean isContactoFicticio(Contacto contacto) {
		if (contacto instanceof ContactoIndividual) return ((ContactoIndividual) contacto).isContactoFicticio();
		else return false; 
	}
	/**
	 * Método para enviar un mensaje
	 * @param texto
	 * @param contacto
	 */
	public void enviarMensaje(String texto, Contacto contacto) {
		Mensaje mensajeNuevo = new Mensaje(texto, usuarioActual, contacto); //Creamos el mensaje a enviar
		usuarioActual.enviarMensaje(mensajeNuevo, contacto);//Lo añadimos a la lista de mensajes
		adaptadorMensaje.registrarMensaje(mensajeNuevo);

		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
		} else {
			Grupo grupo = (Grupo) contacto;
			grupo.getParticipantes().forEach(p -> {
				Mensaje msjIndividual = new Mensaje(mensajeNuevo.getTexto(), mensajeNuevo.getHora(), mensajeNuevo.getEmoticono(), mensajeNuevo.getEmisor(), p);
				usuarioActual.enviarMensaje(msjIndividual,p);
				adaptadorMensaje.registrarMensaje(msjIndividual);
				adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) p);
				adaptadorGrupo.modificarGrupo(grupo);
			});
		}
	}
	/**
	 * Método para enviar un emoticono
	 * @param emoticono
	 * @param contacto
	 */
	public void enviarEmoticono(int emoticono, Contacto contacto) {
		Mensaje emoticonoNuevo = new Mensaje(emoticono, usuarioActual, contacto);
		usuarioActual.enviarMensaje(emoticonoNuevo, contacto);
		adaptadorMensaje.registrarMensaje(emoticonoNuevo);

		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
		} else {
			Grupo grupo = (Grupo) contacto;
			grupo.getParticipantes().forEach(p -> {
				Mensaje msjIndividual = new Mensaje(emoticonoNuevo.getTexto(), emoticonoNuevo.getHora(), emoticonoNuevo.getEmoticono(), emoticonoNuevo.getEmisor(), p);
				usuarioActual.enviarMensaje(msjIndividual,p);
				adaptadorMensaje.registrarMensaje(msjIndividual);
				adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) p);
				adaptadorGrupo.modificarGrupo(grupo);
			});
		}
	}

	/**
	 * Metodo que cambia el nombre de un contacto
	 * 
	 * @param nombre
	 * @param contacto
	 */
	public void cambiarNombreContacto(String nombre, Contacto contacto) {
		contacto.setNombre(nombre);
		adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Método para modificar un grupo
	 * @param nombre
	 * @param contactos
	 * @param fotoGrupoCodificada
	 * @param estado
	 * @param grupo
	 */
	public void editarGrupo(String nombre, List<ContactoIndividual> contactos, String fotoGrupoCodificada, String estado, Grupo grupo) {
		grupo.editarGrupo(nombre, contactos, fotoGrupoCodificada, estado);
		adaptadorGrupo.modificarGrupo(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Metodo que cambia el estado del usuario actual
	 * @param estado
	 * @param grupo
	 */
	public void cambiarEstadoPerfil(String estado) {
		usuarioActual.setEstado(estado);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Metodo que cambia la foto de perfil del usuario actual
	 * @param foto
	 * @param grupo
	 */
	public void cambiarFotoPerfil(String fotoCodificada) {
		usuarioActual.setFotoPerfilCodificada(fotoCodificada);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	/**
	 * Metodo que genera un PDF con los datos
	 * @param contacto
	 * @param directorio
	 * @return true si se ha generado correctamente, false en caso contrario
	 */
	public boolean generatePDF(Contacto contacto, File directorio) {		
		return pdfService.generatePDF(directorio, contacto, usuarioActual);		
	}

	/**
	 * Metodo para buscar mensajes
	 * @param texto
	 * @param tlf
	 * @param nombreContato
	 * @return lista de mensajes encontrados
	 */
	public List<Mensaje> buscarMensaje(String texto, String tlf, String nombreContacto, boolean isEnviados, boolean isRecibidos) {
		List<Mensaje> mensajes = usuarioActual.getContactos().stream()
				.flatMap(c -> c.getTodosLosMensajes(usuarioActual).stream())
				.distinct()
				.collect(Collectors.toList());
		return buscadorMensajes.buscarMensajes(usuarioActual, mensajes, tlf, nombreContacto, texto, isEnviados, isRecibidos);
	}

	/**
	 * Metodo que añade un contacto 
	 * @param nombre
	 * @param contactos
	 */
	public boolean anadirContactoNuevo(String nombre, String tlf) {
		if (repositorioUsuarios.containsTlf(Integer.parseInt(tlf))) { // Si el usuario existe, comprobamos que sea ContactoNuevo
			Usuario usuarioAsociado = repositorioUsuarios.getUsuarioPorTlf(Integer.parseInt(tlf)); //Obtenemos el usuario asociado al tlf
			Optional<ContactoIndividual> contacto = Optional.ofNullable((ContactoIndividual)usuarioActual.encontrarContactoPorNumTlf(Integer.parseInt(tlf)));
			if (usuarioAsociado.equals(usuarioActual)) { //Comprobamos que no sea el mismo usuario
				//No se puede añadir a uno mismo
				return false;
			} else if (!contacto.isPresent()) { //contacto no esta registrado
				ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuarioAsociado);
				adaptadorContactoIndividual.registrarContactoIndividual(nuevoContacto);
				usuarioActual.addContacto(nuevoContacto);
				adaptadorUsuario.modificarUsuario(usuarioActual);
				return true;
			} else if (contacto.get().isContactoFicticio()){
				//Es un contacto ficticio, cambiamos el nombre
				contacto.get().setNombre(nombre);
				adaptadorContactoIndividual.modificarContactoIndividual(contacto.get());
				return true;
			} else {
				//El contacto ya existe y no es ficticio
				return false;
			}			
		}          
		return false;
	}

	/**
	 * Metodo que añade un contacto con nombre=numTlf
	 * @param nombre
	 * @param contact
	 */
	public void anadirContactoPorTlf(String tlf, Usuario usuario) {
		ContactoIndividual nuevoContacto = new ContactoIndividual(tlf, usuario);//Creamos contacto con el tlf
		adaptadorContactoIndividual.registrarContactoIndividual(nuevoContacto);//Registramos el contacto
		usuarioActual.addContacto(nuevoContacto);//Añadimos el contacto a la lista del usuario
		adaptadorUsuario.modificarUsuario(usuarioActual);//Actualizamos el usuario
	}

	/**
	 * Metodo para crear un grupo
	 * @param nombre
	 * @param contactos
	 * @param fotoCodificada
	 * @param estado
	 */
	public void crearGrupo(String nombre, List<ContactoIndividual> contactos, String fotoGrupoCodificada, String estado) {
		Grupo grupo = new Grupo(nombre, contactos, fotoGrupoCodificada, estado);
		adaptadorGrupo.registrarGrupo(grupo);
		usuarioActual.addContacto(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Método para cargar los mensajes de los usuarios que han enviado mensajes a usuario y no están agregados
	 * @param usuario
	 */
	private void cargarMensajesDeNoAgregados() {
		//Obtenemos los usuarios que han enviado mensajes a usuario y no están en su lista de contactos
		adaptadorMensaje.recuperarTodosLosMensajes().stream()
			.filter(m -> m.getReceptor() instanceof ContactoIndividual)					//receptor es un contacto individual
			.filter(m -> { 
			ContactoIndividual contacto = (ContactoIndividual) m.getReceptor();             			
			return contacto.getUsuarioAsociado().equals(usuarioActual);				//receptor es usuario actual
			})
			.filter(m -> !usuarioActual.tieneAgregado(m.getEmisor())) 					//emisor no está en la lista de contactos del usuario actual
			.map(m -> m.getEmisor())                                                    //obtenemos el emisor						
			.distinct()				                                                    //eliminamos duplicados
			.forEach(u -> anadirContactoPorTlf(String.valueOf(u.getNumTlf()), u));		//se crea un contacto ficticio para el usuario	
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Método para cerrar sesión
	 */
	public void cerrarSesion() {
		usuarioActual = null;
	}

}




