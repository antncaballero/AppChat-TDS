package umu.tds.dominio;
/*
 * Clase que representa un descuento por fecha.
 */
public class DescuentoPorFecha implements Descuento {

	private static final double DESCUENTO_FECHA = 0.9;
	@Override
	public double calcularPrecioFinal(double precioInicial) {
		return precioInicial * DESCUENTO_FECHA;
	}
	
	@Override
	public String toString() {
		return "Descuento por fecha";
	}

}
