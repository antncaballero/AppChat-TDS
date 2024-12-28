package umu.tds.controlador;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

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
	private Usuario usuarioActual = new Usuario("Pepito", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba1.jpeg")));

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

	public boolean registrarUsuario(String nombre, String apellidos, int numTlf, String password, String estado,
			LocalDate fechaNacimiento, String email, String fotoPerfilCodificada) {

		Usuario user = new Usuario(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada);

		if (!repositorioUsuarios.contains(user)) {
			try {
				FactoriaDAO.getInstancia().getUsuarioDAO().registrarUsuario(user);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			repositorioUsuarios.add(user);			
			return true;
		}
		else return false;
	}

	public void hacerPremium(boolean exitoPago) {
		usuarioActual.setPremium(exitoPago);
	}

	public Contacto buscarContactoDeUsuario(String nombre) {
		return VentanaPrincipal.ContactListModel.getContactos().stream().filter(contacto -> contacto.getNombre().equals(nombre)).findFirst().orElse(null);
		//TODO return usuarioActual.encontrarContactoPorNombre(nombre);
	}

	public void enviarMensaje(String mensaje, Contacto contacto) {
		// TODO 
	}

	/**
	 * Metodo que cambia el estado del usuario actual
	 * @param estado
	 * @param grupo
	 */
	public void cambiarEstado(String estado) {
		usuarioActual.setEstado(estado);
		//adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	/**
	 * Metodo que cambia la foto de perfil del usuario actual
	 * @param foto
	 * @param grupo
	 */
	public void cambiarFotoPerfil(String fotoCodificada) {
		usuarioActual.setFotoPerfilCodificada(fotoCodificada);
		//adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public boolean generatePDF(Contacto contacto) {		
		if (!usuarioActual.isPremium()) return false;
		pdfService.generatePDF(contacto);
		return true;
	}


}




