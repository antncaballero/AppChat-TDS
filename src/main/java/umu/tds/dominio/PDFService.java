package umu.tds.dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public enum PDFService {
	INSTANCE;
	
	/**
	 * Genera un PDF con un los mensajes con un contacto
	 * 
	 * @param directorio
	 * @param c
	 * @return
	 */
	public boolean generatePDF(File directorio, Contacto c) {
		
		File file = new File(directorio, generatePDFName(c));
		Document document = new Document();
        
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
	        document.add(new Paragraph("¡Hola, este es un PDF generado desde Java!"));
	        document.add(new Paragraph("Mensajes con " + c.getNombre() + ":"));
		} catch (FileNotFoundException | DocumentException e) {
			System.err.println("Error al generar el PDF");
			e.printStackTrace();
			return false;
		}
        
        document.close();        		
        return true;
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
