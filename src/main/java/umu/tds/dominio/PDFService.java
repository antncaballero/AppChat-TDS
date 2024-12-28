package umu.tds.dominio;



public enum PDFService {
	INSTANCE;

	public void generatePDF(Contacto c) {
		
		System.out.println("generating pdf wih contact: " + c.getNombre());
	}

	

}
