package es.arantxa.dao;

import es.arantxa.connection.DBConnection;
import es.arantxa.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO (Data Access Object) para la entidad {@link es.arantxa.model.Person}.
 * Proporciona procedimientos para interactuar con la base de datos y recuperar
 * información de personas.
 */
public class PersonDAO {
    /**
     * Recupera todas las personas almacenadas en la tabla {@code personas} de la base de datos
     * y las devuelve como un {@link ObservableList}.
     *
     * @return un {@link ObservableList} de objetos {@link es.arantxa.model.Person} con los datos de la base de datos
     * @throws Exception si ocurre algún error al conectar con la base de datos o ejecutar la consulta
     */
    public ObservableList<Person> listarPersonas() throws Exception {
        ObservableList<Person> lstPersonas = FXCollections.observableArrayList();
        String sql = "SELECT person_id, first_name, last_name, birth_date FROM personas";

        try (Connection conn = DBConnection.conexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
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
        }
        return lstPersonas;
    }

    /**
     * Elimina una persona de la base de datos según su identificador único.
     *
     * @param personId el identificador único de la persona a eliminar
     * @return {@code true} si la persona fue eliminada con éxito,
     * {@code false} si no se encontró ninguna coincidencia
     * @throws Exception si ocurre un error al conectar con la base de datos o ejecutar la operación
     */
    public boolean borrarPersona(int personId) throws Exception {
        String sql = "DELETE FROM personas WHERE person_id = ?";

        try (Connection conn = DBConnection.conexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personId);
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    /**
     * Inserta una nueva persona en la base de datos con los valores
     * proporcionados en el objeto {@link es.arantxa.model.Person}.
     *
     * @param p el objeto {@link es.arantxa.model.Person} que contiene
     *          el nombre, apellidos y fecha de nacimiento de la persona
     * @return {@code true} si la persona fue insertada correctamente,
     * {@code false} si no se insertó ningún registro
     * @throws Exception si ocurre un error al conectar con la base de datos o ejecutar la operación
     */
    public boolean insertarPersona(Person p) throws Exception {
        String sql = "INSERT INTO personas (first_name, last_name, birth_date) values (?, ?, ?) ";

        try (Connection conn = DBConnection.conexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getFirstName());
            stmt.setString(2, p.getLastName());
            stmt.setDate(3, java.sql.Date.valueOf(p.getBirthDate()));

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        }
    }

}
