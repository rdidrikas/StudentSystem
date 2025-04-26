module rdid.studentssys {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens rdid.studentssys to javafx.fxml;
    exports rdid.studentssys;
}