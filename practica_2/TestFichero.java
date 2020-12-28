package practica_2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFichero {
	
	private static boolean existeFichero(String fichero) {
		File ficheroEntrada = new File (fichero);

		return ficheroEntrada.exists();
	}

	private static String contenidoFichero(String fichero, Charset codificacion) {
		String s = null;

		if(existeFichero(fichero)) {
			try {
				byte [] contenido = Files.readAllBytes(Paths.get(fichero));

				s = new String(contenido, codificacion);
			} catch (IOException e) { }
		}
		return s;
	}

	public static void main(String[] args) {
		
		if (existeFichero("lexico.txt"))
			System.out.println("El fichero lexico.txt existe");
		else
			System.out.println("Error, no existe el fichero lexico.txt");

		if (existeFichero("programa.txt")) {
			String programa = contenidoFichero("programa.txt", StandardCharsets.UTF_8);

			System.out.println("\n" + programa);

			Lexico lexico = new Lexico(programa);

			System.out.println("\n\nText Lexico \n");

			ComponenteLexico etiquetaLexica;

			int c = 0;

			do {
				etiquetaLexica = lexico.getComponenteLexico();

				System.out.println("<" + etiquetaLexica.toString() + ">");

				c++;

			} while (!etiquetaLexica.getEtiqueta().equals("end_program"));

			System.out.println("\nComponentes lexicos: " + c + ", lineas: " + lexico.getLineas());
		}

	}

}
