package umu.tds.persistencia;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

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
		// TODO Auto-generated method stub
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
				new Propiedad("isPremium", String.valueOf(user.isPremium()))
				)));
		
		// registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		
		// asignar identificador unico al usuario, aprovechando el que genera el servicio de persistencia
		user.setCodigo(eUsuario.getId());
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
		
	}

	@Override
	public void modificarUsuario(Usuario user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario recuperarUsuario(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

}
