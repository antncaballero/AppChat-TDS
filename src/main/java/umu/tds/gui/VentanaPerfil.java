
package umu.tds.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.utils.Utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

@SuppressWarnings("serial")
/**
 * Ventana que permite editar el perfil de un usuario
 */
public class VentanaPerfil extends JFrame {

	private ControladorAppChat controlador = ControladorAppChat.getInstancia();

    public VentanaPerfil() {
        // Configuración de la ventana
        setTitle("Perfil de Usuario");
        setBounds(100, 100, 600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel Norte
        JPanel panelNorte = new JPanel(new FlowLayout());
        JLabel lblPerfil = new JLabel("Perfil");
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
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Cambio Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(5, 5, 5, 5);
        gbc1.fill = GridBagConstraints.BOTH;

        // JTextArea dentro de JScrollPane
        JTextArea textArea = new JTextArea(3, 20);
        textArea.setText(controlador.getUsuarioActual().getEstado());
        JScrollPane scrollPane = new JScrollPane(textArea);
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridheight = 2;
        panelCentro.add(scrollPane, gbc1);

        
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
        ImageIcon imageIconIni = new ImageIcon(controlador.getUsuarioActual().getFotoPerfil().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
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
		
		//Añadimos los paneles al panel central
        GridBagConstraints gbcBotones = new GridBagConstraints();
        gbcBotones.gridx = 1;
        gbcBotones.gridy = 0;
        gbcBotones.gridheight = 1;
        panelCentro.add(panelBotones, gbcBotones);
        
        GridBagConstraints gbcImagenes = new GridBagConstraints();
        gbcImagenes.gridx = 1;
        gbcImagenes.gridy = 1;
        panelCentro.add(panelArrastrar, gbcImagenes);

		btnVolver.addActionListener(e -> {
			new VentanaPrincipal().setVisible(true);
			this.dispose();
		});
		
		btnAplicar.addActionListener(e -> {
			
			String estado = textArea.getText() == null ? "" : textArea.getText();
			String fotoCodificada = null;
			 
			if (imageLabel.getIcon() == null) fotoCodificada = Utils.convertImageToBase64(new File("src/main/resources/user.png"));
			else if (((ImageIcon) imageLabel.getIcon()).getDescription() != null) fotoCodificada = Utils.convertImageToBase64(new File(((ImageIcon) imageLabel.getIcon()).getDescription()));
			else fotoCodificada = controlador.getUsuarioActual().getFotoPerfilCodificada();;
				
			controlador.cambiarEstadoPerfil(estado);
			controlador.cambiarFotoPerfil(fotoCodificada);
		});
		
        getContentPane().add(panelCentro, BorderLayout.CENTER);
    }
}
