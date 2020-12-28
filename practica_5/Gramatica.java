package practica_5;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Gramatica {                                                                                          
	private ComponenteLexicoBasico componenteLexico;                                                              
	private Lexico lexico;                                                                                        
	private Hashtable<String, TipoDato> variables; 
	private Vector<String> errores;
	private String tipo;                                                                                          
	private int tamaño;                                                                                        
                                                                                    
	public Gramatica(Lexico lexico) {  //constructor crea la gramatica                                                                                       
		this.lexico=lexico;                                                                                       
		this.variables=new Hashtable<String, TipoDato>();                                                         
		this.componenteLexico=lexico.getComponenteLexicoBasico(); 
		this.errores=new Vector<>();


	}                                                                                                             
	public void TablaSimbolos() {                                                                                  
		Enumeration e = variables.keys();   //enumera el numero de objetos de una coleccion                                                                               
		Object variable;                                                                                             
		Object dato;                                                                                             
		while(e.hasMoreElements()){                                                                             
			variable = e.nextElement();                                                                              
			dato = variables.get(variable);                                                                       
			System.out.println("Nombre de la variable: " + variable + "\nTipo de dato: " + dato + "\n");  //para que los programas se compilen "bonitos"                                      
		}                                                                                                         
	}

	public void programa() {
		compara("void");
		compara("main");
		compara("open_bracket");
		declaraciones();
		instrucciones();
		compara("closed_bracket");

	}
	public void Error() {
		if(this.errores.isEmpty()) {//si el vector de errores esta vacio no hace nada
			return;
		}
		for(String e:this.errores) {//si esta lleno devuelvo sus elementos
			System.out.println(e);
		}
	}
	
	public void compara(String etiqueta) {//compara las etiquetas para comprobar que la estructura esté bien hecha
		if(this.componenteLexico.getEtiqueta().equals(etiqueta)) {
			this.componenteLexico=this.lexico.getComponenteLexicoBasico();
			if(this.componenteLexico==null) this.componenteLexico=this.lexico.getComponenteLexicoBasico();	
		}	
		else {
			System.out.println("ERROR: se esperaba "+etiqueta);
		}
	}
	private void declaraciones() {
		if(this.componenteLexico.getEtiqueta().equals("int")||this.componenteLexico.getEtiqueta().equals("float")||this.componenteLexico.getEtiqueta().equals("boolean")) {
			declaracion_variable();
			declaraciones();

		}


	}
	private void declaracion_variable(){ //te dice que solo la puedes declarar si tienes paréntesis
		tipo_primitivo();
		if(this.componenteLexico.getEtiqueta().equals("open_square_bracket")) {
			tipo_vector();
			variables.put(this.componenteLexico.getData(), new TipoArray(this.tamaño,this.tipo));
			compara("id"); //compara para que le metas algo compatible

			compara("semicolon");

		}else {
			lista_identificadores();//si el primer identificador no está pasa al siguiente
			compara("semicolon");
		}
	}
	private void tipo_primitivo() {
		if(this.componenteLexico.getEtiqueta().equals("int")) {
			this.tipo="int";
			compara("int");

		}else if(this.componenteLexico.getEtiqueta().equals("float")) {
			this.tipo="float";
			compara("float");

		}else {
			if(this.componenteLexico.getEtiqueta().equals("boolean")) {
				this.tipo="boolean";
				compara("boolean");
			}
		}
	}
	private void tipo_vector() {
		compara("open_square_bracket");

		this.tamaño=Integer.parseInt(this.componenteLexico.getData()); //ignora todo desde que encuentra un caracter no esperado
		compara("integer");

		compara("closed_square_bracket");
	}
	private boolean busca(String a) { //busca un elemento
		Enumeration e = variables.keys();
		Object clave;
		boolean encontrado=false;
		while( e.hasMoreElements() ){
			clave = e.nextElement();
			if(clave.equals(a)){
				return true;
			}
		}
		return false;
	}
	private void lista_identificadores() { //crea una lista con los diferentes identificadores
		if(this.componenteLexico.getEtiqueta().equals("id")){//llama a busca y si hay una variable con ese nombre no te deja declararla de nuevo
			
			if(!busca(this.componenteLexico.getData())) {
				variables.put(this.componenteLexico.getData(), new TipoPrimitivo(this.tipo));
			}else {
				this.errores.add("ERROR en la linea: "+this.componenteLexico.getLinea()+" identificador "+this.componenteLexico.getData()+" ya declarado");
			}

			compara("id");
			asignacion_declaracion();
			mas_identificadores();
		}
	}
	private void mas_identificadores() { //para definir mas de una variable en una misma linea separadas de ','
		if(this.componenteLexico.getEtiqueta().equals("comma")){
			compara("comma");
			
			if(!busca(this.componenteLexico.getData())) {
				variables.put(this.componenteLexico.getData(), new TipoPrimitivo(this.tipo));

			}else {
				this.errores.add("ERROR en la linea: "+this.componenteLexico.getLinea()+" identificador "+this.componenteLexico.getData()+" ya declarado");
			}
			compara("id");
			asignacion_declaracion();
			mas_identificadores();
		}
	}
	private void asignacion_declaracion() { //no te deja asociar dos variables si no son de un mismo tipo
		if(this.componenteLexico.getEtiqueta().equals("assignment")) {
			compara("assignment");
			if(tipo.equals("int")) {
				if(this.componenteLexico.getEtiqueta().equals("floatN")) {
					this.errores.add("Error de asignacion en la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un float a un int");
				}
				if(this.componenteLexico.getEtiqueta().equals("boolean")) {
					this.errores.add("Error de asignacion en la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un boolean a un int");
				}
			}else if(tipo.equals("float")) {
				if(this.componenteLexico.getEtiqueta().equals("integer")) {
					this.errores.add("Error de asignacionen la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un int a un float");

				}if(this.componenteLexico.getEtiqueta().equals("boolean")) {
					this.errores.add("Error de asignacion en la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un boolean a un float");
				}
			}else if(tipo.equals("boolean")) {
				if(this.componenteLexico.getEtiqueta().equals("integer")) {
					this.errores.add("Error de asignacionen la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un int a un bolean");

				}
				if(this.componenteLexico.getEtiqueta().equals("floatN")) {
					this.errores.add("Error de asignacion en la linea: "+this.componenteLexico.getLinea()+" no puedes asignar un float a un bolean");
				}
			}
			expresion_logica();
		}

	}
	private void instruccion() { //detecta fallos de gramatica segun el componente que uses en las instrucciones
		if(this.componenteLexico.getEtiqueta().equals("int")||this.componenteLexico.getEtiqueta().equals("float")||this.componenteLexico.getEtiqueta().equals("boolean")) {
			declaracion_variable();	
		}

		if(this.componenteLexico.getEtiqueta().equals("id")) {
			variable();
			compara("assignment");
			expresion_logica();
			compara("semicolon");
		}else if(this.componenteLexico.getEtiqueta().equals("if")) {
			compara("if");
			compara("open_parenthesis");
			expresion_logica();
			compara("closed_parenthesis");
			instruccion();
			if(this.componenteLexico.getEtiqueta().equals("else")) {
				compara("else");
				instruccion();
			}
		}
		else if(this.componenteLexico.getEtiqueta().equals("while")) {
			compara("while");
			compara("open_parenthesis");
			expresion_logica();
			compara("closed_parenthesis");
			compara("open_bracket");
			instrucciones();
			compara("closed_bracket");
		}else if(this.componenteLexico.getEtiqueta().equals("do")) {
			compara("do");
			instruccion();
			compara("while");
			compara("open_parenthesis");
			expresion_logica();
			compara("closed_parenthesis");
			compara("semicolon");
		}else if(this.componenteLexico.getEtiqueta().equals("print")) {
			compara("print");
			compara("open_parenthesis");
			variable();
			compara("closed_parenthesis");
			compara("semicolon");
		}else if(this.componenteLexico.getEtiqueta().equals("open_bracket")) {
			compara("open_bracket");
			instrucciones();
			compara("closed_bracket");
		}
	}
	private void instrucciones() {//es recursiva. Hasta que no termina de escribirse la funcion no deja de llamarse a si misma
		instruccion();
		if(this.componenteLexico.getEtiqueta().equals("int")||this.componenteLexico.getEtiqueta().equals("float")||this.componenteLexico.getEtiqueta().equals("boolean")
				||this.componenteLexico.getEtiqueta().equals("id")||this.componenteLexico.getEtiqueta().equals("if")||this.componenteLexico.getEtiqueta().equals("while")
				||this.componenteLexico.getEtiqueta().equals("do")||this.componenteLexico.getEtiqueta().equals("print")||this.componenteLexico.getEtiqueta().equals("open_square_bracket")

				){
			instrucciones();
		}
	}
	private void expresion_logica() {//para que funcione or
		termino_logico();
		if(this.componenteLexico.getEtiqueta().equals("or")) {
			compara("or");
			expresion_logica();
		}
	}
	private void termino_logico() {//para que funcione and
		factor_logico();
		if(this.componenteLexico.getEtiqueta().equals("and")) {
			compara("and");
			termino_logico();
		}
	}
	private void factor_logico() {//para que funcione not
		if(this.componenteLexico.getEtiqueta().equals("not")) {
			compara("not");
			factor_logico();
		}
		else if(this.componenteLexico.getEtiqueta().equals("true")) {//para que funcione true
			compara("true");
		}else if(this.componenteLexico.getEtiqueta().equals("false")) {//para que funcione false
			compara("false");
		}else {
			expresion_relacional();
		}
	}
	private void expresion_relacional() {//igual que antes pero con otras funciones
		expresion();
		if(this.componenteLexico.getEtiqueta().equals("greater_than")||this.componenteLexico.getEtiqueta().equals("greater_equals")||this.componenteLexico.getEtiqueta().equals("less_than")
				||this.componenteLexico.getEtiqueta().equals("less_equals")||this.componenteLexico.getEtiqueta().equals("equals")||this.componenteLexico.getEtiqueta().equals("not_equals")
				||this.componenteLexico.getEtiqueta().equals("assignment")
				){
			operador_relacional();
			expresion();
		}
	}
	private void operador_relacional() {
		if(this.componenteLexico.getEtiqueta().equals("greater_than")) {
			compara("greater_than");
		}
		else if(this.componenteLexico.getEtiqueta().equals("greater_equals")) {
			compara("greater_equals");
		}
		else if(this.componenteLexico.getEtiqueta().equals("less_than")) {
			compara("less_than");
		}else if(this.componenteLexico.getEtiqueta().equals("less_equals")) {
			compara("less_equals");
		}else if(this.componenteLexico.getEtiqueta().equals("equals")) {
			compara("equals");
		}else if(this.componenteLexico.getEtiqueta().equals("not_equals")) {
			compara("not_equals");
		}else if(this.componenteLexico.getEtiqueta().equals("assignment")) {
			compara("assignment");
		}
	}
	private void expresion() {
		termino();
		if(this.componenteLexico.getEtiqueta().equals("add")) {
			compara("add");
			expresion();
		}else if(this.componenteLexico.getEtiqueta().equals("subtract")) {
			compara("subtract");

			expresion();
		}

	}
	private void termino() {
		factor();
		if(this.componenteLexico.getEtiqueta().equals("multiply")) {
			compara("multiply");

			termino();
		}else if(this.componenteLexico.getEtiqueta().equals("divide")) {
			compara("divide");

			termino();
		}else if(this.componenteLexico.getEtiqueta().equals("remainder")) {
			compara("remainder");

			termino();
		}
	}
	private void factor() {
		if(this.componenteLexico.getEtiqueta().equals("open_parenthesis")) {
			compara("open_parenthesis");
			expresion();
			compara("closed_parenthesis");
		}else if(this.componenteLexico.getEtiqueta().equals("id")) {
			variable();
		}else if(this.componenteLexico.getEtiqueta().equals("integer")){
			compara("integer");
		}else if(this.componenteLexico.getEtiqueta().equals("floatN")) {

			compara("floatN");
		}

	}
	private void variable() {
		
		if(!busca(this.componenteLexico.getData())) {
			this.errores.add("ERROR en la linea: "+this.componenteLexico.getLinea()+" identificador "+this.componenteLexico.getData()+" no ha sido declarado");

		}
		compara("id");
		if(this.componenteLexico.getEtiqueta().equals("open_square_bracket")) {
			compara("open_square_bracket");
			expresion();
			compara("closed_square_bracket");
		}
	}


}
