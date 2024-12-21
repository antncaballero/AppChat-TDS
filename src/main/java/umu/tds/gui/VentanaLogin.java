package umu.tds.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import java.awt.Component;

import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JPasswordField;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VentanaLogin {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
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

		JPanel panelNorte = new JPanel();
		panelLogin.add(panelNorte, BorderLayout.NORTH);

		JLabel labelTitulo = new JLabel("AppChat");
		labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 65));
		panelNorte.add(labelTitulo);

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

		JTextField txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 15));
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
		
		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		GridBagConstraints gbc_txtContrasena = new GridBagConstraints();
		gbc_txtContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_txtContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContrasena.gridx = 2;
		gbc_txtContrasena.gridy = 2;
		panelCentro.add(txtContrasena, gbc_txtContrasena);
		txtContrasena.setColumns(10);
		
		JButton btnMostrar = new JButton(getIcon("src/main/resources/OjoOculto.png", 1.5f));
		GridBagConstraints gbc_btnMostrar = new GridBagConstraints();
		gbc_btnMostrar.insets = new Insets(0, 0, 5, 0);
		gbc_btnMostrar.gridx = 3;
		gbc_btnMostrar.gridy = 2;
		panelCentro.add(btnMostrar, gbc_btnMostrar);
		
		btnMostrar.addActionListener(e -> {
			if (txtContrasena.getEchoChar() == '•') {
				txtContrasena.setEchoChar((char) 0);
				btnMostrar.setIcon(getIcon("src/main/resources/OjoAbierto.png", 1.5f));
			} else {
				txtContrasena.setEchoChar('•');
				btnMostrar.setIcon(getIcon("src/main/resources/OjoOculto.png", 1.5f));
			}
		});
		
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

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnAceptar);
		btnAceptar.addActionListener(e -> {
			VentanaPrincipal main = new VentanaPrincipal();
			main.setVisible(true);
			frame.dispose();
		});

		JPanel panelOeste = new JPanel();
		panelLogin.add(panelOeste, BorderLayout.WEST);

		Component espacioOeste = Box.createHorizontalStrut(110);
		panelOeste.add(espacioOeste);

		JPanel panelEste = new JPanel();
		panelLogin.add(panelEste, BorderLayout.EAST);

		Component espacioEste = Box.createHorizontalStrut(110);
		panelEste.add(espacioEste);
	}
	
	public static ImageIcon getIcon(String imageUrl, float factor) {
		//TODO: Pasar este método a una clase de utilidades.
		try {
			// Leer la imagen
			BufferedImage img = ImageIO.read(new File(imageUrl));
			// Obtenemos la proporcion ancho / altura.
			float proporcion = img.getWidth() / ((float) img.getHeight());
			// Obtenemos la Fuente (letra) por defecto especificada por el SO para un textPane.
			Font font = UIManager.getDefaults().getFont("TextPane.font");
			// Obtenemos el tamaño de letra.
			int tamanoLetra = font.getSize();

			// Se reeescala la iamgen.
			Image newimg = img.getScaledInstance(
					Math.round(factor * tamanoLetra * proporcion),  // Anchura: tamaño de la letra multiplicado por la proporcion original.
					Math.round(factor * tamanoLetra), // altura: tamaño de la letra
					java.awt.Image.SCALE_SMOOTH	// Método para reescalar (Calidad:SCALE_SMOOTH o rapidez SCALE_FAST)
					);
			// Se crea un ImageIcon
			return new ImageIcon(newimg);
		} catch (IOException e) {
			// Si falla la lectura de la imagen, el botón se generará sin icono. No es necesario parar la ejecución.
			return null;
		}
	}
	
}
