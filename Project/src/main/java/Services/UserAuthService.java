package Services;

import Models.StudentUser;
import Models.TeacherUser;
import Models.User;
import Repository.userRepository;

import java.sql.SQLException;

public class UserAuthService {

    public static User login(String Email,String password) throws SQLException {
        System.out.println("UserAuth -> userRepository");
        User user = userRepository.getByEmail(Email);
        if(user == null){
            System.out.println("No User found Authentication");
            return null;
        }
        if(PasswordHasher.compareSaltedHash(password, user.getSalt(),user.getSaltedPassword())){
            System.out.println("User FOUND");
            return user;
        }
        return null;
    }

    public static User registerStudent(String Name, String Surname, String Birthdate, String Phone, String Address, int Year, String Email, String password, String Position, String ProfileImg) throws SQLException {
        User CheckUser = userRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");
            return userRepository.insert(new StudentUser(1,Email, saltedPasswordHash, salt, "Student",ProfileImg, Name, Surname, Birthdate, Phone, Address, Year));

    }
    public static User registerTeacher(String Name, String Surname, String Birthdate, String Phone, String Address, String Email, String password, String Position, String ProfileImg) throws SQLException {
        User CheckUser = userRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");


        return userRepository.insert(new TeacherUser(1,Email, saltedPasswordHash, salt, "Teacher", ProfileImg, Name,  Surname, Birthdate, Phone, Address));

    }
}
