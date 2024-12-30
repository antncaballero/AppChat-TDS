package umu.tds.persistencia;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.ContactoIndividual;
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
		Usuario u11 = new Usuario("Pepe", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba2.jpeg")));
		Usuario u12 = new Usuario("Antonio", "López", 202202202, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		
		LinkedList<Usuario> listaUsuarios = new LinkedList<Usuario>();
		//listaUsuarios.add(u1);
		//listaUsuarios.add(u2);
		listaUsuarios.add(u3);
		//listaUsuarios.add(u4);
		//listaUsuarios.add(u5);
		//listaUsuarios.add(u6);
		//listaUsuarios.add(u7);
		//listaUsuarios.add(u8);
		//listaUsuarios.add(u9);
		//listaUsuarios.add(u10);
		//listaUsuarios.add(u11);
		//listaUsuarios.add(u12);
		
		ContactoIndividual c1 = new ContactoIndividual("u1", u1);
		ContactoIndividual c2 = new ContactoIndividual("u2", u2);
		ContactoIndividual c3 = new ContactoIndividual("u3", u3);
		ContactoIndividual c4 = new ContactoIndividual("u4", u4);
		ContactoIndividual c5 = new ContactoIndividual("u5", u5);
		ContactoIndividual c6 = new ContactoIndividual("u6", u6);
		ContactoIndividual c7 = new ContactoIndividual("u7", u7);
		ContactoIndividual c8 = new ContactoIndividual("u8", u8);
		ContactoIndividual c9 = new ContactoIndividual("u9", u9);
		ContactoIndividual c10 = new ContactoIndividual("u10", u10);
		ContactoIndividual c11 = new ContactoIndividual("638912458", u11);
		ContactoIndividual c12 = new ContactoIndividual("202202202", u12);
		
		
		LinkedList<ContactoIndividual> listaContactosIndividuales = new LinkedList<ContactoIndividual>();
		listaContactosIndividuales.add(c1);
		listaContactosIndividuales.add(c2);
		listaContactosIndividuales.add(c3);
		listaContactosIndividuales.add(c4);
		listaContactosIndividuales.add(c5);
		listaContactosIndividuales.add(c6);
		listaContactosIndividuales.add(c7);
		listaContactosIndividuales.add(c8);
		listaContactosIndividuales.add(c9);
		listaContactosIndividuales.add(c10);	
		listaContactosIndividuales.add(c11);
		listaContactosIndividuales.add(c12);
		
		//listaContactosIndividuales.forEach(contacto -> adaptadorContactoIndividual.registrarContactoIndividual(contacto));
		
		listaUsuarios.forEach(usuario -> adaptadorUsuario.registrarUsuario(usuario)); 

		
	}

}
