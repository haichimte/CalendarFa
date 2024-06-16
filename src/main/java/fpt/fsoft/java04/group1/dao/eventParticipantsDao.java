package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.EventParticipants;
import fpt.fsoft.java04.group1.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class eventParticipantsDao {
    DBUntil dbUntil = new DBUntil();

    public List<EventParticipants> searchByEventId(int id) {
        List<EventParticipants> list = new ArrayList<>();
        DBUntil dbUntil = new DBUntil();
        String query = "select e.participantId,e.eventId, u.userId ,u.username, u.email \n" +
                "from eventParticipants e inner join [users] u\n" +
                "on e.userId = u.userId\n" +
                "where eventId = ?\n";

        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EventParticipants p = new EventParticipants();
                p.setParricipantId(rs.getInt("participantId"));
                p.setEventId(rs.getInt("eventId"));

                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                p.setUser(user);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

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
            e.printStackTrace();
        }

        return result;
    }

    public String deleteGeneralEvent(int eventId) {
        String result = "Fail";

        String deleteParticipantsQuery = "DELETE FROM [dbo].[eventParticipants] WHERE eventId = ?";
        String deleteEventQuery = "DELETE FROM [dbo].[events] WHERE eventId = ?";

        try (Connection conn = dbUntil.getCon()) {
            conn.setAutoCommit(false);  // Start transaction

            try (PreparedStatement psDeleteParticipants = conn.prepareStatement(deleteParticipantsQuery);
                 PreparedStatement psDeleteEvent = conn.prepareStatement(deleteEventQuery)) {

                // Delete participants
                psDeleteParticipants.setInt(1, eventId);
                psDeleteParticipants.executeUpdate();

                // Delete the event
                psDeleteEvent.setInt(1, eventId);
                int count = psDeleteEvent.executeUpdate();

                if (count > 0) {
                    result = "Event and related participants deleted successfully.";
                } else {
                    result = "Event not found.";
                }

                conn.commit();  // Commit transaction
            } catch (SQLException e) {
                conn.rollback();  // Rollback transaction on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
