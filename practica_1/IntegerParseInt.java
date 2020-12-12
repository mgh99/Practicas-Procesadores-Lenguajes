package practica_1;

public class IntegerParseInt {
	
public static int parseInt(String numero) {
		
		char [] digitos = numero.toCharArray();
		int valorEntero = 0;
		
		for (int i = 0; i < digitos.length; i++) {
			
			valorEntero = 10 * valorEntero + Character.digit(digitos[i], 10);
		}
		
		return valorEntero;
	}

	public static void main(String[] args) {

		String numero = "2020";
		
		System.out.println ("El valor numerico de '" + numero + "' es " + parseInt(numero) + " y usando Integer.parseInt " +
				Integer.parseInt(numero));
		
		/*int valorEntero = Integer.parseInt(numero);

		System.out.println ("El valor numerico de '" + numero + "' usando Integer.parseInt es " + 
				valorEntero);
		
		valorEntero += 10;
		System.out.println("Valor entero " + valorEntero);
		
		String decimal = "105.5";
		float valorDecimal = Float.parseFloat(decimal);
		
		System.out.println ("El valor numerico de '" + decimal + "' usando Float.parseFloat es " + 
				valorDecimal);
		
		valorDecimal += 0.75;
		System.out.println("Valor entero " + valorDecimal);*/
				
	}

}
