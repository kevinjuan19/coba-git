package Dao;

import Model.User;
import Utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class userDao implements daoInterface<User>{
    private final Connection conn = JDBCConnection.getConnection();
    @Override
    public int addData(User data) {
        int result = 0;
        try{
            String query = "INSERT INTO user (id,name,username,password,position ) VALUE(?,?,?,md5(?),?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            conn.setAutoCommit(false);
            ps.setInt(1,data.getId());
            ps.setString(2,data.getName());
            ps.setString(3,data.getUsername());
            ps.setString(4,data.getPassword());
            ps.setString(5, data.getPosition());
            result = ps.executeUpdate();
            if (result != 0){
                conn.commit();
            }else {
                conn.rollback();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int delData(User data) {
        int result = 0;
        try {
            String query = "DELETE FROM user WHERE id = ?";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getId());
            result= ps.executeUpdate();
            if (result != 0){
                conn.commit();
            }
            else{
                conn.rollback();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(User data) {
        return 0;
    }

    @Override
    public ObservableList<User> showData() {
        ObservableList<User> uList = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM user";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                String username = res.getString("username");
                String password = res.getString("password");
                String position = res.getString("position");
                User user = new User(id,name,username,password,position);
                uList.add(user);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return uList;
    }
    public User tampilData(User data){
        User user = null;
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = md5(?)";
            PreparedStatement ps ;
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getUsername());
            ps.setString(2, data.getPassword());
            ResultSet res = ps.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                String username = res.getString("username");
                String password = res.getString("password");
                String position = res.getString("position");
                user = new User(id,name,username,password,position);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

//    public int login(User data){
//        int result = 0;
//        try {
//            String query = "SELECT count(1) FROM user WHERE username = ? AND password = md5(?)";
//            PreparedStatement ps;
//            ps = conn.prepareStatement(query);
//            ps.setString(1, data.getUsername());
//            ps.setString(2, data.getPassword());
//            ResultSet res = ps.executeQuery();
//            while (res.next()){
//                if (res.getInt(1) == 1){
//                    result = 1;
//                }
//            }
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//        return result;
//    }
}
