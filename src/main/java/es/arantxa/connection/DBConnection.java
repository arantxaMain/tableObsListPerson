package es.arantxa.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexión con la base de datos MariaDB.
 * <p>
 * La configuración de conexión (URL, usuario y contraseña) se obtiene
 * desde un archivo de propiedades llamado {@code db.properties}.
 * </p>
 */
public class DBConnection {

    /**
     * Establece y devuelve una conexión a la base de datos utilizando
     * los parámetros definidos en el archivo {@code db.properties}.
     *
     * @return un objeto {@link Connection} válido para interactuar con la base de datos
     * @throws Exception si ocurre un error al cargar las propiedades
     *                   o al establecer la conexión mediante JDBC
     */
    public static Connection conexion() throws Exception {
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
