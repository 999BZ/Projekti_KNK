package Repository;


import Models.AdminUser;
import Models.StudentUser;
import Models.TeacherUser;
import Models.User;
import Services.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static User insert(User user) throws SQLException {
        if(user instanceof StudentUser student){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position, U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String StudentSql="INSERT INTO Students (S_Name, S_Surname, S_Birthdate, S_Phone, S_Address, S_GLevel, S_Paralel, S_UID) VALUES (?, ?, ?, ?, ?,?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, student.getEmail());
                UserStatement.setString(2, student.getSaltedPassword());
                UserStatement.setString(3, student.getSalt());
                UserStatement.setString(4, student.getPosition());
                UserStatement.setString(5, student.getProfileImg());
                UserStatement.executeUpdate();
                PreparedStatement StudentStatement = connection.prepareStatement(StudentSql);

                StudentStatement.setString(1, student.getName());
                StudentStatement.setString(2, student.getSurname());
                StudentStatement.setString(3, student.getBirthdate());
                StudentStatement.setString(4, student.getPhone());
                StudentStatement.setString(5, student.getAddress());
                StudentStatement.setInt(6, student.getYear());
                StudentStatement.setInt(7,student.getParalel());
                StudentStatement.setString(8, student.getEmail());
                StudentStatement.executeUpdate();

            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof TeacherUser teacher){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position,  U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String TeacherSql = "INSERT INTO Teachers (T_Name, T_Surname, T_Birthdate, T_Phone, T_Address, T_UID) VALUES (?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, teacher.getEmail());
                UserStatement.setString(2, teacher.getSaltedPassword());
                UserStatement.setString(3, teacher.getSalt());
                UserStatement.setString(4, teacher.getPosition());
                UserStatement.setString(5, teacher.getProfileImg());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, teacher.getName());
                TeacherStatement.setString(2, teacher.getSurname());
                TeacherStatement.setString(3, teacher.getBirthdate());
                TeacherStatement.setString(4, teacher.getPhone());
                TeacherStatement.setString(5, teacher.getAddress());
                TeacherStatement.setString(6, teacher.getEmail());
                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof AdminUser admin){
            String UserSql = "INSERT INTO Users (Email, Salted_Password, Salt, U_Position,  U_ProfileImg) VALUES (?, ?, ?, ?, ?)";
            String TeacherSql = "INSERT INTO Admins (A_Name, A_Surname, A_Phone, A_UID) VALUES (?, ?, ?, ?, ?, (SELECT U_ID FROM Users WHERE Email = ?))";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, admin.getEmail());
                UserStatement.setString(2, admin.getSaltedPassword());
                UserStatement.setString(3, admin.getSalt());
                UserStatement.setString(4, admin.getPosition());
                UserStatement.setString(5, admin.getProfileImg());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, admin.getName());
                TeacherStatement.setString(2, admin.getSurname());
                TeacherStatement.setString(4, admin.getPhone());
                TeacherStatement.setString(6, admin.getEmail());
                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }

        return null;

    }

    public static User getByEmail(String Email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("U_ID");
                String saltedHash = resultSet.getString("Salted_password");
                String salt = resultSet.getString("Salt");
                String User_Position = resultSet.getString("U_Position");
                String User_ProfileImg = resultSet.getString("U_ProfileImg");
                return new User(ID, Email, saltedHash, salt,User_Position, User_ProfileImg);
            } else {
                return null;
            }
        }
    }
    public static User getByID(int  U_ID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, U_ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ID = resultSet.getInt("U_ID");
                String saltedHash = resultSet.getString("Salted_password");
                String salt = resultSet.getString("Salt");
                String User_Position = resultSet.getString("U_Position");
                String User_ProfileImg = resultSet.getString("U_ProfileImg");
                String User_Email = resultSet.getString("Email");
                return new User(ID, User_Email, saltedHash, salt,User_Position, User_ProfileImg);
            } else {
                return null;
            }
        }
    }
    public static User update(User user) throws SQLException {
        if(user instanceof StudentUser student){
            String UserSql = "UPDATE Users set Email=?, U_Position=?, U_ProfileImg=? where U_ID = ?";
            String StudentSql = "UPDATE Students set S_Name=?, S_Surname=?, S_Birthdate=?, S_Phone=?, S_Address=?, S_GLevel=?, S_Paralel=? where S_UID=?";

            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement UserStatement = connection.prepareStatement(UserSql);
                 PreparedStatement StudentStatement = connection.prepareStatement(StudentSql)) {

                UserStatement.setString(1, student.getEmail());
                UserStatement.setString(2, student.getPosition());
                UserStatement.setString(3, student.getProfileImg());

                UserStatement.setInt(4, student.getID());

                UserStatement.executeUpdate();

                StudentStatement.setString(1, student.getName());
                StudentStatement.setString(2, student.getSurname());
                StudentStatement.setString(3, student.getBirthdate());
                StudentStatement.setString(4, student.getPhone());
                StudentStatement.setString(5, student.getAddress());
                StudentStatement.setInt(6, student.getYear());
                StudentStatement.setInt(7, student.getParalel());
                StudentStatement.setInt(8, student.getID());

                StudentStatement.executeUpdate();

        }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());
        }else if(user instanceof TeacherUser teacher){
            String UserSql = "UPDATE Users set Email=?, U_ProfileImg=? where U_ID=?";
            String TeacherSql = "UPDATE Teachers set T_Name=?, T_Surname=?, T_Birthdate=?, T_Phone=?, T_Address=?  where T_UID = ?";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, teacher.getEmail());
                UserStatement.setString(2, teacher.getProfileImg());
                UserStatement.setInt(3, teacher.getID());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, teacher.getName());
                TeacherStatement.setString(2, teacher.getSurname());
                TeacherStatement.setString(3, teacher.getBirthdate());
                TeacherStatement.setString(4, teacher.getPhone());
                TeacherStatement.setString(5, teacher.getAddress());
                TeacherStatement.setInt(6, teacher.getID());


                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            return UserRepository.getByEmail(user.getEmail());
        }
        else if(user instanceof AdminUser admin){
            String UserSql = "UPDATE Users set Email=?, Salted_Password=?, Salt=?, U_Position=?,  U_ProfileImg=? where U_ID=?";
            String TeacherSql = "UPDATE Admins set A_Name=?, A_Surname=?,T_Phone=? where T_UID = ?";

            try(Connection connection = ConnectionUtil.getConnection();
                PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setString(1, admin.getEmail());
                UserStatement.setString(2, admin.getSaltedPassword());
                UserStatement.setString(3, admin.getSalt());
                UserStatement.setString(4, admin.getPosition());
                UserStatement.setString(5, admin.getProfileImg());
                UserStatement.setInt(6, admin.getID());

                UserStatement.executeUpdate();

                PreparedStatement TeacherStatement = connection.prepareStatement(TeacherSql);
                TeacherStatement.setString(1, admin.getName());
                TeacherStatement.setString(2, admin.getSurname());
                TeacherStatement.setString(3, admin.getPhone());
                UserStatement.setInt(4, admin.getID());


                TeacherStatement.executeUpdate();
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            return UserRepository.getByEmail(user.getEmail());
        }

        return null;

    }
    public static User delete(User user) throws SQLException {
            String UserSql = "Delete from Users where u_id=?";

            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement UserStatement = connection.prepareStatement(UserSql)
            ) {
                UserStatement.setInt(1, user.getID());
                UserStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            return UserRepository.getByEmail(user.getEmail());


        }
    }
