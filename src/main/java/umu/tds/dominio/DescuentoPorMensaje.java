package umu.tds.dominio;

/**
 * Clase que representa un descuento por número de mensajes.
 * 
 */
public class DescuentoPorMensaje implements Descuento {

	private static final double DESCUENTO_MENSAJES = 0.85;
	private static final int NUM_MENSAJES_DESCUENTO = 7;
	
	@Override
	public double calcularPrecioFinal(double precioInicial) {
		return precioInicial * DESCUENTO_MENSAJES;
	}

	@Override
	public String toString() {
		return "Descuento por mensaje";
	}

	@Override
	public boolean isApplicable(Usuario u) {
		System.out.println(u.getNumMensajesUltimoMes());
		return u.getNumMensajesUltimoMes() >= NUM_MENSAJES_DESCUENTO;
	}
}
