package umu.tds.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.utils.Utils;
import org.apache.commons.validator.routines.EmailValidator;

@SuppressWarnings("serial")
/**
 * Ventana que permite registrar un nuevo usuario
 */
public class VentanaRegistro extends JFrame {
	private JTextField emailField;
	private JTextField nombreField;
	private JTextField apellidosField;
	private JTextField telefonoField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JDateChooser dateChooser;
	private JLabel imageLabel;
	private JTextArea saludoField;

	public VentanaRegistro() {
		// Configuración de la ventana
		setTitle("Registro de Usuario");
		setBounds(100, 100, 850, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Panel principal
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Registro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_panelCentro.columnWidths = new int[]{0, 0, 0, 0, 152};
		panelCentro.setLayout(gbl_panelCentro);

		// Etiqueta y campo de "Nombre"
		GridBagConstraints gbcNombreLabel = new GridBagConstraints();
		gbcNombreLabel.anchor = GridBagConstraints.WEST;
		gbcNombreLabel.gridwidth = 2;
		gbcNombreLabel.insets = new Insets(5, 5, 5, 5);
		gbcNombreLabel.gridx = 0;
		gbcNombreLabel.gridy = 0;
		JLabel label = new JLabel("Nombre:");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label, gbcNombreLabel);

		GridBagConstraints gbcNombreField = new GridBagConstraints();
		gbcNombreField.anchor = GridBagConstraints.WEST;
		gbcNombreField.gridwidth = 3;
		gbcNombreField.insets = new Insets(5, 5, 5, 0);
		gbcNombreField.gridx = 2;
		gbcNombreField.gridy = 0;
		nombreField = new JTextField(55);
		nombreField.setBorder(new LineBorder(Color.BLACK, 1));
		panelCentro.add(nombreField, gbcNombreField);
		nombreField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				nombreField.setBorder(new LineBorder(Color.BLACK, 2));
			}
			public void focusLost(FocusEvent evt) {
				nombreField.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		
		nombreField.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
		});

		// Etiqueta y campo de "Apellidos"
		GridBagConstraints gbcApellidosLabel = new GridBagConstraints();
		gbcApellidosLabel.anchor = GridBagConstraints.WEST;
		gbcApellidosLabel.gridwidth = 2;
		gbcApellidosLabel.insets = new Insets(5, 5, 5, 5);
		gbcApellidosLabel.gridx = 0;
		gbcApellidosLabel.gridy = 1;
		JLabel label_1 = new JLabel("Apellidos:");
		label_1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_1, gbcApellidosLabel);

		GridBagConstraints gbcApellidosField = new GridBagConstraints();
		gbcApellidosField.anchor = GridBagConstraints.WEST;
		gbcApellidosField.gridwidth = 3;
		gbcApellidosField.insets = new Insets(5, 5, 5, 0);
		gbcApellidosField.gridx = 2;
		gbcApellidosField.gridy = 1;
		apellidosField = new JTextField(55);
		apellidosField.setBorder(new LineBorder(Color.BLACK, 1));
		panelCentro.add(apellidosField, gbcApellidosField);
		apellidosField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				apellidosField.setBorder(new LineBorder(Color.BLACK, 2));
			}

			public void focusLost(FocusEvent evt) {
				apellidosField.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		
		apellidosField.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
        });	

		// Etiqueta y campo de "Teléfono"
		GridBagConstraints gbcTelefonoLabel = new GridBagConstraints();
		gbcTelefonoLabel.anchor = GridBagConstraints.WEST;
		gbcTelefonoLabel.gridwidth = 2;
		gbcTelefonoLabel.insets = new Insets(5, 5, 5, 5);
		gbcTelefonoLabel.gridx = 0;
		gbcTelefonoLabel.gridy = 2;
		JLabel label_2 = new JLabel("Teléfono:");
		label_2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_2, gbcTelefonoLabel);

		GridBagConstraints gbcTelefonoField = new GridBagConstraints();
		gbcTelefonoField.anchor = GridBagConstraints.EAST;
		gbcTelefonoField.insets = new Insets(5, 5, 5, 5);
		gbcTelefonoField.gridx = 2;
		gbcTelefonoField.gridy = 2;
		telefonoField = new JTextField(20);
		telefonoField.setBorder(new LineBorder(Color.BLACK, 1));
		panelCentro.add(telefonoField, gbcTelefonoField);
		telefonoField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				telefonoField.setBorder(new LineBorder(Color.BLACK, 2));
			}

			public void focusLost(FocusEvent evt) {
				telefonoField.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});

		telefonoField.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
		});
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 3;
		gbc_lblEmail.gridy = 2;
		panelCentro.add(lblEmail, gbc_lblEmail);

		emailField = new JTextField(20);
		emailField.setBorder(new LineBorder(Color.BLACK, 1));
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		emailField.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				emailField.setBorder(new LineBorder(Color.BLACK, 2));
			}
			public void focusLost(FocusEvent evt) {
				emailField.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		
		emailField.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
        });
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.insets = new Insets(0, 0, 5, 0);
		gbc_emailField.gridx = 4;
		gbc_emailField.gridy = 2;
		panelCentro.add(emailField, gbc_emailField);

		// Etiquetas y campos de "Contraseña"
		GridBagConstraints gbcPasswordLabel1 = new GridBagConstraints();
		gbcPasswordLabel1.anchor = GridBagConstraints.WEST;
		gbcPasswordLabel1.gridwidth = 2;
		gbcPasswordLabel1.insets = new Insets(5, 5, 5, 5);
		gbcPasswordLabel1.gridx = 0;
		gbcPasswordLabel1.gridy = 3;
		JLabel label_3 = new JLabel("Contraseña:");
		label_3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_3, gbcPasswordLabel1);

		GridBagConstraints gbcPasswordField1 = new GridBagConstraints();
		gbcPasswordField1.anchor = GridBagConstraints.EAST;
		gbcPasswordField1.insets = new Insets(5, 5, 5, 5);
		gbcPasswordField1.gridx = 2;
		gbcPasswordField1.gridy = 3;
		passwordField1 = new JPasswordField(20);
		passwordField1.setBorder(new LineBorder(Color.BLACK, 1));
		panelCentro.add(passwordField1, gbcPasswordField1);
		passwordField1.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				passwordField1.setBorder(new LineBorder(Color.BLACK, 2));
			}

			public void focusLost(FocusEvent evt) {
				passwordField1.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});

		passwordField1.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
		});
		
		GridBagConstraints gbcPasswordLabel2 = new GridBagConstraints();
		gbcPasswordLabel2.insets = new Insets(5, 5, 5, 5);
		gbcPasswordLabel2.gridx = 3;
		gbcPasswordLabel2.gridy = 3;
		JLabel label_7 = new JLabel("Confirmar Contraseña:");
		label_7.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_7, gbcPasswordLabel2);

		passwordField2 = new JPasswordField(15);
		passwordField2.setBorder(new LineBorder(Color.BLACK, 1));
		passwordField2.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField2.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				passwordField2.setBorder(new LineBorder(Color.BLACK, 2));
			}

			public void focusLost(FocusEvent evt) {
				passwordField2.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		
		passwordField2.addActionListener(e -> {
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
		});

		JPanel btnMostrarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));		
		JButton btnMostrar = new JButton(Utils.getIcon("src/main/resources/OjoOculto.png", 1.5f));
		btnMostrar.setFont(new Font("Segoe UI", Font.BOLD, 13));			
		btnMostrarPanel.add(passwordField2);
		btnMostrarPanel.add(btnMostrar);

		GridBagConstraints gbcbtnMostrarPanel = new GridBagConstraints();
		gbcbtnMostrarPanel.anchor = GridBagConstraints.WEST;
		gbcbtnMostrarPanel.insets = new Insets(5, 5, 5, 0);
		gbcbtnMostrarPanel.gridx = 4;
		gbcbtnMostrarPanel.gridy = 3;

		panelCentro.add(btnMostrarPanel, gbcbtnMostrarPanel);

		// Etiqueta y campo de "Fecha"
		GridBagConstraints gbcFechaLabel = new GridBagConstraints();
		gbcFechaLabel.anchor = GridBagConstraints.WEST;
		gbcFechaLabel.gridwidth = 2;
		gbcFechaLabel.insets = new Insets(5, 5, 5, 5);
		gbcFechaLabel.gridx = 0;
		gbcFechaLabel.gridy = 4;		
		JLabel label_4 = new JLabel("Fecha:");
		label_4.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_4, gbcFechaLabel);

		GridBagConstraints gbcFechaField = new GridBagConstraints();
		gbcFechaField.anchor = GridBagConstraints.WEST;
		gbcFechaField.insets = new Insets(5, 5, 5, 5);
		gbcFechaField.gridx = 2;
		gbcFechaField.gridy = 4;
		dateChooser = new JDateChooser();
		dateChooser.setBorder(new LineBorder(Color.BLACK, 1));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		dateChooser.setPreferredSize(new Dimension(165, 20));
		panelCentro.add(dateChooser, gbcFechaField);

		// Etiqueta y botón de "Imagen"
		GridBagConstraints gbcImagenLabel = new GridBagConstraints();
		gbcImagenLabel.insets = new Insets(5, 5, 5, 5);
		gbcImagenLabel.gridx = 3;
		gbcImagenLabel.gridy = 4;
		JLabel label_6 = new JLabel("Imagen:");
		label_6.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_6, gbcImagenLabel);

		imageLabel = new JLabel();
		imageLabel.setVisible(false);

		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setText("Selecciona una imagen o<br>arrástrala aquí"); 
		editorPane.setEditable(false); 
		editorPane.setDropTarget(new DropTarget() {

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					if (!droppedFiles.isEmpty()) {
						File file = droppedFiles.get(0);		                
						ImageIcon imageIcon = Utils.getScaledIcon(file.getPath(), 80, 80);
						imageIcon.setDescription(file.getPath());
						imageLabel.setIcon(imageIcon);
						editorPane.setVisible(false);
						imageLabel.setVisible(true);					
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JPanel panelArrastrar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panelArrastrar.add(imageLabel);
		panelArrastrar.add(editorPane);		
		GridBagConstraints gbcEditorPane = new GridBagConstraints();
		gbcEditorPane.insets = new Insets(5, 5, 0, 0);
		gbcEditorPane.gridx = 4;
		gbcEditorPane.gridy = 5;
		gbcEditorPane.fill = GridBagConstraints.BOTH;
		gbcEditorPane.anchor = GridBagConstraints.CENTER;
		panelCentro.add(panelArrastrar, gbcEditorPane);

		//PANEL DE BOTONES
		JPanel panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 5, 0);
		gbc_panelBotones.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotones.gridx = 4;
		gbc_panelBotones.gridy = 4;
		panelCentro.add(panelBotones, gbc_panelBotones);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton btnSeleccionar = new JButton("Seleccionar");
		panelBotones.add(btnSeleccionar);
		btnSeleccionar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panelBotones.add(btnEliminar);



		// Etiqueta y área de texto de "Saludo"
		GridBagConstraints gbcSaludoLabel = new GridBagConstraints();
		gbcSaludoLabel.anchor = GridBagConstraints.WEST;
		gbcSaludoLabel.gridwidth = 2;
		gbcSaludoLabel.insets = new Insets(5, 5, 0, 5);
		gbcSaludoLabel.gridx = 0;
		gbcSaludoLabel.gridy = 5;
		JLabel label_5 = new JLabel("Saludo:");
		label_5.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_5, gbcSaludoLabel);

		GridBagConstraints gbcSaludoField = new GridBagConstraints();
		gbcSaludoField.insets = new Insets(5, 5, 0, 5);
		gbcSaludoField.gridx = 2;
		gbcSaludoField.gridy = 5;
		saludoField = new JTextArea(3, 20);
		saludoField.setText("Hi there..");
		panelCentro.add(new JScrollPane(saludoField), gbcSaludoField);


		getContentPane().add(panelCentro);

		JPanel panelOeste = new JPanel();
		getContentPane().add(panelOeste, BorderLayout.WEST);

		Component rigidArea = Box.createRigidArea(new Dimension(50, 40));
		panelOeste.add(rigidArea);

		JPanel panelEste = new JPanel();
		getContentPane().add(panelEste, BorderLayout.EAST);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(50, 40));
		panelEste.add(rigidArea_1);

		JPanel panelNorte = new JPanel();
		getContentPane().add(panelNorte, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("AppChat");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 45));
		panelNorte.add(lblNewLabel);

		JPanel panelSur = new JPanel();
		getContentPane().add(panelSur, BorderLayout.SOUTH);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 50));
		panelSur.add(rigidArea_2);
		JButton cancelarButton = new JButton("Volver a login");
		panelSur.add(cancelarButton);
		cancelarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));	
		JButton aceptarButton = new JButton("Aceptar");
		panelSur.add(aceptarButton);
		aceptarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));

		// Acciones de los botones
		cancelarButton.addActionListener(e -> {
			VentanaLogin window = new VentanaLogin();
			window.setVisible(true);
			dispose();
		});

		btnEliminar.addActionListener(e -> {
			imageLabel.setIcon(null);
			editorPane.setText("Selecciona una imagen o<br>arrástrala aquí");
			editorPane.setVisible(true);
			imageLabel.setVisible(false);
		});

		btnSeleccionar.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedFile.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
					imageIcon.setDescription(selectedFile.getPath());
					imageLabel.setIcon(imageIcon);
					editorPane.setVisible(false);
					imageLabel.setVisible(true);
				}
			}
		});

		btnMostrar.addActionListener(e -> {
			if (passwordField1.getEchoChar() == '•') {
				passwordField1.setEchoChar((char) 0);
				passwordField2.setEchoChar((char) 0);
				btnMostrar.setIcon(Utils.getIcon("src/main/resources/OjoAbierto.png", 1.5f));
			} else {
				passwordField1.setEchoChar('•');
				passwordField2.setEchoChar('•');
				btnMostrar.setIcon(Utils.getIcon("src/main/resources/OjoOculto.png", 1.5f));
			}
		});

		aceptarButton.addActionListener(e -> {		
			registrar(nombreField.getText(),apellidosField.getText(), telefonoField.getText(), emailField.getText(), String.valueOf(passwordField1.getPassword()), String.valueOf(passwordField2.getPassword()));
		});

	}
	/**
	 * Método para registrar un nuevo usuario.
	 * @param nombre
	 * @param apellidos
	 * @param tlf
	 * @param email
	 * @param pass1
	 * @param pass2
	 */
	private void registrar(String nombre, String apellidos, String tlf, String email, String pass1, String pass2) {
		String error = validarEntrada(nombre, apellidos, tlf, email, pass1, pass2);
		if (error.isEmpty()) {
			String fotoPerfilCodificada = imageLabel.getIcon() != null 
					? Utils.convertImageToBase64(new File(((ImageIcon) imageLabel.getIcon()).getDescription())) 
					: Utils.FOTO_USUARIO_POR_DEFECTO;

			LocalDate fechaNacimiento = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			boolean success = ControladorAppChat.getInstancia().registrarUsuario(nombre, apellidos, Integer.parseInt(tlf),pass1, saludoField.getText(), fechaNacimiento,email, fotoPerfilCodificada);

			JOptionPane.showMessageDialog(VentanaRegistro.this, success ? "Te has registrado con éxito" : "El usuario ya existe");
			if (success) {
				VentanaLogin window = new VentanaLogin(telefonoField.getText());
				window.setVisible(true);
				dispose();
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
			mostrarError(error, nombre, apellidos, tlf, email, pass1, pass2);
		}
	}
	
	/**
	 * Método para validar la entrada del usuario.
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param tlf
	 * @param email
	 * @param pass1
	 * @param pass2
	 * La fecha no se pasa como parametro porque antes debemos comprobar que sea distinta de null.
	 * Si no lo hacemos así, el programa lanzará una excepción.
	 * @return
	 */
	private String validarEntrada(String nombre, String apellidos, String tlf, String email, 
			String pass1, String pass2) {
		String error = "";

		if (nombre.isEmpty() || apellidos.isEmpty() || tlf.isEmpty() || email.isEmpty() || pass1.isEmpty()
				|| pass2.isEmpty() || dateChooser.getDate() == null) {
			error = "Debes rellenar los siguientes campos: \n\t";
			if (nombre.isEmpty()) error += "nombre, ";
			if (apellidos.isEmpty()) error += "apellidos, ";
			if (tlf.isEmpty()) error += "teléfono, ";
			if (email.isEmpty()) error += "email, ";
			if (pass1.isEmpty()) error += "contraseña, ";
			if (pass2.isEmpty()) error += "confirmación de contraseña, ";
			if (dateChooser.getDate() == null) error += "fecha de nacimiento, ";
			error = error.substring(0, error.length() - 2);
		} else if (!pass1.equals(pass2)) {
			error = "Las contraseñas no coinciden";
		} else if (!tlf.matches("[0-9]{9}")) {
			error = "El teléfono debe tener 9 dígitos";
		} else if (!EmailValidator.getInstance().isValid(email)) {
			error = "El email debe ser compatible con el estándar RFC 5322";	
		}

		return error;
	}

	/**
	 * Método para mostrar un mensaje y configurar bordes.
	 * 
	 * @param mensaje
	 * @param nombre
	 * @param apellidos
	 * @param tlf
	 * @param email
	 * @param pass1
	 * @param pass2
	 * @param fecha
	 */
	private void mostrarError(String mensaje, String nombre, String apellidos, String tlf, String email, 
			String pass1, String pass2) {
		nombreField.setBorder(nombre.isEmpty() ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		apellidosField.setBorder(apellidos.isEmpty() ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		telefonoField.setBorder(tlf.isEmpty() || !tlf.matches("[0-9]{9}") ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		emailField.setBorder(email.isEmpty() || !EmailValidator.getInstance().isValid(email) ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		passwordField1.setBorder(pass1.isEmpty() || !pass1.equals(pass2) ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		passwordField2.setBorder(pass2.isEmpty() || !pass2.equals(pass1) ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		dateChooser.setBorder(dateChooser.getDate() == null ? new LineBorder(Color.RED, 2) : new LineBorder(Color.BLACK, 1));
		JOptionPane.showMessageDialog(VentanaRegistro.this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}





}