package practica_1;

import java.util.Hashtable;

public class TablaHash {
	
	/* La tabla Hash alumnos usa una clave entera (key) asociada
	 * con el nombre del alumno (String), la tabla notas usa
	 * una clave String y un valor float
	 */

	public static void main(String[] args) {

		Hashtable<Integer, String> alumnos =  new Hashtable<Integer, String>();

		alumnos.put(10, "Carlos");
		alumnos.put(20, "Luis");
		alumnos.put(30, "María");
		alumnos.put(40, "Pedro");
		alumnos.put(50, "Paula");
		alumnos.put(60, "Cristina");
		alumnos.put(70, "Javier");
		alumnos.put(80, "Laura");
		alumnos.put(90, "Fernando");
		alumnos.put(100, "Alicia");

		System.out.println("La tabla alumnos " + alumnos + "\n");
		System.out.println("<50>      " + alumnos.get(50));
		System.out.println("<80>      " +  alumnos.get(80));
		System.out.println("<30>      " + alumnos.get(30));
		System.out.println("<70>      " + alumnos.get(70)); 

		// si la clave de búsqueda no existe, devuelve null
		 System.out.println("<75>      " + alumnos.get(75)); 
		 
		 
		// tabla hash con clave de tipo String y valor de tipo float
		 Hashtable<String, Float> notas = new Hashtable<String, Float>();
		 
		 notas.put("Carlos", 7.0f);
		 notas.put("Paula", 9.0f);
		 notas.put("Cristina", 8.5f);
		 notas.put("Fernando", 7.5f);
		 notas.put("Alicia", 8.0f);

		 System.out.println("");
		 System.out.println("La tabla notas   " + notas + "\n");
		 System.out.println("<Carlos> " + notas.get("Carlos"));

		 float y = notas.get("Paula");

		 System.out.println("<Paula> " + y);
		 System.out.println("<Cristina> " + notas.get("Cristina"));
		 System.out.println("<Fernanda> " + notas.get("Fernanda"));
		 System.out.println("");

		 if (notas.containsKey("Paula"))
			 System.out.println("notas contiene la clave Paula");
		 else
			 System.out.println("notas no contiene la clave Paula");
		 if (notas.containsValue(9.0f))
			 System.out.println("notas contiene el valor 9.0f");
		 else
			 System.out.println("notas no contiene el valor 9.0f");

	}


}
