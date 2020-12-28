package practica_4;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.lang.model.element.VariableElement;

import nebrija.traductor.ComponenteLexico;
import nebrija.traductor.Lexico;
import nebrija.traductor.Identificador;
import nebrija.traductor.NumeroEntero;
import nebrija.traductor.NumeroReal;
import practica_2.TipoArray;
import practica_2.TipoDato;
//import nebrija.traductor.TipoDato;
//import nebrija.traductor.TipoPrimitiva;
//import nebrija.traductor.TipoArray;
import practica_2.TipoPrimitivo;

/*
 *  declaraciones       => declaración declaraciones   | epsilon
 *  declaracion         => tipo identificadores  ;
 *  tipo                => int  |  float
 *  identificadores     => id  más-identificadores
 *  más-identificadores => ,    id  más-identificadores  |  epsilon
 * 
 */

public class AnalizadorSintactico {

	private Lexico lexico;
	private ComponenteLexico componenteLexico;
	private Hashtable<String,TipoDato> simbolos;
	private String tipo;
	private int tamaño;

	public AnalizadorSintactico(Lexico lexico) {
		
		this.lexico = lexico;
		this.componenteLexico = this.lexico.getComponenteLexico();
		this.simbolos = new Hashtable<String,TipoDato>();
	}

	public void analisisSintactico() {
		declaraciones();
	}
	
	/*public void tablaSimbolos() {
		
		Enumeration e = simbolos.keys();   //enumera el numero de objetos de una coleccion                                                                               
		
		Object simbolo;                                                                                             
		Object dato; 
		
		while(e.hasMoreElements()){ 
			
			simbolo = e.nextElement();                                                                              
			dato = simbolos.get(simbolo);                                                                       
			System.out.println("Nombre de la variable: " + simbolos + "\nTipo de dato: " + dato + "\n");  //para que los programas se compilen "bonitos"                                      
		} 
	}*/
	
	public String tablaSimbolos() {
		
		String simbolos = "";
		Set<Entry<String, TipoDato>> s = this.simbolos.entrySet();
		
		for(Entry<String, TipoDato> m: s)
			simbolos = simbolos + "<'" + m.getKey() + "'," + m.getValue().toString() + "> \n";
		return simbolos;
	}


	public void declaraciones() {
		
		if(this.componenteLexico.getEtiqueta().equals("int") || this.componenteLexico.getEtiqueta().equals("float")) {
			
			declaracionVariable();
			declaraciones();
		}
	}

	public void declaracionVariable() {
		
		tipoPrimitivo();
		
		if(this.componenteLexico.getEtiqueta().equals("open_square_bracket")) {
			tipoArray();
			simbolos.put(this.componenteLexico.getDato(), TipoArray(this.tipo, this.tamaño));
			compara("id");
			compara("semicolon");
			
		} else {
			
			listaIdentificadores();
			compara("semicolon");
		}
	}

	private TipoDato TipoArray(String tipo2, int tamaño2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void tipoPrimitivo() {
		if(this.componenteLexico.getEtiqueta().equals("int")) {
			this.tipo = "int";
			compara("int");
		}else {
			this.tipo = "float";
			compara("float");
		}
	}

	public void tipoArray() {
		compara("open_square_bracket");
		this.tamaño = Integer.parseInt(this.componenteLexico.getDato());
		compara("integer");
		compara("closed_square_bracket");
	}

	public void listaIdentificadores() {
		if(this.componenteLexico.getEtiqueta().equals("id")) {
			simbolos.put(this.componenteLexico.getDato(), TipoPrimitivo(this.tipo));
			compara("id");
			masIdentificadores();
		}
	}

	public void masIdentificadores() {
		if(this.componenteLexico.getEtiqueta().equals("comma")) {
			compara("comma");
			simbolos.put(this.componenteLexico.getDato(), TipoPrimitivo(this.tipo));
			compara("id");
			masIdentificadores();
		}
	}

	private TipoDato TipoPrimitivo(String tipo2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void compara(String token) {
		if(this.componenteLexico.getEtiqueta().equals(token)) {
			this.componenteLexico = this.lexico.getComponenteLexico();
		}else {
			System.out.println("Expected: " + token);
		}
	}
	
	public void printSimbolos() {
		Enumeration keys = simbolos.keys();
		Enumeration clave = simbolos.elements();
		
		System.out.println("Lexema           Tipo de Dato");
		System.out.println("-----------------------------");
		while(keys.hasMoreElements() && clave.hasMoreElements()) {
			System.out.println(keys.nextElement() + "                " + clave.nextElement().toString());
		}
	}

	public Lexico getLexico() {
		return lexico;
	}

	public void setLexico(Lexico lexico) {
		this.lexico = lexico;
	}

	public ComponenteLexico getComponenteLexico() {
		return componenteLexico;
	}

	public void setComponenteLexico(ComponenteLexico componenteLexico) {
		this.componenteLexico = componenteLexico;
	}

	public Hashtable<String, TipoDato> getPalabras() {
		return this.simbolos;
	}

	public void setPalabras(Hashtable<String, TipoDato> simbolos) {
		this.simbolos = simbolos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
