package umu.tds.utils;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class Utils {

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

    // Método para convertir una cadena Base64 a una imagen
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

    public static ImageIcon imageToImageIcon(Image image, int width, int height) {
        // Escalar la imagen a las nuevas dimensiones
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Crear y retornar el ImageIcon con la imagen escalada
        return new ImageIcon(scaledImage);
    }
	
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
			// Si falla la lectura de la imagen, el botón se generará sin icono. No es necesario parar la ejecución.
			return null;
		}
	}
	
	public static ImageIcon getScaledIcon(String imageUrl, int width, int height) {		
		return new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));		
	}
}
