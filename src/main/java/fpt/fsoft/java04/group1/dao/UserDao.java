package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.DisplayImportantEvent;
import fpt.fsoft.java04.group1.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    DBUntil dbUntil = new DBUntil();
    Connection conn = dbUntil.getCon();
    public List<User> listUser() {

        List<User> list = new ArrayList<>();
        String query = "select * from users";
        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                User u = new User();
                u.setUserId(rs.getInt("userId"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return list;

    }
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
                System.out.println("Well Come "+ u.getUserName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Not have user");
        }
        return u;
    }

//    public static void main(String[] args) {
//        UserDao dao = new UserDao();
//        List<User> list = dao.listUser();
//        for (User u : list) {
//            System.out.println(u);
//
//        }
//    }
    public static void main(String[] args) {
        UserDao dao = new UserDao();
        dao.login("user1@mail.com", "pass1");
    }

}
