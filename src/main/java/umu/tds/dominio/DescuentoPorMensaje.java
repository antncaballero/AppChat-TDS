package umu.tds.dominio;

public class DescuentoPorMensaje implements Descuento {

	private static final double DESCUENTO_MENSAJES = 0.85;
	
	@Override
	public double calcularPrecioFinal(double precioInicial) {
		return precioInicial * DESCUENTO_MENSAJES;
	}

	@Override
	public String toString() {
		return "Descuento por mensaje";
	}
}
