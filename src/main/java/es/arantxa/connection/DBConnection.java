package es.arantxa.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Clase que gestiona la conexión con la base de datos de forma asíncrona.
 */
public class DBConnection {

    /**
     * Establece una conexión con la base de datos de forma asíncrona.
     *
     * @return un CompletableFuture que representa la conexión
     */
    public static CompletableFuture<Connection> conexionAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Properties props = new Properties();
                try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    props.load(input);
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                return DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                throw new RuntimeException("Error al conectar con la base de datos", e);
            }
        });
    }
}
