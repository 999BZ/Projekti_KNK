package Services;

import Models.User;
import Repository.userRepository;

import java.sql.SQLException;

public class UserAuthService {

    public static User login(String username,String password) throws SQLException {
        User user = userRepository.getByUsername(username);
        if(user == null){
            return null;
        }
        if(PasswordHasher.compareSaltedHash(password, user.getSalt(),user.getSaltedPassword())){
            return user;
        }
        return null;
    }

    public static User register(String firstname, String lastname, int age, String username,String password) throws SQLException {
        User user = userRepository.getByUsername(username);
        if(user != null){
            //throw a new ResourceAlreadyExistError
            return null;
        }
        String salt = PasswordHasher.generateSalt();
        String saltedPasswordHash = PasswordHasher.generateSaltedHash(password,salt);
        user = userRepository.insert(new User(0,username,saltedPasswordHash,salt));

//        UserProfileRepository.insert(new UserProfile(0,user.getId(),firstname,lastname,age));
        return user;


    }



}
