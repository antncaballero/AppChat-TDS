package umu.tds.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import tds.BubbleText;
import umu.tds.dominio.Contacto;
import umu.tds.dominio.ContactoIndividual;
import umu.tds.dominio.Grupo;
import umu.tds.dominio.Mensaje;
import umu.tds.dominio.Usuario;
import umu.tds.utils.Utils;

/**
 * Servicio para generar PDFs con los mensajes de un contacto
 * 
 */
public enum PDFService {
	INSTANCE;

	/**
	 * Genera un PDF con un los mensajes con un contacto
	 * 
	 * @param directorio
	 * @param contacto
	 * @param usuario que tiene añadido al contacto
	 * @return true si se ha generado correctamente, false en caso contrario
	 */
	public boolean generatePDF(File directorio, Contacto c, Usuario usuario) {
		File file = new File(directorio, generatePDFName(c));
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
			document.open();
			document.add(generarInicio(c));
			document.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{1, 1});

			c.getTodosLosMensajes(usuario).forEach(m -> {
				PdfPCell cell;
				if (!m.getEmisor().equals(usuario)) {//Comprobamos si el mensaje es del usuario
					cell = crearCeldaIzquierda(m, regularFont, boldFont);
					table.addCell(cell);
					table.addCell(createEmptyCell());
				} else {
					table.addCell(createEmptyCell());
					cell = crearCeldaDerecha(m, regularFont, boldFont);
					table.addCell(cell);
				}
			});

			document.add(table);
		} catch (FileNotFoundException | DocumentException e) {
			System.err.println("Error al generar el PDF");
			e.printStackTrace();
			return false;
		}

		document.close();
		return true;
	}

	/**
	 * Genera el inicio del PDF
	 * 
	 * @param contacto
	 * @return parrafo inicio
	 */
	private Paragraph generarInicio(Contacto contacto) {
		Paragraph paragraph = new Paragraph();
		if(contacto instanceof ContactoIndividual) {
			ContactoIndividual contactoIndividual = (ContactoIndividual) contacto;
            paragraph.add(new Chunk("Chat con " + contactoIndividual.getNombre() 
            	+ " - " + contactoIndividual.getUsuarioAsociado().getNumTlf()));
		} else {
			Grupo grupo = (Grupo) contacto;
			paragraph.add(new Chunk("Chat con " + grupo.getNombre()));
			paragraph.add(new Chunk("\n"));
			paragraph.add(new Chunk("Estado: " + grupo.getEstado()));
			paragraph.add(new Chunk("\n"));
			paragraph.add(new Chunk("Participantes: "));
			grupo.getParticipantes().forEach(c -> paragraph.add(new Chunk(c.getNombre() + "-" + c.getUsuarioAsociado().getNumTlf() + "; ")));	
		}		
		return paragraph;
	}
	
	/**
	 * Crea una celda a la izquierda con un mensaje o un emoticono
	 * 
	 * @param mensaje m
	 * @param regularFont
	 * @param paragraph
	 * @param esIzquierda
	 * @return celda izquierda
	 */
	private PdfPCell crearCeldaIzquierda(Mensaje m, Font regularFont, Font boldFont) {
		Paragraph paragraph = new Paragraph();
		if (m.getTexto().equals("")) {
			return createCeldaEmoji(m, regularFont, paragraph, true);
		} else {
			paragraph.add(new Chunk(m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) 
					+ " - " + m.getEmisor().getNombre() + ": ", regularFont));
			paragraph.add(new Chunk(m.getTexto(), boldFont));
			PdfPCell cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(PdfPCell.NO_BORDER);
			return cell;
		}
	}

	/**
	 * Crea una celda a la derecha con un mensaje o un emoticono
	 * 
	 * @param m
	 * @param regularFont
	 * @param paragraph
	 * @param esIzquierda
	 * @return celda derecha
	 */
	private PdfPCell crearCeldaDerecha(Mensaje m, Font regularFont, Font boldFont) {
		Paragraph paragraph = new Paragraph();
		if (m.getTexto().equals("")) {
			return createCeldaEmoji(m, regularFont, paragraph, false);
		} else {
			paragraph.add(new Chunk(m.getTexto(), boldFont));
			paragraph.add(new Chunk(": " + m.getEmisor().getNombre() 
					+ " - " + m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), regularFont));
			PdfPCell cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(PdfPCell.NO_BORDER);
			return cell;
		}
	}

	/**
	 * Crea una celda con un emoticono
	 * 
	 * @param mensaje
	 * @param regularFont
	 * @param paragraph
	 * @param esIzquierda
	 * @return
	 */
	private PdfPCell createCeldaEmoji(Mensaje m, Font regularFont, Paragraph paragraph, boolean esIzquierda) {
		Image image = null;
		int lado = esIzquierda ? Element.ALIGN_LEFT : Element.ALIGN_RIGHT;
		try {
			image = Utils.imageIconToPDFImage(BubbleText.getEmoji(m.getEmoticono()));
		} catch (BadElementException | IOException e) {
			e.printStackTrace();
		}
		image.scaleToFit(15, 15);
		if (esIzquierda) {
			paragraph.add(new Chunk(m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) 
					+ " - " + m.getEmisor().getNombre() + ": ", regularFont));
			paragraph.add(new Chunk(image, 0, 0, true));
		}else {
			paragraph.add(new Chunk(image, 0, 0, true));
			paragraph.add(new Chunk(": " + m.getEmisor().getNombre() 
					+ " - " + m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
		}
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setHorizontalAlignment(lado);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	/**
	 * Crea una celda vacía
	 * 
	 * @return
	 */
	private PdfPCell createEmptyCell() {
		PdfPCell emptyCell = new PdfPCell();
		emptyCell.setBorder(PdfPCell.NO_BORDER);
		return emptyCell;
	}

	/**
	 * Genera el nombre del PDF
	 * 
	 * @param Contacto
	 * @return nombre del PDF
	 */
	private String generatePDFName(Contacto c) {		
		return "Chat-" + c.getNombre() + ".pdf";	
	}

}
