package Services;

import Models.RegisterUser;
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

    public static User register(String Name, String Surname,String Birthdate,String Phone,String Address, int Year, String Email,String password) throws SQLException {
        User CheckUser = userRepository.getByEmail(Email);
        if(CheckUser != null){
            //throw a new ResourceAlreadyExistError
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        System.out.println("Trying to Register!");
        User NewUser = userRepository.insert(new RegisterUser(1,Email,saltedPasswordHash,salt,"Students",Name,Surname,Birthdate,Phone,Address,Year));

//        UserProfileRepository.insert(new UserProfile(0,user.getId(),firstname,lastname,age));
        return NewUser;


    }



}
