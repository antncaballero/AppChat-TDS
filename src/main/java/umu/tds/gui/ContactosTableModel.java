
package umu.tds.gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
/**
 * Modelo de tabla para los contactos
 */
public class ContactosTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final List<Contacto> contactos;
	private final String[] columnNames = {"Nombre", "Teléfono", "Estado"};

	public ContactosTableModel(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	@Override
	public int getRowCount() {
		return contactos.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Contacto contacto = contactos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return contacto.getNombre();
		case 1:
			return contacto instanceof ContactoIndividual 
					? String.valueOf(((ContactoIndividual) contacto).getNumTlf()) 
					: "--";
		case 2:
			return contacto.getEstado();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}

