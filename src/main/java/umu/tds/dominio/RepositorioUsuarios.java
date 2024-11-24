package umu.tds.dominio;

import java.util.HashMap;
import java.util.Map;

public class RepositorioUsuarios {
	
	private Map<Integer, Usuario> usuarios;
	private static RepositorioUsuarios unicaInstancia = null;
	
	private RepositorioUsuarios() {
		usuarios = new HashMap<Integer, Usuario>();
	}
	
	public static RepositorioUsuarios getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioUsuarios();
		return unicaInstancia;
	}
	

}
