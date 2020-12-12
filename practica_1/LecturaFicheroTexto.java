package practica_1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LecturaFicheroTexto {
	
	public static void main(String[] args) throws IOException {

		String idFichero = "C:\\Users\\Propietario\\eclipse-workspace\\Compis\\programa";
		File ficheroEntrada = new File (idFichero);
		ComponenteLexicoBasico etiquetaLexica;

		if(ficheroEntrada.exists()) {

			Scanner datosFichero = new Scanner(ficheroEntrada);
			System.out.println("Numeros del fichero \n");

			while (datosFichero.hasNext()) {

				String [] numerosFichero = datosFichero.nextLine().split(",");

				for(int i = 0; i < numerosFichero.length; i++) {

					System.out.println(numerosFichero[i] + "\t");
				}

				System.out.println("");
			}

			datosFichero.close();

		}else {
			System.out.println("¡El fichero no existe!");
		}
		

	}

}
