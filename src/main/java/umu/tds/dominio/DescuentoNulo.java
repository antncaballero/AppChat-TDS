package umu.tds.dominio;

public class DescuentoNulo implements Descuento {

	//patrón null object
	@Override
	public double getDescuento() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "Sin descuento";
	}

}
