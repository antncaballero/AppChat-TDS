package umu.tds.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import tds.BubbleText;

public class ChatPanel extends JPanel implements Scrollable {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setSize(500,600); 
		
        //EJEMPLO DE MENSAJES
		BubbleText burbuja=new BubbleText(this,"Hola grupo!!", Color.GREEN, "YOU", BubbleText.SENT); 
		BubbleText burbuja2=new BubbleText(this, "Hola, ¿Está seguro de que la burbuja usa varias lineas si es necesario?", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED); 		
		BubbleText burbuja3=new BubbleText(this, 4, Color.GREEN, "YOU", BubbleText.SENT, 12);
		BubbleText burbuja4=new BubbleText(this, "prueba chat", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
		BubbleText burbuja5=new BubbleText(this, "prueba chat", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
		BubbleText burbuja6=new BubbleText(this, "prueba chat", Color.GREEN, "YOU", BubbleText.SENT);
		BubbleText burbuja7=new BubbleText(this, "prueba chat", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
		BubbleText burbuja8=new BubbleText(this, "prueba chat", Color.GREEN, "YOU", BubbleText.SENT);
		BubbleText burbuja9=new BubbleText(this, "prueba chat", Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
		
		add(burbuja);
		add(burbuja2);
		add(burbuja3);
		add(burbuja4);
		add(burbuja5);
		add(burbuja6);
		add(burbuja7);
		add(burbuja8);
		add(burbuja9);
		
		
	}

	
	
	public void enviarMensaje(String mensaje) {
		BubbleText burbuja = new BubbleText(this, mensaje, Color.GREEN, "You", BubbleText.SENT);
		add(burbuja);
	}



	@Override
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return getPreferredSize();
	}



	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 16;
	}



	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		// TODO Auto-generated method stub
		return 16;
	}



	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
