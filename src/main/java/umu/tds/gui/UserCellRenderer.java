package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;

public class UserCellRenderer extends JPanel implements ListCellRenderer<Usuario>{

	private JLabel nameLabel;
	private JLabel imageLabel;
	private JLabel estadoLabel;

	public UserCellRenderer() {
		
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
	public Component getListCellRendererComponent(JList<? extends Usuario> list, Usuario user, int index,
			boolean isSelected, boolean cellHasFocus) {
				
		nameLabel.setText(user.getNombre() + " "+ user.getApellidos());
		estadoLabel.setText(user.getEstado());
		imageLabel.setIcon(Utils.imageToImageIcon(user.getFotoPerfil(), 40, 40));
		
		if (isSelected) setBackground(Color.lightGray);
		else setBackground(list.getBackground());
		
		setForeground(Color.BLACK);
		
		return this;
	}

}
