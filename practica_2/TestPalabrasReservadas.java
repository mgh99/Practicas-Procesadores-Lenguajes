package practica_2;

import java.util.Hashtable;

public class TestPalabrasReservadas {
	
	private static String etiqueta(String s) {
		return s.substring(0, s.indexOf("\t")).trim();
	}
	
	private static String lexema(String s) {
		return s.substring(s.lastIndexOf("\t")+1).trim();
	}
	
	public static void main (String[] args) {
		
		PalabrasReservadas componentesLexicos = new PalabrasReservadas("lexico.txt");
		
		System.out.println(">		\t" + componentesLexicos.getEtiqueta(">"));
		System.out.println(">=		\t" + componentesLexicos.getEtiqueta(">="));
		System.out.println("<		\t" + componentesLexicos.getEtiqueta("<"));
		System.out.println("<=		\t" + componentesLexicos.getEtiqueta("<="));
		System.out.println("=		\t" + componentesLexicos.getEtiqueta("="));
		System.out.println("==		\t" + componentesLexicos.getEtiqueta("=="));
		System.out.println("!=		\t" + componentesLexicos.getEtiqueta("!="));
		
		System.out.println("+		\t" + componentesLexicos.getEtiqueta("+"));
		System.out.println("-		\t" + componentesLexicos.getEtiqueta("-"));
		System.out.println("/		\t" + componentesLexicos.getEtiqueta("/"));
		System.out.println("%		\t" + componentesLexicos.getEtiqueta("%"));
		
		System.out.println("&&		\t" + componentesLexicos.getEtiqueta("&&"));
		System.out.println("||		\t" + componentesLexicos.getEtiqueta("||"));
		System.out.println("!		\t" + componentesLexicos.getEtiqueta("!"));
		
		System.out.println("(		\t" + componentesLexicos.getEtiqueta("("));
		System.out.println(")		\t" + componentesLexicos.getEtiqueta(")"));
		
		System.out.println("do		\t" + componentesLexicos.getEtiqueta("do"));
		System.out.println("while		\t" + componentesLexicos.getEtiqueta("while"));
		System.out.println("if		\t" + componentesLexicos.getEtiqueta("if"));
		System.out.println("else		\t" + componentesLexicos.getEtiqueta("else"));
		System.out.println("true		\t" + componentesLexicos.getEtiqueta("true"));
		System.out.println("false		\t" + componentesLexicos.getEtiqueta("(false"));
		System.out.println("int		\t" + componentesLexicos.getEtiqueta("int"));
		System.out.println("float		\t" + componentesLexicos.getEtiqueta("float"));
	}

}
