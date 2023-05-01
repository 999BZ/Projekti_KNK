package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SideNavController implements Initializable {
    @FXML
    private ImageView photo;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/Images/logo.png"));
        this.photo.setImage(image);
    }

}
