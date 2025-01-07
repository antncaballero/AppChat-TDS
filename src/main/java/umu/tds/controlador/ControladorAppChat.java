package umu.tds.controlador;
import java.io.File;
import java.time.LocalDate;
import java.util.List;


import java.util.Optional;
import javax.swing.ImageIcon;

import umu.tds.persistencia.AdaptadorContactoIndividualTDS;
import umu.tds.persistencia.AdaptadorGrupoTDS;
import umu.tds.persistencia.AdaptadorMensajeTDS;
import umu.tds.persistencia.AdaptadorUsuarioTDS;
import umu.tds.persistencia.ContactoIndividualDAO;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.GrupoDAO;
import umu.tds.persistencia.MensajeDAO;
import umu.tds.persistencia.UsuarioDAO;
import umu.tds.utils.Utils;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Descuento;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.PDFService;
import umu.tds.dominio.RepositorioUsuarios;
import umu.tds.dominio.Usuario;
import umu.tds.gui.VentanaPrincipal;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia = null;

	//TODO quitar el new Usuario, esto es para hacer pruebas
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
	
	//Constructor privado
	private ControladorAppChat() {
		
		inicializarAdaptadores();
		inicializarRepositorios();
		inicializarServicios();
		
	}
	
	//Inicialización de los adaptadores, repositorios y servicios
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			//por defecto se crea FactoriaTDS, se le podría especificar el tipo de factoría
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}
	
	private void inicializarRepositorios() {
		repositorioUsuarios = RepositorioUsuarios.INSTANCE;
	}
	
	private void inicializarServicios() {
		pdfService = PDFService.INSTANCE;
	}

	//Método para obtener la instancia de la clase
	public static ControladorAppChat getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}

	//Métodos de la clase
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public List<Descuento> getDescuentosUsuarioActual() {
		return usuarioActual.getDescuentosAplicables();
	}

	public boolean iniciarSesion(int numTlf, String password) {
		Optional<Usuario> usuario = Optional.ofNullable(repositorioUsuarios.getUsuarioPorTlf(numTlf));
		if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
			usuarioActual = usuario.get();
			return true;
		}
		return false;
	}
	
	public boolean registrarUsuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {

		Usuario user = new Usuario(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada);

		if (!repositorioUsuarios.contains(user)) {
			adaptadorUsuario.registrarUsuario(user);
			repositorioUsuarios.add(user);			
			return true;
		}
		else return false;
	}

	public void hacerPremium(boolean exitoPago) {
		usuarioActual.setPremium(exitoPago);
	}

	public Contacto buscarContactoDeUsuario(String nombre) {		
		return usuarioActual.encontrarContactoPorNombre(nombre);
	}
	
	public boolean esContactoRegistrado(Contacto contacto) {
		return ((ContactoIndividual) contacto).nombreEsIgualNumTlf();
	}
	
	public void enviarMensaje(String texto, Contacto contacto) {
		Mensaje mensajeNuevo = new Mensaje(texto, usuarioActual, contacto);
		usuarioActual.enviarMensaje(mensajeNuevo, contacto);
		adaptadorMensaje.registrarMensaje(mensajeNuevo);
		
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
		} else {
			adaptadorGrupo.modificarGrupo((Grupo) contacto);
		}
	}
	
	public void enviarEmoticono(int emoticono, Contacto contacto) {
		Mensaje emoticonoNuevo = new Mensaje(emoticono, usuarioActual, contacto);
		usuarioActual.enviarMensaje(emoticonoNuevo, contacto);
		adaptadorMensaje.registrarMensaje(emoticonoNuevo);
		
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
		} else {
			adaptadorGrupo.modificarGrupo((Grupo) contacto);
		}
	}

	/**
	 * Metodo que cambia el estado del usuario actual
	 * @param estado
	 * @param grupo
	 */
	public void cambiarEstado(String estado) {
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
	
	public boolean generatePDF(Contacto contacto, File directorio) {		
		return pdfService.generatePDF(directorio, contacto);		
	}

	/**
	 * Metodo que añade un contacto 
	 * @param nombre
	 * @param contactos
	 */
	public boolean anadirContactoNuevo(String nombre, String tlf) {
		Optional<Usuario> usuario = Optional.ofNullable(repositorioUsuarios.getUsuarioPorTlf(Integer.parseInt(tlf)));
		if (usuario.isPresent()) { // Si el usuario existe, comprobamos que sea ContactoNuevo
			Optional<ContactoIndividual> contacto = Optional.ofNullable((ContactoIndividual)usuarioActual.encontrarContactoPorNumTlf(Integer.parseInt(tlf)));
			if (!contacto.isPresent()) {
				System.out.println("El contacto no está registrado");
				ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, usuario.get());
				adaptadorContactoIndividual.registrarContactoIndividual(nuevoContacto);
				usuarioActual.addContacto(nuevoContacto);
				adaptadorUsuario.modificarUsuario(usuarioActual);
			} else {
				System.out.println("El contacto ya existe con nombre: " + contacto.get().getNombre());
				contacto.get().setNombre(nombre);
				adaptadorContactoIndividual.modificarContactoIndividual(contacto.get());
			}
			return true;
		}          
		System.out.println("El usuario no existe");
		return false;
	}
	
	/**
     * Metodo que añade un contacto con nombre=numTlf
     * @param nombre
     * @param contact
     */
	public void anadirContactoPorTlf(String tlf) {
		ContactoIndividual nuevoContacto = new ContactoIndividual(tlf, usuarioActual);//Creamos contacto con el tlf
		adaptadorContactoIndividual.registrarContactoIndividual(nuevoContacto);//Registramos el contacto
		usuarioActual.addContacto(nuevoContacto);//Añadimos el contacto a la lista del usuario
		adaptadorUsuario.modificarUsuario(usuarioActual);//Actualizamos el usuario
	}
	
	public List<Contacto> getContactosUsuarioActual() {
		return usuarioActual.getContactos();
	}
	
	public void crearGrupo(String nombre, List<ContactoIndividual> contactos) {
		Grupo grupo = new Grupo(nombre, contactos);
		adaptadorGrupo.registrarGrupo(grupo);
		usuarioActual.addContacto(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		//TODO habría que modificar los participantes?
	}

}




