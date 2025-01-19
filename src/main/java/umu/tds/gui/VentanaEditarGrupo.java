
package umu.tds.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.utils.Utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
/**
 * Ventana que permite editar un grupo
 */
public class VentanaEditarGrupo extends JFrame {

	private ControladorAppChat controlador = ControladorAppChat.getInstancia();

	public VentanaEditarGrupo(Grupo grupo) {
		// Configuración de la ventana
		setTitle("Datos de Grupo");
		setBounds(100, 100, 1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		// Panel Norte
		JPanel panelNorte = new JPanel(new FlowLayout());
		JLabel lblPerfil = new JLabel("Grupo");
		panelNorte.add(lblPerfil);
		getContentPane().add(panelNorte, BorderLayout.NORTH);

		// Panel Este
		JPanel panelEste = new JPanel(new FlowLayout());
		panelEste.add(Box.createRigidArea(new Dimension(50, 40)));
		getContentPane().add(panelEste, BorderLayout.EAST);

		// Panel Oeste
		JPanel panelOeste = new JPanel(new FlowLayout());
		panelOeste.add(Box.createRigidArea(new Dimension(50, 40)));
		getContentPane().add(panelOeste, BorderLayout.WEST);

		// Panel Sur
		JPanel panelSur = new JPanel(new FlowLayout());
		panelSur.add(Box.createRigidArea(new Dimension(20, 50)));
		JButton btnVolver = new JButton("Volver");
		panelSur.add(btnVolver);
		JButton btnAplicar = new JButton("Aplicar");
		panelSur.add(btnAplicar);
		getContentPane().add(panelSur, BorderLayout.SOUTH);

		// Panel Centro
		JPanel panelCentro = new JPanel();
		JPanel panelEdicion = new JPanel(new GridBagLayout());
		JPanel panelListas = new JPanel(new BorderLayout());
		panelCentro.add(panelEdicion);
		panelCentro.add(panelListas);

		//Panel Edición
		panelEdicion.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Cambio Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 5, 0, 5);
		gbc1.fill = GridBagConstraints.BOTH;

		//Campo de nombre de contacto
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));	
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		panelEdicion.add(lblNombre, gbc1);
		JTextField txtNombre = new JTextField();
		txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtNombre.setColumns(15);
		txtNombre.setText(grupo.getNombre());
		txtNombre.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				txtNombre.setBorder(new LineBorder(Color.BLACK, 2));
			}
			public void focusLost(FocusEvent evt) {
				txtNombre.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		panelEdicion.add(txtNombre, gbc1);
		// JTextArea dentro de JScrollPane
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		panelEdicion.add(lblEstado, gbc1);
		JTextArea textArea = new JTextArea(3, 20);
		textArea.setText(grupo.getEstado());
		JScrollPane scrollPane = new JScrollPane(textArea);
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.gridheight = 2;
		panelEdicion.add(scrollPane, gbc1);


		// JLabel con imagen
		JLabel imageLabel = new JLabel();
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

		//Inicializamos con la foto de perfin del usuario		                		                                   	
		ImageIcon imageIconIni = new ImageIcon(grupo.getFoto().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
		imageLabel.setIcon(imageIconIni);
		editorPane.setVisible(false);
		imageLabel.setVisible(true);

		JPanel panelArrastrar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panelArrastrar.add(imageLabel);
		panelArrastrar.add(editorPane);

		JPanel panelBotones = new JPanel();
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

		//Añadimos los paneles al panel central
		GridBagConstraints gbcBotones = new GridBagConstraints();
		gbcBotones.gridx = 1;
		gbcBotones.gridy = 1;
		gbcBotones.gridheight = 1;
		panelEdicion.add(panelBotones, gbcBotones);

		GridBagConstraints gbcImagenes = new GridBagConstraints();
		gbcImagenes.gridx = 1;
		gbcImagenes.gridy = 2;
		panelEdicion.add(panelArrastrar, gbcImagenes);


		//Panel listas
		panelListas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Cambio en participantes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panelListasEste = new JPanel();
		JPanel panelListasOeste = new JPanel();
		JPanel panelListasCentro = new JPanel();
		panelListasCentro.setLayout(new BoxLayout(panelListasCentro, BoxLayout.Y_AXIS));
		panelListas.add(panelListasEste, BorderLayout.EAST);
		panelListas.add(panelListasOeste, BorderLayout.WEST);
		panelListas.add(panelListasCentro, BorderLayout.CENTER);

		DefaultListModel<ContactoIndividual> modelNotAdded = new DefaultListModel<>();
		DefaultListModel<ContactoIndividual> modelAdded = new DefaultListModel<>();

		grupo.getParticipantes().forEach(contacto -> modelAdded.addElement(contacto));
		
		controlador.getUsuarioActual().getContactos().stream()
			.filter(c -> c instanceof ContactoIndividual)
			.map(c -> (ContactoIndividual) c)
			.filter(contacto -> !grupo.getParticipantes().contains(contacto))
			.forEach(contacto -> modelNotAdded.addElement(contacto));

		JList<ContactoIndividual> listaContactosNotAdded = new JList<>(modelNotAdded);
		listaContactosNotAdded.setCellRenderer(new ContactCellRenderer());

		JList<ContactoIndividual> listaContactosAdded = new JList<>(modelAdded);
		listaContactosAdded.setCellRenderer(new ContactCellRenderer());

		JScrollPane scrollContactos = new JScrollPane(listaContactosNotAdded);
		scrollContactos.setPreferredSize(new Dimension(360,280));

		JScrollPane scrollContactosAdded = new JScrollPane(listaContactosAdded);
		scrollContactosAdded.setPreferredSize(new Dimension(360,280));	

		scrollContactos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollContactosAdded.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panelListasOeste.add(scrollContactos);
		panelListasEste.add(scrollContactosAdded);

		JButton btnRemove = new JButton(Utils.getScaledIcon("src/main/resources/person-remove.png", 40, 40));
		JButton btnAdd = new JButton(Utils.getScaledIcon("src/main/resources/person-add.png", 40, 40));
		panelListasCentro.add(Box.createVerticalGlue());
		panelListasCentro.add(btnRemove);
		panelListasCentro.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre los botones
		panelListasCentro.add(btnAdd);
		panelListasCentro.add(Box.createVerticalGlue());

		//Eventos botones
		btnVolver.addActionListener(e -> {
			new VentanaPrincipal().setVisible(true);
			this.dispose();
		});

		btnAplicar.addActionListener(e -> {

			String estado = textArea.getText() == null ? "" : textArea.getText();
			String nombre = txtNombre.getText();
			String fotoCodificada = null;

			if (imageLabel.getIcon() == null) fotoCodificada = Utils.convertImageToBase64(new File("src/main/resources/group.png"));
			else if (((ImageIcon) imageLabel.getIcon()).getDescription() != null) fotoCodificada = Utils.convertImageToBase64(new File(((ImageIcon) imageLabel.getIcon()).getDescription()));
			else fotoCodificada = grupo.getFotoGrupoCodificada();
			
			LinkedList<ContactoIndividual> nuevosParticipantes = new LinkedList<>();
			modelAdded.elements().asIterator().forEachRemaining(nuevosParticipantes::add);			
		    
			controlador.editarGrupo(nombre, nuevosParticipantes, fotoCodificada, estado, grupo);
			
			JOptionPane.showMessageDialog(this, "El grupo ha sido editado correctamente, pulsa en 'Volver' para regresar a la ventana principal ", "OK", JOptionPane.INFORMATION_MESSAGE);
		});
		
		btnAdd.addActionListener(e -> {
			ContactoIndividual contacto = listaContactosNotAdded.getSelectedValue();
			if (contacto != null) {
				modelNotAdded.removeElement(contacto);
				modelAdded.addElement(contacto);
			}
		});
		
		btnRemove.addActionListener(e -> {
			ContactoIndividual contacto = listaContactosAdded.getSelectedValue();
			if (contacto != null) {
				modelAdded.removeElement(contacto);
				modelNotAdded.addElement(contacto);
			}
		});
		
		btnSeleccionar.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null) {
					ImageIcon imageIcon = Utils.getScaledIcon(selectedFile.getPath(), 80, 80);
					imageIcon.setDescription(selectedFile.getPath());
					imageLabel.setIcon(imageIcon);
					editorPane.setVisible(false);
					imageLabel.setVisible(true);
				}
			}
		});

		getContentPane().add(panelCentro, BorderLayout.CENTER);
	}
}
