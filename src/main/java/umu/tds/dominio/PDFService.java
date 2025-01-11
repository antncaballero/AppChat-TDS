package umu.tds.dominio;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

public enum PDFService {
	INSTANCE;
	
	/**
	 * Genera un PDF con un los mensajes con un contacto
	 * 
	 * @param directorio
	 * @param c
	 * @return
	 */
	public boolean generatePDF(File directorio, Contacto c, Usuario usuario) {
		
		File file = new File(directorio, generatePDFName(c));
	    Document document = new Document();
	    

	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();
	        document.add(new Paragraph("¡Hola, este es un PDF generado desde Java!"));
	        document.add(new Paragraph("Mensajes con " + c.getNombre() + ":"));
	        document.add(new Paragraph(" "));

	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{1, 1});
	        
	        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	        Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	        
	        c.getTodosLosMensajes(usuario).forEach(m -> {
	            PdfPCell cell;
				if (!m.getEmisor().equals(usuario)) {	
					Paragraph paragraph = new Paragraph();
					if(m.getEmoticono() != 0) {
						Image image = null;
						try {
							image = ImageIconToImage(BubbleText.getEmoji(m.getEmoticono()));
						} catch (BadElementException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						paragraph.add(new Chunk(
								m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) 
								+ " - "+ m.getEmisor().getNombre() + ": ", regularFont));
						image.scaleToFit(15, 15);
						paragraph.add(new Chunk(image, 0, 0, true));
						cell = new PdfPCell(paragraph);
					} else {
		                paragraph.add(new Chunk(m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " - " + m.getEmisor().getNombre() + ": ", regularFont));
		                paragraph.add(new Chunk(m.getTexto(), boldFont));
		                cell = new PdfPCell(paragraph);
					}
						
				    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cell.setBorder(PdfPCell.NO_BORDER);
				    table.addCell(cell);
				    PdfPCell emptyCell = new PdfPCell();
				    emptyCell.setBorder(PdfPCell.NO_BORDER);
				    table.addCell(emptyCell); // Empty cell for the right side
				} else {
					Paragraph paragraph = new Paragraph();
				    PdfPCell emptyCell = new PdfPCell();
				    emptyCell.setBorder(PdfPCell.NO_BORDER);
				    table.addCell(emptyCell); // Empty cell for the left side
				    if(m.getEmoticono() != 0) {
				    	Image image = null;
						try {
							image = ImageIconToImage(BubbleText.getEmoji(m.getEmoticono()));
						} catch (BadElementException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						image.scaleToFit(15, 15);
				    	paragraph.add(new Chunk(image, 0, 0, true));
				    	paragraph.add(new Chunk(
				    			": " + m.getEmisor().getNombre() + " - " 
				    			+ m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) , regularFont));
				    	
				    	cell = new PdfPCell(paragraph);
				    	
				    }
				    	
				    else {			
				    	paragraph.add(new Chunk(m.getTexto(), boldFont));
				    	paragraph.add(new Chunk(": " + m.getEmisor().getNombre() + " - " 
				    							+ m.getHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), regularFont));
				    	cell = new PdfPCell(paragraph);
				    }
				    
				    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    cell.setBorder(PdfPCell.NO_BORDER);
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
	
	private Image ImageIconToImage(ImageIcon imageIcon) throws IOException, BadElementException {
		// Convert ImageIcon to iText Image
        BufferedImage bufferedImage = new BufferedImage(
            imageIcon.getIconWidth(),
            imageIcon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        imageIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        Image image = Image.getInstance(baos.toByteArray());
        return image;
	}
	
	/**
	 * Genera el nombre del PDF a partir del contacto
	 * 
	 * @param c
	 * @return
	 */
	private String generatePDFName(Contacto c) {		
		return "Chat-" + c.getNombre() + ".pdf";	
	}

}
