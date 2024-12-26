
package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;

public class VentanaContactos extends JFrame {

 private static final long serialVersionUID = 1L;
 private JPanel contentPane;
 ControladorAppChat controlador = ControladorAppChat.getInstancia();

 /**
  * Launch the application.
  */
 public static void main(String[] args) {
  EventQueue.invokeLater(new Runnable() {
   public void run() {
    try {
     VentanaContactos frame = new VentanaContactos();
     frame.setVisible(true);
    } catch (Exception e) {
     e.printStackTrace();
    }
   }
  });
 }

 /**
  * Create the frame.
  */
 public VentanaContactos() {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBounds(100, 100, 650, 500);
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  contentPane.setLayout(new BorderLayout(10, 10));
  setContentPane(contentPane);

  // Obtener los contactos del usuario actual
  // TODO List<Contacto> contactosUsuario = controlador.getContactosUsuarioActual();
  List<Contacto> contactosUsuario = VentanaPrincipal.ContactListModel.getContactos();

  // Crear el panel de contactos y añadirlo al contentPane
  ContactosPanel contactosPanel = new ContactosPanel(contactosUsuario);
  contentPane.add(contactosPanel, BorderLayout.CENTER);

  // Aplicar el renderer personalizado
  ContactTableCellRenderer renderer = new ContactTableCellRenderer();
  for (int i = 0; i < contactosPanel.getTable().getColumnCount(); i++) {
   contactosPanel.getTable().getColumnModel().getColumn(i).setCellRenderer(renderer);
  }

  JButton btnCerrar = new JButton("Cerrar");
  btnCerrar.addActionListener(e -> {
   dispose();
  });

  contentPane.add(btnCerrar, BorderLayout.SOUTH);
 }
}
