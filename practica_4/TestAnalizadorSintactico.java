package practica_4;

import nebrija.traductor.*;

public class TestAnalizadorSintactico {
	
	public static void main (String[] args) {
		
		boolean mostrarComponentesLexicos = true;
		
		String expresion = " void int a, b, c, d; float x, y, z;";
		
		ComponenteLexico etiquetaLexica;
		Lexico lexico = new Lexico(expresion);
		
		if(mostrarComponentesLexicos) {
			
			do {
				etiquetaLexica = lexico.getComponenteLexico();
				System.out.println("<" + etiquetaLexica.toString() + ">");
				
			}while(!etiquetaLexica.getEtiqueta().equals("end_program"));
			
			System.out.println("");
		}
		
		AnalizadorSintactico compilador = new AnalizadorSintactico (new Lexico(expresion));
		
		System.out.println("Compilación de sentencia de declaraciones de variables");
		System.out.println(expresion + "\n");
		
		//compilador.declaraciones();
		System.out.println("Tabla de símbolos \n\n" ); 
		compilador.tablaSimbolos();
	}

}
