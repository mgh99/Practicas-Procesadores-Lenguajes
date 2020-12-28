package practica_2;

public class TipoArray extends TipoDato {
	
	private int tamaño;
	
	public TipoArray(String tipo, int tamaño) {
		super(tipo);
		
		this.tamaño = tamaño;
	}
	
	public String toString() {
		return super.getTipo() + "[" + this.tamaño + "]";
	}
	
	public int getTamaño() {
		return tamaño;
	}
	
	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}

}
