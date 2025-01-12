package umu.tds.lanzador;

import java.awt.EventQueue;

import umu.tds.gui.VentanaLogin;

/**
 * Lanzador de la aplicación.
 * 
 */
public class Lanzador {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
