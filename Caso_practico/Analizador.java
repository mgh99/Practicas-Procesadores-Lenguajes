import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

//convertir = castear

public class Analizador {

	private ComponenteLexico componenteLexico; //al java.class
	private Lexico lexico; //indica lo que va a contener al java.call de lexico
	private Hashtable<String, TipoDato> simbolos; 
	private Vector<String> errores = new Vector<String>(); //inicializa
	private String comparaciontipo=null; 
	//tipo objeto que desde la libreria ya está templatizado puede adoptar cualquier tipo de dato (podría ser un String) => Si fuese un Object

	//constructor
	public Analizador(Lexico lexico) {
		this.simbolos = new Hashtable<String, TipoDato>();
		this.lexico = lexico;
		this.componenteLexico = this.lexico.getComponenteLexico(); //coge las palabras reservadas
	}
	
	// solo se referencia desde el main (es la ultima) y se llama tantas veces como letras a sido declaradas
	public String tablaSimbolos() {
		String simbolos = "";

		//mapeamos 
		Set<Map.Entry<String, TipoDato>> s = this.simbolos.entrySet();

		//simbolos añade que el k.key (nombre de la varible que es el string) y el k.value 
		//( devuelve el tipo de dato y es un string porque es primitivo)
		for(Map.Entry<String, TipoDato> k: s)
			simbolos = simbolos + "<'" + k.getKey() + "'," + k.getValue().toString() + "> \n"; //se guarda simbolos

		return simbolos;
	}

	//comprueba de que sea igual al token y avanza
	private void compara(String etiqueta) {
		
		if(this.componenteLexico.getEtiqueta().equals(etiqueta)) {
			this.componenteLexico = this.lexico.getComponenteLexico(); //AVANZA
		}else 
			System.out.println("Error, se esperaba " + etiqueta);
	}

	//
	//programa -> void main { declaraciones }
	public void programa() {
		compara("void");
		compara("main");
		compara("open_bracket"); // {

		declaraciones(); 
		instrucciones();

		compara("closed_bracket"); // }

	} 

	//declaraciones -> declaración-variable declaraciones | epsilon
	// llama tanto porque busca más variables y cuando deja de encontrarlas sale al epsilon
	private void declaraciones() {
		String etiqueta = this.componenteLexico.getEtiqueta();

		if(etiqueta.equals("int") || etiqueta.equals("float") || etiqueta.equals("boolean")) {
			declaracionVariable();
			declaraciones();
		}else {
			//epsilon
		}

	}

	
	public void declaracionVariable() {
		String tipo = tipoPrimitivo();// te dice el tipo que es Int o FLoat boolean
		int tamaño = 1; 
		
		// partimos de que tipoPrimitivo ya ha hecho el cambio 
		//si despues del tipo viene un open_square_bracket es una declacion de un tipo-vector, 
		//en caso contrario, es una lista de identificadores de tipos primitivos int o float 

		//this.componente lexico es una variable 
		//TIPO VECTOR
			if (this.componenteLexico.getEtiqueta().equals("open_square_bracket")){ 
				// tipo-vector 

				compara("open_square_bracket");  // [ avanza

				if(this.componenteLexico.getEtiqueta().equals("int")) {
					NumeroEntero numero = (NumeroEntero) this.componenteLexico; //casting = convertir
					tamaño = numero.getValor();
				}
				compara("int");// avance
				compara("closed_square_bracket");  // ] completa vector y avanza

				if(this.componenteLexico.getEtiqueta().equals("id")) {
					Identificador id = (Identificador) this.componenteLexico;
					this.simbolos.put(id.getLexema(), new TipoArray(tipo,tamaño));
				}

				compara("id");
				compara("semicolon"); // ;

			}else { // tipo-primitivo
				this.comparaciontipo = tipo;
				
				//tipo primitivo int o float , boolean
				listaDeIdentificadores(tipo);
				compara("semicolon");
				this.comparaciontipo = null; //verificacion de tipos

			}
		}

	
	//lista-identificadores -> id asignacion-declaración más-identificadores
	//identificadores se igualen a int a float o a boolean
	private void listaDeIdentificadores(String tipo) {
		if(this.componenteLexico.getEtiqueta().equals("id")) {// tipo: ya te hace la transicion, es decir, avanza (es de Santi el comentario)

			Identificador id = (Identificador) this.componenteLexico; //variable id es cun componente lexico
			if(this.simbolos.get(id.getLexema())!=null) { //null: no lo entra
				this.errores.add("ERROR, en la linea "+this.lexico.getLineas()+" , varibale "+
						id.getLexema() +" ya ha sido declarada");
			}else {
				//añade el lexema del id y su tipo a la tabla de simbolos 
				simbolos.put(id.getLexema(), new TipoPrimitivo(tipo));
			}
			compara("id"); //avanza
			asignacion();// pasar el id , para la tabla de simbolos.
			masIdentificadores(tipo); 

		}
	}

