package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Mensaje;

public class AdaptadorMensajeTDS {
	
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
	}

	public void borrarMensaje(Mensaje mensaje) {
		//Recuperamos la entidad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		
		//Borramos la entidad
		servPersistencia.borrarEntidad(eMensaje);
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
		// TODO - implement AdaptadorMensajeDAO.recuperarMensaje
		return null;
	}
}
