package practica_5;

import java.nio.charset.StandardCharsets;

public class Programa {

	public static void main(String[] args) {
		
		Lexico lex=new Lexico("programa1.txt",StandardCharsets.UTF_8);
		Gramatica gramar=new Gramatica(lex);
		gramar.programa();
		gramar.TablaSimbolos();
		gramar.Error();


	}

}
