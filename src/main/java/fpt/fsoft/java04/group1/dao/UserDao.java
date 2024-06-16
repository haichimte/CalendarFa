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

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        List<User> list = dao.listUser();
        for (User u : list) {
            System.out.println(u);

        }
    }

}
