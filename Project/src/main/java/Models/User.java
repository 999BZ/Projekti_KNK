package Models;

public class User {

    private int ID;
    private String Email;
    private String SaltedPassword;
    private String Salt;
    private String Position;

    public User(int ID, String email, String saltedPassword, String salt, String position) {
        this.ID = ID;
        Email = email;
        SaltedPassword = saltedPassword;
        Salt = salt;
        Position = position;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSaltedPassword() {
        return SaltedPassword;
    }

    public void setSaltedPassword(String saltedPassword) {
        SaltedPassword = saltedPassword;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

}

