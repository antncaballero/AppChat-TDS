package umu.tds.persistencia;

import beans.Mensaje;

public interface MensajeDAO {
	public void registrarMensaje(Mensaje mensaje);
	public void borrarMensaje(Mensaje mensaje);
	public void modificarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
}
