package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import umu.tds.dominio.Descuento;
import java.awt.Font;
import java.awt.Component;

@SuppressWarnings("serial")
/**
 * Clase que define el JDialog para seleccionar un descuento
 */
public class JDialogDescuentos extends JDialog {
	
	private static final double PRECIO_PREMIUM = 100;
	
    private JComboBox<Descuento> comboDescuentos;
    private boolean confirmed = false;

    public JDialogDescuentos(Frame owner, List<Descuento> descuentos) {
        super(owner, "Convertirse en premium", true);      
   
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        comboDescuentos= new JComboBox<Descuento>();
        comboDescuentos.setSelectedIndex(-1);
        comboDescuentos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        for (Descuento descuento : descuentos) {
            comboDescuentos.addItem(descuento);
        }
       
        JPanel panelCentro = new JPanel();        
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));        
        
        JLabel lblSelecciona = new JLabel("Selecciona uno de tus descuentos:");
        lblSelecciona.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSelecciona.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(lblSelecciona);
        panelCentro.add(comboDescuentos);
        panelCentro.add(Box.createVerticalStrut(20));
        
        JLabel lblPrecio = new JLabel("Precio para ser Premium: 100€");
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panelCentro.add(lblPrecio);
        panelCentro.add(Box.createVerticalStrut(20));
        
        
        comboDescuentos.addActionListener(e -> {
			Descuento descuento = (Descuento) comboDescuentos.getSelectedItem();
			double precioFinal = descuento.calcularPrecioFinal(PRECIO_PREMIUM);
			lblPrecio.setText("Precio para ser Premium: " + precioFinal + "€");
		
		});
               
        JPanel panelSur = new JPanel(); 
        JButton btnPagar = new JButton("Pagar");
        btnPagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPagar.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
            JOptionPane.showMessageDialog(owner,
                    "¡Felicidades! Ahora eres usuario premium.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });      
        panelSur.add(btnPagar);
        
        panel.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);
        panel.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
        panel.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
        btnPagar.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        
   
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(owner);
    }

	/**
	 * Devuelve si se ha confirmado la compra
	 * @return true si se ha confirmado la compra
	 */
    public boolean isConfirmed() {
        return confirmed;
    }
	/**
	 * Devuelve el descuento seleccionado
	 * @return descuento seleccionado
	 */
    public String getDescuentoSeleccionado() {
        return (String) comboDescuentos.getSelectedItem();
    }

}
