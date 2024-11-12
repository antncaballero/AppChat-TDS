package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
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
		
		//Creamos una entidad producto
		eUsuario = new Entidad();
		
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("nombre", user.getNombre()),
				new Propiedad("apellidos", user.getApellidos()),
				new Propiedad("numTlf", String.valueOf(user.getNumTlf())),
				new Propiedad("password", user.getPassword()),
				new Propiedad("estado", user.getEstado())
				//new Propiedad("fechaNacimiento", user.getFechaNacimiento()),
				
				
				
				
				
				
				)));
		
	}

	@Override
	public void borrarUsuario(Usuario user) {
		// TODO Auto-generated method stub

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