	//más-identificadores -> , id asignacion-declaración más-identificadores | epsilon
	private void masIdentificadores(String tipo) {

		if(this.componenteLexico.getEtiqueta().equals("comma")) {
			compara("comma"); // , 

			if(this.componenteLexico.getEtiqueta().equals("id")) {

				Identificador id = (Identificador) this.componenteLexico; //casting = convertir
				this.simbolos.put(id.getLexema(), new TipoPrimitivo(tipo)); 

				compara("id");
				asignacion();
				masIdentificadores(tipo); //recursivo pq busca más identificadores
			}

		}else {
			//epsilon
		}
	}

	//asignacion -> = expresión-logica | epsilon
	public void asignacion() {
		if(this.componenteLexico.getEtiqueta().equals("assignment")) { //=
			compara("assignment"); //avanza
			expresionLogica(); //llama a expresion logica
		}else {
			//epsion
		}

	}


	//tipo-primitivo -> int | float | boolean
	private String tipoPrimitivo() {
		String tipo = this.componenteLexico.getEtiqueta(); 

		if(tipo.equals("int")) {
			compara("int");
		}
		else if(tipo.equals("float")) {
			compara("float");
		}else if(tipo.equals("boolean")) {
			compara("boolean");
		}else{
			System.out.println("Error, se esperaba un tipo de dato Int , Float , Boolean");
		}
		return tipo;
	}

	//instrucciones -> 	instrucción instrucciones | epsilon
	public void instrucciones() { 
		if(this.componenteLexico.getEtiqueta().equals("closed_bracket")) {
			//no hay instruccion que no es lo mismo que no haya epsilon
			//es decir aquí por fin se acaba el programa --FIN
		}else {
			instruccion();
			instrucciones(); //recursivo
		}

	}

