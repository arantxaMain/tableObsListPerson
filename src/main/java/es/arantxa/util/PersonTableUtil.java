package es.arantxa.util;

import java.time.LocalDate;

import es.arantxa.dao.PersonDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import es.arantxa.model.Person;

/**
 * Utilidades para trabajar con objetos {@link Person} en el contexto de JavaFX.
 * Proporciona funciones para generar listas observables de personas y para
 * crear columnas configuradas que pueden ser añadidas a una {@code TableView}.
 */
public class PersonTableUtil {

    /**
     * Devuelve una lista observable con un conjunto de personas de ejemplo.
     * Esta lista se puede usar directamente como modelo de datos en una {@code TableView}.
     *
     * @return una lista observable de objetos {@link Person}
     * @throws Exception si ocurre un error al acceder a la base de datos
     */
    public static ObservableList<Person> getPersonList() throws Exception {
        PersonDAO personDAO = new PersonDAO();
        return personDAO.listarPersonas();
    }

    /**
     * Crea y devuelve una columna de tabla configurada para mostrar el
     * identificador único de una persona.
     *
     * @return columna de tipo {@code TableColumn<Person, Integer>}
     */
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> personIdCol = new TableColumn<>("Id");
        personIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        return personIdCol;
    }

    /**
     * Crea y devuelve una columna de tabla configurada para mostrar
     * el nombre de pila de una persona.
     *
     * @return columna de tipo {@code TableColumn<Person, String>}
     */
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        return fNameCol;
    }

    /**
     * Crea y devuelve una columna de tabla configurada para mostrar
     * el apellido de una persona.
     *
     * @return columna de tipo {@code TableColumn<Person, String>}
     */
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        return lastNameCol;
    }

    /**
     * Crea y devuelve una columna de tabla configurada para mostrar
     * la fecha de nacimiento de una persona.
     *
     * @return columna de tipo {@code TableColumn<Person, LocalDate>}
     */
    public static TableColumn<Person, LocalDate> getBirthDateColumn() {
        TableColumn<Person, LocalDate> bDateCol = new TableColumn<>("Birth Date");
        bDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        return bDateCol;
    }
}
