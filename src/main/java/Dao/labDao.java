package Dao;

import Model.Laboratorium;
import Utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class labDao implements daoInterface<Laboratorium>{
    private final Connection conn = JDBCConnection.getConnection();
    @Override
    public int addData(Laboratorium data) {
        int result = 0;
        try {
            String query = "INSERT INTO laboratorium (name,capacity) VALUES (?,?)";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setString(1,data.getName());
            ps.setInt(2,data.getCapacity());
            result = ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int delData(Laboratorium data) {
        int result = 0;
        try {
            String query = "Delete FROM laboratorium WHERE id=?";
            conn.setAutoCommit(false);
            PreparedStatement ps;
            ps= conn.prepareStatement(query);
            ps.setInt(1,data.getId());
            result= ps.executeUpdate();
            if (result != 0){
                conn.commit();
            }
            else{
                conn.rollback();
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(Laboratorium data) {
        int result = 0;
        try {
            String query = "Update laboratorium set name=?, capacity=? where id=?";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ps.setString(1, data.getName());
            ps.setInt(2, data.getCapacity());
            ps.setInt(3, data.getId());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    @Override
    public ObservableList<Laboratorium> showData() {
        ObservableList<Laboratorium> lList = FXCollections.observableArrayList();
        try{
            String query = "SELECT * FROM laboratorium";
            PreparedStatement ps;
            ps = conn.prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                int capacity = res.getInt("capacity");
                Laboratorium lab = new Laboratorium(id,name,capacity);
                lList.add(lab);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lList;
    }
}
