package Services;

import Models.AdminUser;
import Models.StudentUser;
import Models.TeacherUser;
import Models.User;
import Repository.UserRepository;

import java.sql.SQLException;

public class UserAuthService {

    public static User login(String Email,String password) throws SQLException {
        try{System.out.println("UserAuth -> UserRepository");
        User user = UserRepository.getByEmail(Email);
        if(user == null){
            System.out.println("No User found Authentication");
            return null;
        }
        if(PasswordHasher.compareSaltedHash(password, user.getSalt(),user.getSaltedPassword())){
            System.out.println("User FOUND");
            return user;
        }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }

    public static User registerStudent(String Name, String Surname, String Birthdate, String Phone, String Address, int Year, int Paralel,  String Email, String password, String ProfileImg) throws SQLException {
        User CheckUser = UserRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");
            return UserRepository.insert(new StudentUser(1,Email, saltedPasswordHash, salt, "Student",ProfileImg, Name, Surname, Birthdate, Phone, Address, Year, Paralel));

    }

    public static User registerAdmin(String Name, String Surname, String Phone,  String Email, String password, String ProfileImg) throws SQLException {
        User CheckUser = UserRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");
        return UserRepository.insert(new AdminUser(1,Email, saltedPasswordHash, salt, "Admin",ProfileImg, Name, Surname, Phone));

    }
    public static User updateStudent(int id,String Name, String Surname, String Birthdate, String Phone, String Address, int Year,int Paralel, String Email, String Position, String ProfileImg) throws SQLException {
        return UserRepository.update(new StudentUser(id,Email, null, null, "Student",ProfileImg, Name, Surname, Birthdate, Phone, Address, Year, Paralel));
    }
    public static User registerTeacher(String Name, String Surname, String Birthdate, String Phone, String Address, String Email, String password, String Position, String ProfileImg) throws SQLException {
        User CheckUser = UserRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");


        return UserRepository.insert(new TeacherUser(1,Email, saltedPasswordHash, salt, "Teacher", ProfileImg, Name,  Surname, Birthdate, Phone, Address));

    }
    public static User updateTeacher(int id, String Name, String Surname, String Birthdate, String Phone, String Address, String Email, String Position, String ProfileImg) throws SQLException {
        return UserRepository.update(new TeacherUser(id,Email, null, null, "Teacher",ProfileImg, Name, Surname, Birthdate, Phone, Address));
    }
}
