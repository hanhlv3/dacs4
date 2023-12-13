/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO extends DAO{

    public UserDAO() {
        super();
    }
    public User verifyUser(User user) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT *\n"
                    + "FROM user\n"
                    + "WHERE username = ?\n"
                    + "AND password = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        (rs.getInt(9) != 0),
                        (rs.getInt(10) != 0)
                     );    
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByID(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user\n"
                    + "WHERE ID=?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        (rs.getInt(9) != 0),
                        (rs.getInt(10) != 0)
                );
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user(username, password, nickname, avatar)\n"
                    + "VALUES(?,?,?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            preparedStatement.setString(4, user.getAvatar());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean checkDuplicated(String username){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkIsBanned(User user){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM banned_user WHERE ID_User = ?");
            preparedStatement.setInt(1, user.getID());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    
    public void updateBannedStatus(User user,boolean ban){
        try {
            PreparedStatement preparedStatement1 = con.prepareStatement("INSERT INTO `banned_user`(`ID_User`) VALUES (?)");
            PreparedStatement preparedStatement2 = con.prepareStatement("DELETE FROM `banned_user` WHERE ID_User=?");
            if(ban){
                preparedStatement1.setInt(1, user.getID());
                preparedStatement1.executeUpdate();
            } else{
                preparedStatement2.setInt(1, user.getID());
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateToOnline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET IsOnline = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateToOffline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET IsOnline = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateToPlaying(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET IsPlaying = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateToNotPlaying(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET IsPlaying = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getNumberOfWin(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NumberOfWin\n"
                    + "FROM user\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    public int getNumberOfDraw(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NumberOfDraw\n"
                    + "FROM user\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    
    public void addDrawGame(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NumberOfDraw = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfDraw(ID)+1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addWinGame(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NumberOfWin = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfWin(ID)+1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getNumberOfGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NumberOfGame\n"
                    + "FROM user\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public void addGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NumberOfGame = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfGame(ID) + 1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void decreaseGame(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NumberOfGame = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfGame(ID) - 1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getNickNameByID(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NickName\n"
                    + "FROM user\n"
                    + "WHERE user.ID=?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
}
