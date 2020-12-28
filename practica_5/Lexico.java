package practica_5;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;


public class Lexico {
	private PalabrasReservadas palabras; //las palabras reservadas del documento lexemas.txt
	private int posicion;
	private int lineas;
	private char caracter;
	private String programa;

	public Lexico(String programa) {//constructor para inicializar
		this.posicion=0;
		this.lineas=1;
		this.palabras=new PalabrasReservadas("lexemas.txt");
		this.programa=programa + "#";
	}
	public Lexico(String ficheroEntrada,Charset codificacion) {//llamar fichero
		this(contenidoFichero(ficheroEntrada,codificacion)); 
	}
	private static boolean existeFichero(String fichero) {//comprobar que existe el fichero
		File ficheroEntrada=new File(fichero);
		return ficheroEntrada.exists();
	}
	private static String contenidoFichero(String fichero, Charset codificacion) {//saca el contenido y lo codifica en un string
		String s=null;
		if(existeFichero(fichero)) {
			try {
				byte[] contenido=Files.readAllBytes(Paths.get(fichero));
				s= new String(contenido, codificacion);
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return s;
		
	}
	private char extraeCaracter() {
		return this.programa.charAt(this.posicion++);
	}
	private void devuelveCaracter() {
		this.posicion--;
	}
	public int getLineas() {
		return this.lineas;
	}

	public ComponenteLexicoBasico getComponenteLexicoBasico() {
		String etiqueta;//es ASCI
		while(true) {
			this.caracter=extraeCaracter();

			if(this.caracter==' ' || (int) this.caracter==9 || (int) this.caracter==13) {//9 tabulador //13 retorno de carro
				continue;
			}else if((int) this.caracter==10) {//10 salto de linea
				this.lineas++;
			}else {
				break;
			}
		}
		//Quitar espacios
		//Secuencias de digitos

		if(Character.isDigit(this.caracter)) {//transforma digito en string
			String numero="";
			boolean FloatTrue=false;
			boolean error=false;
			int dot=0;
			
			do{
				numero=numero+this.caracter;
				this.caracter=extraeCaracter();

			}while(Character.isDigit(this.caracter)||this.caracter=='.');//no puedes poner dos puntos seguidos
			devuelveCaracter();
			
			for(int i=0;i<numero.length()-1;i++) {
				if(numero.charAt(i)=='.') {
					dot++;
				}
			}
			if(dot==1) {
				FloatTrue=true;
			}else if(dot>1) {
				FloatTrue=true;
				error=true;
			}
			if(FloatTrue) {
				if(error) {
					return new NumeroReal("error","No puede haber mas de un punto",lineas);
				}else {
					return new NumeroReal("floatN",Float.parseFloat(numero)+"",lineas);
				}
			}else {
				return new NumeroEntero("integer",Integer.parseInt(numero) +"",lineas);

			}
		}

		
		if(Character.isLetter(this.caracter)) { //comprueba si lo que se escribe son palabras reservadas
			String lexema="";
			
			do {
				lexema=lexema+this.caracter;
				this.caracter=extraeCaracter();
				if(caracter=='_') {
					lexema=lexema+this.caracter;
					this.caracter=extraeCaracter();
				}

			}while(Character.isLetterOrDigit(this.caracter));

			devuelveCaracter();


			etiqueta=palabras.getEtiquetaLexica(lexema);
			if(etiqueta==null) {
				return new Identificador("id", lexema,lineas);
			}
			else {
				return new Identificador((String) etiqueta,lexema,lineas);
			}

		}
		String lexema ="",lexemaAlternativo, etiquetaAlternativa;

		do {
			lexema =lexema + this.caracter;
			etiqueta =palabras.getEtiquetaLexica(lexema);
			
			if(etiqueta!=null) {
				if(etiqueta.equals("end_program"))
					return new Identificador(etiqueta, lexema,lineas);
			}
			
			lexemaAlternativo =lexema;
			this.caracter=extraeCaracter();
			lexemaAlternativo=lexemaAlternativo + this.caracter;
			etiquetaAlternativa=palabras.getEtiquetaLexica(lexemaAlternativo);
			
			if(etiquetaAlternativa!=null) {
				etiqueta=etiquetaAlternativa;
			}
			
		}while(etiquetaAlternativa != null);
		boolean existeComentario=false;
		if(etiqueta.equals("comments")||etiqueta.equals("open_comments")) { //comprueba si es un comentario para ignorarlo hasta el salto de linea
			existeComentario=true;
		}
		 devuelveCaracter();
		 if(etiqueta.equals("comments")) {

			do {
				lexema = lexema+this.caracter;
				this.caracter = extraeCaracter();
			}while((int) this.caracter!=10);
		 }
		 if(etiqueta.equals("open_comments")) {
			 boolean asterisco = false;
			 char barra;
			 
			 do {
					lexema = lexema + this.caracter;
					this.caracter = extraeCaracter();
					
					if(this.caracter == '*')
						asterisco = true;
					if(asterisco) {
						barra = extraeCaracter();
						if(barra == '/') {
							lexema = lexema + this.caracter + barra;
						}else {
							asterisco = false;
							devuelveCaracter();
						}
						
					}
					if((int) this.caracter == 10) {
						this.lineas++;
					}
				}while(!asterisco);
		 }
		 if(existeComentario) {
			 return null;
		 }else {
			 return new Identificador(etiqueta, lexema,lineas);
		 }

	}

}
