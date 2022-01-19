package Dao;

import Model.Laboratorium;
import Model.Main;
import Model.Maintenance;
import Model.User;
import Utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class maintenanceDao implements daoInterface<Maintenance> {
    private final Connection conn = JDBCConnection.getConnection();
    @Override
    public int addData(Maintenance data) {
        int result = 0;
        try{
            String query = "INSERT INTO maintenance(user_id,laboratorium_id,task,date) VALUES(?,?,?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            conn.setAutoCommit(false);
            ps.setInt(1,data.getUser().getId());
            ps.setInt(2,data.getLaboratorium().getId());
            ps.setString(3,data.getTask());
            ps.setDate(4,data.getDate());
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
    public int delData(Maintenance data) {
        return 0;
    }

    @Override
    public int updateData(Maintenance data) {
        return 0;
    }

    @Override
    public ObservableList<Maintenance> showData() {
        return null;
    }


    public ObservableList<Maintenance> fetchData(Maintenance data) {
        ObservableList<Maintenance> mList = FXCollections.observableArrayList();
        try{
            String query = "SELECT m.id,m.user_id,m.laboratorium_id,m.task,m.date , l.name as laboratorium_name ,l.capacity, u.name as user_name, u.username, u.password,u.position FROM maintenance m JOIN laboratorium l ON m.laboratorium_id = l.id JOIN user u ON m.user_id = u.id WHERE m.user_id = ?";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setInt(1,data.getUser().getId());
            ResultSet res = ps.executeQuery();
            while (res.next()){
                int id = res.getInt("id");
                int userId = res.getInt("user_id");
                int labId = res.getInt("laboratorium_id");
                String task = res.getString("task");
                Date date = res.getDate("date");
                String labName = res.getString("laboratorium_name");
                int capacity = res.getInt("capacity");
                String userName = res.getString("user_name");
                String username = res.getString("username");
                String password = res.getString("password");
                String position = res.getString("position");
                User user = new User(userId,userName,username,password,position);
                Laboratorium lab = new Laboratorium(labId,labName,capacity);
                Maintenance m =new Maintenance(id,user,lab,task,date);
                mList.add(m);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return mList;
    }

    public ObservableList<User> fetchDatas(Maintenance data){
        return null;
    }
}
