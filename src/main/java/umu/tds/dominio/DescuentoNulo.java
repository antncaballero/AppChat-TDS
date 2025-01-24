package umu.tds.dominio;

/**
 * Clase que representa un descuento nulo.
 * 
 */
public class DescuentoNulo implements Descuento {

	//patrón null object
	@Override
	public double calcularPrecioFinal(double precioInicial) {
		return precioInicial;
	}
	
	@Override
	public String toString() {
		return "Sin descuento";
	}

	@Override
	public boolean isApplicable(Usuario u) {
		return true;
	}

}
