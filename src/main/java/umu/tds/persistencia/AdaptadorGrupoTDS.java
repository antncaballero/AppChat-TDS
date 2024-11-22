package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;

public class AdaptadorGrupoTDS {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupoTDS unicaInstancia = null;

	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public static AdaptadorGrupoTDS getInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorGrupoTDS();
		return unicaInstancia;
	}

	public void registrarGrupo(Grupo grupo) {
		
		Optional<Entidad> eGrupo = Optional.ofNullable(servPersistencia.recuperarEntidad(grupo.getCodigo()));
		if (eGrupo.isPresent()) return;
		
        grupo.getParticipantes().forEach(AdaptadorContactoIndividualTDS.getInstancia()::registrarContactoIndividual);
        grupo.getListaMensajes().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::registrarMensaje);
		
        eGrupo = Optional.of(new Entidad());
        eGrupo.get().setNombre("grupo");
		eGrupo.get().setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", grupo.getNombre()),
						new Propiedad("participantes", obtenerCodigosContactosIndividual(grupo.getParticipantes())),
						new Propiedad("listaMensajes", obtenerCodigosMensajes(grupo.getListaMensajes())))
        ));
		
		eGrupo = Optional.ofNullable(servPersistencia.registrarEntidad(eGrupo.get()));
		grupo.setCodigo(eGrupo.get().getId());
		
	}

	public void borrarGrupo(Grupo grupo) {

		Entidad eGrupo;
		grupo.getListaMensajes().forEach(AdaptadorMensajeTDS.getUnicaInstancia()::borrarMensaje);
		grupo.getParticipantes().forEach(AdaptadorContactoIndividualTDS.getInstancia()::borrarContactoIndividual);

		eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		servPersistencia.borrarEntidad(eGrupo);
		
		if (PoolDAO.INSTANCE.contains(grupo.getCodigo()))
			PoolDAO.INSTANCE.removeObject(grupo.getCodigo());
	}

	public void modificarGrupo(Grupo grupo) {
		// TODO Auto-generated method stub
	}

	public Grupo recuperarGrupo(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO esto esta en el adaptador individual, revisar si podriamos crear un adaptador abstracto con este metodo o si lo podriamos mover a mensajes
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
	
	private String obtenerCodigosContactosIndividual(List<ContactoIndividual> contactosIndividuales) {
		return contactosIndividuales.stream()
				.map(c -> String.valueOf(c.getCodigo()))
				.reduce("", (l, c) -> l + c + " ")
				.trim();
	}
}
