package umu.tds.persistencia;

import java.util.List;

import umu.tds.dominio.Usuario;
/**
 * Interfaz que define las operaciones de persistencia relacionadas con los usuarios
 */
public interface UsuarioDAO {
	public void registrarUsuario(Usuario user);
	public void borrarUsuario(Usuario user);
	public void modificarUsuario(Usuario user);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarTodosLosUsuarios();
}
