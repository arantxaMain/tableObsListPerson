package es.arantxa.util;

import java.time.LocalDate;
import javafx.collections.FXCollections;
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
     */
    public static ObservableList<Person> getPersonList() {
        Person p1 = new Person("Ashwin", "Sharan", LocalDate.of(2012, 10, 11));
        Person p2 = new Person("Advik", "Sharan", LocalDate.of(2012, 10, 11));
        Person p3 = new Person("Layne", "Estes", LocalDate.of(2011, 12, 16));
        Person p4 = new Person("Mason", "Boyd", LocalDate.of(2003, 4, 20));
        Person p5 = new Person("Babalu", "Sharan", LocalDate.of(1980, 1, 10));
        return FXCollections.observableArrayList(p1, p2, p3, p4, p5);
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
