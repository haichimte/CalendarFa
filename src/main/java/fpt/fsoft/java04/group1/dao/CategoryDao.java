package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.ImportantEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    DBUntil dbUntil = new DBUntil();
    public String addNewCategory(EventCategories cate) {
        String result = "Fail";

        String query = "INSERT INTO [dbo].[eventsCategories]\n" +
                "           ([categoryName])\n" +
                "     VALUES\n" +
                "           (?)";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1,cate.getCategoryName());

            int count = ps.executeUpdate();
            if (count > 0) {
                result = "Have " + count + " added";
            } else {
                result = "Dont have added record";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public String updateCategory(int id, String nameCate) {
        String result = "Fail";

        String query = "UPDATE [dbo].[eventsCategories]\n" +
                "   SET [categoryName] =?\n" +
                " WHERE categoryId = ?";
        try {
            Connection conn = dbUntil.getCon();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(2, id);
            ps.setString(1, nameCate);

            int count = ps.executeUpdate();

            if (count > 0) {

                result = "Have" + count + "Update";
            } else {

                result = "Don't have update";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public List<EventCategories> listCategories() {
        List<EventCategories> list = new ArrayList<>();


        String query = "select * from eventsCategories";

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventCategories cate = new EventCategories();
                    cate.setCategoryId(rs.getInt("categoryId"));
                    cate.setCategoryName(rs.getString("categoryName"));

                    list.add(cate);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
    public EventCategories checkCateExist(String name) {
        EventCategories cate = null;

        DBUntil dbUntil = new DBUntil();
        String query = "select * from eventsCategories \n" +
                "where categoryName = ?";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1,name);
            ResultSet rs  = ps.executeQuery();
            if (rs.next()) {
               int id = rs.getInt("categoryId");
               String categoryName = rs.getString("categoryName");
               cate = new EventCategories(id, categoryName);




            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return cate;
    }


    public static void main(String[] args) {
        CategoryDao dao = new CategoryDao();
        EventCategories cate = dao.checkCateExist("choi bo11i");
        System.out.println(cate);

    }
}