	/*instrucción -> declaración-variable |
					 variable = expresión-logica ; | linea 199 - 208
					 if (expresión-lógica) instrucción |
					 if (expresión-lógica) instrucción else instrucción |
					 while (expresión-lógica) instrucción |
 					 do instrucción while (expresión-lógica) ; |
					 print (variable) ; |
					 { instrucciones }
	 */
	//detecta fallos de gramatica segun el componente que uses en las instrucciones
	private void instruccion() { 
		
		if(this.componenteLexico.getEtiqueta().equals("int")||this.componenteLexico.getEtiqueta().equals("float")
				||this.componenteLexico.getEtiqueta().equals("boolean")) {
			declaracionVariable();
		}else if(this.componenteLexico.getEtiqueta().equals("id")){
			Identificador id =(Identificador) this.componenteLexico;
			variable();
			compara("assignment");
			
			//? cuando la instruccion es verdadera (es la instrucccion igual a true)
			//: cuando la instruccion no es verdadera ( la instruccion es igual a false)
			
			// si  existe (null) que ta se ha declarado la variable sino (this.simbolos.get(id.getLexema()).getTipo())
			//this.comparaciontipo=(this.simbolos.get(id.getLexema())==null)?null:this.simbolos.get(id.getLexema()).getTipo(); es lo
			//mismo que el if de a continuación
			
			if(this.simbolos.get(id.getLexema())==null){
				this.comparaciontipo=null;
			}else{
				this.comparaciontipo=this.simbolos.get(id.getLexema()).getTipo();
			}

			expresionLogica();
			this.comparaciontipo=null; //corta la direcciona lo que tenia, es decir, vacío no apunta a nada
			compara("semicolon");

		}else if(this.componenteLexico.getEtiqueta().equals("if")) {
			compara("if");
			compara("open_parenthesis");
			expresionLogica();
			compara("closed_parenthesis");
			instruccion();
		}else if(this.componenteLexico.getEtiqueta().equals("else")) {
			compara("else");
			instruccion();
		}else if(this.componenteLexico.getEtiqueta().equals("while")) {
			compara("while");
			compara("open_parenthesis");
			expresionLogica();
			compara("closed_parenthesis");
			if(this.componenteLexico.getEtiqueta().equals("semicolon")) {
				compara("semicolon");
			}else {
				instrucciones();
			}
		}else if(this.componenteLexico.getEtiqueta().equals("do")) {
			compara("do");
			instruccion();
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

	/*expresion -> expresión + término |
	 * 			   expresión – término |
	 * 			   término
	 * 
	 * Quitando la recursividad por la derecha 
	 * 
	 * expresion => termino expresionPrima
	 * 		ExpresionPrima=> + termino expresionPrima
	 * 					  => - termino expresionPrima
	 * 					  => epsilon
	 */
	
	private void expresion() {
		termino();
		expresionPrima();
	}
	
	private void expresionPrima() {
		if(this.componenteLexico.getEtiqueta().equals("add")) {
			compara("add");
			termino();
			expresionPrima();
		}else if(this.componenteLexico.getEtiqueta().equals("subtract")) {
			compara("subtract");
			termino();
			expresionPrima();
		}else {
			//epsilon
		}
	}
	
	/*termino -> término * factor |
	 * 		   	 término / factor |
	 * 			 término % factor |
	 * 			 factor
	 * Quitando la recursividad por la izquierda
	 * 
	 * Termino=> factor Termino'
	 * 		Termino'=> * factor Termino'
	 * 		Termino'=> / factor Termino'
	 * 		Termino'=> % factor Termino'
	 * 		Termino'=> epsilon
	 */
	
	private void termino() {
		factor();
		terminoPrima();
	}

	private void terminoPrima() {
		if(	this.componenteLexico.getEtiqueta().equals("multiply")){
			compara("multiply");
			factor();
			terminoPrima();
		}else if(this.componenteLexico.getEtiqueta().equals("divide")) {
			compara("divide");
			factor();
			terminoPrima();
		}else if(this.componenteLexico.getEtiqueta().equals("remainder") ) {
			compara("remainder");
			factor();
			terminoPrima();
		}else {
			//epsilon
		}
	}

	/*factor ->  ( expresión ) |
	 * 			  variable |
	 *  		  num
	 */
	
	private void factor() {

		if (this.componenteLexico.getEtiqueta().equals("open_parenthesis")) {
			compara("open_parenthesis");
			expresion();
			compara("closed_parenthesis");
		}
		else if (this.componenteLexico.getEtiqueta().equals("int")) {
			if(this.comparaciontipo != null) {
				if(!this.comparaciontipo.equals("int")) {
					this.errores.add("ERROR, en la linea " + this.lexico.getLineas() + ""
							+ " se intenta asignar un " + this.comparaciontipo + " con un int");
				}
			}
			compara("int");
		}else if(this.componenteLexico.getEtiqueta().equals("float")) { 
			if(this.comparaciontipo != null) {
				if(!this.comparaciontipo.equals("float")) {
					this.errores.add("ERROR, en la linea " + this.lexico.getLineas() + ""
							+ " se intenta asignar un " + this.comparaciontipo + " con un float");
				}
			}
			compara("float");
		}else {
			variable();
		}	
	}

	//variable ->  id | id [ expresión ]
	private void variable() {
		if(this.componenteLexico.getEtiqueta().equals("id")) {
			Identificador id= (Identificador) this.componenteLexico;
			if(this.simbolos.get(id.getLexema())==null) {// si no encuentra el id en la tabla 

				this.errores.add("ERROR, en la linea "+this.lexico.getLineas()+" , varibale "+id.getLexema() +" no ha sido declarada");
			}// si lo ha sido
			compara("id");// avanzamos 
			if (this.componenteLexico.getEtiqueta().equals("open_square_bracket")) {//miramos si es un vector
				compara("open_square_bracket");  // [
				expresion();
				compara("closed_square_bracket");  // ]
			}
			if(this.comparaciontipo!=null) { //para ver si es compatible con el siguiente id y si son diferentes te sale el error
				if(this.simbolos.get(id.getLexema()).getTipo()!= this.comparaciontipo.toString()) {
					this.errores.add("ERROR, en la linea "+this.lexico.getLineas()+" ,incompatibilidad de tipos en la instruccion de asignacion");
				}
			}
		}
	}

	/*expresion-logico -> expresion-logica || termino-logico |
	 * 					  termino-logico
	 * 
	 * Quitando la recursividad por la izquierda
	 * 
	 * expresionlogica -> termino-logico ExpresionLogica' | 
	 * 					EXP'=>	|| termino logico EXP' | epsilon
	 */
	private void expresionLogica() {
		terminoLogico();
		expresionLogicaPrima();

	}
	private void expresionLogicaPrima(){
		if (this.componenteLexico.getEtiqueta().equals("or")) {
			compara("or");
			terminoLogico();
			expresionLogicaPrima();
		}else {
			//epsilon
		}	
	}

	/*termino-logico -> termino-logico && factor-logico | 
	 * 					factor-logico
	 * 
	 * Quitando la recursividad por la izquierda
	 * Termino logico => factor Logico terminoLogico' 
	 * 		TerminoLogico'=> && factor logico Terminologico' | epsilon
	 */
	private void terminoLogico() {
		factorLogico();
		terminoLogicoPrimo();
	}
	private void terminoLogicoPrimo() {
		if (this.componenteLexico.getEtiqueta().equals("and")) {
			compara("and");
			factorLogico();
			terminoLogicoPrimo();
		}else {
			//epsilon
		}		
	}

	/*factor-logico -> ! factor-logico | true | false | 
	 * 				   expresion-relacional
	 */
	private void factorLogico() {
		if (this.componenteLexico.getEtiqueta().equals("not")) {
			compara("not");
			factorLogico();
		}else if(this.componenteLexico.getEtiqueta().equals("true")) {
			compara("true");
		}else if(this.componenteLexico.getEtiqueta().equals("false")) {
			compara("false");
		}else {
			expresionRelacional();
		}
	}

	/*expresion-relacional -> expresión operador-relacional expresión |
	 * 						  expresion
	 */
	private void expresionRelacional() { 
		expresion();
		if( 
				this.componenteLexico.getEtiqueta().equals("greater_than") ||
				this.componenteLexico.getEtiqueta().equals("greater_equals") ||
				this.componenteLexico.getEtiqueta().equals("less_than") ||
				this.componenteLexico.getEtiqueta().equals("less_equals") ||
				this.componenteLexico.getEtiqueta().equals("equals") ||
				this.componenteLexico.getEtiqueta().equals("not_equals")
				) {
			operadorRelacional();
			expresion();
		}
	}

	//operador-relacional -> < | <= | > | >= | == | !=
	private void operadorRelacional() {
		if (this.componenteLexico.getEtiqueta().equals("less_than")) {
			compara("less_than");
			factor();	
		}
		else if (this.componenteLexico.getEtiqueta().equals("less_equals")) {
			compara("less_equals");
			factor();
		}
		else if (this.componenteLexico.getEtiqueta().equals("greater_than")) {
			compara("greater_than");
			factor();
		}
		else if (this.componenteLexico.getEtiqueta().equals("greater_equals")) {
			compara("greater_equals");
		}
		else if (this.componenteLexico.getEtiqueta().equals("equals")) {
			compara("equals");
			factor();
		}
		else if (this.componenteLexico.getEtiqueta().equals("not_equals")) {
			compara("not_equals");
			factor();
		}
	}
	
	
	//////////////////////////////
	public String errores() {
		
		//si el programa está vacío de errores es que funciona correctamente
		if(this.errores.isEmpty()) {
			return "El programa ha compilado correctamente";
		}else {
			//Sino se crea un String inicializado como una cadena vacía
			String deVuelta="";
			
			//para cada elemento de la lista de errores que son de tipo String 
			//muestrame cada  elemento == muestrame cada String
			for(String elem: this.errores) {
				deVuelta+="*"+elem+"\n";
			}
			return deVuelta;
		}
	}
}
