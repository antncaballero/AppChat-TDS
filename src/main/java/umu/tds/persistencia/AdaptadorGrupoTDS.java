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

/**
 * Clase que implementa el Adaptador de Grupo para el tipo de persistencia TDS.
 */
public class AdaptadorGrupoTDS implements GrupoDAO{
	
	private static final String PROPIEDAD_NOMBRE = "nombre";
	private static final String PROPIEDAD_PARTICIPANTES = "participantes";
	private static final String PROPIEDAD_MENSAJES_RECIBIDOS = "listaMensajes";
	private static final String PROPIEDAD_FOTO_GRUPO = "fotoGrupo";
	private static final String PROPIEDAD_ESTADO= "estado";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupoTDS unicaInstancia = null;
	/**
	 * Constructor de la clase.
	 */
	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	/**
	 * Obtiene la instancia única de la clase.
	 * @return la instancia única de la clase.
	 */
	public static AdaptadorGrupoTDS getInstancia() {
		if (unicaInstancia == null) unicaInstancia = new AdaptadorGrupoTDS();
		return unicaInstancia;
	}
	/**
	 * Registra un grupo en la base de datos.
	 * @param grupo el grupo a registrar.
	 */
	public void registrarGrupo(Grupo grupo) {
		
		Optional<Entidad> eGrupo = Optional.ofNullable(servPersistencia.recuperarEntidad(grupo.getCodigo()));
		if (eGrupo.isPresent()) return;
		
        grupo.getParticipantes().forEach(AdaptadorContactoIndividualTDS.getInstancia()::registrarContactoIndividual);
        grupo.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getInstancia()::registrarMensaje);
		
        eGrupo = Optional.of(new Entidad());
        eGrupo.get().setNombre("grupo");
        eGrupo.get().setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
        		new Propiedad(PROPIEDAD_NOMBRE, grupo.getNombre()),
        		new Propiedad(PROPIEDAD_PARTICIPANTES, obtenerCodigosContactosIndividual(grupo.getParticipantes())),
        		new Propiedad(PROPIEDAD_FOTO_GRUPO, grupo.getFotoGrupoCodificada()),
        		new Propiedad(PROPIEDAD_ESTADO, grupo.getEstado()),
        		new Propiedad(PROPIEDAD_MENSAJES_RECIBIDOS, obtenerCodigosMensajes(grupo.getMensajesRecibidos())))  		
        		));
		
		eGrupo = Optional.ofNullable(servPersistencia.registrarEntidad(eGrupo.get()));
		grupo.setCodigo(eGrupo.get().getId());
		
	}
	/**
	 * Borra un grupo de la base de datos.
	 * @param grupo el grupo a borrar.
	 */
	public void borrarGrupo(Grupo grupo) {
		grupo.getMensajesRecibidos().forEach(AdaptadorMensajeTDS.getInstancia()::borrarMensaje);
		grupo.getParticipantes().forEach(AdaptadorContactoIndividualTDS.getInstancia()::borrarContactoIndividual);
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		servPersistencia.borrarEntidad(eGrupo);
		if (PoolDAO.INSTANCE.contains(grupo.getCodigo())) PoolDAO.INSTANCE.removeObject(grupo.getCodigo());
	}
	/**
	 * Modifica un grupo en la base de datos.
	 * @param grupo el grupo a modificar.
	 */
	public void modificarGrupo(Grupo grupo) {	
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		for (Propiedad p : eGrupo.getPropiedades()) {
			if (p.getNombre().equals(PROPIEDAD_NOMBRE)) {
				p.setValor(grupo.getNombre());
			}else if (p.getNombre().equals(PROPIEDAD_PARTICIPANTES)) {
				p.setValor(obtenerCodigosContactosIndividual(grupo.getParticipantes()));
			}else if (p.getNombre().equals(PROPIEDAD_MENSAJES_RECIBIDOS)) {
				p.setValor(obtenerCodigosMensajes(grupo.getMensajesRecibidos()));
			} else if (p.getNombre().equals(PROPIEDAD_FOTO_GRUPO)) {
				p.setValor(grupo.getFotoGrupoCodificada());
			} else if (p.getNombre().equals(PROPIEDAD_ESTADO)) {
				p.setValor(grupo.getEstado());
			}
			servPersistencia.modificarPropiedad(p);
		}
	}
	/**
	 * Recupera un grupo de la base de datos.
	 * @param codigo el código del grupo a recuperar.
	 * @return el grupo recuperado.
	 */
	public Grupo recuperarGrupo(int codigo) {
		
		if (PoolDAO.INSTANCE.contains(codigo)) return (Grupo) PoolDAO.INSTANCE.getObject(codigo);
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(codigo);
	
		String nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_NOMBRE);
		String fotoGrupo = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_FOTO_GRUPO);
		String estado = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_ESTADO);

		Grupo grupo = new Grupo(nombre, new LinkedList<ContactoIndividual>(), fotoGrupo, estado);
		grupo.setCodigo(codigo);
		
		PoolDAO.INSTANCE.addObject(codigo, grupo);
		
		String participantes = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_PARTICIPANTES);
		obtenerIntegrantesDesdeCodigos(participantes).forEach(grupo::addParticipante);
		
		String mensajes = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_MENSAJES_RECIBIDOS);
		obtenerMensajesDesdeCodigos(mensajes).forEach(grupo::addMensaje);
		
		return grupo;

	}
	/**
	 * Recupera todos los grupos de la base de datos.
	 * @return una lista con todos los grupos recuperados.
	 */
	public List<Grupo> recuperarTodosLosGrupos() {
        List<Grupo> grupos = new LinkedList<Grupo>();
        List<Entidad> entidades = servPersistencia.recuperarEntidades("grupo");
        entidades.forEach(eGrupo -> grupos.add(recuperarGrupo(eGrupo.getId())));
        return grupos;
	}
	/**
	 * Devuelve la lista de mensajes a partir de sus códigos.
	 * @param códigos
	 * @return lista de mensajes
	 */
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		return Arrays.stream(codigos.split(" "))
				.filter(c -> !c.isEmpty())
                .map(Integer::parseInt)
                .map(AdaptadorMensajeTDS.getInstancia()::recuperarMensaje)
                .collect(Collectors.toList());

	}
	/**
	 * Devuelve los códigos de los mensajes.
	 * @param mensajesRecibidos
	 * @return códigos de los mensajes
	 */
	private String obtenerCodigosMensajes(List<Mensaje> mensajesRecibidos) {
		return mensajesRecibidos.stream()
				.map(m -> String.valueOf(m.getCodigo()))
				.reduce("", (l, m) -> l + m + " ")
				.trim();
	}
	/**
	 * Devuelve los códigos de los contactos individuales
	 * @param lista de contactosIndividuales
	 * @return códigos de los contactos individuales
	 */
	private String obtenerCodigosContactosIndividual(List<ContactoIndividual> contactosIndividuales) {
		return contactosIndividuales.stream()
				.map(c -> String.valueOf(c.getCodigo()))
				.reduce("", (l, c) -> l + c + " ")
				.trim();
	}
	/**
	 * Devuelve la lista de integrantes a partir de sus códigos.
	 * @param códigos
	 * @return lista de contactos individuales
	 */
	private List<ContactoIndividual> obtenerIntegrantesDesdeCodigos(String codigos) {
		return Arrays.stream(codigos.split(" "))
				.filter(c -> !c.isEmpty())
				.map(Integer::parseInt)
				.map(AdaptadorContactoIndividualTDS.getInstancia()::recuperarContactoIndividual)
				.collect(Collectors.toList());
	}
}
