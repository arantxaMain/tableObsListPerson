module es.arantxa {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires javafx.graphics;

    opens es.arantxa.controller to javafx.fxml;
    opens es.arantxa.model to javafx.base;
    exports es.arantxa;
}
