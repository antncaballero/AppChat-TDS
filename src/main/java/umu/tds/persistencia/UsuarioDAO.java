package umu.tds.persistencia;

import umu.tds.dominio.Usuario;

public interface UsuarioDAO {

	public void registrarUsuario(Usuario user);
	public void borrarUsuario(Usuario user);
	public void modificarUsuario(Usuario user);
	public Usuario recuperarUsuario(int codigo);
	

}
