package practica_5;

//para definir tipo primitivo
public class TipoPrimitivo extends TipoDato{
	public TipoPrimitivo(String t) {
		super.setTipo(t);
	}
	@Override
	public String toString() {
		return super.getTipo();
		}

}
