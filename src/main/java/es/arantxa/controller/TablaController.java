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
        setupTableColumns();

        loadInitialData();
    }

    private void setupTableColumns() {
        tableView.getColumns().clear();

        tableView.getColumns().add(PersonTableUtil.getIdColumn());
        tableView.getColumns().add(PersonTableUtil.getFirstNameColumn());
        tableView.getColumns().add(PersonTableUtil.getLastNameColumn());
        tableView.getColumns().add(PersonTableUtil.getBirthDateColumn());
    }


    private void loadInitialData() {
        personList = PersonTableUtil.getPersonList();

        assignPersonIds();

        backupList = FXCollections.observableArrayList(personList);

        tableView.setItems(personList);
    }


    private void assignPersonIds() {
        for (int i = 0; i < personList.size(); i++) {
            personList.get(i).setPersonId(i + 1);
        }
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

            clearInputFields();

        } else {
            showErrorAlert(errors);
        }
    }

    @FXML
    void btnDeleteClick(ActionEvent event) {
        ObservableList<Person> selectedPersons = tableView.getSelectionModel().getSelectedItems();

        if (selectedPersons.isEmpty()) {
            showInfoAlert("No hay filas seleccionadas", "Por favor, selecciona al menos una fila para eliminar.");
            return;
        }

        List<Person> toRemove = new ArrayList<>(selectedPersons);

        personList.removeAll(toRemove);
    }

    @FXML
    void btnRestoreClick(ActionEvent event) {
        personList.clear();
        personList.addAll(backupList);

        assignPersonIds();
    }

    /**
     * Obtiene el siguiente ID disponible para una nueva persona
     */
    private int getNextPersonId() {
        return personList.stream()
                .mapToInt(Person::getPersonId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Limpia los campos de entrada
     */
    private void clearInputFields() {
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
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