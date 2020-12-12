package practica_1;

public class IntegerFloatParsing {

	public static void main(String[] args) {

		String entero = "2020";
		int valorEntero = Integer.parseInt(entero);

		System.out.println ("El valor numerico de '" + entero + "' usando Integer.parseInt es " + valorEntero);

		valorEntero = valorEntero + 10;

		System.out.println ("Valor entero " + valorEntero);

		String decimal = "105.5";
		float valorDecimal = Float.parseFloat(decimal);

		System.out.println("El valor numerico de '" + decimal + "' usando Float.parseFloat es " + valorDecimal);

		valorDecimal += 0.75;

		System.out.println ("Valor decimal " + valorDecimal);
	}

}
