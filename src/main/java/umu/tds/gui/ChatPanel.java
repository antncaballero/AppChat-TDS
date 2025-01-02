package umu.tds.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import tds.BubbleText;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;

public class ChatPanel extends JPanel implements Scrollable {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setSize(500,600);
		setMinimumSize(new Dimension(500,600));
		add(Box.createHorizontalStrut(500));
	}

	
	public void mostrarChat(Contacto contacto) {
		removeAll();
		add(Box.createHorizontalStrut(500));
		MensajesToBubbleText(contacto).forEach(this::add);
		revalidate();
		repaint();
	}
	
	public void enviarMensaje(String mensaje) {
		BubbleText burbuja = new BubbleText(this, mensaje, Color.GREEN, "You", BubbleText.SENT, 12);
		add(burbuja);
	}
	
	public void enviarEmoticono(int emoticono) {
		BubbleText burbuja = new BubbleText(this, emoticono, Color.GREEN, "You", BubbleText.SENT, 12);
		add(burbuja);
	}
	
	
	private List<BubbleText> MensajesToBubbleText(Contacto contacto) {		
		Usuario actual = ControladorAppChat.getInstancia().getUsuarioActual();
		List<Mensaje> mensajes = contacto.getTodosLosMensajes(actual);
		
		return mensajes.stream()
				.map(m -> { 					
					if (isEmoticono(m)) {						
						return m.getEmisor().equals(actual) 
								? new BubbleText(this, m.getEmoticono(), Color.GREEN, actual.getNombre(), BubbleText.SENT, 12) 
								: new BubbleText(this, m.getEmoticono(), Color.GREEN, contacto.getNombre(), BubbleText.RECEIVED, 12);					
					} else {					
						return m.getEmisor().equals(actual)
								? new BubbleText(this, m.getTexto(), Color.GREEN, actual.getNombre(), BubbleText.SENT, 12)
								: new BubbleText(this, m.getTexto(), Color.GREEN, contacto.getNombre(),BubbleText.RECEIVED, 12);
					}
				})
				.collect(Collectors.toList());	
	}
	
	private boolean isEmoticono(Mensaje mensaje) {
		return mensaje.getTexto().equals("");
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
