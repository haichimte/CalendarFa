package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.DisplayImportantEvent;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.ImportantEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportantEventDao {
    public String AddEvent(ImportantEvent event) {
        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "INSERT INTO [dbo].[importantEvents] " +
                "([eventId], [priorityLevel], [note]) " +
                "VALUES (?, ?, ?)";

        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, event.getEventId());
            ps.setInt(2, event.getPriorrityLevel());
            ps.setString(3, event.getNote());

            int count = ps.executeUpdate();
            if (count > 0) {
                result = "Have " + count + " added to important events";
            } else {
                result = "No records added";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public String UpdateImportantEvent(int eventId,int priority,String note) {
        String result = "Fail";
        DBUntil dbUntil = new DBUntil();

        String query = "UPDATE [dbo].[importantEvents]\n" +
                "   SET [eventId] = ?\n" +
                "      ,[priorityLevel] = ?\n" +
                "      ,[note] =? \n" +
                " WHERE eventId = ?";
        try {
            Connection conn = dbUntil.getCon();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,eventId);
            ps.setInt(2,priority);
            ps.setString(3, note);
            ps.setInt(4,eventId);

            int count = ps.executeUpdate();
            if(count > 0){

                result = "Update sucessfully";
            }else {
                System.out.println("Updated 3");
                result = "Don't have update";
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return  result;
    }
    public String deleteImportantEvent(int eventId) {

        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "DELETE FROM [dbo].[importantEvents]\n" +
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
    public List<DisplayImportantEvent> displayImportantEvents() {
        DBUntil dbUntil = new DBUntil();
        List<DisplayImportantEvent> list = new ArrayList<>();
        String query = "select e.eventId,ime.priorityLevel,ime.note,e.title,e.description,e.startTime,e.endTime,e.location from [events] e\n" +
                " inner join [importantEvents] ime\n" +
                "on e.eventId = ime.eventId\n" +
                "order by priorityLevel desc";
        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DisplayImportantEvent event = new DisplayImportantEvent();
                event.setEventId(rs.getInt("eventId"));
                event.setPriority(rs.getInt("priorityLevel"));
                event.setNote(rs.getString("note"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getTimestamp("startTime"));
                event.setEndDate(rs.getTimestamp("endTime"));
                event.setLocation(rs.getString("location"));
                list.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return list;

    }

    public int getLastestEventId(){
        DBUntil dbUntil = new DBUntil();
        int lastestEventId = -1;
        String query = "SELECT TOP 1 eventId\n" +
                "FROM events\n" +
                "ORDER BY eventId DESC;" ;
        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               lastestEventId = rs.getInt("eventId");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return lastestEventId;

    }
    public ImportantEvent GetImportantEventbyId(int id) {
        ImportantEvent event = null;
        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "select * from importantEvents \n" +
                "where eventId = ?";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1,id);
            ResultSet rs  = ps.executeQuery();
            if (rs.next()) {
                int eventId = rs.getInt("eventId");
                int priorityLevel = rs.getInt("priorityLevel");
                String note = rs.getString("note");



                event = new ImportantEvent(eventId,priorityLevel,note);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return event;
    }
}
