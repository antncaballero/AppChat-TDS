package umu.tds.persistencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	private static final String PROPIEDAD_NOMBRE = "nombre";
	private static final String PROPIEDAD_APELLIDOS = "apellidos";
	private static final String PROPIEDAD_NUM_TLF = "numTlf";
	private static final String PROPIEDAD_PASSWORD = "password";
	private static final String PROPIEDAD_FOTO_PERFIL_CODIFICADA = "fotoPerfilCodificada";
	private static final String PROPIEDAD_ESTADO = "estado";
	private static final String PROPIEDAD_FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String PROPIEDAD_EMAIL = "email";
	private static final String PROPIEDAD_IS_PREMIUM = "isPremium";
	private static final String PROPIEDAD_CONTACTOS = "contactos";
	private static final String PROPIEDAD_FECHA_REGISTRO = "fechaRegistro";
	
	
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
		//Comprobamos si ya esta registrado
		Optional<Entidad> eUsuario = Optional.ofNullable(servPersistencia.recuperarEntidad(user.getCodigo()));
		if (eUsuario.isPresent()) return;
		
		//Registramos atributos que son objetos
		for (Contacto contacto: user.getContactos()) {
			if(contacto instanceof ContactoIndividual) {
				AdaptadorContactoIndividualTDS.getInstancia().registrarContactoIndividual((ContactoIndividual) contacto);
			}else if (contacto instanceof Grupo) {
				AdaptadorGrupoTDS.getInstancia().registrarGrupo((Grupo) contacto);
			}
		}
		
		//Creamos una entidad usuario
		eUsuario = Optional.of(new Entidad());
        //Asignamos tipo
		eUsuario.get().setNombre("usuario");
		//Registramos atributos
		eUsuario.get().setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(PROPIEDAD_NOMBRE, user.getNombre()),
				new Propiedad(PROPIEDAD_APELLIDOS, user.getApellidos()),
				new Propiedad(PROPIEDAD_NUM_TLF, Integer.toString(user.getNumTlf())),
				new Propiedad(PROPIEDAD_PASSWORD, user.getPassword()),
				new Propiedad(PROPIEDAD_FOTO_PERFIL_CODIFICADA, user.getFotoPerfilCodificada()),
				new Propiedad(PROPIEDAD_ESTADO, user.getEstado()),
				new Propiedad(PROPIEDAD_FECHA_NACIMIENTO, user.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
				new Propiedad(PROPIEDAD_EMAIL, user.getEmail()),
				new Propiedad(PROPIEDAD_IS_PREMIUM, String.valueOf(user.isPremium())),
				new Propiedad(PROPIEDAD_CONTACTOS, obtenerCodigosContactos(user.getContactos())),
				new Propiedad(PROPIEDAD_FECHA_REGISTRO, user.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) 
			    )));
		
		// registrar entidad cliente
		eUsuario = Optional.ofNullable(servPersistencia.registrarEntidad(eUsuario.get()));
		// asignar identificador unico al usuario, aprovechando el que genera el servicio de persistencia
		user.setCodigo(eUsuario.get().getId());
		
		//Añadimos al pool
		PoolDAO.INSTANCE.addObject(user.getCodigo(), user);
	}
	

	@Override
	public void borrarUsuario(Usuario user) {
		//Se recupera entidad usuario
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		
		//Eliminamos sus entidades agregadas
		for (Contacto contacto : user.getContactos()) {
			if (contacto instanceof ContactoIndividual) {
				AdaptadorContactoIndividualTDS.getInstancia().borrarContactoIndividual((ContactoIndividual) contacto);
			} else{
				AdaptadorGrupoTDS.getInstancia().borrarGrupo((Grupo) contacto);
			}
		}
		
		//Eliminamos la entidad usuario
		servPersistencia.borrarEntidad(eUsuario);
		
		//Si esta en el pool, lo eliminamos
		if (PoolDAO.INSTANCE.contains(user.getCodigo()))
			PoolDAO.INSTANCE.removeObject(user.getCodigo());
		
	}

	@Override
	public void modificarUsuario(Usuario user) {
		//Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getCodigo());
		
		//Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(PROPIEDAD_NOMBRE)) {
				prop.setValor(user.getNombre());
			} else if (prop.getNombre().equals(PROPIEDAD_APELLIDOS)) {
				prop.setValor(user.getApellidos());
			} else if (prop.getNombre().equals(PROPIEDAD_NUM_TLF)) {
				prop.setValor(Integer.toString(user.getNumTlf()));
			} else if (prop.getNombre().equals(PROPIEDAD_PASSWORD)) {
				prop.setValor(user.getPassword());
			} else if (prop.getNombre().equals(PROPIEDAD_FOTO_PERFIL_CODIFICADA)) {
				prop.setValor(String.valueOf(user.getFotoPerfilCodificada()));
			} else if (prop.getNombre().equals(PROPIEDAD_ESTADO)) {
				prop.setValor(user.getEstado());
			} else if (prop.getNombre().equals(PROPIEDAD_FECHA_NACIMIENTO)) {
				prop.setValor(user.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			} else if (prop.getNombre().equals(PROPIEDAD_EMAIL)) {
				prop.setValor(user.getEmail());
			} else if (prop.getNombre().equals(PROPIEDAD_IS_PREMIUM)) {
				prop.setValor(String.valueOf(user.isPremium()));
			} else if (prop.getNombre().equals(PROPIEDAD_CONTACTOS)) {
				prop.setValor(obtenerCodigosContactos(user.getContactos()));
			} else if (prop.getNombre().equals(PROPIEDAD_FECHA_REGISTRO)) {
				prop.setValor(user.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			}
			
			//actualizamos la entidad
			servPersistencia.modificarEntidad(eUsuario);
		}
	}

	@Override
	public Usuario recuperarUsuario(int codigo) {
		//Si esta en el pool, devolvemos el objeto del pool
		if (PoolDAO.INSTANCE.contains(codigo))
			return (Usuario) PoolDAO.INSTANCE.getObject(codigo);
		
		//Sino, recuperamos la entidad usuario de BD
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);
		
		//Recuperamos sus propiedades que no son objetos
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario,PROPIEDAD_NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_APELLIDOS);
		int numTlf = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_NUM_TLF));
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_PASSWORD);
		String fotoPerfilCodificada = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_FOTO_PERFIL_CODIFICADA);
		String estado = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_ESTADO);
		LocalDate fechaNacimiento = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_FECHA_NACIMIENTO), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_EMAIL);
		boolean isPremium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_IS_PREMIUM));
		LocalDate fechaRegistro = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_FECHA_REGISTRO), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		//Creamos el usuario
		Usuario usuario = new Usuario(nombre, apellidos, numTlf, password, estado, fechaNacimiento, email, fotoPerfilCodificada, fechaRegistro);
		usuario.setPremium(isPremium);
		usuario.setCodigo(codigo);
		
		//Añadimos al pool antes de añadir los contactos
		PoolDAO.INSTANCE.addObject(codigo, usuario);
		
		//Añadimos las propiedades que son objetos
		List<Contacto> contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_CONTACTOS));
		usuario.setContactos(contactos);
		
		//Devolvemos el usuario
		return usuario;
	}
	
	@Override
	public List<Usuario> recuperarTodosLosUsuarios() {
		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		eUsuarios.forEach(e -> usuarios.add(recuperarUsuario(e.getId())));
		return usuarios;
	}
