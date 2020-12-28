package practica_3;

public class ComponenteLexico {

	private String etiqueta;
	private String valor;

	public ComponenteLexico(String etiqueta) {
		
		this.etiqueta = etiqueta;
		this.valor = "";
	}
	
	public ComponenteLexico(float f) {
		
	}
	
	public ComponenteLexico(String etiqueta, String valor) {
		
		this.etiqueta = etiqueta;
		this.valor = valor;
	}

	public String getEtiqueta() {

		return this.etiqueta;
	}

	public String getValor() {
		return this.valor;
	}

	public String toString() {

		if (this.valor.length() == 0) {
			
			return "<" + this.etiqueta + ">";
			
		}else {
			
			return "<" + this.etiqueta + ">";
		}
	}
}
