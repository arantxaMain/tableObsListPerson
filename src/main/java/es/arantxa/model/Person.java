package es.arantxa.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa una persona con datos personales como nombre y fecha de nacimiento.
 * Incluye reglas de validación y métodos para determinar categorías de edad.
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(0);
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    /**
     * Categorías que representan las diferentes etapas de la vida según la edad.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    }

    /**
     * Constructor por defecto que inicializa la persona con valores nulos.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una persona con los atributos dados.
     *
     * @param firstName nombre de pila de la persona
     * @param lastName  apellido de la persona
     * @param birthDate fecha de nacimiento de la persona
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Obtiene el identificador único de la persona.
     *
     * @return el ID de la persona
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Establece el identificador único de la persona.
     *
     * @param personId el ID a asignar
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return el nombre
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param firstName el nombre a asignar
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return el apellido
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido de la persona.
     *
     * @param lastName el apellido a asignar
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     *
     * @return la fecha de nacimiento
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     *
     * @param birthDate la fecha de nacimiento a asignar
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Valida si una fecha de nacimiento es aceptable.
     *
     * @param bdate fecha de nacimiento a validar
     * @return true si la fecha es válida o nula, false si está en el futuro
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Valida si una fecha de nacimiento es aceptable.
     *
     * @param bdate     fecha de nacimiento a validar
     * @param errorList lista donde se añadirán los mensajes de error
     * @return true si la fecha es válida o nula, false en caso contrario
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("La fecha de nacimiento no puede estar en el futuro.");
            return false;
        }
        return true;
    }

    /**
     * Valida si la instancia actual de persona es válida.
     *
     * @param errorList lista donde se añadirán los mensajes de error
     * @return true si la persona es válida, false en caso contrario
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Valida si un objeto persona es válido según las reglas de negocio.
     *
     * @param p         la persona a validar
     * @param errorList lista donde se añadirán los mensajes de error
     * @return true si la persona es válida, false en caso contrario
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().isEmpty()) {
            errorList.add("El nombre debe contener al menos un carácter.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().isEmpty()) {
            errorList.add("El apellido debe contener al menos un carácter.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Determina la categoría de edad de la persona según su fecha de nacimiento.
     *
     * @return la categoría de edad correspondiente ({@link AgeCategory})
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /**
     * Guarda la persona si pasa las validaciones.
     *
     * @param errorList lista donde se añadirán los mensajes de error
     * @return true si la persona fue guardada con éxito, false en caso contrario
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Guardado " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    /**
     * Devuelve una representación en cadena de la persona.
     *
     * @return una cadena con personId, firstName, lastName y birthDate
     */
    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}
