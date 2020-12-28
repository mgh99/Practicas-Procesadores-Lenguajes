package practica_5;

//definir arrays

public class TipoArray extends TipoDato{
	private int tamaño;
	public TipoArray(int tamaño,String t) {
		this.tamaño=tamaño;
		super.setTipo(t);
	}
	public String toString() {
		return super.getTipo() +" ["+this.tamaño+"] ";

	}

}
