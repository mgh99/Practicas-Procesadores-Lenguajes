package practica_4;

public abstract class TipoDato {

	
	private String tipo;
	
	public TipoDato(String tipo) {
		this.tipo = tipo;
	}

	public abstract String toString();
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
