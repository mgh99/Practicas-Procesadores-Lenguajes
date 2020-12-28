package practica_5;

public class NumeroEntero extends ComponenteLexicoBasico{

	private String valor;

	public NumeroEntero(String etiqueta, String valor, int linea_) {
		super(etiqueta);
		super.setLinea(linea_);
		this.valor = valor;
	}

	public String getData() {
		return this.valor;
	}
	public String toString() {
		return "<"+super.getEtiqueta() + ", "+this.valor+">";
	}
}
