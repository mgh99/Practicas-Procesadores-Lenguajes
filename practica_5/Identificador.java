package practica_5;

public class Identificador extends ComponenteLexicoBasico{
	@Override
	public String toString() {
		return "<"+super.getEtiqueta() + ", "+this.valor+">";
	}


	private String valor;

	public Identificador(String etiqueta, String valor,int linea_) {
		super(etiqueta);
		this.valor = valor;
		super.setLinea(linea_);
	}


	public String getData() {
		return this.valor;
	}
	
}
