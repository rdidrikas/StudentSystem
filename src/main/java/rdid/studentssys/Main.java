package rdid.studentssys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/rdid/studentssys/home.fxml")));

        // Create the scene
        Scene scene = new Scene(root, 800, 600);
        // scene.getStylesheets().add(getClass().getResource("/rdid/studentssys/css/styles.css").toExternalForm());

        // Set window title
        primaryStage.setTitle("Student Attendance System");

        // Add the scene to the stage
        primaryStage.setScene(scene);

        // Show the window
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
