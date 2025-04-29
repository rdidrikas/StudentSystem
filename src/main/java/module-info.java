module rdid.studentssys {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires com.opencsv;

    opens rdid.studentssys to javafx.fxml;
    exports rdid.studentssys;
    opens rdid.studentssys.controller to javafx.fxml;
    exports rdid.studentssys.controller;
}