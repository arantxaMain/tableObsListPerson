package es.arantxa.controller;

import es.arantxa.model.Person;
import es.arantxa.util.PersonTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar la tabla de personas en la interfaz JavaFX.
 * Permite agregar, eliminar y restaurar personas, así como inicializar
 * la tabla con datos predefinidos.
 */
public class TablaController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(TablaController.class);

    @FXML
    private DatePicker dateBirth;

    @FXML
    private TableView<Person> tableView;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    private ObservableList<Person> personList;

    private ObservableList<Person> backupList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Inicializando TablaController...");
        setupTableColumns();
        loadInitialData();
        logger.info("Tabla inicializada con {} personas.", personList.size());
    }

    private void setupTableColumns() {
        tableView.getColumns().clear();

        tableView.getColumns().add(PersonTableUtil.getIdColumn());
        tableView.getColumns().add(PersonTableUtil.getFirstNameColumn());
        tableView.getColumns().add(PersonTableUtil.getLastNameColumn());
        tableView.getColumns().add(PersonTableUtil.getBirthDateColumn());
        logger.debug("Columnas de la tabla configuradas.");
    }


    private void loadInitialData() {
        personList = PersonTableUtil.getPersonList();
        assignPersonIds();
        backupList = FXCollections.observableArrayList(personList);
        tableView.setItems(personList);
        logger.debug("Datos iniciales cargados y backup creado.");
    }


    private void assignPersonIds() {
        for (int i = 0; i < personList.size(); i++) {
            personList.get(i).setPersonId(i + 1);
        }
        logger.debug("IDs de personas asignados.");
    }

    @FXML
    void btnAddClick(ActionEvent event) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthDate = dateBirth.getValue();

        Person newPerson = new Person(firstName, lastName, birthDate);

        List<String> errors = new ArrayList<>();
        if (newPerson.isValidPerson(errors)) {
            newPerson.setPersonId(getNextPersonId());

            personList.add(newPerson);
            logger.info("Persona agregada: {}", newPerson);
            clearInputFields();

        } else {
            logger.warn("Error al agregar persona: {}", errors);
            showErrorAlert(errors);
        }
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        ObservableList<Person> selectedPersons = tableView.getSelectionModel().getSelectedItems();

        if (selectedPersons.isEmpty()) {
            logger.info("Intento de eliminar sin selección.");
            showInfoAlert("No hay filas seleccionadas", "Por favor, selecciona al menos una fila para eliminar.");
            return;
        }

        List<Person> toRemove = new ArrayList<>(selectedPersons);
        personList.removeAll(toRemove);
        logger.info("Personas eliminadas: {}", toRemove);
    }

    @FXML
    void btnRestoreClick(ActionEvent event) {
        personList.clear();
        personList.addAll(backupList);

        assignPersonIds();
        logger.info("Lista restaurada a estado inicial con {} personas.", backupList.size());
    }

    /**
     * Obtiene el siguiente ID disponible para una nueva persona
     */
    private int getNextPersonId() {
        int nextId = personList.stream().mapToInt(Person::getPersonId).max().orElse(0) + 1;
        logger.debug("Siguiente ID disponible: {}", nextId);
        return nextId;
    }

    /**
     * Limpia los campos de entrada
     */
    private void clearInputFields() {
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        logger.debug("Campos de entrada limpiados.");
    }

    /**
     * Muestra un diálogo de error con la lista de errores
     */
    private void showErrorAlert(List<String> errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText("Los siguientes errores deben ser corregidos:");
        alert.setContentText(String.join("\n", errors));
        alert.showAndWait();
    }

    /**
     * Muestra un diálogo informativo
     */
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}