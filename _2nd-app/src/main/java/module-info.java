module org.example._2ndapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.bouncycastle.provider;

    opens org.example._2ndapp to javafx.fxml;
    exports org.example._2ndapp;
}