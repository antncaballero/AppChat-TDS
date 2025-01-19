package umu.tds.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import umu.tds.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.ContactoIndividual;

@SuppressWarnings("serial")
/**
 * Ventana que permite crear un grupo
 */
public class VentanaGrupo extends JFrame {

	private JPanel contentPane;
	private JTextField nombregrupo;
	private DefaultListModel<ContactoIndividual> modelAdded;
	private JLabel imageLabel;
	private JTextArea areaEstado;

	/**
	 * Create the frame.
	 */
	public VentanaGrupo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Crear grupo");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(100, 100, 1000, 620);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOeste = new JPanel();
		contentPane.add(panelOeste, BorderLayout.WEST);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		
		JPanel panelEste = new JPanel();
		contentPane.add(panelEste, BorderLayout.EAST);
		
		JPanel panelNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnAceptar = new JButton("Aceptar");
		JButton btnVolver = new JButton("Volver");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnAceptar);
		panelSur.add(btnVolver);
		
		JLabel user = new JLabel(ControladorAppChat.getInstancia().getUsuarioActual().getNombre() + " ");
		user.setFont(new Font("Segoe UI", Font.BOLD, 15));
		user.setIcon(Utils.imageToImageIcon(ControladorAppChat.getInstancia().getUsuarioActual().getFotoPerfil(), 40, 40));
		user.setBorder(new LineBorder(Color.BLACK, 1));
		
		panelNorte.add(user);
		
		
		modelAdded = new DefaultListModel<>();
		DefaultListModel<ContactoIndividual> modelNotAdded = new DefaultListModel<>();
		
		
		ControladorAppChat.getInstancia().getUsuarioActual().getContactos().forEach(c -> {
			if (c instanceof ContactoIndividual) {
				modelNotAdded.addElement((ContactoIndividual) c);
			}
		});
			
		
		JList<ContactoIndividual> listaContactosNotAdded = new JList<>(modelNotAdded);
		listaContactosNotAdded.setCellRenderer(new ContactCellRenderer());
		
		JList<ContactoIndividual> listaContactosAdded = new JList<>(modelAdded);
		listaContactosAdded.setCellRenderer(new ContactCellRenderer());
				
		JScrollPane scrollContactos = new JScrollPane(listaContactosNotAdded);
		scrollContactos.setPreferredSize(new Dimension(360,450));
		
		JScrollPane scrollContactosAdded = new JScrollPane(listaContactosAdded);
		scrollContactosAdded.setPreferredSize(new Dimension(360,450));	
		
		scrollContactos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollContactosAdded.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelOeste.add(scrollContactos);
		panelOeste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Contactos", TitledBorder.LEADING, TitledBorder.TOP));
		panelEste.add(scrollContactosAdded);
		panelEste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Contactos añadidos", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagLayout gbl_panelCentro = new GridBagLayout();
        gbl_panelCentro.columnWidths = new int[]{0};
        gbl_panelCentro.rowHeights = new int[]{1, 1, 1, 1, 1, 1, 1};
        gbl_panelCentro.columnWeights = new double[]{0.0};
        gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panelCentro.setLayout(gbl_panelCentro);
        
        //Etiqueta y campo de nombre
        JLabel labelNombregrupo = new JLabel("Nombre del grupo:");
		labelNombregrupo.setFont(new Font("Segoe UI", Font.BOLD, 15));
		GridBagConstraints gbcLabelNombreGrupo = new GridBagConstraints();
		gbcLabelNombreGrupo.insets = new Insets(5, 5, 5, 0);
		gbcLabelNombreGrupo.gridx = 0;
		gbcLabelNombreGrupo.gridy = 0;
		panelCentro.add(labelNombregrupo, gbcLabelNombreGrupo);
        
        nombregrupo = new JTextField();
        nombregrupo.setColumns(8);
        nombregrupo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		nombregrupo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				nombregrupo.setBorder(new LineBorder(Color.BLACK, 2));
			}

			public void focusLost(FocusEvent evt) {
				nombregrupo.setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
		nombregrupo.addActionListener(e -> {
			String nombreGrupo = nombregrupo.getText() != null ? nombregrupo.getText() : "";
			String fotoGrupoCodificada = imageLabel.getIcon() != null 
					? Utils.convertImageToBase64(new File(((ImageIcon) imageLabel.getIcon()).getDescription())) 
					: Utils.convertImageToBase64(new File("src/main/resources/group.png"));
			String estado = areaEstado.getText() != null ? areaEstado.getText() : "";
			List<ContactoIndividual> contactos = new LinkedList<>();
			modelAdded.elements().asIterator().forEachRemaining(contactos::add);			
			crearGrupo(nombreGrupo, fotoGrupoCodificada, contactos, estado);
        });
        GridBagConstraints gbc_nombregrupo = new GridBagConstraints();
        gbc_nombregrupo.insets = new Insets(5, 5, 10, 0);
        gbc_nombregrupo.gridx = 0;
        gbc_nombregrupo.gridy = 1;
        panelCentro.add(nombregrupo, gbc_nombregrupo);   
        
        //Etiqueta y campo de descripción
        JLabel labelEstado = new JLabel("Descripción:");
        labelEstado.setFont(new Font("Segoe UI", Font.BOLD, 15));
		GridBagConstraints gbcLabelEstado = new GridBagConstraints();
		gbcLabelEstado.insets = new Insets(5, 5, 5, 0);
		gbcLabelEstado.gridx = 0;
		gbcLabelEstado.gridy = 2;
		panelCentro.add(labelEstado, gbcLabelEstado);
        
		areaEstado = new JTextArea(3, 15);
		GridBagConstraints gbcAreaEstado = new GridBagConstraints();
		gbcAreaEstado.insets = new Insets(5, 5, 5, 5);
		gbcAreaEstado.gridx = 0;
		gbcAreaEstado.gridy = 3;
		panelCentro.add(new JScrollPane(areaEstado), gbcAreaEstado);
		
        
        //Etiqueta de imagen
        JLabel labelImagen = new JLabel("Imagen:");
		labelImagen.setFont(new Font("Segoe UI", Font.BOLD, 15));
		GridBagConstraints gbcLabelImagen = new GridBagConstraints();
		gbcLabelImagen.insets = new Insets(5, 5, 5, 0);
		gbcLabelImagen.gridx = 0;
		gbcLabelImagen.gridy = 4;
		panelCentro.add(labelImagen, gbcLabelImagen);
		
		//Botones de seleccionar y eliminar
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panelBotones.add(btnSeleccionar);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
		panelBotones.add(btnEliminar);
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 5, 0);
		gbc_panelBotones.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotones.gridx = 0;
		gbc_panelBotones.gridy = 5;
		panelCentro.add(panelBotones, gbc_panelBotones);
        
		//EditorPane para arrastrar imagen
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
		gbcEditorPane.insets = new Insets(5, 5, 10, 0);
		gbcEditorPane.gridx = 0;
		gbcEditorPane.gridy = 6;
		panelCentro.add(panelArrastrar, gbcEditorPane);
		
        //Botones de añadir y eliminar contactos
        JButton btnAdd = new JButton(Utils.getScaledIcon("src/main/resources/person-add.png", 40, 40));
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.setBackground(Color.WHITE);
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
        gbc_btnAdd.gridx = 0;
        gbc_btnAdd.gridy = 7;
        panelCentro.add(btnAdd, gbc_btnAdd);
               
        JButton btnRemove = new JButton(Utils.getScaledIcon("src/main/resources/person-remove.png", 40, 40));
        btnRemove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRemove.setBackground(Color.WHITE);
        GridBagConstraints gbc_btnRemove = new GridBagConstraints();
        gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
        gbc_btnRemove.gridx = 0;
        gbc_btnRemove.gridy = 8;
        panelCentro.add(btnRemove, gbc_btnRemove);
		
		btnAdd.addActionListener(e -> {
			if (!listaContactosNotAdded.isSelectionEmpty()) {
				modelAdded.addElement(listaContactosNotAdded.getSelectedValue());
				modelNotAdded.removeElement(listaContactosNotAdded.getSelectedValue());
			}
		});
		
		btnRemove.addActionListener(e -> {
			if (!listaContactosAdded.isSelectionEmpty()) {
				modelNotAdded.addElement(listaContactosAdded.getSelectedValue());
				modelAdded.removeElement(listaContactosAdded.getSelectedValue());
			}
		});
		
		//Acciones de los botones 
		
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
		
		btnVolver.addActionListener(e -> {
			VentanaPrincipal main = new VentanaPrincipal();
			main.setVisible(true);
			dispose();
		});
		
		btnAceptar.addActionListener(e -> {
			String nombreGrupo = nombregrupo.getText() != null ? nombregrupo.getText() : "";
			String fotoGrupoCodificada = imageLabel.getIcon() != null 
					? Utils.convertImageToBase64(new File(((ImageIcon) imageLabel.getIcon()).getDescription())) 
					: Utils.convertImageToBase64(new File("src/main/resources/group.png"));
			String estado = areaEstado.getText() != null ? areaEstado.getText() : "";
			List<ContactoIndividual> contactos = new LinkedList<>();
			modelAdded.elements().asIterator().forEachRemaining(contactos::add);			
			crearGrupo(nombreGrupo, fotoGrupoCodificada, contactos, estado);
		});
		
	}
	/**
	 * Crea un grupo con los datos introducidos
	 * @param nombre
	 * @param fotoGrupoCodificada
	 * @param lista
	 * @param estado
	 */
	private void crearGrupo(String nombre, String fotoGrupoCodificada, List<ContactoIndividual> lista, String estado) {
		if (!validarEntrada(nombre, lista).isEmpty()) return;				
		ControladorAppChat.getInstancia().crearGrupo(nombre, lista, fotoGrupoCodificada, estado);
		JOptionPane.showMessageDialog(this, "Grupo creado correctamente");
		new VentanaPrincipal().setVisible(true);
		dispose();
	}
	/**
	 * Valida los datos introducidos
	 * @param nombre
	 * @param lista
	 * @return String con los errores encontrados
	 */
	private String validarEntrada(String nombre,List<ContactoIndividual> lista) {		
		String error = "";
		if (lista.isEmpty()) error += "El grupo debe tener al menos un contacto\n";
		if (nombre.isEmpty()) error +=  "El grupo debe tener un nombre\n";
		if (!error.isEmpty()) JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
		return error;
	}

}
