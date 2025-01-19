package umu.tds.persistencia;

@SuppressWarnings("serial")
/**
 * Excepción que se lanza cuando se produce un error en la capa de persistencia
 */
public class DAOException extends Exception {

	public DAOException(final String mensaje) {
		super(mensaje);
	}

}