package umu.tds.persistencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Usuario;

public class AdaptadorUsuarioTDS implements UsuarioDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorUsuarioTDS();
		}else {
			return unicaInstancia;
		}
	}
	
	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registrarUsuario(Usuario user) {
		Entidad eUsuario = null;
		//Comprobamos que la entidad no este registrada
		try {
			eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		} catch (Exception e) {}
		if (eUsuario != null) return;
		
		//Registramos atributos que son objetos
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstancia();
		for (Contacto contacto: user.getContactos()) {
			if(contacto instanceof ContactoIndividual) {
				adaptadorContactoIndividual.registrarContactoIndividual((ContactoIndividual) contacto);
			}else if (contacto instanceof Grupo) {
				adaptadorGrupo.registrarGrupo((Grupo) contacto);
			}
		}
		
		//Creamos una entidad usuario
		eUsuario = new Entidad();
		
		//Asignamos tipo
		eUsuario.setNombre("usuario");
		
		//Registramos atributos
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", user.getNombre()),
				new Propiedad("apellidos", user.getApellidos()),
				new Propiedad("numTlf", Integer.toString(user.getNumTlf())),
				new Propiedad("password", user.getPassword()),
				new Propiedad("fotoPerfil", String.valueOf(user.getFotoPerfil().getDescription())),
				new Propiedad("estado", user.getEstado()),
				new Propiedad("fechaNacimiento", user.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
				new Propiedad("email", user.getEmail()),
				new Propiedad("isPremium", String.valueOf(user.isPremium())),
				new Propiedad("contactos", obtenerCodigosContactos(user.getContactos()))
			    )));
		
		// registrar entidad cliente
		eUsuario = Optional.ofNullable(servPersistencia.registrarEntidad(eUsuario)).get();
		
		// asignar identificador unico al usuario, aprovechando el que genera el servicio de persistencia
		user.setCodigo(eUsuario.getId());
		
		//Añadimos al pool
		PoolDAO.getUnicaInstancia().addObjeto(user.getCodigo(), user);
	}
	

	@Override
	public void borrarUsuario(Usuario user) {
		//Se recupera entidad usuario
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstancia();
		
		//Eliminamos sus entidades agregadas
		for (Contacto contacto : user.getContactos()) {
			if (contacto instanceof ContactoIndividual) {
				adaptadorContactoIndividual.borrarContactoIndividual((ContactoIndividual) contacto);
			} else{
				adaptadorGrupo.borrarGrupo((Grupo) contacto);
			}
		}
		
		//Eliminamos la entidad usuario
		servPersistencia.borrarEntidad(eUsuario);
		
		//Si esta en el pool, lo eliminamos
		if (PoolDAO.getUnicaInstancia().contiene(user.getCodigo()))
			PoolDAO.getUnicaInstancia().removeObjeto(user.getCodigo());
		
	}

	@Override
	public void modificarUsuario(Usuario user) {
		//Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		
		//Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(user.getNombre());
			} else if (prop.getNombre().equals("apellidos")) {
				prop.setValor(user.getApellidos());
			} else if (prop.getNombre().equals("numTlf")) {
				prop.setValor(Integer.toString(user.getNumTlf()));
			} else if (prop.getNombre().equals("password")) {
				prop.setValor(user.getPassword());
			} else if (prop.getNombre().equals("fotoPerfil")) {
				prop.setValor(String.valueOf(user.getFotoPerfil().getDescription()));
			} else if (prop.getNombre().equals("estado")) {
				prop.setValor(user.getEstado());
			} else if (prop.getNombre().equals("fechaNacimiento")) {
				prop.setValor(user.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(user.getEmail());
			} else if (prop.getNombre().equals("isPremium")) {
				prop.setValor(String.valueOf(user.isPremium()));
			} else if (prop.getNombre().equals("contactos")) {
				prop.setValor(obtenerCodigosContactos(user.getContactos()));
			}
			
			//actualizamos la entidad
			servPersistencia.modificarEntidad(eUsuario);
		}
	}

	@Override
	public Usuario recuperarUsuario(int codigo) {
		//Si esta en el pool, devolvemos el objeto del pool
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		//Sino, recuperamos la entidad usuario de BD
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);
		
		//Recuperamos sus propiedades que no son objetos
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		int numTlf = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUsuario, "numTlf"));
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		ImageIcon fotoPerfil = new ImageIcon(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fotoPerfil"));
		String estado = servPersistencia.recuperarPropiedadEntidad(eUsuario, "estado");
		LocalDate fechaNacimiento = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		boolean isPremium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium"));
		
		//Creamos el usuario
		Usuario usuario = new Usuario(nombre, apellidos, numTlf, password, fotoPerfil, estado, fechaNacimiento, email);
		usuario.setPremium(isPremium);
		usuario.setCodigo(codigo);
		
		//Añadimos al pool antes de añadir los contactos
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);
		
		//Añadiomos las propiedades que son objetos
		List<Contacto> contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		usuario.setContactos(contactos);
		
		//Devolvemos el usuario
		return usuario;
	}
//___________________________________Fnciones Auxiliares________________________________________
	
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		return contactos.stream()
				.map(c -> String.valueOf(c.getCodigo()))
				.reduce("", (l, c) -> l + c + " ")
				.trim();
	}
	
	private List<Contacto> obtenerContactosDesdeCodigos(String codigos) {
		List<Contacto> contactos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorContactoIndividualTDS adaptadorCI = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorG = AdaptadorGrupoTDS.getInstancia();
		while (strTok.hasMoreTokens()) {
			int codigo = Integer.parseInt(strTok.nextToken());
			Contacto contacto = adaptadorCI.recuperarContactoIndividual(codigo);
			if (contacto == null) {
				contacto = adaptadorG.recuperarGrupo(codigo);
			}
			contactos.add(contacto);
		}		
		return contactos;
	}
	
	
}
