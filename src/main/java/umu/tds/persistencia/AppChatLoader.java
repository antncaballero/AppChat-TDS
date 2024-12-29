package umu.tds.persistencia;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;


/**
 * Clase para cargar datos de prueba en la BBDD de la aplicación si se desea   
 */
public class AppChatLoader {

	public static void main(String[] args) {
		
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstancia();
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstancia();
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getInstancia();
		
		Usuario u1 = new Usuario("Pepe", "López", 123456789, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba2.jpeg")));
		Usuario u2 = new Usuario("Antonio", "López", 111111111, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u3 = new Usuario("Jose", "López Rodríguez", 222222222, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u4 = new Usuario("Pepe", "López", 333333333, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u5 = new Usuario("Jesús", "López", 444444444, "pass", "prueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estado", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba1.jpeg")));
		Usuario u6 = new Usuario("Manuel", "López", 555555555, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u7 = new Usuario("Miguel", "Fernández", 666666666, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u8 = new Usuario("Miguel", "Fernández Giménez", 777777777, "pass", "ocupado", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u9 = new Usuario("Miguel", "López", 888888888, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u10 = new Usuario("Miguel", "Giménez", 999999999, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		
		LinkedList<Usuario> listaUsuarios = new LinkedList<Usuario>();
		listaUsuarios.add(u1);
		listaUsuarios.add(u2);
		listaUsuarios.add(u3);
		listaUsuarios.add(u4);
		listaUsuarios.add(u5);
		listaUsuarios.add(u6);
		listaUsuarios.add(u7);
		listaUsuarios.add(u8);
		listaUsuarios.add(u9);
		listaUsuarios.add(u10);
		
		listaUsuarios.forEach(usuario -> adaptadorUsuario.registrarUsuario(usuario));

		
	}

}
