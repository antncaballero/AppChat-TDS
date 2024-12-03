package umu.tds.utils;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {

	public static String convertImageToBase64(File imageFile) throws IOException {
        // Leer el archivo de imagen en un arreglo de bytes
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] imageBytes = fileInputStream.readAllBytes();
        fileInputStream.close();
        
        // Convertir el array de bytes a una cadena Base64
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Método para convertir una cadena Base64 a una imagen
    public static Image convertBase64ToImage(String base64String) throws IOException {
        // Decodificar la cadena Base64 a un arreglo de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        
        // Leer los bytes como una imagen
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bais);
    }

    public static ImageIcon imageToImageIcon(Image image, int width, int height) {
        // Escalar la imagen a las nuevas dimensiones
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Crear y retornar el ImageIcon con la imagen escalada
        return new ImageIcon(scaledImage);
    }
	
}
