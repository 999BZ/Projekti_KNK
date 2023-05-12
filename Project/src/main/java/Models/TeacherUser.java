package Models;

import Services.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherUser extends User {

    private String Name;
    private String Surname;
    private String Birthdate;
    private String Phone;
    private String Address;

    public TeacherUser(int ID, String email, String saltedPassword, String salt, String position, String profilePic, String name, String surname, String birthdate, String phone, String address) {
        super(ID, email, saltedPassword, salt, position, profilePic);
        Name = name;
        Surname = surname;
        Birthdate = birthdate;
        Phone = phone;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return (this.getName()+ " " +this.getSurname());
    }



}
