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
}
