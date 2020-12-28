package practica_5;

public class NumeroReal extends ComponenteLexicoBasico{

	private String valor;
	
	public NumeroReal(String etiqueta, String valor,int linea_) {
		super(etiqueta);
		this.valor = valor;
		super.setLinea(linea_);
	}
	
	public String getData() {
		return this.valor;
	}
	public String toString() {
		return "<"+super.getEtiqueta() + ", "+this.valor+">";
	}
}
