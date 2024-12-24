package umu.tds.gui;

import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.dominio.Descuento;
import umu.tds.dominio.Usuario;

public class JDialogDescuentos extends JDialog {

    private JComboBox<String> comboDescuentos;
    private boolean confirmed = false;

    public JDialogDescuentos(Frame owner, List<Descuento> descuentos) {
        super(owner, "Hacer Premium", true);

        // Crear el panel de contenido
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Selecciona un descuento:"));

        // ComboBox para descuentos
        comboDescuentos = new JComboBox<>();
        for (Descuento descuento : descuentos) {
            comboDescuentos.addItem(descuento.toString());
        }
        
        panel.add(comboDescuentos);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Precio para ser Premium: $100"));
        panel.add(Box.createVerticalStrut(10));
        
        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        panel.add(btnPagar);

   
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getDescuentoSeleccionado() {
        return (String) comboDescuentos.getSelectedItem();
    }

}
