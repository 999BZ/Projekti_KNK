package Controllers;

import Models.StudentUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;

public class StudentInfoController {

    @FXML
    private Label fullName;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label gradeLvl;
    @FXML
    private Label birthday;
    @FXML
    private Label email;
    @FXML
    private ImageView profilePic;

    private StudentUser student;

    public void setStudent(StudentUser student) {
        this.student = student;
        if (student != null) {
            fullName.setText(student.getName() + " " + student.getSurname());
            phone.setText(student.getPhone());
            birthday.setText(student.getBirthdate());
            address.setText(student.getAddress());
            email.setText(student.getEmail());
            if (student.getYear() != 0) {
                gradeLvl.setText(String.valueOf(student.getYear()));
            }
            try {
                String relativePath = student.getProfileImg().replace("src/main/resources", "");
                Image image = new Image(getClass().getResourceAsStream(relativePath));
                profilePic.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());

            }
        }
    }



}
