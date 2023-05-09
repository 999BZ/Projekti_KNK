package Main;

import Services.WindowSizeUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.UUID;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("Students.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load() , WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
        scene.getStylesheets().add(getClass().getResource("/CSS/style.css").toExternalForm());
        stage.setTitle("Lower Education Managment System");
        stage.setScene(scene);
        stage.show();
        System.out.println(UUID.randomUUID());
    }
}