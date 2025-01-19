
package umu.tds.dominio;

import java.util.HashMap;
import java.util.Map;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;

/**
 * Repositorio de usuarios.
 */
public enum RepositorioUsuarios {
	INSTANCE;

	private Map<Integer, Usuario> usuarios;
	private Map<Integer, Usuario> usuariosTlf;
	
	/**
	 * Constructor para inicializar los mapas.
     *
	 */
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
	
	/**
	 * Devuelve el usuario con el teléfono pasado por parámetro.
	 * @param tlf
	 * @return
	 */
	public Usuario getUsuarioPorTlf(int tlf) {
		return usuariosTlf.get(tlf);
	}

	/**
	 * Devuelve si un usuario con el código pasado por parámetro existe.
	 * 
	 * @param codigo
	 * @return
	 */
	public boolean contains(Usuario usuario) {
		return usuarios.containsValue(usuario);
	}
	
	/**
	 * Devuelve si un usuario con el teléfono pasado por parámetro existe.
	 * @param tlf
	 * @return
	 */
	public boolean containsTlf(int tlf) {
		return usuariosTlf.containsKey(tlf);
	}

	/**
	 * Añade un usuario al repositorio.
	 * @param usuario
	 */
	public void add(Usuario usuario) {
		usuarios.put(usuario.getCodigo(), usuario);
		usuariosTlf.put(usuario.getNumTlf(), usuario); // Añadir al otro Map
	}
}
