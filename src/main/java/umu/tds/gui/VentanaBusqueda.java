package umu.tds.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDateTime;

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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import umu.tds.dominio.Contacto;
import umu.tds.dominio.Mensaje;

public class VentanaBusqueda extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBusqueda frame = new VentanaBusqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		JLabel labelIcono = new JLabel(VentanaPrincipal.getIcon("src/main/resources/glass.png", 4f));
		labelIcono.setText("Búsqueda de mensajes ");
		labelIcono.setFont(new Font("Segoe UI", Font.BOLD, 18));
		labelIcono.setBorder(new LineBorder(Color.BLACK, 2));
		labelIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(labelIcono);
		
		JPanel panelBuscar = new JPanel();
		panelBuscar.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(panelBuscar);
		panelBuscar.setLayout(new BoxLayout(panelBuscar, BoxLayout.Y_AXIS));
		
		JPanel panelArriba = new JPanel();
		panelBuscar.add(panelArriba);
		
		JLabel lblTexto = new JLabel("Introduce el texto a buscar: ");
		lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		JTextField textFieldTexto = new JTextField();
		textFieldTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTexto.setColumns(40);
		panelArriba.add(lblTexto);
		panelArriba.add(textFieldTexto);
		
		JPanel panelAbajo = new JPanel();
		
		JLabel lblTlf = new JLabel("Teléfono: ");
		lblTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		JTextField textFieldTlf = new JTextField();
		textFieldTlf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldTlf.setColumns(18);
		panelAbajo.add(lblTlf);
		panelAbajo.add(textFieldTlf);
		
		JLabel lblContacto = new JLabel("Contacto: ");
		lblContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		JTextField textFieldContacto = new JTextField();
		textFieldContacto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textFieldContacto.setColumns(18);
		panelAbajo.add(lblContacto);
		panelAbajo.add(textFieldContacto);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelAbajo.add(btnBuscar);
		panelBuscar.add(panelAbajo);
		
		panelBuscar.setPreferredSize(new Dimension(800, 100));
		panelBuscar.setMaximumSize(new Dimension(800, 100));
		
		DefaultListModel<Mensaje> mensajesBuscados = new DefaultListModel<>();
		mensajesBuscados.addElement(new Mensaje("¡Hola! ¿Cómo estás?", LocalDateTime.of(2023, 11, 1, 9, 30), 1, 123456789, 987654321));
		mensajesBuscados.addElement(new Mensaje("¿Quieres salir a cenar esta noche?", LocalDateTime.of(2023, 11, 1, 17, 45), 2, 123456789, 987654321));
		mensajesBuscados.addElement(new Mensaje("¡Felicidades en tu nuevo trabajo!", LocalDateTime.of(2023, 11, 2, 10, 15), 3, 234567890, 123456789));
		mensajesBuscados.addElement(new Mensaje("¿A qué hora paso por ti?", LocalDateTime.of(2023, 11, 3, 14, 0), 0, 345678901, 234567890));
		mensajesBuscados.addElement(new Mensaje("¡Nos vemos mañana! Nos vemos mañana! Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!Nos vemos mañana!", LocalDateTime.of(2023, 11, 3, 20, 30), 4, 456789012, 345678901));
		mensajesBuscados.addElement(new Mensaje("Gracias por el consejo", LocalDateTime.of(2023, 11, 4, 16, 20), 5, 567890123, 456789012));
		mensajesBuscados.addElement(new Mensaje("¿Estás disponible para una reunión?", LocalDateTime.of(2023, 11, 5, 11, 10), 1, 678901234, 567890123));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));
		mensajesBuscados.addElement(new Mensaje("Te llamo en un momento.", LocalDateTime.of(2023, 11, 5, 13, 55), 0, 789012345, 678901234));

		JList<Mensaje> listaMensajes = new JList<>(mensajesBuscados);
		listaMensajes.setCellRenderer(createListRenderer());
		
		JScrollPane scrollPane = new JScrollPane(listaMensajes);
		scrollPane.setBorder(new TitledBorder(null, "Resultados", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(scrollPane);
		
		JButton btnVolver = new JButton("Volver a la ventana principal");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
		contentPane.add(btnVolver);
		
		btnVolver.addActionListener(e -> {
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.setVisible(true);
			dispose();
		});
		
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
				JLabel labelEmisor = new JLabel(" " + Integer.toString(msj.getTlfEmisor()));
				labelEmisor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelReceptor = new JLabel(Integer.toString(msj.getTlfReceptor()) + " ");
				labelReceptor.setFont(new Font("Segoe UI", Font.BOLD, 15));
				JLabel labelTexto = new JLabel(" " + msj.getTexto());
				labelTexto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
				labelTexto.setBorder(new LineBorder(Color.BLACK, 1));
				
				labelTexto.setPreferredSize(new Dimension(730, 50));
				
				panel.add(labelEmisor, BorderLayout.WEST);
				panel.add(labelReceptor, BorderLayout.EAST);
				panel.add(labelTexto, BorderLayout.SOUTH);
				panel.setPreferredSize(new Dimension(730, 80));
				panel.setMaximumSize(new Dimension(730, 80));
				
				panel.setBackground((isSelected) ? Color.lightGray : list.getBackground());
							
				panelEntero.add(panel);
				panelEntero.add(Box.createVerticalStrut(10));
	
				return panelEntero;
			}
		};
	}
}
