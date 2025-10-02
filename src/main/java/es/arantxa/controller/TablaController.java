package es.arantxa.controller;

import es.arantxa.dao.PersonDAO;
import es.arantxa.model.Person;
import es.arantxa.util.AlertUtil;
import es.arantxa.util.PersonTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.text.MessageFormat;
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
    private PersonDAO personDAO;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        logger.info(resources.getString("log.init"));
        personDAO = new PersonDAO();
        setupTableColumns();
        loadInitialData();
        logger.info(MessageFormat.format(resources.getString("log.tableInit"), personList.size()));
    }

    /**
     * Configura las columnas de la tabla y establece el modo de selección múltiple.
     * Añade las columnas para ID, nombre, apellido y fecha de nacimiento.
     */
    private void setupTableColumns() {
        tableView.getColumns().clear();

        tableView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        tableView.getColumns().add(PersonTableUtil.getIdColumn());
        tableView.getColumns().add(PersonTableUtil.getFirstNameColumn());
        tableView.getColumns().add(PersonTableUtil.getLastNameColumn());
        tableView.getColumns().add(PersonTableUtil.getBirthDateColumn());
        logger.debug(resources.getString("log.columnsConfigured"));
    }

    /**
     * Carga los datos iniciales desde la base de datos y crea una copia de respaldo.
     * En caso de error, inicializa listas vacías para que la aplicación pueda seguir funcionando.
     */
    private void loadInitialData() {
        try {
            personList = personDAO.listarPersonas();
            backupList = FXCollections.observableArrayList(personList);
            tableView.setItems(personList);
            logger.debug(resources.getString("log.dataLoaded"));
        } catch (Exception e) {
            logger.error("Error al cargar datos desde la base de datos", e);
            AlertUtil.mostrarError(
                    resources.getString("alert.error.connection"),
                    MessageFormat.format(resources.getString("alert.error.connectionMsg"), e.getMessage())
            );
            personList = FXCollections.observableArrayList();
            backupList = FXCollections.observableArrayList();
            tableView.setItems(personList);
        }
    }

    /**
     * Maneja el evento de clic en el botón de agregar.
     * Crea una nueva persona con los datos ingresados y la guarda en la base de
     * datos.
     *
     * @param event el evento de acción que desencadenó esta función
     */
    @FXML
    void btnAddClick(ActionEvent event) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthDate = dateBirth.getValue();

        Person newPerson = new Person(firstName, lastName, birthDate);

        List<String> errors = new ArrayList<>();
        if (newPerson.isValidPerson(errors)) {
            try {
                if (personDAO.insertarPersona(newPerson)) {
                    personList.clear();
                    personList.addAll(personDAO.listarPersonas());

                    logger.info(MessageFormat.format(resources.getString("log.personAdded"), newPerson));
                    clearInputFields();
                }
            } catch (Exception e) {
                logger.error("Error al insertar persona en la base de datos", e);
                AlertUtil.mostrarError(
                        resources.getString("alert.error.save"),
                        MessageFormat.format(resources.getString("alert.error.saveMsg"), e.getMessage())
                );
            }
        } else {
            logger.warn(MessageFormat.format(resources.getString("log.addError"), errors));
            AlertUtil.mostrarErrorValidacion(errors);
        }
    }

    /**
     * Maneja el evento de clic en el botón de eliminar.
     * Elimina las personas seleccionadas de la base de datos.
     *
     * @param event el evento de acción que desencadenó esta función
     */
    @FXML
    void btnDeleteClick(ActionEvent event) {
        ObservableList<Person> selectedPersons = tableView.getSelectionModel().getSelectedItems();

        if (selectedPersons.isEmpty()) {
            logger.info(resources.getString("log.deleteAttempt"));
            AlertUtil.mostrarInformacion(
                    resources.getString("alert.info.noSelection"),
                    resources.getString("alert.info.noSelectionMsg")
            );
            return;
        }

        String mensaje = selectedPersons.size() == 1
                ? resources.getString("alert.confirm.deleteSingle")
                : MessageFormat.format(resources.getString("alert.confirm.deleteMultiple"), selectedPersons.size());

        if (!AlertUtil.mostrarConfirmacion(resources.getString("alert.confirm.delete"), mensaje)) {
            logger.info(resources.getString("log.deleteCancelled"));
            return;
        }

        try {
            for (Person person : selectedPersons) {
                personDAO.borrarPersona(person.getPersonId());
            }

            personList.clear();
            personList.addAll(personDAO.listarPersonas());

            logger.info(MessageFormat.format(resources.getString("log.personsDeleted"), selectedPersons));
        } catch (Exception e) {
            logger.error("Error al eliminar personas de la base de datos", e);
            AlertUtil.mostrarError(
                    resources.getString("alert.error.delete"),
                    MessageFormat.format(resources.getString("alert.error.deleteMsg"), e.getMessage())
            );
        }
    }

    /**
     * Maneja el evento de clic en el botón de restaurar.
     * Borra todas las personas de la base de datos y reinserta las personas
     * del backup para restaurar el estado inicial de la tabla.
     *
     * @param event el evento de acción que desencadenó esta función
     */
    @FXML
    void btnRestoreClick(ActionEvent event) {
        if (!AlertUtil.mostrarConfirmacion(
                resources.getString("alert.confirm.restore"),
                resources.getString("alert.confirm.restoreMsg"))) {
            logger.info(resources.getString("log.restoreCancelled"));
            return;
        }

        try {
            personDAO.borrarTodasPersonas(personList);

            for (Person person : backupList) {
                personDAO.insertarPersona(person);
            }

            personList.clear();
            personList.addAll(personDAO.listarPersonas());

            logger.info(MessageFormat.format(resources.getString("log.listRestored"), backupList.size()));
        } catch (Exception e) {
            logger.error("Error al restaurar la lista de personas", e);
            AlertUtil.mostrarError(
                    resources.getString("alert.error.restore"),
                    MessageFormat.format(resources.getString("alert.error.restoreMsg"), e.getMessage())
            );
        }
    }

    /**
     * Limpia los campos de entrada del formulario.
     */
    private void clearInputFields() {
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        logger.debug(resources.getString("log.fieldsCleared"));
    }
}