package nebrija.traductor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;

public class Lexico {

	private ComponentesLexicos componentesLexicos;
	private int posicion;
	private int lineas;
	private char caracter;
	private String programa;

	public Lexico (String programa) {

		this.posicion = 0;
		this.lineas = 1;
		this.componentesLexicos = new ComponentesLexicos("lexico.txt");
		this.programa = programa + (char) (0);
	}

	public Lexico(String ficheroEntrada, Charset codificacion) {

		this(contenidoFichero (ficheroEntrada, codificacion));
	}

	private char extraeCaracter() {

		return this.programa.charAt(this.posicion++);
	}

	private void devuelveCaracter() {
		this.posicion --;
	}

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

			}catch(IOException e) {

			}
		}

		return s;
	}

	public int getLineas() {

		return this.lineas;
	}

	public ComponenteLexico getComponenteLexico() {
		String etiquetaLexica;

		//el analizador lexico descarta los espacios (codigo 32), tabuladores (codigo 9) y los
		//saltos de lineas (codigos 10 y 13)

		while (true) {

			this.caracter = extraeCaracter();

			if( this.caracter == 0) {
				return new ComponenteLexico("end_program");
			}else if(this.caracter == ' ' || (int) this.caracter == 9 || (int) this.caracter == 13) {
				continue;
			}else if((int) this.caracter == 10) {
				this.lineas++;
			}else {
				break;
			}
		}

		//secuencias de digitos de numeros enteros o reales

		if(Character.isDigit(this.caracter)) {
			String numero = "";

			do {
				numero = numero + this.caracter;

				this.caracter = extraeCaracter();
			}while (Character.isDigit(this.caracter));

			if(this.caracter != '.') {
				devuelveCaracter();

				return new NumeroEntero(Integer.parseInt(numero));
			}

			do {

				numero = numero + this.caracter;
				this.caracter = extraeCaracter();

			}while (Character.isDigit(this.caracter));

			devuelveCaracter();

			return new NumeroReal(Float.parseFloat(numero));
		}

		// identificadores y palabras reservadas

		if(Character.isLetter(this.caracter)) {
			String lexema = "";

			do {

				lexema = lexema + this.caracter;
				this.caracter = extraeCaracter();

			}while(Character.isLetterOrDigit(this.caracter));

			devuelveCaracter();

			etiquetaLexica = componentesLexicos.getEtiqueta(lexema);

			if (etiquetaLexica ==  null) {
				return new Identificador(lexema);
			}else {
				return new ComponenteLexico(etiquetaLexica);
			}
		}

		// operadores aritmeticos, relaciones, logicos y caracteres delimitadores

		String lexema = "", lexemaAlternativo, etiquetaAlternativa;

		do {

			lexema = lexema + this.caracter;
			etiquetaLexica = componentesLexicos.getEtiqueta(lexema);

			if(etiquetaLexica == null) {
				return new ComponenteLexico("invalid_char");
			}else {
				lexemaAlternativo = lexema;
				this.caracter = extraeCaracter ();

				lexemaAlternativo = lexemaAlternativo + this.caracter;
				etiquetaAlternativa = componentesLexicos.getEtiqueta(lexemaAlternativo);

				if(etiquetaAlternativa != null) {
					etiquetaLexica = etiquetaAlternativa;
				}
			}
		}while(etiquetaAlternativa != null);

		devuelveCaracter();

		return new ComponenteLexico(etiquetaLexica);

	}

}
