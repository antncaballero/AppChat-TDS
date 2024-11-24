package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;

public class AdaptadorGrupoTDS implements GrupoDAO{

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
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		for (Propiedad p : eGrupo.getPropiedades()) {
            switch (p.getNombre()) {
            case "nombre":
                p.setValor(grupo.getNombre());
                break;
            case "participantes":
                p.setValor(obtenerCodigosContactosIndividual(grupo.getParticipantes()));
                break;
            case "listaMensajes":
                p.setValor(obtenerCodigosMensajes(grupo.getListaMensajes()));
                break;  
            }
            servPersistencia.modificarEntidad(eGrupo);
		}
}

	public Grupo recuperarGrupo(int codigo) {
		
		if (PoolDAO.INSTANCE.contains(codigo)) return (Grupo) PoolDAO.INSTANCE.getObject(codigo);
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(codigo);
	
		String nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre");

		Grupo grupo = new Grupo(nombre, new LinkedList<ContactoIndividual>());
		grupo.setCodigo(codigo);
		
		PoolDAO.INSTANCE.addObject(codigo, grupo);
		
		String participantes = servPersistencia.recuperarPropiedadEntidad(eGrupo, "participantes");
		obtenerIntegrantesDesdeCodigos(participantes).forEach(grupo::addParticipante);
		
		String mensajes = servPersistencia.recuperarPropiedadEntidad(eGrupo, "listaMensajes");
		obtenerMensajesDesdeCodigos(mensajes).forEach(grupo::addMensaje);
		
		return grupo;

	}
	
	//TODO esto esta en el adaptador individual, revisar si podriamos crear un adaptador abstracto con este metodo o si lo podriamos mover a mensajes o utils
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		return Arrays.stream(codigos.split(" "))
                .map(Integer::parseInt)
                .map(AdaptadorMensajeTDS.getUnicaInstancia()::recuperarMensaje)
                .collect(Collectors.toList());

	}
	
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
	
	private List<ContactoIndividual> obtenerIntegrantesDesdeCodigos(String codigos) {
		return Arrays.stream(codigos.split(" "))
				.map(Integer::parseInt)
				.map(AdaptadorContactoIndividualTDS.getInstancia()::recuperarContactoIndividual)
				.collect(Collectors.toList());
	}
}
