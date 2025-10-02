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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Inicializando TablaController...");
        personDAO = new PersonDAO();
        setupTableColumns();
        loadInitialData();
        logger.info("Tabla inicializada con {} personas.", personList.size());
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
        logger.debug("Columnas de la tabla configuradas con selección múltiple.");
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
            logger.debug("Datos iniciales cargados y backup creado.");
        } catch (Exception e) {
            logger.error("Error al cargar datos desde la base de datos", e);
            AlertUtil.mostrarError("Problema de conexión",
                    "No se pudieron cargar los datos desde la base de datos.\n" +
                            "Error: " + e.getMessage());
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
     * @param event el evento de acción que desencadenó este método
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

                    logger.info("Persona agregada: {}", newPerson);
                    clearInputFields();
                }
            } catch (Exception e) {
                logger.error("Error al insertar persona en la base de datos", e);
                AlertUtil.mostrarError("Error al guardar",
                        "No se pudo guardar la persona en la base de datos.\n" +
                                "Error: " + e.getMessage());
            }
        } else {
            logger.warn("Error al agregar persona: {}", errors);
            AlertUtil.mostrarErrorValidacion(errors);
        }
    }

    /**
     * Maneja el evento de clic en el botón de eliminar.
     * Elimina las personas seleccionadas de la base de datos.
     *
     * @param event el evento de acción que desencadenó este método
     */
    @FXML
    void btnDeleteClick(ActionEvent event) {
        ObservableList<Person> selectedPersons = tableView.getSelectionModel().getSelectedItems();

        if (selectedPersons.isEmpty()) {
            logger.info("Intento de eliminar sin selección.");
            AlertUtil.mostrarInformacion("No hay filas seleccionadas",
                    "Por favor, selecciona al menos una fila para eliminar.");
            return;
        }
        
        String mensaje = selectedPersons.size() == 1 
            ? "¿Estás seguro de que deseas eliminar la persona seleccionada?" 
            : "¿Estás seguro de que deseas eliminar las " + selectedPersons.size() + " personas seleccionadas?";
            
        if (!AlertUtil.mostrarConfirmacion("Confirmar eliminación", mensaje)) {
            logger.info("Eliminación cancelada por el usuario.");
            return;
        }

        try {
            for (Person person : selectedPersons) {
                personDAO.borrarPersona(person.getPersonId());
            }

            personList.clear();
            personList.addAll(personDAO.listarPersonas());

            logger.info("Personas eliminadas: {}", selectedPersons);
        } catch (Exception e) {
            logger.error("Error al eliminar personas de la base de datos", e);
            AlertUtil.mostrarError("Error al eliminar",
                    "No se pudieron eliminar las personas seleccionadas.\n" +
                            "Error: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento de clic en el botón de restaurar.
     * Borra todas las personas de la base de datos y reinserta las personas
     * del backup para restaurar el estado inicial de la tabla.
     *
     * @param event el evento de acción que desencadenó este método
     */
    @FXML
    void btnRestoreClick(ActionEvent event) {
        if (!AlertUtil.mostrarConfirmacion("Confirmar restauración", 
                "¿Estás seguro de que deseas restaurar la tabla a su estado inicial?\nEsta acción eliminará todos los cambios realizados.")) {
            logger.info("Restauración cancelada por el usuario.");
            return;
        }
        
        try {
            personDAO.borrarTodasPersonas(personList);

            for (Person person : backupList) {
                personDAO.insertarPersona(person);
            }

            personList.clear();
            personList.addAll(personDAO.listarPersonas());

            logger.info("Lista restaurada a estado inicial con {} personas.", backupList.size());
        } catch (Exception e) {
            logger.error("Error al restaurar la lista de personas", e);
            AlertUtil.mostrarError("Error al restaurar",
                    "No se pudo restaurar la lista de personas.\n" +
                            "Error: " + e.getMessage());
        }
    }

    /**
     * Limpia los campos de entrada del formulario.
     */
    private void clearInputFields() {
        txtFirstName.clear();
        txtLastName.clear();
        dateBirth.setValue(null);
        logger.debug("Campos de entrada limpiados.");
    }
}