//Funciones auxiliares
	
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		return contactos.stream()
				.map(c -> String.valueOf(c.getCodigo()))
				.reduce("", (l, c) -> l + c + " ")
				.trim();
	}
	
	private List<Contacto> obtenerContactosDesdeCodigos(String codigos) {
		//TODO: Revisar si se puede hacer con un solo stream
		
		return Arrays.stream(codigos.split(" "))
				.filter(codigo -> !codigo.isEmpty())  //filtro cadenas vacias
                .map(Integer::parseInt)
                .map(c -> {
                    Optional<Contacto> contactoIndividual = Optional.ofNullable(AdaptadorContactoIndividualTDS.getInstancia().recuperarContactoIndividual(c));
                    if (contactoIndividual.isPresent()) return contactoIndividual.get();
                    return AdaptadorGrupoTDS.getInstancia().recuperarGrupo(c);
                })
                .collect(Collectors.toList());
		/*List<Contacto> listaContactoInduvidual = Arrays.stream(codigos.split(" "))
			.map(Integer::parseInt)
			.filter(c -> AdaptadorContactoIndividualTDS.getInstancia().recuperarContactoIndividual(c) != null)
			.map(AdaptadorContactoIndividualTDS.getInstancia()::recuperarContactoIndividual)
			.collect(Collectors.toList());
		List<Contacto> listaGrupo = Arrays.stream(codigos.split(" "))
			.map(Integer::parseInt)
			.filter(c -> AdaptadorGrupoTDS.getInstancia().recuperarGrupo(c) != null)
			.map(AdaptadorGrupoTDS.getInstancia()::recuperarGrupo)
			.collect(Collectors.toList());
		return Stream.concat(listaContactoInduvidual.stream(), listaGrupo.stream()).collect(Collectors.toList());*/
	}

	
	
	
}
