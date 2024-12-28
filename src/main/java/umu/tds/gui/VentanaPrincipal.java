package umu.tds.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import tds.BubbleText;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Descuento;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private ChatPanel chatPanel;
	private TextField fieldMensaje;
	private ControladorAppChat controlador = ControladorAppChat.getInstancia();
	private JScrollPane scrollPaneChat;
	private JList<Contacto> lista;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */


	public static class ContactListModel extends AbstractListModel<Contacto> {
	//Para probar la ventana, se crean usuarios y contactos
	
		private static Usuario[] users = new Usuario[] {
			    new Usuario("Pepe", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba2.jpeg"))),
				new Usuario("Antonio", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Jose", "López Rodríguez", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Pepe", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Jesús", "López", 638912458, "pass", "prueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estadoprueba de estado",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/fotoPrueba1.jpeg"))),
				new Usuario("Manuel", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass","ocupado",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com", Utils.convertImageToBase64(new File("src/main/resources/user.png"))),
		};
		
		private static ContactoIndividual c1 = new ContactoIndividual("Miguel clase", users[11]);
		private static ContactoIndividual c2 = new ContactoIndividual("Miguel2", users[12]);
		private static ContactoIndividual c3 = new ContactoIndividual("Miguel primo", users[10]);
			
		private static Contacto[] contactos = new Contacto[] { 
				new ContactoIndividual("Pepe", users[0]),
				new ContactoIndividual("Antonio", users[1]), new ContactoIndividual("Jose", users[2]),
				new ContactoIndividual("Pepiyo", users[3]), new ContactoIndividual("Jesús", users[4]),
				new ContactoIndividual("Manuel", users[5]), new ContactoIndividual("Miguel", users[6]),
				new ContactoIndividual("Miguelon", users[7]), new ContactoIndividual("Miguelito", users[8]),
				new ContactoIndividual("Miguelin", users[9]),c1,c2,c3,
				new Grupo("Grupo de prueba", Arrays.asList(c1,c2,c3)),
		};

		@Override
		public int getSize() {
			return contactos.length;
		}

		@Override
		public Contacto getElementAt(int index) {
			return contactos[index];
		}
		
		public static List<Contacto> getContactos() {
			   return Arrays.asList(contactos);
		}

	}
	
	public VentanaPrincipal() {
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		//BOTONES DE ZONA NORTE
		JButton botonPremium = new JButton(Utils.getIcon("src/main/resources/premium.png", 3.3f));
		JButton botonBuscar = new JButton(Utils.getIcon("src/main/resources/glass.png", 2.5f));
		JButton botonContactos = new JButton(Utils.getIcon("src/main/resources/contacts.png", 2.5f));
		JButton botonPerfil = new JButton(Utils.imageToImageIcon(controlador.getUsuarioActual().getFotoPerfil(), 40, 40));
		JButton botonAddGrupo = new JButton(Utils.getIcon("src/main/resources/group.png", 2.7f));
		JButton botonAnadirContacto = new JButton(Utils.getIcon("src/main/resources/user.png", 2.5f));
		JButton btnGenerarPdf = new JButton("+ PDF");
		
		//EVENTOS DE LOS BOTONES DE ZONA NORTE
		botonPremium.addActionListener(e -> {
			if (controlador.getUsuarioActual().isPremium()) {
				JOptionPane.showMessageDialog(this, "Ya eres usuario premium", "Premium",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				List<Descuento> descuentos = controlador.getDescuentosUsuarioActual();
				JDialogDescuentos dialogoDescuentos = new JDialogDescuentos(this, descuentos);
                dialogoDescuentos.setVisible(true);
                boolean exitoPago = dialogoDescuentos.isConfirmed();
                controlador.hacerPremium(exitoPago); //si paga con éxito se hace premium, si no no hace nada
			}
		});
		
		botonAddGrupo.addActionListener(e -> {
			VentanaGrupo ventanaGrupo = new VentanaGrupo();
			ventanaGrupo.setVisible(true);
			dispose();
		});
		
		botonAnadirContacto.addActionListener(e -> {
			VentanaAnadirContacto ventanaAnadirContacto = new VentanaAnadirContacto();
			ventanaAnadirContacto.setVisible(true);
			dispose();
			/*TODO: podriamos ver si chat seleccionado esta en la lista de contactos y añadirlo si no esta,
					habría que ver como diferenciar si se quiere añadir al contacto del chat seleccionado
			 		o si se quiere añadir un contacto nuevo que no tiene nada que ver con los chats que hay
			 */
		});
		
		botonContactos.addActionListener(e -> {
			VentanaContactos ventanaContactos = new VentanaContactos(this);
			ventanaContactos.setVisible(true);
			setVisible(false);
		});
		
		botonPerfil.addActionListener(e -> {
			VentanaPerfil ventanaPerfil = new VentanaPerfil();
			ventanaPerfil.setVisible(true);
			dispose();
		});
		
		btnGenerarPdf.addActionListener(e -> {
			
			if (lista.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(this, "Selecciona un chat para generar PDF", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			boolean success = controlador.generatePDF(lista.getSelectedValue());
			if (success) {
				JOptionPane.showMessageDialog(this, "PDF generado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Debes ser un usuario premium para generar PDFs", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		//ESTILOS DE LOS BOTONES DE ZONA NORTE
		botonPremium.setBackground(Color.WHITE);
		botonPremium.setBorder(BorderFactory.createEmptyBorder());
		botonBuscar.setBackground(Color.WHITE);
		botonPremium.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonContactos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonContactos.setBackground(Color.WHITE);
		botonContactos.setText("Contactos");
		botonContactos.setFont(new Font("Segoe UI", Font.BOLD, 15));
		botonContactos.setIconTextGap(8);
		botonPerfil.setBackground(Color.WHITE);
		botonPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonPerfil.setFont(new Font("Segoe UI", Font.BOLD, 15));
		botonPerfil.setIconTextGap(10);
		botonPerfil.setText(controlador.getUsuarioActual().getNombre());
		botonAddGrupo.setBackground(Color.WHITE);
		botonAddGrupo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonAddGrupo.setText("+");
		botonAnadirContacto.setText("+");
		botonAnadirContacto.setBackground(Color.WHITE);
		botonAnadirContacto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonAnadirContacto.setFont(new Font("Segoe UI", Font.BOLD, 25));
		botonAnadirContacto.setIconTextGap(5);
		botonAddGrupo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        botonAddGrupo.setIconTextGap(5);
        btnGenerarPdf.setBackground(Color.WHITE);
        btnGenerarPdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGenerarPdf.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
		
        //AÑADIR BOTONES A LA ZONA NORTE
        panelNorte.add(btnGenerarPdf);
        panelNorte.add(botonAnadirContacto);
        panelNorte.add(botonAddGrupo);
		panelNorte.add(botonBuscar);		
		panelNorte.add(botonContactos);
		panelNorte.add(botonPremium);
		panelNorte.add(botonPerfil);
		
		//PANEL ESTE(CHAT + ENVIAR MENSAJE)
		JPanel panelEste = new JPanel();
		panelEste.setLayout(new BorderLayout());
		panelEste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "mensajes con contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//PANEL DE CHATS
		chatPanel = new ChatPanel();
		scrollPaneChat = new JScrollPane(chatPanel);
		
	
		//boton y campo de envio de mensajes
		fieldMensaje = new TextField();
		JButton botonSend = new JButton(Utils.getIcon("src/main/resources/send.png", 2f));
		botonSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSend.setBackground(Color.WHITE);
		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.add(fieldMensaje, BorderLayout.CENTER);
		messagePanel.add(botonSend, BorderLayout.EAST);

        //añadir chatPanel y messagePanel al panelEste, y panelEste al contentPane
		panelEste.add(scrollPaneChat, BorderLayout.CENTER);
		panelEste.add(messagePanel, BorderLayout.SOUTH);
		contentPane.add(panelEste, BorderLayout.EAST);
		
		//LISTA DE CONTACTOS
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Mis chats", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(panelCentro, BorderLayout.CENTER);
		
		lista = new JList<>(new ContactListModel());
		lista.setCellRenderer(new ContactCellRenderer());
		lista.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Contacto selectedContact = lista.getSelectedValue();
				if (selectedContact != null) {
					String contactoSeleccionado = selectedContact.getNombre();
					panelEste.setBorder(new TitledBorder(null, "Mensajes con " + contactoSeleccionado, TitledBorder.LEADING, TitledBorder.TOP, null, null));
					loadChat(selectedContact);
				}
			}
		});
		
		

		JScrollPane scrollPane = new JScrollPane(lista);
		scrollPane.setSize(380,490); 
		scrollPane.setMinimumSize(new Dimension(380,490)); 
		scrollPane.setPreferredSize(new Dimension(380,490));
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCentro.add(scrollPane);
		
		botonBuscar.addActionListener(e -> {
			VentanaBusqueda ventanaBusqueda = new VentanaBusqueda();
			ventanaBusqueda.setVisible(true);
			dispose();
		});
		
		botonSend.addActionListener(e -> {
			String mensaje = fieldMensaje.getText();
			if (!mensaje.isEmpty()) {
				Contacto contacto = lista.getSelectedValue();
				if (contacto != null) {
					enviarMensaje(mensaje, contacto);
				}
			}
		});
	
	}

	public void loadChat(Contacto contacto) {
		// TODO
		System.out.println("Cargando chat con " + contacto.getNombre());
		lista.setSelectedValue(contacto, true);
	}
	
	public void enviarMensaje(String mensaje, Contacto contacto) {
		controlador.enviarMensaje(mensaje, contacto);
		chatPanel.enviarMensaje(mensaje);
		fieldMensaje.setText("");
		System.out.println("Mensaje enviado: " + mensaje + " || a " + contacto.getNombre());
		
		//Scroll automático al final del chat
		chatPanel.scrollRectToVisible(new Rectangle(0, chatPanel.getHeight(), 1, 1));
		
	}
}
