package umu.tds.dominio;

public class DescuentoPorFecha implements Descuento {

	@Override
	public double getDescuento() {
		return 0.9;
	}
	
	@Override
	public String toString() {
		return "Descuento por fecha";
	}

}
