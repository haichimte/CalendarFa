package fpt.fsoft.java04.group1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eventParticipantsDao {
    DBUntil dbUntil = new DBUntil();
    public String DeleteEventParticipants(int eventId,int userId) {

        String result = "Fail";

        String query = "DELETE FROM [dbo].[eventParticipants]\n" +
                "WHERE eventId = ? and userId = ? ";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            int count = ps.executeUpdate();
            if (count > 0) {
                result = "Have " + count + " Deleted";
            } else {
                result = "Dont have any deleted record because Id is not exsit";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }
}
