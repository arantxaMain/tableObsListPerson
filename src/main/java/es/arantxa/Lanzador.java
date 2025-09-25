package es.arantxa;

/**
 * Clase lanzadora de la aplicación.
 * Permite iniciar la aplicación JavaFX delegando en la clase {@link App}.
 * Se utiliza como punto de entrada alternativo o para compatibilidad con ciertos entornos.
 */
public class Lanzador {

    /**
     * Función principal que arranca la aplicación delegando en {@link App#main(String[])}.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        App.main(args);
    }
}
