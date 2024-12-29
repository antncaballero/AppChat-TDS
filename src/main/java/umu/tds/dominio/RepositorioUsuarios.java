
package umu.tds.dominio;

import java.util.HashMap;
import java.util.Map;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

public enum RepositorioUsuarios {
	INSTANCE;

	private Map<Integer, Usuario> usuarios;
	private Map<Integer, Usuario> usuariosTlf;

	private RepositorioUsuarios() {
		usuarios = new HashMap<Integer, Usuario>();
		usuariosTlf = new HashMap<Integer, Usuario>();
		try {
			FactoriaDAO.getInstancia().getUsuarioDAO().recuperarTodosLosUsuarios().stream()
		    .forEach(usuario -> {
		        usuarios.put(usuario.getCodigo(), usuario);
		        usuariosTlf.put(usuario.getNumTlf(), usuario); // Añadir al otro Map
		    });
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getUsuarioPorTlf(int tlf) {
		return usuariosTlf.get(tlf);
	}

	public boolean contains(Usuario usuario) {
		return usuarios.containsValue(usuario);
	}

	public void add(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
		usuariosTlf.put(usuario.getNumTlf(), usuario); // Añadir al otro Map
	}
}
