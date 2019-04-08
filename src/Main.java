import connections.mysql.MySQL;
import controller.Logs;

public class Main {

	public static void main(String[] args) {

		/*
		 * IMPORTANTE - El archivo SQL que dejo en el proyecto, el Stored Procedure a
		 * veces no se carga bien en PHPMyAdmin, no s� porque a veces lo le� bien y
		 * otras no.
		 */

		// Instanciamos la clase MySQL que est� se encargar� del acceso a datos a una
		// base de datos MySQL:
		// En este caso, al no pasarle nada, nos da un aviso de que tendremos que
		// indicarle en alg�n momento del programa a que base de datos queremos acceder.
		MySQL mysql = new MySQL();
		mysql.setDatabase("forhonor");
		// Y indicar que queremos crear la conexi�n con el m�todo connection();
		mysql.connection();

		// La clase Logs se encargar� de darnos la informaci�n sobre todos los
		// movimientos que se hagan en la Base de Datos.
		Logs log = new Logs();

		// Para activar los eventos que se efectuen en la base de datos mysql,
		// llamaremos al siguiente m�todo:
		mysql.addPropertyChangeListener(log);

		// Para insertar informaci�n nueva en la Base de Datos, llamaremos al m�todo
		// insert() y le pasaremos un String con la query, en caso de que el m�todo
		// devuelva un false, se printar� un mensaje de error.
		if (!mysql.insert("INSERT INTO facciones(faccion_name, lore) VALUES('B', 'b')")) {
			System.err.println("[ERROR] - No se pudieron insertar los datos.");
		}

		// Lo mismo en este caso para el m�todo delete();
		if (!mysql.delete("DELETE FROM facciones WHERE faccion_name = 'B'")) {
			System.err.println("[ERROR] - No se pudieron eliminar los datos.");
		}

		// Para llamar a un Stored Procedure que este en la base de datos, debemos de
		// pasarle un String con el call + nombre del Stored Procedure y el par�metro si
		// fuera
		// necesario
		mysql.callProcedure("call removeCharacter('B')");
		// Importante que cuando se finalice el programa, se cierre la conexi�n MySQL.
		mysql.close();

		// Printamos la informaci�n llamando a los m�todos de la Clase Logs.
		System.out.println("Resultados de la b�squeda: " + log.getSentenceFromDatabaseAndUser("forhonor", "root"));
		System.out.println("Resultados de la b�squeda: " + log.getSentence("forhonor", "root", "INSERT"));
		System.out.println("Resultados de la b�squeda: " + log.getSentenceFromDatabaseAndType("forhonor", "insert"));
	}

}
