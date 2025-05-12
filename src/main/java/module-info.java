module rdid.studentssys {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires com.opencsv;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;

    opens rdid.studentssys to javafx.fxml;
    exports rdid.studentssys;
    opens rdid.studentssys.controller to javafx.fxml;
    exports rdid.studentssys.controller;
}