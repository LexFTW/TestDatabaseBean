import connections.mysql.MySQL;
import controller.Logs;

public class Main {

	public static void main(String[] args) {

		/*
		 * IMPORTANTE - El archivo SQL que dejo en el proyecto, el Stored Procedure a
		 * veces no se carga bien en PHPMyAdmin, no sé porque a veces lo leé bien y
		 * otras no.
		 */

		// Instanciamos la clase MySQL que está se encargará del acceso a datos a una
		// base de datos MySQL:
		// En este caso, al no pasarle nada, nos da un aviso de que tendremos que
		// indicarle en algún momento del programa a que base de datos queremos acceder.
		MySQL mysql = new MySQL();
		mysql.setDatabase("forhonor");
		// Y indicar que queremos crear la conexión con el método connection();
		mysql.connection();

		// La clase Logs se encargará de darnos la información sobre todos los
		// movimientos que se hagan en la Base de Datos.
		Logs log = new Logs();

		// Para activar los eventos que se efectuen en la base de datos mysql,
		// llamaremos al siguiente método:
		mysql.addPropertyChangeListener(log);

		// Para insertar información nueva en la Base de Datos, llamaremos al método
		// insert() y le pasaremos un String con la query, en caso de que el método
		// devuelva un false, se printará un mensaje de error.
		if (!mysql.insert("INSERT INTO facciones(faccion_name, lore) VALUES('B', 'b')")) {
			System.err.println("[ERROR] - No se pudieron insertar los datos.");
		}

		// Lo mismo en este caso para el método delete();
		if (!mysql.delete("DELETE FROM facciones WHERE faccion_name = 'B'")) {
			System.err.println("[ERROR] - No se pudieron eliminar los datos.");
		}

		// Para llamar a un Stored Procedure que este en la base de datos, debemos de
		// pasarle un String con el call + nombre del Stored Procedure y el parámetro si
		// fuera
		// necesario
		mysql.callProcedure("call removeCharacter('B')");
		// Importante que cuando se finalice el programa, se cierre la conexión MySQL.
		mysql.close();

		// Printamos la información llamando a los métodos de la Clase Logs.
		System.out.println("Resultados de la búsqueda: " + log.getSentenceFromDatabaseAndUser("forhonor", "root"));
		System.out.println("Resultados de la búsqueda: " + log.getSentence("forhonor", "root", "INSERT"));
		System.out.println("Resultados de la búsqueda: " + log.getSentenceFromDatabaseAndType("forhonor", "insert"));
	}

}
