package umu.tds.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import tds.BubbleText;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import umu.tds.dominio.Usuario;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

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



	public static class UserListModel extends AbstractListModel<Usuario> {

		private static Usuario[] users = new Usuario[] {
				new Usuario("Pepe", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Antonio", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Jose", "López Rodríguez", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Pepe", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Jesús", "López", 638912458, "pass", "prueba de estado",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Manuel", "López", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass","ocupado",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
				new Usuario("Miguel", "Fernández", 638912458, "pass",LocalDate.of(2004, 7, 5),"name@gmail.com"),
		};

		@Override
		public int getSize() {
			return users.length;
		}

		@Override
		public Usuario getElementAt(int index) {
			return users[index];
		}

	}
	
	public VentanaPrincipal() {
		setTitle("AppMusic");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);

		JButton botonPremium = new JButton(getIcon("src/main/resources/premium.png", 3f));
		JButton botonBuscar = new JButton(getIcon("src/main/resources/glass.png", 2.5f));
		botonPremium.setBackground(Color.WHITE);

		botonPremium.setBorder(BorderFactory.createEmptyBorder());
		botonBuscar.setBackground(Color.WHITE);
		botonPremium.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panelNorte.add(botonBuscar);
		panelNorte.add(botonPremium);


		JPanel panelChat = new JPanel();
		panelChat.setBorder(new TitledBorder(null, "mensajes con contacto1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelChat, BorderLayout.EAST);
		panelChat.setLayout(new BoxLayout(panelChat,BoxLayout.Y_AXIS)); 
		panelChat.setSize(500,600); 
		panelChat.setMinimumSize(new Dimension(500,600)); 
		panelChat.setMaximumSize(new Dimension(500,600)); 
		panelChat.setPreferredSize(new Dimension(500,600));

		BubbleText burbuja; 
		burbuja=new BubbleText(panelChat,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT); 

		BubbleText burbuja2; 
		burbuja2=new BubbleText(panelChat, "Hola, ¿Está seguro de que la burbuja usa varias lineas si es necesario?", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED); 		

		BubbleText burbuja3=new BubbleText(panelChat, 4, Color.GREEN, "J.Ramón", BubbleText.SENT, 12); 
		panelChat.add(burbuja);

		TextField mensaje = new TextField();
		JButton botonSend = new JButton(getIcon("src/main/resources/send.png", 2f));
		botonSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonSend.setBackground(Color.WHITE);


		panelChat.setLayout(new BorderLayout());

		JPanel burbujasPanel = new JPanel();
		burbujasPanel.setLayout(new BoxLayout(burbujasPanel, BoxLayout.Y_AXIS));
		burbujasPanel.add(burbuja);
		burbujasPanel.add(burbuja2);
		burbujasPanel.add(burbuja3);

		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.add(mensaje, BorderLayout.CENTER);
		messagePanel.add(botonSend, BorderLayout.EAST);

		panelChat.add(burbujasPanel, BorderLayout.CENTER);
		panelChat.add(messagePanel, BorderLayout.SOUTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(null, "Mis chats", TitledBorder.LEADING, TitledBorder.TOP));
		contentPane.add(panelCentro, BorderLayout.CENTER);

		JList<Usuario> lista = new JList<>(new UserListModel());
		lista.setCellRenderer(new UserCellRenderer());

		JScrollPane scrollPane = new JScrollPane(lista);
		scrollPane.setSize(380,490); 
		scrollPane.setMinimumSize(new Dimension(380,490)); 
		scrollPane.setMaximumSize(new Dimension(380,490)); 
		scrollPane.setPreferredSize(new Dimension(380,490));
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCentro.add(scrollPane);
	}

	private ImageIcon getIcon(String imageUrl, float factor) {
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
