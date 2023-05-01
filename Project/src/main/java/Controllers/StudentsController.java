package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class StudentsController {
    @FXML
    private Button uploadImageButton;

    @FXML
    private ImageView imageView;

    @FXML
    void onUploadImageButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(uploadImageButton.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }
}
