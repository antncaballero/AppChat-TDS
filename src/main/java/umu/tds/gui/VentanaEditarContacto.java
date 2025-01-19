
package umu.tds.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.ContactoIndividual;

import java.awt.*;

@SuppressWarnings("serial")
/**
 * Ventana que permite editar un contacto individual
 */
public class VentanaEditarContacto extends JFrame {

	private ControladorAppChat controlador = ControladorAppChat.getInstancia();

    public VentanaEditarContacto(ContactoIndividual contacto) {
        // Configuración de la ventana
        setTitle("Datos de Contacto");
        setBounds(100, 100, 450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel Norte
        JPanel panelNorte = new JPanel(new FlowLayout());
        JLabel lblPerfil = new JLabel("Contacto");
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

        //Creamos el campo del nombre del contacto
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panelCentro.add(lblNombre, gbc1);
        JTextField txtNombre = new JTextField();
        txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNombre.setColumns(15);
        txtNombre.setText(contacto.getNombre());
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panelCentro.add(txtNombre, gbc1);
        
		btnVolver.addActionListener(e -> {
			new VentanaPrincipal().setVisible(true);
			this.dispose();
		});
		
		btnAplicar.addActionListener(e -> {
            String nombre = txtNombre.getText();
			if (!validarDatos(nombre)) return;
            controlador.cambiarNombreContacto(nombre, contacto);
			JOptionPane.showMessageDialog(null, "Contacto modificado con éxito", "Contacto modificado", JOptionPane.INFORMATION_MESSAGE);
			new VentanaPrincipal().setVisible(true);
			this.dispose();
		});
		
        getContentPane().add(panelCentro, BorderLayout.CENTER);
    }
    /**
     * Método que valida los datos introducidos
     * @param nombre
     * @return true si los datos son válidos, false en caso contrario
     */
	private static boolean validarDatos(String nombre) {
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
