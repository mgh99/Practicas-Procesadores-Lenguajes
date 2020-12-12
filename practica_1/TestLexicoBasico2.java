package practica_1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestLexicoBasico2 {
	
private static boolean existeFichero (String fichero) {
		
		File ficheroEntrada = new File (fichero);
		return ficheroEntrada.exists();
	}
	
	private static String contenidoFichero (String fichero, Charset codificacion) {
		
		String s = null;
		
		if(existeFichero(fichero)) {
			try {
				byte [] contenido = Files.readAllBytes(Paths.get(fichero));
				
				s = new String (contenido, codificacion);
			}catch (IOException e) { }
		}
		
		return s;
	}
	
	public static void main (String[] args) {

		String ficheroEntrada = "programa.txt";
		String programa = contenidoFichero (ficheroEntrada, StandardCharsets.UTF_8);
		
		if(programa != null) {
			
			System.out.println ("Codigo fuente \n\n" + programa + "\n\n");
			
			ComponenteLexicoBasico etiquetaLexica;
			LexicoBasico lexico = new LexicoBasico(programa);
			
			int c = 0;
		}
	}

}
