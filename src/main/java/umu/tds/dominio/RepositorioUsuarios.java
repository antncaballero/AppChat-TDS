package umu.tds.dominio;

import java.util.HashMap;
import java.util.Map;

import umu.tds.dao.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public class RepositorioUsuarios {
	
	private Map<Integer, Usuario> usuarios;
	private static RepositorioUsuarios unicaInstancia = null;
	
	private RepositorioUsuarios() {
		usuarios = new HashMap<Integer, Usuario>();
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().recuperarTodosLosUsuarios().stream()
				.forEach(usuario -> usuarios.put(usuario.getCodigo(), usuario));
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public static RepositorioUsuarios getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioUsuarios();
		return unicaInstancia;
	}
	
	public boolean contains(Usuario usuario) {
		return usuarios.containsValue(usuario);
	}
	
	public void add(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
	}
	

}
