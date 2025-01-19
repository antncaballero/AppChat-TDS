package umu.tds.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TextField;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import tds.BubbleText;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Descuento;
import umu.tds.dominio.Grupo;
import umu.tds.utils.Utils;

@SuppressWarnings("serial")
/**
 * Ventana principal de la aplicación
 */
public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private ChatPanel chatPanel;
	private TextField fieldMensaje;
	private ControladorAppChat controlador = ControladorAppChat.getInstancia();
	private JScrollPane scrollPaneChat;
	private JList<Contacto> lista;
	private String tlfContacto;


	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		setTitle("AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 630);
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
		JButton botonLogout = new JButton(Utils.getIcon("src/main/resources/logout.png", 2.5f));
		JButton btnGenerarPdf = new JButton("+ PDF");

		//EVENTOS DE LOS BOTONES DE ZONA NORTE
		botonPremium.addActionListener(e -> {
			if (controlador.getUsuarioActual().isPremium()) {
				int response = JOptionPane.showConfirmDialog(this, "¿Quieres dejar de ser usuario premium?", "Premium", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					controlador.setPremiumUsuarioActual(false);
					JOptionPane.showMessageDialog(this, "Has dejado de ser usuario premium", "Premium", JOptionPane.INFORMATION_MESSAGE);
				}				
			} else {
				List<Descuento> descuentos = controlador.getUsuarioActual().getDescuentosAplicables();
				JDialogDescuentos dialogoDescuentos = new JDialogDescuentos(this, descuentos);
				dialogoDescuentos.setVisible(true);
				boolean exitoPago = dialogoDescuentos.isConfirmed();
				controlador.setPremiumUsuarioActual(exitoPago);  //si paga con éxito se hace premium, si no no hace nada
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
			//SI NO HAY NINGÚN CHAT SELECCIONADO O NO ERES PREMIUM NO SE PUEDE
			if (!validarBotonGenerarPdf()) return;

			JFileChooser directoryChooser = new JFileChooser();
			directoryChooser.setDialogTitle("Seleccionar directorio para guardar el archivo PDF");
			directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo directorios
			int userSelection = directoryChooser.showSaveDialog(null);

			if (userSelection == JFileChooser.APPROVE_OPTION) {	            
				File directorio = directoryChooser.getSelectedFile();	        	
				if (controlador.generatePDF(lista.getSelectedValue(), directorio)) {
					JOptionPane.showMessageDialog(this, "PDF generado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Error al generar PDF", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}	   
		});

		botonLogout.addActionListener(e -> {
			controlador.cerrarSesion();
			VentanaLogin ventanaLogin = new VentanaLogin();
			ventanaLogin.setVisible(true);
			dispose();
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
		botonLogout.setBackground(Color.WHITE);
		botonLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		//AÑADIR BOTONES A LA ZONA NORTE
		panelNorte.add(btnGenerarPdf);
		panelNorte.add(botonAnadirContacto);
		panelNorte.add(botonAddGrupo);
		panelNorte.add(botonBuscar);		
		panelNorte.add(botonContactos);
		panelNorte.add(botonPremium);
		panelNorte.add(botonPerfil);
		panelNorte.add(botonLogout);

		//PANEL ESTE(CHAT + ENVIAR MENSAJE)
		JPanel panelEste = new JPanel();
		panelEste.setLayout(new BorderLayout());
		panelEste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Mensajes con contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		//PANEL DE CHATS
		chatPanel = new ChatPanel();
		scrollPaneChat = new JScrollPane(chatPanel);


		//boton y campo de envio de mensajes
		fieldMensaje = new TextField();
		fieldMensaje.addActionListener(e -> accionSend());
		JButton botonSend = new JButton(Utils.getIcon("src/main/resources/send.png", 2f));
		botonSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSend.setBackground(Color.WHITE);
		JButton botonShowEmojis = new JButton(Utils.getIcon("src/main/resources/emoji.png", 2f));
		botonShowEmojis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonShowEmojis.setBackground(Color.WHITE);
		JPanel messagePanel = new JPanel(new BorderLayout());		
		messagePanel.add(botonShowEmojis, BorderLayout.WEST);
		messagePanel.add(fieldMensaje, BorderLayout.CENTER);
		messagePanel.add(botonSend, BorderLayout.EAST);

		JPanel anadirContacto = new JPanel(new FlowLayout());
		JButton btnAnadirContacto = new JButton(Utils.getIcon("src/main/resources/person-add.png", 2.0f));
		anadirContacto.add(btnAnadirContacto);
		anadirContacto.setVisible(false);

		//Panel scroll de emoticonos
		JPanel panelEmojis = new JPanel();
		panelEmojis.setLayout(new BoxLayout(panelEmojis, BoxLayout.X_AXIS));

		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			JButton botonEmoji = new JButton(BubbleText.getEmoji(i));
			botonEmoji.setName(Integer.toString(i));
			botonEmoji.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			botonEmoji.setBackground(panelEmojis.getBackground());
			botonEmoji.setBorder(BorderFactory.createEmptyBorder());
			botonEmoji.addActionListener(e -> enviarEmoticono(Integer.parseInt(botonEmoji.getName()), lista.getSelectedValue()));
			panelEmojis.add(botonEmoji);
			panelEmojis.add(Box.createRigidArea(new Dimension(25, 0)));			
		}		
		JScrollPane scrollPaneEmojis = new JScrollPane();
		scrollPaneEmojis.setViewportView(panelEmojis);
		scrollPaneEmojis.setPreferredSize(new Dimension(500, 70));

		scrollPaneEmojis.setVisible(false);
		messagePanel.add(scrollPaneEmojis, BorderLayout.NORTH);

		botonShowEmojis.addActionListener(e -> {
			scrollPaneEmojis.setVisible(!scrollPaneEmojis.isVisible());
			messagePanel.revalidate();
			messagePanel.repaint();
		});

		messagePanel.setMaximumSize(new Dimension(500, 200));

		//añadir chatPanel y messagePanel al panelEste y boton añadirContacto, y panelEste al contentPane
		panelEste.add(scrollPaneChat, BorderLayout.CENTER);
		panelEste.add(messagePanel, BorderLayout.SOUTH);
		panelEste.add(anadirContacto, BorderLayout.NORTH);
		contentPane.add(panelEste, BorderLayout.EAST);

		//LISTA DE CONTACTOS
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Mis chats", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(panelCentro, BorderLayout.CENTER);


		AbstractListModel<Contacto> model = new AbstractListModel<Contacto>() {
			@Override
			public int getSize() {
				return controlador.getUsuarioActual().getContactosOrdenados().size();
			}

			@Override
			public Contacto getElementAt(int index) {
				return controlador.getUsuarioActual().getContactosOrdenados().get(index);
			}
		};

		lista = new JList<>(model);
		lista.setCellRenderer(new ContactCellRenderer());
		lista.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Contacto selectedContact = lista.getSelectedValue();
				if (selectedContact != null) {
					String tituloBorde;
					//Se decide el título del borde del panel de los chats, según si es un contacto ind. (su nombre) o un grupo (nombres de participantes)
					if (selectedContact instanceof ContactoIndividual) {
						tituloBorde = "Mensajes con " + selectedContact.getNombre();
					} else {
						Grupo grupo = (Grupo) selectedContact;
						String nombresParticipantes = grupo.getParticipantes().stream()
								.map(Contacto::getNombre)
								.collect(Collectors.joining(", "));
						tituloBorde = "Mensajes con " + nombresParticipantes;
					}
					panelEste.setBorder(new TitledBorder(null, tituloBorde, TitledBorder.LEADING, TitledBorder.TOP, null, null));
					//Si es un contacto ficticio, muestra el botón de añadir contactos ficticios
					boolean esContactoFicticio = controlador.isContactoFicticio(selectedContact);	
					anadirContacto.setVisible(esContactoFicticio);
					//Se prepara el teléfono del contacto fictio (si lo es) para que al intentar añadirlo se muestre sin que lo tenga que escribir el usuario
					tlfContacto = esContactoFicticio ? selectedContact.getNombre() : null;
					//Se carga el chat seleccionado, independientemente del tipo de contacto
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
		panelCentro.setMaximumSize(new Dimension(380, 490));

		botonBuscar.addActionListener(e -> {
			VentanaBusqueda ventanaBusqueda = new VentanaBusqueda();
			ventanaBusqueda.setVisible(true);
			dispose();
		});

		botonSend.addActionListener(e -> {
			accionSend();
		});

		btnAnadirContacto.addActionListener(e -> {
			VentanaAnadirContacto ventanaAnadir = new VentanaAnadirContacto(tlfContacto);
			ventanaAnadir.setVisible(true);
			dispose();
		});

	}
	/**
	 * Acción que se realiza al pulsar el botón de enviar mensaje
	 */
	private void accionSend() {
		String mensaje = fieldMensaje.getText();
		if (!mensaje.isEmpty()) {
			Contacto contacto = lista.getSelectedValue();
			enviarMensaje(mensaje, contacto);				
		}
	}
	/**
	 * Método que valida si se puede generar el pdf
	 * @return true si se puede generar, false en caso contrario
	 */
	private boolean validarBotonGenerarPdf() {
		if (lista.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "Selecciona un chat para generar PDF", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (!controlador.getUsuarioActual().isPremium()) {
			JOptionPane.showMessageDialog(this, "Debes ser usuario premium para generar PDF", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	/**
	 * Método que carga el chat con un contacto
	 * @param contacto
	 */
	public void loadChat(Contacto contacto) {
		lista.setSelectedValue(contacto, true);
		chatPanel.mostrarChat(contacto);
		SwingUtilities.invokeLater(() -> fieldMensaje.requestFocusInWindow());
	}
	/**
	 * Método que envía un mensaje a un contacto
	 * @param mensaje
	 * @param contacto
	 */
	public void enviarMensaje(String mensaje, Contacto contacto) {		
		if (contacto != null) {
			controlador.enviarMensaje(mensaje, contacto);
			chatPanel.enviarMensaje(mensaje);
			fieldMensaje.setText("");
			//Scroll automático al final del chat
			chatPanel.scrollRectToVisible(new Rectangle(0, chatPanel.getHeight(), 1, 1));
		}

	}
	/**
	 * Método que envía un emoticono a un contacto
	 * @param emoticono
	 * @param contacto
	 */
	public void enviarEmoticono(int emoticono, Contacto contacto) {
		if (contacto != null) {
			controlador.enviarEmoticono(emoticono, contacto);
			chatPanel.enviarEmoticono(emoticono);
			chatPanel.scrollRectToVisible(new Rectangle(0, chatPanel.getHeight(), 1, 1));
		}
	}
}
