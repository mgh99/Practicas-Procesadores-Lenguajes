package practica_1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

public class LexicoBasico {
	

	private Hashtable<String, String> palabrasReservadas;
	private int posiciones;
	private int lineas;
	private char caracter;
	private String programa;

	public LexicoBasico (String programa) {

		this.posiciones = 0;
		this.lineas = 1;
		
		//la tabla de palabras reservadas almacena el lexema(clave) y el token (valor)
		this.palabrasReservadas = new Hashtable<String, String> ();
		
		this.palabrasReservadas.put("break", "break");
		this.palabrasReservadas.put("do", "do");
		this.palabrasReservadas.put("else", "else");
		this.palabrasReservadas.put("float", "float");
		this.palabrasReservadas.put("for", "for");
		this.palabrasReservadas.put("if", "if");
		this.palabrasReservadas.put("int", "int");
		this.palabrasReservadas.put("while", "while");
		this.programa = programa + "#";

		//this.programa = programa + (char) (0);

	}
	
	public LexicoBasico (String ficheroEntrada, Charset codificacion) {
		this (contenidoFichero(ficheroEntrada, codificacion));
	} 
	
	private char extraerCaracter() {
		return this.programa.charAt(this.posiciones++);
	}
	
	//extraerCaracter(char c) se usa para reconocer operadores con lexemas de 2 caracteres: &&, ||, <=, >=, ==, !=
	private boolean extraerCaracter(char c) {
		
		if(this.posiciones < this.programa.length() -1) {
			this.caracter = extraerCaracter();

			if(c == this.caracter)
				return true;
			else {
				devuelveCaracter();

				return false;
			}
		}else

			return false;
	}

	private void devuelveCaracter() {
		this.posiciones--; // retrocede el puntero
	}
	
	public int getLineas() {
		return this.lineas;
	}
	
	public ComponenteLexicoBasico getComponenteLexico() {
		
		//el analizador lexico descarta los espacios (codigo 32),
		//tabuladores (cidigo 9) y saltos de líneas (codigos 10 y 13)
		
		while (true) {
			this.caracter = extraerCaracter();
			
					if(this.caracter == 0)
						return new ComponenteLexicoBasico ("end_program");
					else if (this.caracter == ' ' || (int) this.caracter == 9 || (int) this.caracter == 10)
						continue;
					else if ((int) this.caracter == 13)
						this.lineas++;
					else
						break;
						
		}
		
		//secuencias de digitos de numeros enteros o reales
		
		if(Character.isDigit(this.caracter)) {
			
			String numero = "";
			
			do {
				numero = numero + this.caracter;
				this.caracter = extraerCaracter();
			}while (Character.isDigit(this.caracter));
			
			if(this.caracter != '.') {
				
				devuelveCaracter();
				return new ComponenteLexicoBasico ("integer", Integer.parseInt(numero) + "");
			}
			
			do {
				
				numero = numero + this.caracter;
				this.caracter = extraerCaracter();
			}while (Character.isDigit(this.caracter));
			
			devuelveCaracter();
			
			//return new ComponenteLexicoBasico ("float", Float.parseFloat(numero) + "");
			return new ComponenteLexicoBasico ("float", numero);
			
		}
		
		
		//identificadores y palabras reservadas
		
		if(Character.isLetter(this.caracter)) {
			String lexema = " ";

			do {

				lexema = lexema + this.caracter;

				this.caracter = extraerCaracter();

			}while (Character.isLetterOrDigit(this.caracter));

			devuelveCaracter();

			if(this.palabrasReservadas.containsKey(lexema))

				return new ComponenteLexicoBasico ((String) this.palabrasReservadas.get(lexema));
			else

				return new ComponenteLexicoBasico("id", lexema);
		}
		
		// operadores sritmrticos, relacionales, logicos y caracteres delimitadores
		
		switch (this.caracter) {
		
		case '&':
			
			if(this.extraerCaracter('&')) {
				
				return new ComponenteLexicoBasico("and"); 
				
			}else {
				return new ComponenteLexicoBasico("bitwise_and");
			}

		case '|':

			if(this.extraerCaracter('|')) {
				
				return new ComponenteLexicoBasico("or");
				
			}else {
				return new ComponenteLexicoBasico("bitwise_or");
			}
			
		case '=':
			
			if(this.extraerCaracter('=')) {
				
				return new ComponenteLexicoBasico("equals");
				
			}else {
				return new ComponenteLexicoBasico("assignment");
			}
			
		case '!':
			
			if(this.extraerCaracter('=')) {
				
				return new ComponenteLexicoBasico("not_equals");
				
			}else {
				return new ComponenteLexicoBasico("not");
			}
			
		case '<':
			
			if(this.extraerCaracter('=')) {

				return new ComponenteLexicoBasico("less_equals");

			}else {
				return new ComponenteLexicoBasico("less_than");
			}
			
		case '>':
			
			if(this.extraerCaracter('=')) {

				return new ComponenteLexicoBasico("greater_equals");

			}else {
				return new ComponenteLexicoBasico("greater_than");
			}

		case '+': 
			return new ComponenteLexicoBasico("add");
			
		case '-':
			return new ComponenteLexicoBasico("subtract");
			
		case '*':
			return new ComponenteLexicoBasico("multiply");
			
		case '/':
			return new ComponenteLexicoBasico("divide");
			
		case '%':
			return new ComponenteLexicoBasico("remainder");
			
		case ';':
			return new ComponenteLexicoBasico("semicolon");
			
		case '(':
			return new ComponenteLexicoBasico("open_parenthesis");
			
		case ')':
			return new ComponenteLexicoBasico("closed_parenthesis");
			
		case '[':
			return new ComponenteLexicoBasico("open_square_braket");
			
		case ']':
			return new ComponenteLexicoBasico("closed_square_braket");

		case '{':
			return new ComponenteLexicoBasico("open_braquet");

		case '}':
			return new ComponenteLexicoBasico("closed_braquet");
	
		case '#':
			return new ComponenteLexicoBasico("end_program");

		// resto de operadores y delimitadores
		default:
			
			//return new ComponenteLexicoBasico(this.caracter + ""); el propio caracter
			return new ComponenteLexicoBasico("this.caracter" + "");
		}

	}
	
	private static boolean existeFichero(String fichero) {
		
		File ficheroEntrada = new File (fichero);
		return ficheroEntrada.exists();
		
	}
	
	private static String contenidoFichero (String fichero, Charset codificacion) {
		
		String s = null;
		
		if(existeFichero(fichero)) {
			
			try {
				
				byte [] contenido = Files.readAllBytes(Paths.get(fichero));
				s = new String(contenido, codificacion);
				
			}catch (IOException e) { }
			
		}
		
		return s;
	}

}
