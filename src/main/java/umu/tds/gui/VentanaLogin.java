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
import javax.swing.border.TitledBorder;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.utils.Utils;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Ventana de login de la aplicación.
 * 
 */
public class VentanaLogin {
	
	private static final String ERROR_FALTA_CONTRASEÑA = "Falta por introducir una contraseña";
	private static final String ERROR_FALTA_TELEFONO = "Falta por introducir un teléfono de 9 dígitos";
	private static final String ERROR_FALTA_TLF_CONTRASEÑA = "Falta por introducir un teléfono de 9 dígitos y una contraseña";
	
	private JFrame frame;
	private JTextField txtTelefono;
	private JPasswordField txtContrasena;

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
	}
	
	public VentanaLogin(String tlf) {
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
		frame.setTitle("AppChat");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelLogin);
		
		//Panel norte con título
		JPanel panelNorte = new JPanel();
		panelLogin.add(panelNorte, BorderLayout.NORTH);

		JLabel labelTitulo = new JLabel("AppChat");
		labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 65));
		panelNorte.add(labelTitulo);
		
		//Panel central con los campos de texto
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLogin.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelCentro.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelCentro.columnWeights = new double[]{0.5, 0.0, 1.0, 0.5};
		gbl_panelCentro.rowWeights = new double[]{0.5, 0.0, 0.0, 0.5};
		panelCentro.setLayout(gbl_panelCentro);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 1;
		panelCentro.add(lblTelefono, gbc_lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtTelefono.setBorder(new LineBorder(Color.BLACK, 1));
		
		//Añadimos un listener para cambiar el borde al entrar y salir del campo de texto
		txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
            	txtTelefono.setBorder(new LineBorder(Color.BLACK, 2));
            }
            public void focusLost(FocusEvent evt) {
            	txtTelefono.setBorder(new LineBorder(Color.BLACK, 1));
            }
        });
		//para que al pulsar enter se intente el inicie sesión
		txtTelefono.addActionListener(e -> {
			iniciarSesion(txtTelefono.getText(), String.valueOf(txtContrasena.getPassword()));
		});
		
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 1;
		panelCentro.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_lblContrasena = new GridBagConstraints();
		gbc_lblContrasena.anchor = GridBagConstraints.EAST;
		gbc_lblContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasena.gridx = 1;
		gbc_lblContrasena.gridy = 2;
		panelCentro.add(lblContrasena, gbc_lblContrasena);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtContrasena.setBorder(new LineBorder(Color.BLACK, 1));
		txtContrasena.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
            	txtContrasena.setBorder(new LineBorder(Color.BLACK, 2));
            }
            public void focusLost(FocusEvent evt) {
            	txtContrasena.setBorder(new LineBorder(Color.BLACK, 1));
            }
         });
		txtContrasena.addActionListener(e -> {
			iniciarSesion(txtTelefono.getText(), String.valueOf(txtContrasena.getPassword()));
		});
		
		GridBagConstraints gbc_txtContrasena = new GridBagConstraints();
		gbc_txtContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_txtContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContrasena.gridx = 2;
		gbc_txtContrasena.gridy = 2;
		panelCentro.add(txtContrasena, gbc_txtContrasena);
		txtContrasena.setColumns(10);
		
		JButton btnMostrar = new JButton(Utils.getIcon("src/main/resources/OjoOculto.png", 1.5f));
		GridBagConstraints gbc_btnMostrar = new GridBagConstraints();
		gbc_btnMostrar.insets = new Insets(0, 0, 5, 0);
		gbc_btnMostrar.gridx = 3;
		gbc_btnMostrar.gridy = 2;
		panelCentro.add(btnMostrar, gbc_btnMostrar);
		
		btnMostrar.addActionListener(e -> {
			if (txtContrasena.getEchoChar() == '•') {
				txtContrasena.setEchoChar((char) 0);
				btnMostrar.setIcon(Utils.getIcon("src/main/resources/OjoAbierto.png", 1.5f));
			} else {
				txtContrasena.setEchoChar('•');
				btnMostrar.setIcon(Utils.getIcon("src/main/resources/OjoOculto.png", 1.5f));
			}
		});
		
		//Panel sur con botones
		JPanel panelSur = new JPanel();
		panelLogin.add(panelSur, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnRegistrar);
		btnRegistrar.addActionListener(e -> {
            VentanaRegistro registro = new VentanaRegistro();
            registro.setVisible(true);
            frame.dispose();
        });

		Component espacioBotones = Box.createHorizontalStrut(150);
		panelSur.add(espacioBotones);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnCancelar);
		btnCancelar.addActionListener(e -> {
			txtTelefono.setText("");
			txtContrasena.setText("");
		});

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnAceptar);
		btnAceptar.addActionListener(e -> {
			iniciarSesion(txtTelefono.getText(), String.valueOf(txtContrasena.getPassword()));
		});
		
		//Paneles este y oeste para ajustar mejor el panel de login
		JPanel panelOeste = new JPanel();
		panelLogin.add(panelOeste, BorderLayout.WEST);

		Component espacioOeste = Box.createHorizontalStrut(110);
		panelOeste.add(espacioOeste);

		JPanel panelEste = new JPanel();
		panelLogin.add(panelEste, BorderLayout.EAST);

		Component espacioEste = Box.createHorizontalStrut(110);
		panelEste.add(espacioEste);
	}

	/**
	 *  Método para iniciar sesión a partir del controlador con los datos introducidos
	 * @param tlf 
	 * @param pass
	 */
	private void iniciarSesion(String tlf, String pass) {
		//Antes de llamar al controlador, validamos posibles campos vacíos
		String error = validarEntrada(tlf, pass);
	    if (error.isEmpty()) {
	    	boolean success = ControladorAppChat.getInstancia().iniciarSesion(Integer.parseInt(tlf), pass);
	    	JOptionPane.showMessageDialog(frame, success ? "Bienvenido a AppChat" : "Usuario no registrado");
	    	if (success) {
	    		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
				ventanaPrincipal.setVisible(true);
				frame.dispose();
			}	    	
	    }else { 
	    	//Las entradas no son válidas, mostramos mensaje de error y configuramos bordes
	    	Toolkit.getDefaultToolkit().beep();
	        mostrarError(error, tlf, pass);
	    }
	}

	/**
	 * Método para validar la entrada del usuario.
	 * 
	 * @param tlf
	 * @param pass
	 * @return
	 */
	private String validarEntrada(String tlf, String pass) {
	    if ((tlf.isEmpty() || !tlf.matches("\\d{9}")) && pass.isEmpty()) {
	        return ERROR_FALTA_TLF_CONTRASEÑA;
	    }
	    if ((!tlf.isEmpty() && tlf.matches("\\d{9}")) && pass.isEmpty()) {
	        return ERROR_FALTA_CONTRASEÑA;
	    }
	    if ((tlf.isEmpty() || !tlf.matches("\\d{9}"))  && !pass.isEmpty()) {
	        return ERROR_FALTA_TELEFONO;
	    }
	    return ""; // No hay errores
	}
	
	/**
	 * Método para mostrar un mensaje de error y configurar bordes.
	 * 
	 * @param mensaje
	 * @param tlf
	 * @param pass
	 */
	private void mostrarError(String mensaje, String tlf, String pass) {
	    // Cambiar bordes según tipo de error
	    txtTelefono.setBorder(tlf.isEmpty() || !tlf.matches("[0-9]{9}") 
	                          ? new LineBorder(Color.RED, 2) 
	                          : new LineBorder(Color.BLACK, 1));
	    
	    txtContrasena.setBorder(pass.isEmpty() 
	                            ? new LineBorder(Color.RED, 2) 
	                            : new LineBorder(Color.BLACK, 1));
	    
	    // Mostrar mensaje de error
	    JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}







