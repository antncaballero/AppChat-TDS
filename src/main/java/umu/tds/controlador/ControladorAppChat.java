package umu.tds.controlador;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.swing.ImageIcon;

import umu.tds.persistencia.FactoriaDAO;
import umu.tds.utils.Utils;
import umu.tds.dao.DAOException;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Descuento;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.RepositorioUsuarios;
import umu.tds.dominio.Usuario;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia = null;
	private Usuario usuarioActual= new Usuario("Antonio", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
	private RepositorioUsuarios repositorioUsuarios;

	private ControladorAppChat() {
	}

	public static ControladorAppChat getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}
	
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
}




