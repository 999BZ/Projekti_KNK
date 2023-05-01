package Models;

public class RegisterUser extends User {

    private String Name;
    private String Surname;
    private String Birthdate;
    private String Phone;
    private String Address;
    private int Year;

    public RegisterUser(int ID, String email, String saltedPassword, String salt, String position, String name, String surname, String birthdate, String phone, String address, int year) {
        super(ID, email, saltedPassword, salt, position);
        Name = name;
        Surname = surname;
        Birthdate = birthdate;
        Phone = phone;
        Address = address;
        Year = year;
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

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
