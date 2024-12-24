package umu.tds.dominio;

public class DescuentoPorMensaje implements Descuento {

	@Override
	public double getDescuento() {
		return 0.85;
	}

	@Override
	public String toString() {
		return "Descuento por mensaje";
	}
}
