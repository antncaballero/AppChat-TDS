package umu.tds.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import java.io.File;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

public class VentanaRegistro extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro frame = new VentanaRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaRegistro() {
		// Configuración de la ventana
		setTitle("Registro de Usuario");
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Panel principal
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Registro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
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
		JTextField nombreField = new JTextField(55);
		panelCentro.add(nombreField, gbcNombreField);

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
		JTextField apellidosField = new JTextField(55);
		panelCentro.add(apellidosField, gbcApellidosField);

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
		JTextField telefonoField = new JTextField(20);
		panelCentro.add(telefonoField, gbcTelefonoField);

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
		JPasswordField passwordField1 = new JPasswordField(20);
		panelCentro.add(passwordField1, gbcPasswordField1);

		GridBagConstraints gbcPasswordLabel2 = new GridBagConstraints();
		gbcPasswordLabel2.insets = new Insets(5, 5, 5, 5);
		gbcPasswordLabel2.gridx = 3;
		gbcPasswordLabel2.gridy = 3;
		JLabel label_7 = new JLabel("Confirmar Contraseña:");
		label_7.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelCentro.add(label_7, gbcPasswordLabel2);

		GridBagConstraints gbcPasswordField2 = new GridBagConstraints();
		gbcPasswordField2.anchor = GridBagConstraints.WEST;
		gbcPasswordField2.insets = new Insets(5, 5, 5, 0);
		gbcPasswordField2.gridx = 4;
		gbcPasswordField2.gridy = 3;
		JPasswordField passwordField2 = new JPasswordField(20);
		passwordField2.setHorizontalAlignment(SwingConstants.LEFT);
		panelCentro.add(passwordField2, gbcPasswordField2);

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
		JDateChooser dateChooser = new JDateChooser();
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
	
		GridBagConstraints gbcEditorPane = new GridBagConstraints();
		gbcEditorPane.insets = new Insets(5, 5, 0, 0);
		gbcEditorPane.gridx = 4;
		gbcEditorPane.gridy = 5;
		gbcEditorPane.fill = GridBagConstraints.BOTH;
		gbcEditorPane.anchor = GridBagConstraints.CENTER;
		
		JLabel imageLabel = new JLabel();
		imageLabel.setVisible(false);
		panelCentro.add(imageLabel, gbcEditorPane);
		
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setText("Selecciona una imagen o<br>arrástrala aquí"); 
		editorPane.setEditable(false); 
		editorPane.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						if (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpeg")) {
							ImageIcon imageIcon = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
							imageLabel.setIcon(imageIcon);
							editorPane.setVisible(false);
							imageLabel.setVisible(true);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
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
		btnEliminar.addActionListener(e -> {
				imageLabel.setIcon(null);
				editorPane.setText("Selecciona una imagen o<br>arrástrala aquí");
				editorPane.setVisible(true);
				imageLabel.setVisible(false);
		});
		
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panelBotones.add(btnEliminar);
		
		btnSeleccionar.addActionListener(e -> {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
	        int result = fileChooser.showOpenDialog(null);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            if (selectedFile != null) {
	                ImageIcon imageIcon = new ImageIcon(new ImageIcon(selectedFile.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	                imageLabel.setIcon(imageIcon);
	                editorPane.setVisible(false);
	                imageLabel.setVisible(true);
	            }
	        }
	    });
		panelCentro.add(editorPane, gbcEditorPane);

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
		JTextArea saludoField = new JTextArea(3, 20);
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
		JButton cancelarButton = new JButton("Cancelar");
		panelSur.add(cancelarButton);
		cancelarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));	
		JButton aceptarButton = new JButton("Aceptar");
		panelSur.add(aceptarButton);
		aceptarButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		aceptarButton.addActionListener(e -> {
			VentanaLogin window = new VentanaLogin();
			window.setVisible(true);
			dispose();			
		});
	}
}