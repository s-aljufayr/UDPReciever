module com.example.udpreciever {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.udpreciever to javafx.fxml;
    exports com.example.udpreciever;
}