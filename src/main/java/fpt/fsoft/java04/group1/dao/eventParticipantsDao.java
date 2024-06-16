package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.EventParticipants;

import java.sql.*;

public class eventParticipantsDao {
    DBUntil dbUntil = new DBUntil();

    public String DeleteEventParticipants(int eventId, int userId) {

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

    public EventParticipants checkEventExist(int userId, int eventId) {
        EventParticipants event = null;
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT * FROM eventParticipants WHERE eventId = ? and userId = ? ";

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                Timestamp start = rs.getTimestamp("startTime");
                Timestamp end = rs.getTimestamp("endTime");
                String location = rs.getString("location");
                int id = rs.getInt("participantId");
                int userId1 = rs.getInt("userId");
                int eventId2 = rs.getInt("eventId");
                event = new EventParticipants(id, eventId2, userId1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    public String add(int userId, int eventId) {
        String result = "Fail";

        String query = "INSERT INTO [dbo].[eventParticipants]\n" +
                "           ([eventId]\n" +
                "           ,[userId])\n" +
                "     VALUES\n" +
                "           (?" +
                "           ,?)";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
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

    public String deleteGeneralEvent(int eventId) {

        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "DELETE FROM [dbo].[eventParticipants]\n" +
                "WHERE eventId = ?";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, eventId);
            int count = ps.executeUpdate();
            if (count > 0) {
                result = "Have " + count + " Deleted";
            } else {
                result = "Dont have any deleted record";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }
}
