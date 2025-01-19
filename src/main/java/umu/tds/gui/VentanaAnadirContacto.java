package umu.tds.gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import umu.tds.controlador.ControladorAppChat;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Dimension;
/**
 * Ventana que permite añadir un contacto
 */
public class VentanaAnadirContacto {

	private static final String ERROR_TLF = "Se necesita un teléfono de 9 dígitos para el contacto";
	private static final String ERROR_NOMBRE = "Falta por introducir un nombre para el contacto";
	private static final String ERROR_TLF_NOMBRE = "Falta por introducir un teléfono de 9 dígitos y un nombre para el contacto";
	private static final String ERROR_AL_AÑADIR = "El contacto ya está añadido, el usuario no existe, o eres tu mismo";
	private static final String AÑADIDO_CORRECTAMENTE = "Contacto añadido correctamente";
	
	private JFrame frame;
	private JTextField txtTelefono;
	private JTextField txtNombre;
	private JButton btnAceptar;
	
	/**
	 * Create the application.
	 */
	public VentanaAnadirContacto() {
		initialize();
	}
	
	/**
	 * Constructor para añadir un contacto ficticio
	 */
	public VentanaAnadirContacto(String tlf) {
		initialize();
		txtTelefono.setText(tlf);
	}

	/**
	 * Set the visibility of the frame. 
	 * @param b
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Añade un contacto");
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelLogin);

		JPanel panelNorte = new JPanel();
		panelLogin.add(panelNorte, BorderLayout.NORTH);

		JLabel labelTitulo = new JLabel("Añade un contacto");
		labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		panelNorte.add(labelTitulo);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelLogin.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelCentro.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelCentro.columnWeights = new double[]{0.5, 0.0, 1.0, 0.5};
		gbl_panelCentro.rowWeights = new double[]{0.5, 0.0, 0.0, 0.5};
		panelCentro.setLayout(gbl_panelCentro);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panelCentro.add(lblNombre, gbc_lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtNombre.addActionListener(e -> {
			String tlf = txtTelefono.getText();
			String nombre = txtNombre.getText();
			añadirContacto(tlf, nombre);
		});
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 1;
		panelCentro.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 15));
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 2;
		panelCentro.add(lblTelefono, gbc_lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtTelefono.addActionListener(e -> {
			String tlf = txtTelefono.getText();
			String nombre = txtNombre.getText();
			añadirContacto(tlf, nombre);
		});
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 2;
		panelCentro.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);

		JPanel panelSur = new JPanel();
		panelLogin.add(panelSur, BorderLayout.SOUTH);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnCancelar);
		
		Component rigidArea = Box.createRigidArea(new Dimension(30, 20));
		panelSur.add(rigidArea);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnAceptar);
		
		btnCancelar.addActionListener(e -> {
			VentanaPrincipal main = new VentanaPrincipal();
			main.setVisible(true);
			frame.dispose();
		});
		
		btnAceptar.addActionListener(e -> {
			String tlf = txtTelefono.getText();
			String nombre = txtNombre.getText();
			añadirContacto(tlf, nombre);
		});

		JPanel panelOeste = new JPanel();
		panelLogin.add(panelOeste, BorderLayout.WEST);

		Component espacioOeste = Box.createHorizontalStrut(50);
		panelOeste.add(espacioOeste);

		JPanel panelEste = new JPanel();
		panelLogin.add(panelEste, BorderLayout.EAST);

		Component espacioEste = Box.createHorizontalStrut(50);
		panelEste.add(espacioEste);
	}
	
	/**
	 * Se comunica con el controlador para añadir un contacto
	 * @param tlf
	 * @param nombre
	 */
	private void añadirContacto(String tlf, String nombre) {		
		String errorEntrada = validarEntrada(tlf, nombre);
	    if (errorEntrada.isEmpty()) {
			boolean success = ControladorAppChat.getInstancia().anadirContactoNuevo(txtNombre.getText(), txtTelefono.getText());
			if (!success) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(frame, ERROR_AL_AÑADIR,
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(frame, AÑADIDO_CORRECTAMENTE, "Info",
						JOptionPane.INFORMATION_MESSAGE);
				VentanaPrincipal main = new VentanaPrincipal();
				main.setVisible(true);
				frame.dispose();
			}
	    }else { //Las entradas no son válidas, mostramos mensaje de error y configuramos bordes
	    	Toolkit.getDefaultToolkit().beep();
	        mostrarError(errorEntrada, tlf, nombre);
	    }
	}
	/**
	 * Comprueba si la entrada es válida
	 * @param tlf
	 * @param nombre
	 * @return mensaje de error
	 */
	private String validarEntrada(String tlf, String nombre) {
	    if ((tlf.isEmpty() || !tlf.matches("\\d{9}")) && nombre.isEmpty()) {
	        return ERROR_TLF_NOMBRE;
	    }
	    if ((!tlf.isEmpty() && tlf.matches("\\d{9}")) && nombre.isEmpty()) {
	        return ERROR_NOMBRE;
	    }
	    if ((tlf.isEmpty() || !tlf.matches("\\d{9}"))  && !nombre.isEmpty()) {
	        return ERROR_TLF;
	    }
	    return ""; // No hay errores
	}
	/**
	 * Muestra un error en la ventana
	 * @param mensaje
	 * @param tlf
	 * @param nombre
	 */
	private void mostrarError(String mensaje, String tlf, String nombre) {
	    // Cambiar bordes según tipo de error
	    txtTelefono.setBorder(tlf.isEmpty() || !tlf.matches("[0-9]{9}") ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
	    txtNombre.setBorder(nombre.isEmpty() ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
	    // Mostrar mensaje de error
	    JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
