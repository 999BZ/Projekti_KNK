package Services;

import Controllers.ChangePasswordController;
import Controllers.DialogController;
import Controllers.StudentsStatisticsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    public static boolean setDialog(String labelText) throws IOException {
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource("/Main/Dialog.fxml"));
        AnchorPane dialogPane = loader.load();
        DialogController dialogController = loader.getController();
        dialogController.setLabelText(labelText);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(new Scene(dialogPane));
        dialogController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        return dialogController.isConfirmClicked();
    }

    public static void setChangePassword(int U_ID) throws IOException {
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource("/Main/ChangePassword.fxml"));
        AnchorPane chPwPane = loader.load();
        ChangePasswordController chPwController = loader.getController();
        Stage chPwStage = new Stage();
        chPwStage.initModality(Modality.APPLICATION_MODAL);
        chPwStage.setScene(new Scene(chPwPane));
        chPwController.setDialogStage(chPwStage);
        chPwController.setUserId(U_ID);
        chPwStage.showAndWait();
    }

    public static void goToFXML(String location, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource(location));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root, WindowSizeUtils.windowWidth, WindowSizeUtils.windowHeight);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getStatistics(int id , double notamesatare) throws IOException {
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource("/Main/Students_Statistics.fxml"));
        AnchorPane statisticsPane = loader.load();
        StudentsStatisticsController statisticsController = loader.getController();
        statisticsController.setLblaverage(Double.toString(notamesatare));
        Stage StatisticStage = new Stage();
        StatisticStage.initModality(Modality.APPLICATION_MODAL);
        StatisticStage.setScene(new Scene(statisticsPane));
        statisticsController.setDialogStage(StatisticStage);
        statisticsController.setUserId(id);
        StatisticStage.showAndWait();
    }


}
