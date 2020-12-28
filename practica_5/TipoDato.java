package practica_5;

public abstract class TipoDato{
	private String tipo;
	protected void setTipo(String t) {
		this.tipo=t;
	}
	public String getTipo() {
		return tipo;
	}
	
	public abstract String toString();
}
