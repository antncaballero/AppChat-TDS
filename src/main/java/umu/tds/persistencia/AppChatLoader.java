package umu.tds.persistencia;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;

/**
 * Clase para cargar datos de prueba en la BBDD de la aplicación si se desea   
 */
public class AppChatLoader {

	public static void main(String[] args) throws InterruptedException {
		
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstancia();
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstancia();
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstancia();
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getInstancia();
		
		//Usuarios de prueba
		Usuario u1 = new Usuario("Pepe", "López", 123456789, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba2.jpeg")));
		Usuario u2 = new Usuario("Antonio", "López", 111111111, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba3.jpg")));
		Usuario u3 = new Usuario("Jose", "López Rodríguez", 222222222, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u4 = new Usuario("Pepe", "López", 333333333, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u5 = new Usuario("Juan", "López", 444444444, "pass", "prueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estado", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba1.jpeg")));
		Usuario u6 = new Usuario("Manuel", "López", 555555555, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u7 = new Usuario("Miguel", "Fernández", 666666666, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u8 = new Usuario("Miguel", "Fernández Giménez", 777777777, "pass", "ocupado", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u9 = new Usuario("Miguel", "López", 888888888, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		Usuario u10 = new Usuario("Miguel", "Giménez", 999999999, "pass", LocalDate.of(2004, 7, 5), "name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png")));
		
		Usuario[] usuarios = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};
		Arrays.stream(usuarios).forEach(adaptadorUsuario::registrarUsuario);
			
		//Contactos individuales de Pepe
		
		ContactoIndividual c1 = new ContactoIndividual("Antonio", u2);
		ContactoIndividual c2 = new ContactoIndividual("Jose", u3);
		ContactoIndividual c3 = new ContactoIndividual("Pepe Hijo", u4);
		//ContactoIndividual c4 = new ContactoIndividual("Juan", u5);
		ContactoIndividual c5 = new ContactoIndividual("Manuel", u6);
		ContactoIndividual c6 = new ContactoIndividual("Miguel", u7);
		ContactoIndividual c7 = new ContactoIndividual("Miguel Primo", u8);
		ContactoIndividual c8 = new ContactoIndividual("Miguel sobrino", u9);
		ContactoIndividual c9 = new ContactoIndividual("Miguelito", u10);
		
		adaptadorContactoIndividual.registrarContactoIndividual(c1);
		adaptadorContactoIndividual.registrarContactoIndividual(c2);
		adaptadorContactoIndividual.registrarContactoIndividual(c3);
		//adaptadorContactoIndividual.registrarContactoIndividual(c4);
		adaptadorContactoIndividual.registrarContactoIndividual(c5);
		adaptadorContactoIndividual.registrarContactoIndividual(c6);
		adaptadorContactoIndividual.registrarContactoIndividual(c7);
		adaptadorContactoIndividual.registrarContactoIndividual(c8);
		adaptadorContactoIndividual.registrarContactoIndividual(c9);
		
		u1.addContacto(c1);
		u1.addContacto(c2);
		u1.addContacto(c3);
		//u1.addContacto(c4);
		u1.addContacto(c5);
		u1.addContacto(c6);
		u1.addContacto(c7);
		u1.addContacto(c8);
		u1.addContacto(c9);
		adaptadorUsuario.modificarUsuario(u1);
		
		
		//Contactos individuales de Antonio
		
		ContactoIndividual c10 = new ContactoIndividual("Pepe", u1);
		adaptadorContactoIndividual.registrarContactoIndividual(c10);
		u2.addContacto(c10);
		adaptadorUsuario.modificarUsuario(u2);
		
		//Juan agrega a Pepe y le manda un mensaje
		ContactoIndividual c11 = new ContactoIndividual("Pepe", u1);
		adaptadorContactoIndividual.registrarContactoIndividual(c11);
		u5.addContacto(c11);
		adaptadorUsuario.modificarUsuario(u5);
		
		Mensaje m7 = new Mensaje("Soy Juan", LocalDateTime.of(2024, 10, 8, 8, 8, 8), 0, u5, c11);
		Mensaje m8 = new Mensaje("Agrégame, tengo nuevo tlf!", LocalDateTime.of(2024, 10, 8, 8, 8, 8), 0, u5, c11);
		adaptadorMensaje.registrarMensaje(m7);
		adaptadorMensaje.registrarMensaje(m8);
		u5.enviarMensaje(m7, c11);
		u5.enviarMensaje(m8, c11);
		adaptadorContactoIndividual.modificarContactoIndividual(c11);
		
		
		//Grupo de familia de Pepe
		
		LinkedList<ContactoIndividual> participantes = new LinkedList<>();
		participantes.add(c6);
		participantes.add(c7);
		participantes.add(c8);
		participantes.add(c9);
			
		Grupo g1 = new Grupo("Familia", participantes, Utils.convertImageToBase64(new File("src/main/resources/group.png")), "Grupo de familia");
		adaptadorGrupo.registrarGrupo(g1);
		
		u1.addContacto(g1);
		adaptadorUsuario.modificarUsuario(u1);
		
		
		//Mensajes de prueba
		
		Mensaje m1 = new Mensaje("Hola", LocalDateTime.of(2024, 10, 8, 8, 8, 8), 0, u1, c1);
		Mensaje m2 = new Mensaje("Holaa", LocalDateTime.of(2024, 10, 9, 9, 9, 9),0, u1, c1);
		Mensaje m3 = new Mensaje("Holaaa",LocalDateTime.of(2024, 10, 10, 10, 10, 10), 0, u2, c10);
		Mensaje m4 = new Mensaje("Que tal", LocalDateTime.of(2024, 10, 10, 10, 10, 11),0, u2, c10);
		Mensaje m5 = new Mensaje("Bien", LocalDateTime.of(2024, 12, 10, 10, 10, 11), 0, u1, c1);
		Mensaje m6 = new Mensaje(1, u1, c1);
		
		adaptadorMensaje.registrarMensaje(m1);
		adaptadorMensaje.registrarMensaje(m2);
		adaptadorMensaje.registrarMensaje(m3);
		adaptadorMensaje.registrarMensaje(m4);
		adaptadorMensaje.registrarMensaje(m5);
		adaptadorMensaje.registrarMensaje(m6);
				
		u1.enviarMensaje(m1, c1);
		u1.enviarMensaje(m2, c1);
		u2.enviarMensaje(m3, c10);
		u2.enviarMensaje(m4, c10);
		u1.enviarMensaje(m5, c1);
		u2.enviarMensaje(m6, c10);
		
		adaptadorContactoIndividual.modificarContactoIndividual(c1);
		adaptadorContactoIndividual.modificarContactoIndividual(c10);
		
		
		
	}

}
