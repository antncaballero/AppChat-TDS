package umu.tds.dominio;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que representa la factoría de descuentos.
 * 
 */
public enum FactoriaDescuentos {
	INSTANCE;
	
	public static final int NUM_MENSAJES_DESCUENTO = 7;
	public static final LocalDate FECHA_INICIO_DESCUENTO = LocalDate.of(2024, 10, 10);
	public static final LocalDate FECHA_FIN_DESCUENTO = LocalDate.of(2025, 1, 20);
		
	public List<Descuento> getDescuentosUsuario(Usuario u) {		
		LinkedList<Descuento> descuentos = new LinkedList<>();
		
		descuentos.add(new DescuentoNulo()); //patrón null object
		if(DescuentoPorMensajesAplicable(u)) descuentos.add(new DescuentoPorMensaje());
		if(DescuentoPorFechaAplicable(u)) descuentos.add(new DescuentoPorFecha());
		
		return descuentos;
	}
	
	/**
	 * Comprueba si el usuario cumple las condiciones para aplicar el descuento por
	 * mensajes
	 * 
	 * @param Usuario u
	 * @return true si cumple las condiciones, false en caso contrario
	 */
	private boolean DescuentoPorMensajesAplicable(Usuario u) {      
        return u.getNumMensajesUltimoMes() >= NUM_MENSAJES_DESCUENTO;      
    }
	
	/**
	 * Comprueba si el usuario cumple las condiciones para aplicar el descuento por 
	 * fecha de registro
	 * 
	 * @param Usuario u
	 * @return true si cumple las condiciones, false en caso contrario
	 */
	private boolean DescuentoPorFechaAplicable(Usuario u) {
		return u.getFechaRegistro().isAfter(FECHA_INICIO_DESCUENTO) && u.getFechaRegistro().isBefore(FECHA_FIN_DESCUENTO);
		
	}
}
