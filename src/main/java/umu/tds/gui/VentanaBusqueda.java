package umu.tds.gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import tds.BubbleText;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
/**
 * Ventana que permite buscar mensajes
 */
public class VentanaBusqueda extends JFrame {

	private final static String ERROR_VACIO = "Debe introducir al menos un filtro de búsqueda";
	private final static String ERROR_FORMATO_TLF = "El teléfono debe ser un número";
	private final static String ERROR_TLF_CONTACTO = "No existe ningún contacto con ese teléfono";
	private final static String ERROR_NOMBRE_CONTACTO = "No existe ningún contacto con ese nombre";
	
	private ControladorAppChat controlador = ControladorAppChat.getInstancia();
	private JPanel contentPane;
	private JTextField textFieldTexto;
	private JTextField textFieldTlf;
	private JTextField textFieldContacto;
	private DefaultListModel<Mensaje> mensajesBuscados;
	private JList<Mensaje> listaMensajes;

	/**
	 * Create the frame.
	 */
	public VentanaBusqueda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		setTitle("Búsqueda de mensajes");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel labelIcono = new JLabel(Utils.getIcon("src/main/resources/glass.png", 4f));
		labelIcono.setText("Búsqueda de mensajes ");
		labelIcono.setFont(new Font("Segoe UI", Font.BOLD, 18));
		labelIcono.setBorder(new LineBorder(Color.BLACK, 2));
		contentPane.add(labelIcono);
		
		JPanel panelBuscar = new JPanel();
		panelBuscar.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(panelBuscar);
		panelBuscar.setLayout(new BoxLayout(panelBuscar, BoxLayout.Y_AXIS));
		
		JPanel panelArriba = new JPanel();
		panelBuscar.add(panelArriba);
		
		JLabel lblTexto = new JLabel("Introduce el texto a buscar: ");
		lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTexto = new JTextField();
		textFieldTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTexto.setColumns(40);
		panelArriba.add(lblTexto);
		panelArriba.add(textFieldTexto);
		
		JPanel panelMedio = new JPanel();
		
		JLabel lblTlf = new JLabel("Teléfono: ");
		lblTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		textFieldTlf = new JTextField();
		textFieldTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTlf.setColumns(18);
		panelMedio.add(lblTlf);
		panelMedio.add(textFieldTlf);
		
		JLabel lblContacto = new JLabel("Contacto: ");
		lblContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldContacto = new JTextField();
		textFieldContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldContacto.setColumns(18);
		panelMedio.add(lblContacto);
		panelMedio.add(textFieldContacto);
		panelBuscar.add(panelMedio);
		
		panelBuscar.setPreferredSize(new Dimension(800, 120));
		
		JPanel panelAbajo = new JPanel();
		panelBuscar.add(panelAbajo);
		
		JLabel lblEmisor = new JLabel("Emisor:");
		lblEmisor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		panelAbajo.add(lblEmisor);
		
		String[] opciones = { "Todos","Enviados", "Recibidos" };
		JComboBox<String> comboBox = new JComboBox<String>(opciones);
		panelAbajo.add(comboBox);
		
		
		JButton btnBuscar = new JButton("Buscar");
		panelAbajo.add(btnBuscar);
		btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		
		btnBuscar.addActionListener(e -> {
			String filtroTexto = textFieldTexto.getText();
			String filtroTlf = textFieldTlf.getText();
			String filtroNombreContacto = textFieldContacto.getText();
			boolean filtroEnviados = comboBox.getSelectedIndex() == 1;
			boolean filtroRecibidos = comboBox.getSelectedIndex() == 2;
			buscarMensajes(filtroTexto, filtroTlf, filtroNombreContacto, filtroEnviados, filtroRecibidos);
		});		
		
		mensajesBuscados = new DefaultListModel<>();
		listaMensajes = new JList<>(mensajesBuscados);
		listaMensajes.setCellRenderer(createListRenderer());
		listaMensajes.setBackground(getForeground());
		
		JScrollPane scrollPane = new JScrollPane(listaMensajes);
		
		scrollPane.setBorder(new TitledBorder(null, "Resultados", TitledBorder.LEADING, TitledBorder.TOP));
		scrollPane.setPreferredSize(new Dimension(800, 480));
		contentPane.add(scrollPane);
		
		contentPane.add(Box.createVerticalStrut(10));
		
		JButton btnVolver = new JButton("Volver a la ventana principal");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
		contentPane.add(btnVolver);
		
