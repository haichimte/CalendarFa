package fpt.fsoft.java04.group1.dao;

import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NormalEventDao {
    public String AddEvent(Event event) {
        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "INSERT INTO [dbo].[events]\n" +
                "           ([title]\n" +
                "           ,[description]\n" +
                "           ,[startTime]\n" +
                "           ,[endTime]\n" +
                "           ,[location]\n" +
                "           ,[categoryId])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?,?)";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setTimestamp(3, event.getStartDate());
            ps.setTimestamp(4, event.getEndDate());
            ps.setString(5, event.getLocation());
            ps.setInt(6, event.getCategory().getCategoryId());
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

    public String UpdateEvent(int id, String title, String description, Timestamp startTime, Timestamp endTime, String location, int categoryId) {
        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "\n" +
                "UPDATE [dbo].[events]\n" +
                "   SET [title] = ?\n" +
                "      ,[description] = ?\n" +
                "      ,[startTime] = ?\n" +
                "      ,[endTime] = ?\n" +
                "      ,[location] = ?\n" +
                "      ,[categoryId] = ?\n" +
                " WHERE eventId = ?";
        try {
            Connection conn = dbUntil.getCon();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(7, id);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setTimestamp(3, startTime);
            ps.setTimestamp(4, endTime);
            ps.setString(5, location);
            ps.setInt(6, categoryId);
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

    public String DeleteEvent(int id) {

        String result = "Fail";
        DBUntil dbUntil = new DBUntil();
        String query = "DELETE FROM [dbo].[events]\n" +
                "WHERE eventId = ?";
        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, id);
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

    public List<Event> SearchEvent(String txt) {
        List<Event> list = new ArrayList<>();

        DBUntil dbUntil = new DBUntil();
        String query = "select e.eventId,e.title,e.[description],e.startTime,e.endTime,e.[location],e.categoryId,ec.categoryName from events e\n" +
                "\t right join eventsCategories ec\n" +
                "\ton e.categoryId = ec.categoryId" +
                " WHERE e.title LIKE ?";

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setString(1, "%" + txt + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEventId(rs.getInt("eventId"));
                    event.setTitle(rs.getString("title"));
                    event.setDescription(rs.getString("description"));
                    event.setStartDate(rs.getTimestamp("startTime"));
                    event.setEndDate(rs.getTimestamp("endTime"));
                    event.setLocation(rs.getString("location"));
                    EventCategories category = new EventCategories(rs.getInt("categoryId"), rs.getString("categoryName"));
                    event.setCategory(category);
                    list.add(event);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Event GetEventbyId(int id) {
        Event event = null;
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT * FROM events WHERE eventId = ?";

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int eventId = rs.getInt("eventId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Timestamp start = rs.getTimestamp("startTime");
                Timestamp end = rs.getTimestamp("endTime");
                String location = rs.getString("location");

                // Assuming EventCategories needs to be populated, modify this as needed
                EventCategories eventCategories = new EventCategories();
                event = new Event(eventId,title, description, start, end, location, eventCategories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }
    public Event GetEventbyIdandCateId(int id) {
        Event event = null;
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT * FROM events WHERE eventId = ? and categoryId = 1";

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int eventId = rs.getInt("eventId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Timestamp start = rs.getTimestamp("startTime");
                Timestamp end = rs.getTimestamp("endTime");
                String location = rs.getString("location");

                // Assuming EventCategories needs to be populated, modify this as needed
                EventCategories eventCategories = new EventCategories();
                event = new Event(eventId,title, description, start, end, location, eventCategories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }


    //phan2
    public List<Event> searchEventsByDate(int userId ,Timestamp startDate, Timestamp endDate) {
        List<Event> events = new ArrayList<>();
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT e.eventId, e.title, e.description, e.startTime, e.endTime, e.location, ec.categoryId, ec.categoryName " +
                "FROM events e " +
                "INNER JOIN eventsCategories ec ON e.categoryId = ec.categoryId " +
                "INNER JOIN eventParticipants ep ON e.eventId = ep.eventId " +
                "WHERE " +
                "    ep.userId = ? AND " +
                "    ((e.startTime BETWEEN ? AND ?) OR " +
                "     (e.endTime BETWEEN ? AND ?) OR " +
                "     (e.startTime <= ? AND e.endTime >= ?)) " +
                "ORDER BY e.startTime;";

        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, userId);
            ps.setTimestamp(2, startDate);
            ps.setTimestamp(3, endDate);
            ps.setTimestamp(4, startDate);
            ps.setTimestamp(5, endDate);
            ps.setTimestamp(6, startDate);
            ps.setTimestamp(7, endDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("eventId"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getTimestamp("startTime"));
                event.setEndDate(rs.getTimestamp("endTime"));
                event.setLocation(rs.getString("location"));

                EventCategories category = new EventCategories(rs.getInt("categoryId"), rs.getString("categoryName"));
                event.setCategory(category);
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return events;
    }

    public List<Event> searchAllEvents(int userId) {
        List<Event> events = new ArrayList<>();
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT e.eventId, e.title, e.description, e.startTime, e.endTime, e.location, ec.categoryId, ec.categoryName " +
                "FROM events e " +
                "INNER JOIN eventsCategories ec ON e.categoryId = ec.categoryId " +
                "INNER JOIN eventParticipants ep ON e.eventId = ep.eventId " +
                "WHERE ep.userId = ? " +
                "ORDER BY e.startTime";
        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("eventId"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getTimestamp("startTime"));
                event.setEndDate(rs.getTimestamp("endTime"));
                event.setLocation(rs.getString("location"));
                EventCategories category = new EventCategories(rs.getInt("categoryId"), rs.getString("categoryName"));
                event.setCategory(category);
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return events;
    }
    public Event checkEventExsist( String title, String description, Timestamp startTime, Timestamp endTime, String location, int categoryId) {
        Event event = null;
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT * FROM events WHERE title = ?" +
                "and description LIKE ? " +
                "and location LIKE ? " +
                "and categoryId = ?";
                ;

        try (Connection conn = dbUntil.getCon(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setInt(4, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int eventId = rs.getInt("eventId");
                String title1 = rs.getString("title");
                String description1 = rs.getString("description");
                Timestamp start = rs.getTimestamp("startTime");
                Timestamp end = rs.getTimestamp("endTime");
                String location1 = rs.getString("location");

                // Assuming EventCategories needs to be populated, modify this as needed
                EventCategories eventCategories = new EventCategories();
                event = new Event(eventId,title1, description1, start, end, location1, eventCategories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    //hetphan2
    public static void main(String[] args) {
        NormalEventDao normalEventDao = new NormalEventDao();
//        List<Event> events = normalEventDao.searchAllEvents();
//        for (Event event : events) {
//            System.out.println(event);
//        EventCategories category = new EventCategories(1,"hai");

        // Create a sample Event object
//        Event event = new Event(
//                "Sample Event",
//                "This is a sample event description",
//                Timestamp.valueOf("2024-06-13 10:00:00"),
//                Timestamp.valueOf("2024-06-13 12:00:00"),
//                "Sample Location",
//                category
//        );
//        normalEventDao.AddEvent(event);
//
//       String s = normalEventDao.UpdateEvent(5,"Update Event",
//                "This is a sample event description",
//                Timestamp.valueOf("2024-06-13 10:00:00"),
//                Timestamp.valueOf("2024-06-13 12:00:00"),
//                "Sample Location",
//                category.getCategoryId());
//        System.out.println(s);
//        }


    }

    public List<Event> searchByMonth(int userId , int month, int year) {
        List<Event> events = new ArrayList<>();
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT e.eventId, e.title, e.description, e.startTime, e.endTime, e.location, ec.categoryId, ec.categoryName " +
                "FROM events e " +
                "INNER JOIN eventsCategories ec ON e.categoryId = ec.categoryId " +
                "INNER JOIN eventParticipants ep ON e.eventId = ep.eventId " +
                "WHERE ep.userId = ? AND " +
                "((MONTH(e.startTime) = ? AND YEAR(e.startTime) = ?) OR (MONTH(e.endTime) = ? AND YEAR(e.endTime) = ?)) " +
                "ORDER BY e.startTime";

        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, userId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setInt(4, month);
            ps.setInt(5, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("eventId"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getTimestamp("startTime"));
                event.setEndDate(rs.getTimestamp("endTime"));
                event.setLocation(rs.getString("location"));

                EventCategories category = new EventCategories(rs.getInt("categoryId"), rs.getString("categoryName"));
                event.setCategory(category);
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return events;
    }

    public List<Event> searchByYear(int userId, int year) {
        List<Event> events = new ArrayList<>();
        DBUntil dbUntil = new DBUntil();
        String query = "SELECT e.eventId, e.title, e.description, e.startTime, e.endTime, e.location, ec.categoryId, ec.categoryName " +
                "FROM events e " +
                "INNER JOIN eventsCategories ec ON e.categoryId = ec.categoryId " +
                "INNER JOIN eventParticipants ep ON e.eventId = ep.eventId " +
                "WHERE ep.userId = ? AND (YEAR(e.startTime) = ? OR YEAR(e.endTime) = ?) " +
                "ORDER BY e.startTime";

        try (Connection conn = dbUntil.getCon();
             PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, userId);
            ps.setInt(2, year);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("eventId"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setStartDate(rs.getTimestamp("startTime"));
                event.setEndDate(rs.getTimestamp("endTime"));
                event.setLocation(rs.getString("location"));

                EventCategories category = new EventCategories(rs.getInt("categoryId"), rs.getString("categoryName"));
                event.setCategory(category);
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return events;
    }
}


