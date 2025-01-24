package umu.tds.dominio;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que representa la factoría de descuentos.
 * 
 */
public enum FactoriaDescuentos {
	INSTANCE;

	private static final List <Descuento> DESCUENTOS_POSIBLES = Arrays.asList(
				new DescuentoPorFecha(), 
				new DescuentoPorMensaje(),
				new DescuentoNulo());
		
	public List<Descuento> getDescuentosUsuario(Usuario u) {		
		return DESCUENTOS_POSIBLES.stream()
				.filter(d -> d.isApplicable(u)).
				collect(Collectors.toList());
	}
	
}