		btnVolver.addActionListener(e -> {
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.setVisible(true);
			dispose();
		});
	}

	/**
	 * Método que se ejecuta al pulsar el botón de buscar.
	 */
	private void buscarMensajes(String texto, String tlf, String nombreContacto, boolean isEnviados, boolean isRecibidos) {
		//Antes de llamar al controlador, validamos la entrada			
		String error = validarEntrada(texto, tlf, nombreContacto, isEnviados, isRecibidos);	    
		if (error.isEmpty()) {
	    	List<Mensaje> mensajes = controlador.buscarMensaje(texto, tlf, nombreContacto, isEnviados, isRecibidos);
	    	mensajesBuscados.clear();
	    	listaMensajes.repaint();
	    	mensajes.forEach(m -> mensajesBuscados.addElement(m));	    		    
	    }else { //Las entradas no son válidas, mostramos mensaje de error y configuramos bordes
	    	Toolkit.getDefaultToolkit().beep();
	    	JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Método para validar la entrada del usuario.
	 * 
	 * @param tlf
	 * @param pass
	 * @return mensaje de error
	 */
	private String validarEntrada(String texto, String tlf, String nombreContacto, boolean isEnviados, boolean isRecibidos ) {		
		if (texto.isEmpty() && tlf.isEmpty() && nombreContacto.isEmpty() && !isEnviados && !isRecibidos) return ERROR_VACIO;	    	    		
		if (!nombreContacto.isEmpty()) {
	    	Optional<Contacto> contacto = Optional.ofNullable(controlador.buscarContactoDeUsuario(nombreContacto));
	    	if (contacto.isEmpty()) return ERROR_NOMBRE_CONTACTO;	    		    
	    }	    	    		
		if (!tlf.isEmpty()) {			
	    	try {
				Integer.parseInt(tlf);
			} catch (NumberFormatException e) {
				return ERROR_FORMATO_TLF;	
			}			
	    	Optional<Contacto> contacto = Optional.ofNullable(ControladorAppChat.getInstancia().buscarContactoDeUsuario(Integer.parseInt(tlf)));
	    	if (contacto.isEmpty()) {
	    		return ERROR_TLF_CONTACTO;
	    	}	        
	    }
	    return ""; // No hay errores
	}	
	
	private static ListCellRenderer<? super Mensaje> createListRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				
				JPanel panelEntero = new JPanel();
				panelEntero.setLayout(new BoxLayout(panelEntero, BoxLayout.Y_AXIS));
				
				Mensaje msj = (Mensaje) value;
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(Color.BLACK, 1));
				panel.setLayout(new BorderLayout());
				JLabel labelEmisor = new JLabel(" " + generarNombreEmisor(msj));
				labelEmisor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelReceptor = new JLabel(generarNombreReceptor(msj) + " ");
				labelReceptor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelTexto = generarLabelTexto(msj);
				labelTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				labelTexto.setBorder(new LineBorder(Color.BLACK, 1));
				
				labelTexto.setPreferredSize(new Dimension(730, 45));
				
				panel.add(labelEmisor, BorderLayout.WEST);
				panel.add(labelReceptor, BorderLayout.EAST);
				panel.add(labelTexto, BorderLayout.SOUTH);
				panel.setMaximumSize(new Dimension(730, 80));
				
				panel.setBackground((isSelected) ? Color.lightGray : Color.white);
							
				panelEntero.add(panel);
				panelEntero.add(Box.createVerticalStrut(10));
	
				return panelEntero;
			}
		};
	}
	
	/**
	 * Método que genera el nombre del receptor para el cell renderer de mensajes
	 * @param mensaje
	 * @return nombre del receptor
	 */
	private static String generarNombreReceptor(Mensaje msj) {
		Usuario usuarioActual = ControladorAppChat.getInstancia().getUsuarioActual();
		//Si el receptor del mensaje es un grupo, se mostrará el nombre del grupo
		if (msj.getReceptor() instanceof Grupo) return msj.getReceptor().getNombre();
		//Si el receptor del mensaje es el usuario actual, se mostrará "Tú"
		else if (((ContactoIndividual) msj.getReceptor()).getUsuarioAsociado().equals(usuarioActual)) return "Tú";
		//Si el receptor del mensaje es un contacto individual que no es el usuario actual, se mostrará el nombre del contacto
		else return msj.getReceptor().getNombre();
	}
	
	/**
	 * Método que genera el nombre del emisor para el cell renderer de mensajes
	 * 
	 * @param mensaje
	 * @return nombre del emisor
	 */
	private static String generarNombreEmisor(Mensaje msj) {
		Usuario usuarioActual = ControladorAppChat.getInstancia().getUsuarioActual();
		if (msj.getEmisor().equals(usuarioActual)) {
			// Si el emisor del mensaje es el usuario actual, se mostrará "Tú"
			return "Tú";
		}else {
            // Si el emisor del mensaje no es el usuario, se mostrará el nombre del contacto agregad 			
			return usuarioActual.getContactos().stream()
				    .filter(c -> c instanceof ContactoIndividual)
				    .map(c -> (ContactoIndividual) c)
				    .filter(c -> c.getUsuarioAsociado().equals(msj.getEmisor()))
				    .map(c -> c.getNombre())
				    .findFirst()
				    .orElse("Usuario desconocido");
		}		
	}
	
	/**
	 * Método que genera el JLabel para el texto del cell renderer de mensajes
	 * 
	 * @param mensaje
	 * @return JLabel con el texto o emoticono del mensaje
	 */
	private static JLabel generarLabelTexto(Mensaje msj) {
		if (!msj.getTexto().isEmpty()) return new JLabel(" " + msj.getTexto());
		else return new JLabel(BubbleText.getEmoji(msj.getEmoticono()));  
	}
}
