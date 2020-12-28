package practica_5;

public abstract class ComponenteLexicoBasico {
	
	private String etiqueta;
	private int linea;
	
	public int getLinea() {
		return linea;
	}
	
	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	public ComponenteLexicoBasico(String etiqueta) {
		this.etiqueta=etiqueta;
	}
	
	public ComponenteLexicoBasico(String etiqueta,String valor) {
		this.etiqueta=etiqueta;
	}
	
	public String getEtiqueta() {
		return this.etiqueta;
	}
	
	public abstract String getData();

}
