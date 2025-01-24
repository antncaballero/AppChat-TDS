package umu.tds.dominio;

/**
 * Interfaz que define el método a implementar por las clases que representan descuentos.
 * 
 */
public interface Descuento {

	/**
	 * Devuelve el precio final tras aplicar el descuento.
	 * @param precioInicial
	 * @return precio final
	 */
	public double calcularPrecioFinal(double precioInicial);
	
	/**
	 * Devuelve si el descuento es aplicable a un usuario o no.
	 * @param Usuario
	 * @return true si es aplicable, false en caso contrario
	 */
	public boolean isApplicable(Usuario u);
}
