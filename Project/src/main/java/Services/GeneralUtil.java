package Services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class GeneralUtil {
    public static String savePhoto(File selectedFile) {
        String imagePath = "";
        try {
            String fileName = selectedFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            imagePath = "src/main/resources/Images/ProfilePics/" + newFileName;
            Path destinationPath = Path.of(imagePath);

            Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Writing file to " + imagePath);
            return imagePath;
        } catch (IOException ex) {
            System.out.println("Error saving image: " + ex.getMessage());
            return null; // or throw an exception, depending on your use case
        }
    }


    public static File handleImageUpdate(ImageView profilePic) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            InputStream inputStream = new FileInputStream(selectedFile);
            Image newImage = new Image(inputStream);
            inputStream.close();
            profilePic.setImage(newImage);
        }
        return selectedFile;
    }

}
