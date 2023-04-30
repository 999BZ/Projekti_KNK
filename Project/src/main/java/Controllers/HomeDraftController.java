package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeDraftController  {
    @FXML
    private ImageView imageView;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void openHome(ActionEvent event) throws IOException   {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Projekt/HomeDraft.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,stage.getHeight(),stage.getWidth());
        if(stage.isMaximized()){
            stage.setMaximized(true);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
        }
        stage.setScene(scene);
        stage.show();
    }

}
