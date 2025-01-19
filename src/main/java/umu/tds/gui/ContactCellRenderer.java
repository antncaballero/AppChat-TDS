package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import umu.tds.dominio.Contacto;
import umu.tds.utils.Utils;

@SuppressWarnings("serial")
/**
 * Clase que define el renderizado de las celdas de la lista de contactos
 */
public class ContactCellRenderer extends JPanel implements ListCellRenderer<Contacto> {
	
	private JLabel nameLabel;
	private JLabel imageLabel;
	private JLabel estadoLabel;
	
	public ContactCellRenderer() {

		setLayout(new BorderLayout(0, 0));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		estadoLabel = new JLabel();
		estadoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		imageLabel = new JLabel();

		JPanel panelCentro = new JPanel();
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.add(nameLabel);		
		panelCentro.add(estadoLabel);
		panelCentro.setBackground(null);	

		JPanel panelOeste = new JPanel();
		panelOeste.add(imageLabel);
		panelOeste.add(Box.createHorizontalStrut(5));
		panelOeste.setBackground(null);

		add(panelOeste, BorderLayout.WEST);
		add(panelCentro, BorderLayout.CENTER);
		add(Box.createHorizontalStrut(5), BorderLayout.EAST);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		nameLabel.setText(contacto.getNombre());
		estadoLabel.setText(contacto.getEstado().length() < 45 ? contacto.getEstado() : contacto.getEstado().substring(0, 44)+"...");
		imageLabel.setIcon(Utils.imageToImageIcon(contacto.getFoto(), 50, 50));
		
		if (isSelected) setBackground(Color.lightGray);
		else setBackground(list.getBackground());
		
		setForeground(Color.BLACK);
		
		return this;
	}

}
