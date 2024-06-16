package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.EventParticipants;
import fpt.fsoft.java04.group1.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
