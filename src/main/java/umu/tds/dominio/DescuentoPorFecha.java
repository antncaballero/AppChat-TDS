package umu.tds.dominio;

import java.time.LocalDate;

/*
 * Clase que representa un descuento por fecha.
 */
public class DescuentoPorFecha implements Descuento {
	
	private static final LocalDate FECHA_INICIO_DESCUENTO = LocalDate.of(2024, 10, 10);
	private static final LocalDate FECHA_FIN_DESCUENTO = LocalDate.of(2025, 1, 31);
	private static final double DESCUENTO_FECHA = 0.9;
	
	@Override
	public double calcularPrecioFinal(double precioInicial) {
		return precioInicial * DESCUENTO_FECHA;
	}
	
	@Override
	public String toString() {
		return "Descuento por fecha";
	}

	@Override
	public boolean isApplicable(Usuario u) {
		return u.getFechaRegistro().isAfter(FECHA_INICIO_DESCUENTO) && u.getFechaRegistro().isBefore(FECHA_FIN_DESCUENTO);

	}

}
