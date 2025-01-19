package umu.tds.utils;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.itextpdf.text.BadElementException;
/**
 * Clase de utilidades para la aplicación
 */
public class Utils {

	public static final String FOTO_USUARIO_POR_DEFECTO = Utils.convertImageToBase64(new File("src/main/resources/user.png"));;
	
	/**
	 * Convierte una imagen a una cadena Base64
	 * @param imageFile
	 * @return cadena Base64
	 */
	public static String convertImageToBase64(File imageFile) {
        // Leer el archivo de imagen en un arreglo de bytes        
		byte[] imageBytes;
		try {
			FileInputStream fileInputStream = new FileInputStream(imageFile);   
			imageBytes = fileInputStream.readAllBytes();
			fileInputStream.close();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			return null;		
		}        
    }
	/**
	 * Convierte una cadena Base64 a una imagen
	 * @param base64String
	 * @return imagen
	 */
    public static Image convertBase64ToImage(String base64String) {
        // Decodificar la cadena Base64 a un arreglo de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64String);      
        // Leer los bytes como una imagen
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        try {
			return ImageIO.read(bais);
		} catch (IOException e) {
			return null;
		}
    }
    /**
     * Convierte una imagen a un ImageIcon
     * @param image
     * @param width
     * @param height
     * @return ImageIcon con las dimensiones especificadas
     */
    public static ImageIcon imageToImageIcon(Image image, int width, int height) {
        // Escalar la imagen a las nuevas dimensiones
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // Crear y retornar el ImageIcon con la imagen escalada
        return new ImageIcon(scaledImage);
    }
	/**
	 * Obtiene un ImageIcon a partir de una imagen y un factor de escala
	 * @param imageUrl
	 * @param factor
	 * @return
	 */
    public static ImageIcon getIcon(String imageUrl, float factor) {
		try {
			// Leer la imagen
			BufferedImage img = ImageIO.read(new File(imageUrl));
			// Obtenemos la proporcion ancho / altura.
			float proporcion = img.getWidth() / ((float) img.getHeight());
			// Obtenemos la Fuente (letra) por defecto especificada por el SO para un textPane.
			Font font = UIManager.getDefaults().getFont("TextPane.font");
			// Obtenemos el tamaño de letra.
			int tamanoLetra = font.getSize();
			// Se reeescala la iamgen.
			Image newimg = img.getScaledInstance(
					Math.round(factor * tamanoLetra * proporcion),  // Anchura: tamaño de la letra multiplicado por la proporcion original.
					Math.round(factor * tamanoLetra), // altura: tamaño de la letra
					java.awt.Image.SCALE_SMOOTH	// Método para reescalar (Calidad:SCALE_SMOOTH o rapidez SCALE_FAST)
					);
			// Se crea un ImageIcon
			return new ImageIcon(newimg);
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * Obtiene un ImageIcon a partir de una imagen y unas dimensiones
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @return ImageIcon con las dimensiones especificadas
	 */
	public static ImageIcon getScaledIcon(String imageUrl, int width, int height) {		
		return new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));		
	}
	/**
	 * Obtiene una imagen a partir de una URL
	 * @param imageUrl
	 * @return Imagen
	 */
	public static Image getImage(String imageUrl) {
		try {
			return ImageIO.read(new File(imageUrl));
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * Convierte un ImageIcon a un iTextImage
	 * 
	 * @param imageIcon
	 * @return imagen específica de iText
	 * @throws IOException
	 * @throws BadElementException
	 */
	public static com.itextpdf.text.Image imageIconToPDFImage(ImageIcon imageIcon) throws IOException, BadElementException {
		// Convert ImageIcon to iText Image
		BufferedImage bufferedImage = new BufferedImage(
				imageIcon.getIconWidth(),
				imageIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB
				);
		imageIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(baos.toByteArray());
		return image;
	}
}
