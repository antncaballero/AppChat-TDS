
package umu.tds.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;

@SuppressWarnings("serial")
/**
 * Clase que define el renderizado de las celdas de la tabla de contactos
 */
public class ContactTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value instanceof Contacto) {
			Contacto contacto = (Contacto) value;
			switch (column) {
			case 0:
				setText(contacto.getNombre());
				break;
			case 1:
				setText(contacto instanceof ContactoIndividual ? String.valueOf(((ContactoIndividual) contacto).getNumTlf()) : "");
				break;
			case 2:
				setText(contacto.getEstado());
				break;
			}
		}
		cellComponent.setFont(new Font("Segoe UI", Font.BOLD, 14));
		setHorizontalAlignment(SwingConstants.CENTER);

		if (isSelected) {
			cellComponent.setBackground(new Color(184, 207, 229));
			cellComponent.setForeground(Color.BLACK);
		} else {
			cellComponent.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
			cellComponent.setForeground(Color.DARK_GRAY);
		}		
		return cellComponent;
	}
}
