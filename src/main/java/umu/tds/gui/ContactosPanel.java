package umu.tds.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import umu.tds.dominio.Contacto;

@SuppressWarnings("serial")
/**
 * Panel que muestra una tabla con los contactos
 */
public class ContactosPanel extends JPanel {

	private JTable table;

	public ContactosPanel(List<Contacto> contactos) {
		setLayout(new BorderLayout());

		table = new JTable(new ContactosTableModel(contactos));
		table.setRowHeight(30);
		ContactTableCellRenderer renderer = new ContactTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	public JTable getTable() {
		return table;
	}
}