package umu.tds.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import tds.BubbleText;
import umu.tds.controlador.ControladorAppChat;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;
/**
 * Panel que muestra el chat con un contacto
 */
public class ChatPanel extends JPanel implements Scrollable {

	private static final long serialVersionUID = 1L;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	/**
	 * Create the panel.
	 */
	public ChatPanel() {	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setSize(500,600);
		setMinimumSize(new Dimension(500,600));
		add(Box.createRigidArea(new Dimension(500, 100)));		
		JLabel lblInicio = new JLabel("¡Selecciona un contacto para empezar a chatear!");
		lblInicio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblInicio.setAlignmentX(CENTER_ALIGNMENT);
		add(lblInicio);		
	}
	/**
	 * Muestra el chat con un contacto
	 * @param contacto
	 */
	public void mostrarChat(Contacto contacto) {
		removeAll();
		add(Box.createHorizontalStrut(500));
		MensajesToBubbleText(contacto).forEach(this::add);
		revalidate();
		repaint();
	}
	/**
	 * Envía un mensaje al contacto en la UI
	 * @param mensaje
	 */
	public void enviarMensaje(String mensaje) {
		String fecha = LocalDateTime.now().format(formatter);
		BubbleText burbuja = new BubbleText(this, mensaje, Color.GREEN, "You - " + fecha, BubbleText.SENT, 12);
		add(burbuja);
	}
	/**
	 * Envía un emoticono al contacto en la UI
	 * @param emoticono
	 */
	public void enviarEmoticono(int emoticono) {
		String fecha = LocalDateTime.now().format(formatter);
		BubbleText burbuja = new BubbleText(this, emoticono, Color.GREEN, "You - " + fecha, BubbleText.SENT, 12);
		add(burbuja);
	}
	
	/**
	 * Convierte los mensajes de un contacto en burbujas de texto
	 * @param contacto
	 * @return lista de burbujas de texto
	 */
	private List<BubbleText> MensajesToBubbleText(Contacto contacto) {		
		Usuario actual = ControladorAppChat.getInstancia().getUsuarioActual();
		List<Mensaje> mensajes = contacto.getTodosLosMensajes(actual);		
		return mensajes.stream()
				.map(m -> { 					
					String fecha = " - " + m.getHora().format(formatter);
					if (isEmoticono(m)) {						
						return m.getEmisor().equals(actual) 
								? new BubbleText(this, m.getEmoticono(), Color.GREEN, actual.getNombre() + fecha, BubbleText.SENT, 12) 
								: new BubbleText(this, m.getEmoticono(), Color.GREEN, contacto.getNombre() + fecha, BubbleText.RECEIVED, 12);					
					} else {					
						return m.getEmisor().equals(actual)
								? new BubbleText(this, m.getTexto(), Color.GREEN, actual.getNombre() + fecha, BubbleText.SENT, 12)
								: new BubbleText(this, m.getTexto(), Color.GREEN, contacto.getNombre() + fecha,BubbleText.RECEIVED, 12);
					}
				})
				.collect(Collectors.toList());	
	}
	/**
	 * Comprueba si un mensaje es un emoticono
	 * @param mensaje
	 * @return true si es un emoticono, false en caso contrario
	 */
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
