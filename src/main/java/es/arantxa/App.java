package es.arantxa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal de la aplicación JavaFX.
 * Se encarga de iniciar la interfaz gráfica cargando el archivo FXML
 * y aplicando los estilos CSS definidos.
 * Configura las propiedades de la ventana principal como tamaño mínimo,
 * máximo y título.
 */
public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Función que se ejecuta al iniciar la aplicación.
     * Carga la interfaz definida en "tabla.fxml", aplica los estilos CSS
     * y configura la ventana principal.
     *
     * @param stage la ventana principal de la aplicación
     */
    @Override
    public void start(Stage stage) {
        logger.info("Iniciando la aplicación");

        try {
            Locale locale = new Locale("es");
            ResourceBundle bundle = ResourceBundle.getBundle("textos", locale);

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/tabla.fxml"), bundle);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Tableview from an observableList");
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setMaxHeight(500);
            stage.setMaxWidth(800);
            stage.show();

            logger.info("Aplicación lanzada correctamente");
        } catch (Exception e) {
            logger.error("Error al iniciar la aplicación", e);
        }
    }

    /**
     * Función principal que arranca la aplicación JavaFX.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
