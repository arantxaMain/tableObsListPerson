module es.arantxa {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires javafx.graphics;

    opens es.arantxa to javafx.fxml;
    exports es.arantxa;
}
