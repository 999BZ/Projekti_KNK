package Models;

public class AdminUser extends User {

    private String name;
    private String surname;
    private String phone;

    public AdminUser(int ID, String email, String saltedPassword, String salt, String position, String profileImg, String name, String surname, String phone) {
        super(ID, email, saltedPassword, salt, position, profileImg);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
