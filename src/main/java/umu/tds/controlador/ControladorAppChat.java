package umu.tds.controlador;
import java.util.List;

import umu.tds.dao.FactoriaDAO;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia = null;
	private Usuario usuarioActual;

	private ControladorAppChat() {
	}

	public static ControladorAppChat getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppChat();
		return unicaInstancia;
	}
	
}




