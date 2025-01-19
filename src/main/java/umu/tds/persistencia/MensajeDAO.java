package umu.tds.persistencia;

import java.util.List;

import umu.tds.dominio.Mensaje;
/**
 * Interfaz que define las operaciones de persistencia relacionadas con los mensajes
 */
public interface MensajeDAO {
	public void registrarMensaje(Mensaje mensaje);
	public void borrarMensaje(Mensaje mensaje);
	public void modificarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
	public List<Mensaje> recuperarTodosLosMensajes();
}
