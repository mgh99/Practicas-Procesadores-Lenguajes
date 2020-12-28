package practica_2;

import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TestLexico {

	public static void main(String[] args) {

		ComponenteLexico componenteLexico;
		
		String programa = "int k; for (int i=0; i<10; i=i+1) k = k * 2; ";
		
		Lexico lexico1 = new Lexico(programa);

		// test lexico, pasando cadena de texto
		
		System.out.println(programa + "\n\n");
		
		int c = 0;
		
		do {
			
			componenteLexico = lexico1.getComponenteLexico();
			System.out.println("<" + componenteLexico.toString() + ">");
			c++;

		} while (!componenteLexico.getEtiqueta().equals("end_program"));

		System.out.println("\nComponentes léxicos: " + c +
				", lineas: " + lexico1.getLineas() + "\n\n");
		
		//String programa = "int @k; for (in i=0; i<10; i=i+1) k = k* 2;";
		
		
		//TEST LEXICO, PASANDO EL ARCHIVO DE CODIGO FUENTE
		
		String ficheroEntrada = "programa.txt";
		
		Lexico lexico2 = new Lexico(ficheroEntrada, (StandardCharsets.UTF_8));
		
		System.out.println(programa + "\n\n");
		
		 c = 0;
		
		do {
			
			componenteLexico = lexico2.getComponenteLexico();
			
			System.out.println("<" + componenteLexico.toString() + ">");
			
			c++;
			
		}while (!componenteLexico.getEtiqueta().equals("end_program"));
		
		System.out.println("\nComponente lexicos: " + c + ", lineas: " + 
		lexico2.getLineas() + "\n\n");
			
	}
}
