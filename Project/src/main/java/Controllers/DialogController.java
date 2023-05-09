package Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    private AnchorPane dialogPane;

    @FXML
    private Label dialogLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private Stage dialogStage;

    private boolean confirmClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isConfirmClicked() {
        return confirmClicked;
    }

    public void setLabelText(String text) {
        dialogLabel.setText(text);
    }

    @FXML
    private void handleConfirmButton() {
        confirmClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelButton() {
        dialogStage.close();
    }
}
