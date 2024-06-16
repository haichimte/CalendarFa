package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    DBUntil dbUntil = new DBUntil();
    Connection conn = dbUntil.getCon();
    public User login (String email, String password) {
        String query = "select*from users\n" +
                "where email = ? and password = ?";
        User u = null;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                System.out.println("sucess");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Not have user");
        }
        return u;
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        dao.login("user1@mail.com", "pass1");
    }
}
