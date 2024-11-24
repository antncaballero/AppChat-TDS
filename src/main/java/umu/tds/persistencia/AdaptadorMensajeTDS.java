package umu.tds.persistencia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Mensaje;

public class AdaptadorMensajeTDS implements MensajeDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;
	
	public static AdaptadorMensajeTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorMensajeTDS();
		} else {
			return unicaInstancia;
		}
	}
	
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	
	
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = null;
		
		//Comprobamos que la entidad no este registrada
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (Exception e) {}
		
		if (eMensaje != null) return;
		
	    //Creamos una entidad mensaje
		eMensaje = new Entidad();
		
		//Asignamos tipo
		eMensaje.setNombre("mensaje");
		
		//Asignamos atributos
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("hora", mensaje.getHora().toString()),
				new Propiedad("emoticono", Integer.toString(mensaje.getEmoticono())),
				new Propiedad("tlfEmisor", Integer.toString(mensaje.getTlfEmisor())),
				new Propiedad("tlfReceptor", Integer.toString(mensaje.getTlfReceptor()))
				)));
		
		//Registramos la entidad
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		
		//Asignamos el codigo unico, aprovechamos el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.getId());
		
		//Añadimos al pool
		PoolDAO.getUnicaInstancia().addObjeto(mensaje.getCodigo(), mensaje);
	}

	public void borrarMensaje(Mensaje mensaje) {
		//Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		
		//Borramos la entidad
		servPersistencia.borrarEntidad(eMensaje);
		
		//Si esta en el pool, lo borramos
		if (PoolDAO.getUnicaInstancia().contiene(mensaje.getCodigo())) 
			PoolDAO.getUnicaInstancia().removeObjeto(mensaje.getCodigo());
		
	}

	public void modificarMensaje(Mensaje mensaje) {
		// Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		//Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eMensaje.getPropiedades()) {
			if (prop.getNombre().equals("texto")) {
				prop.setValor(mensaje.getTexto());
			} else if (prop.getNombre().equals("hora")) {
				prop.setValor(mensaje.getHora().toString());
			} else if (prop.getNombre().equals("emoticono")) {
				prop.setValor(Integer.toString(mensaje.getEmoticono()));
			} else if (prop.getNombre().equals("tlfEmisor")) {
				prop.setValor(Integer.toString(mensaje.getTlfEmisor()));
			} else if (prop.getNombre().equals("tlfReceptor")) {
				prop.setValor(Integer.toString(mensaje.getTlfReceptor()));
			}
			// Actualizamos la entidad
			servPersistencia.modificarEntidad(eMensaje);
		}		
	}

	public Mensaje recuperarMensaje(int codigo) {
		//Si esta en el pool, lo devolvemos
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		
		//Sino, recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		//Recuperamos sus propiedades
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		String hora = servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora");
		LocalDateTime fechaHora = LocalDateTime.parse(hora);
		
		String emoticono = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono");
		String tlfEmisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, "tlfEmisor");
		String tlfReceptor = servPersistencia.recuperarPropiedadEntidad(eMensaje, "tlfReceptor");
		
		//Creamos el mensaje
		Mensaje mensaje = new Mensaje(texto, fechaHora, Integer.parseInt(emoticono), Integer.parseInt(tlfEmisor), Integer.parseInt(tlfReceptor));
		mensaje.setCodigo(codigo);
		
		//Añadimos al pool
		PoolDAO.getUnicaInstancia().addObjeto(codigo, mensaje);
		
		//Devolvemos el mensaje
		return mensaje;
	}
}














