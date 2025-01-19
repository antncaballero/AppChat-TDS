package umu.tds.dominio;

/**
 * Interfaz que define el método a implementar por las clases que representan descuentos.
 * 
 */
public interface Descuento {

	public double calcularPrecioFinal(double precioInicial);
	
}
