
package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;

public class VentanaContactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ControladorAppChat controlador = ControladorAppChat.getInstancia();
	private ContactosPanel contactosPanel;
	private VentanaPrincipal parent;
	

	/**
	 * Create the frame.
	 */
	public VentanaContactos(VentanaPrincipal parent) {
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(10, 10));
		setContentPane(contentPane);

		// Obtener los contactos del usuario actual
		List<Contacto> contactosUsuario = controlador.getContactosUsuarioActual();
		// TODO List<Contacto> contactosUsuario = VentanaPrincipal.ContactListModel.getContactos();

		// Crear el panel de contactos y añadirlo al contentPane
		contactosPanel = new ContactosPanel(contactosUsuario);
		contentPane.add(contactosPanel, BorderLayout.CENTER);

		// Aplicar el renderer personalizado
		ContactTableCellRenderer renderer = new ContactTableCellRenderer();
		for (int i = 0; i < contactosPanel.getTable().getColumnCount(); i++) {
			contactosPanel.getTable().getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		// Añadir el panel sur con sus botones
		contentPane.add(crearPanelSur(), BorderLayout.SOUTH);
	}
	
	
	
	public JPanel crearPanelSur() {
		JPanel panelSur = new JPanel();
		panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> {
			new VentanaPrincipal().setVisible(true);
			dispose();
		});
		
		JButton btnChat = new JButton("Ir al chat");
		btnChat.addActionListener(e -> {
			
			if (contactosPanel.getTable().getSelectedRow() == -1) return;
			
			JTable t = contactosPanel.getTable();
			Contacto contactoSeleccionado = controlador.buscarContactoDeUsuario(t.getValueAt(t.getSelectedRow(), 0).toString());
			parent.loadChat(contactoSeleccionado);
			parent.setVisible(true);
			dispose();
			
		});
		
		panelSur.add(btnCerrar);
		panelSur.add(btnChat);
		
		btnCerrar.setFont(new Font("Segoe UI", 1, 14));
		btnChat.setFont(new Font("Segoe UI", 1, 14));
		
		return panelSur;
	}
	
	
}
