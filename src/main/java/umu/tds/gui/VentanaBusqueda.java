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
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.Mensaje;
import umu.tds.utils.Utils;

@SuppressWarnings("serial")
public class VentanaBusqueda extends JFrame {

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
		
		JPanel panelAbajo = new JPanel();
		
		JLabel lblTlf = new JLabel("Teléfono: ");
		lblTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		textFieldTlf = new JTextField();
		textFieldTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTlf.setColumns(18);
		panelAbajo.add(lblTlf);
		panelAbajo.add(textFieldTlf);
		
		JLabel lblContacto = new JLabel("Contacto: ");
		lblContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldContacto = new JTextField();
		textFieldContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldContacto.setColumns(18);
		panelAbajo.add(lblContacto);
		panelAbajo.add(textFieldContacto);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelAbajo.add(btnBuscar);
		panelBuscar.add(panelAbajo);
		
		panelBuscar.setPreferredSize(new Dimension(800, 120));
		
		mensajesBuscados = new DefaultListModel<>();
		/*
		mensajesBuscados.addElement(new Mensaje("¿A qué hora paso por ti?", LocalDateTime.of(2023, 11, 3, 14, 0), 0, 345678901, 234567890));
		mensajesBuscados.addElement(new Mensaje("¡Nos vemos mañana! Nos vemos mañana! Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!", LocalDateTime.of(2023, 11, 3, 20, 30), 4, 456789012, 345678901));
		mensajesBuscados.addElement(new Mensaje("Gracias por el consejo", LocalDateTime.of(2023, 11, 4, 16, 20), 5, 567890123, 456789012));
		mensajesBuscados.addElement(new Mensaje("¿Estás disponible para una reunión?", LocalDateTime.of(2023, 11, 5, 11, 10), 1, 678901234, 567890123));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		*/
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
		
		btnBuscar.addActionListener(e -> {
            accionAceptar();
		});

		
	}
	
	private void accionAceptar() {
		//Antes de llamar al controlador, validamos la entrada
		String texto = textFieldTexto.getText();
		String tlf = textFieldTlf.getText();
		String nombreContacto = textFieldContacto.getText();
		
		Optional<String> error = Optional.ofNullable(validarEntrada(texto, tlf, nombreContacto));

	    if (error.isEmpty()) {
	    	List<Mensaje> mensajes = ControladorAppChat.getInstancia().buscarMensaje(texto, tlf, nombreContacto);
	    	mensajesBuscados.clear();
	    	listaMensajes.repaint();
	    	mensajes.forEach(m -> mensajesBuscados.addElement(m));	    		    
	    }else { //Las entradas no son válidas, mostramos mensaje de error y configuramos bordes
	    	Toolkit.getDefaultToolkit().beep();
	        mostrarError(error.get());
	    }
	}

	/**
	 * Método para validar la entrada del usuario.
	 * 
	 * @param tlf
	 * @param pass
	 * @return
	 */
	private String validarEntrada(String texto, String tlf, String nombreContacto ) {
	    if (texto.isEmpty() && tlf.isEmpty() && nombreContacto.isEmpty()) {
	        return "Debe introducir al menos un filtro de búsqueda";
	    }	    
	    if (!nombreContacto.isEmpty()) {
	    	Optional<Contacto> contacto = Optional.ofNullable(ControladorAppChat.getInstancia().buscarContactoDeUsuario(nombreContacto));
	    	if (contacto.isEmpty()) {
	    		return "No existe ningún contacto con ese nombre";
	    	}	    
	    }	    
	    if (!tlf.isEmpty()) {
	    	Optional<Contacto> contacto = Optional.ofNullable(ControladorAppChat.getInstancia().buscarContactoDeUsuario(Integer.parseInt(tlf)));
	    	if (contacto.isEmpty()) {
	    		return "No existe ningún contacto con ese teléfono";
	    	}	        
	    }
	    return null; // No hay errores
	}

	/**
	 * Método para mostrar un mensaje de error y configurar bordes.
	 * 
	 * @param mensaje
	 * @param tlf
	 * @param pass
	 */
	private void mostrarError(String mensaje) {
	    JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	private static ListCellRenderer<? super Mensaje> createListRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				
				JPanel panelEntero = new JPanel();
				panelEntero.setLayout(new BoxLayout(panelEntero, BoxLayout.Y_AXIS));
				
				Mensaje msj = (Mensaje) value;
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(Color.BLACK, 1));
				panel.setLayout(new BorderLayout());
				JLabel labelEmisor = new JLabel(" " + msj.getEmisor().getNombre());
				labelEmisor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelReceptor = new JLabel(msj.getReceptor().getNombre() + " ");
				labelReceptor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelTexto = new JLabel(" " + msj.getTexto());
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
}
