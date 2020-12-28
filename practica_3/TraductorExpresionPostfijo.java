package practica_3;

public class TraductorExpresionPostfijo {

	/*
	 * Uso de la traduccion dirigida por sintaxis para traducir una expresion aritmetica en notacion
	 * infija a postfija (notacion polaca)
	 * 
	 * LA GRAMATICA Y EL ESQUEMA DE TRADUCCION
	 *
	 * expresion → expresion + term { print('+') } |
	 * 		  	   expresion - term { print('-') } |
	 * 		  	   term
	 * term		 → term * factor { print('*') } |
	 * 		  	   term / factor { print('/') } |
	 * 		       factor 
	 * factor    → ( expresion ) | 
	 * 			   num { print(num.val) }
	 *
	 *Para eliminar la recursion por la izq se usan las reglas:
	 *
	 *		A -> A alpha | 							A' -> beta A'
	 *			 beta              ===>   			A' -> alfa A' |
	 *												 	  epsilon
	 *			 
	 *LA GRAMATICA SIN RECURSION POR LA IZQ
	 *
	 *expresion   → termno masTerminos
	 *
	 *masTerminos → + termino { print('+') } masTerminos |
	 *				- termino { print('-') } masTerminos |
	 *				epsilon
	 *
	 *termino	  → factor masFactores
	 *
	 *masFactores → * factor { print('*') } masFactores |
	 *				 / factor { print('/') } masFactores |
	 *				epsilon
	 *
	 *factor	  → ( expresion ) | 
	 * 			  num { print(num.val) }
	 *
	 **/
	private ComponenteLexico componenteLexico;
	private Lexico lexico;

	public TraductorExpresionPostfijo( Lexico lexico) {
		this.lexico = lexico;
		this.componenteLexico = this.lexico.getComponenteLexico();
	}

	public void postfijo() {
		expresion();
	}
	private void expresion() {
		termino();
		masTerminos();
	}
	public void factor() {
		if(this.componenteLexico.getEtiqueta().equals("open_parenthesis")) {
			compara("open_parenthesis");
			expresion();
			compara("closed_parenthesis");
		}else if(this.componenteLexico.getEtiqueta().equals("integer")) {
			System.out.print(" " + this.componenteLexico.getValor() + " ");
			compara("integer");
		}
	}
	public void termino() {
		factor();
		masFactores();
	}

	private void masTerminos() {
		if(this.componenteLexico.getEtiqueta().equals("add")){
			compara("add");
			termino();
			System.out.print(" + ");
			masTerminos();
		}else if(this.componenteLexico.getEtiqueta().equals("subtract")) {
			compara("subtract");
			termino();
			System.out.println(" - ");
			masTerminos();
		}
	}
	private void masFactores() {
		if(this.componenteLexico.getEtiqueta().equals("multiply")){
			compara("multiply");
			factor();
			System.out.print(" * ");
			masFactores();
		}else if(this.componenteLexico.getEtiqueta().equals("divide")) {
			compara("divide");
			factor();
			System.out.print(" / ");
			masFactores();
		}
	}

	public void compara(String etiquetaLexica) {
		// TODO Auto-generated method stub
		if(this.componenteLexico.getEtiqueta().equals(etiquetaLexica)) {
			this.componenteLexico = lexico.getComponenteLexico();
		}else {
			System.out.println("ERROR. Se esperaba " + etiquetaLexica);
		}

	}


}
