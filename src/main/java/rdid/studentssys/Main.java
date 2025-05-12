package rdid.studentssys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rdid.studentssys.controller.ControllerLocator;
import rdid.studentssys.controller.HomeController;
import rdid.studentssys.controller.SidebarController;
import rdid.studentssys.controller.TableController;
import rdid.studentssys.data.CSVhandler;

import java.io.IOException;
import java.util.Objects;

// Rokas Danielius Didrikas Gr. 5

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/rdid/studentssys/home.fxml")));
        FXMLLoader sidebarLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/rdid/studentssys/sidebar.fxml")));
        FXMLLoader tableLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/rdid/studentssys/table.fxml")));

        Parent tableRoot = tableLoader.load();
        TableController tableController = tableLoader.getController();

        Parent sidebarRoot = sidebarLoader.load();
        SidebarController sidebarController = sidebarLoader.getController();

        Parent root = loader.load();

        // Create the scene
        Scene scene = new Scene(root, 900, 600);
        // scene.getStylesheets().add(getClass().getResource("/rdid/studentssys/css/styles.css").toExternalForm());

        // Set window title
        primaryStage.setTitle("Student Attendance System");

        HomeController homeController = loader.getController();
        ControllerLocator.setHomeController(homeController);

        CSVhandler csvHandler = new CSVhandler();
        csvHandler.studentCSVData(csvHandler.importData("src/main/resources/data/students.csv")); // loading data

        // Add the scene to the stage
        primaryStage.setScene(scene);

        // Show the window
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
