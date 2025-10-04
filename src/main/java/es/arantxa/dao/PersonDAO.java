package es.arantxa.dao;

import es.arantxa.connection.DBConnection;
import es.arantxa.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

/**
 * DAO (Data Access Object) para la entidad {@link Person}.
 * Proporciona procedimientos para interactuar con la base de datos y recuperar
 * información de personas.
 */
public class PersonDAO {
    /**
     * Recupera todas las personas almacenadas en la tabla {@code personas} de la base de datos
     * y las devuelve como un {@link ObservableList}.
     *
     * @return un {@link ObservableList} de objetos {@link Person} con los datos de la base de datos
     * @throws RuntimeException si ocurre algún error al conectar con la base de datos o ejecutar la consulta
     */
    public CompletableFuture<ObservableList<Person>> listarPersonasAsync() {
        String sql = "SELECT person_id, first_name, last_name, birth_date FROM personas";

        return DBConnection.conexionAsync().thenApply(conn -> {
            ObservableList<Person> lstPersonas = FXCollections.observableArrayList();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Person p = new Person(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("birth_date").toLocalDate()
                    );
                    p.setPersonId(rs.getInt("person_id"));
                    lstPersonas.add(p);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return lstPersonas;
        });
    }


    /**
     * Elimina una persona de la base de datos según su identificador único.
     *
     * @param personId el identificador único de la persona a eliminar
     * @return {@code true} si la persona fue eliminada con éxito,
     * {@code false} si no se encontró ninguna coincidencia
     * @throws RuntimeException si ocurre algún error al conectar con la base de datos o ejecutar la consulta
     */
    public CompletableFuture<Boolean> borrarPersonaAsync(int personId) {
        String sql = "DELETE FROM personas WHERE person_id = ?";

        return DBConnection.conexionAsync().thenApply(conn -> {
            try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {
                try {
                    stmt.setInt(1, personId);
                    int rows = stmt.executeUpdate();
                    return rows > 0;

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException ignored) {
            }
            return null;
        });
    }

    /**
     * Elimina todas las personas de la base de datos.
     *
     * @return {@code true} si se eliminaron todas las personas con éxito,
     * {@code false} si no se eliminaron ninguna persona
     * @throws RuntimeException si ocurre un error al conectar con la base de datos o ejecutar la operación
     */
    public CompletableFuture<Void> borrarTodasPersonasAsync() {
        String sql = "DELETE FROM personas";

        return DBConnection.conexionAsync().thenAccept(conn -> {
            try (conn; Statement stmt = conn.createStatement()) {
                try {
                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException ignored) {
            }
        });
    }

    /**
     * Inserta una nueva persona en la base de datos con los valores
     * proporcionados en el objeto {@link Person}.
     *
     * @param person el objeto {@link Person} que contiene
     *          el nombre, apellidos y fecha de nacimiento de la persona
     * @return {@code true} si la persona fue insertada correctamente,
     * {@code false} si no se insertó ningún registro
     * @throws RuntimeException si ocurre un error al conectar con la base de datos o ejecutar la operación
     */
    public CompletableFuture<Boolean> insertarPersonaAsync(Person person) {
        String sql = "INSERT INTO personas (first_name, last_name, birth_date) VALUES (?, ?, ?)";

        return DBConnection.conexionAsync().thenApply(conn -> {
            try (conn; PreparedStatement stmt = conn.prepareStatement(sql)) {
                try {
                    stmt.setString(1, person.getFirstName());
                    stmt.setString(2, person.getLastName());
                    stmt.setDate(3, Date.valueOf(person.getBirthDate()));

                    int rows = stmt.executeUpdate();
                    return rows > 0;

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException ignored) {
            }
            return null;
        });
    }

}
