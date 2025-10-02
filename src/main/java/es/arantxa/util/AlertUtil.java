package es.arantxa.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.List;
import java.util.Optional;

/**
 * Utilidad para mostrar diferentes tipos de alertas en la aplicación.
 */
public class AlertUtil {

    /**
     * Muestra una alerta de error.
     *
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarError(String mensaje) {
        mostrarError("Error", mensaje);
    }

    /**
     * Muestra una alerta de error con título personalizado.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de error de validación con una lista de errores.
     *
     * @param errores lista de mensajes de error
     */
    public static void mostrarErrorValidacion(List<String> errores) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText("Los siguientes errores deben ser corregidos:");
        alert.setContentText(String.join("\n", errores));
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de información.
     *
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarInformacion(String mensaje) {
        mostrarInformacion("Información", mensaje);
    }

    /**
     * Muestra una alerta de información con título personalizado.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de advertencia.
     *
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarAdvertencia(String mensaje) {
        mostrarAdvertencia("Advertencia", mensaje);
    }

    /**
     * Muestra una alerta de advertencia con título personalizado.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de éxito.
     *
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarExito(String mensaje) {
        mostrarExito("Éxito", mensaje);
    }

    /**
     * Muestra una alerta de éxito con título personalizado.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje a mostrar
     */
    public static void mostrarExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación y devuelve true si el usuario acepta.
     *
     * @param mensaje el mensaje a mostrar
     * @return true si el usuario presiona OK, false en caso contrario
     */
    public static boolean mostrarConfirmacion(String mensaje) {
        return mostrarConfirmacion("Confirmación", mensaje);
    }

    /**
     * Muestra una alerta de confirmación con título personalizado.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje a mostrar
     * @return true si el usuario presiona OK, false en caso contrario
     */
    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    /**
     * Muestra una alerta de confirmación con header personalizado.
     *
     * @param titulo el título de la alerta
     * @param header el encabezado de la alerta
     * @param mensaje el mensaje a mostrar
     * @return true si el usuario presiona OK, false en caso contrario
     */
    public static boolean mostrarConfirmacionConHeader(String titulo, String header, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(mensaje);

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}