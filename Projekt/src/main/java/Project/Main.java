package Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                 Main.class.getResource("Main.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.getStylesheets().add("CSS/style.css");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
