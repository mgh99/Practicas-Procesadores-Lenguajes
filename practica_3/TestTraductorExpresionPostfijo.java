package practica_3;

public class TestTraductorExpresionPostfijo {

	public static void main(String[] args) {

		String expresion = "(25*(2+2))/2*3";

		TraductorExpresionPostfijo expr = new TraductorExpresionPostfijo(new Lexico(expresion));

		System.out.println("Conversion de una expresion aritmetica usando traduccion dirigida por la sintaxis (recursivo)");

		System.out.println("La expresion " + expresion + " en notacion postfija es ");
		expr.postfijo();


		ComponenteLexico etiquetaLexica;

		String programa ="(25*(2+2))/2*3";

		Lexico lexico = new Lexico(programa);


		System.out.println("Test lexico basico \t" + programa + "\n");

		do {

			etiquetaLexica = lexico.getComponenteLexico();

			System.out.println(etiquetaLexica.toString());

		} while (!etiquetaLexica.getEtiqueta().equals("end_program"));

	}

}
