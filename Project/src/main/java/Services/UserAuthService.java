package Services;

import Models.TeacherStudent;
import Models.TeacherUser;
import Models.User;
import Repository.UserRepository;

import java.sql.SQLException;

public class UserAuthService {

    public static User login(String Email,String password) throws SQLException {
        System.out.println("UserAuth -> UserRepository");
        User user = UserRepository.getByEmail(Email);
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
        User CheckUser = UserRepository.getByEmail(Email);
        if(CheckUser != null){
            System.out.println("User already exists.");
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");
            return UserRepository.insert(new TeacherStudent(1,Email, saltedPasswordHash, salt, "Student",ProfileImg, Name, Surname, Birthdate, Phone, Address, Year));

    }
    public static User updateStudent(int id,String Name, String Surname, String Birthdate, String Phone, String Address, int Year, String Email, String Position, String ProfileImg) throws SQLException {
        return UserRepository.update(new TeacherStudent(id,Email, null, null, "Student",ProfileImg, Name, Surname, Birthdate, Phone, Address, Year));
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
