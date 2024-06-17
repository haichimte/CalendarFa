package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.*;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.ImportantEvent;
import fpt.fsoft.java04.group1.model.User;
import fpt.fsoft.java04.group1.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class GeneralEvent {
    Scanner sc = new Scanner(System.in);
    NormalEventDao normalEventDao = new NormalEventDao();
    ImportantEventDao importantEventDao = new ImportantEventDao();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Validator va = new Validator();
    CategoryDao categoryDao = new CategoryDao();
    eventParticipantsDao participantsDao = new eventParticipantsDao();
    UserDao userDao = new UserDao();
    List<User> listOfUsers= userDao.listUser();
    EventNormal eventNormal = new EventNormal();
 public void addAllEvent(){
     System.out.println("============== Add Event ==============");
     System.out.println("Type Title: \n");
     String title = sc.nextLine();
     System.out.println("Type Description: \n");
     String description = sc.nextLine();

     boolean validDate = false;
     java.sql.Timestamp Start = null;
     java.sql.Timestamp End = null;
     while (!validDate) {
         try {
             System.out.println("Type Start Date (pls type dd/MM/yyyy HH:mm:ss): \n");
             String startDate = sc.nextLine();
             System.out.println("Type End Date:(pls type dd/MM/yyyy HH:mm:ss) \n");
             String endDate = sc.nextLine();
             Start = java.sql.Timestamp.valueOf(startDate);
             End = java.sql.Timestamp.valueOf(endDate);
             validDate = true;
         } catch (Exception e) {
             System.out.println("Invalid Date");
         }
     }
     System.out.println("Type Location: ");
     String location = sc.nextLine();


     Event event = new Event(title, description, Start, End, location,new EventCategories(1));
     normalEventDao.AddEvent(event);

     int id = importantEventDao.getLastestEventId();
     for (User user : listOfUsers) {

         String ass = participantsDao.add(user.getUserId(),id);
         System.out.println(ass);
     }


 }
    public void removeGeneralEvent() {
        System.out.println("=============== Remove Event ================");
        int id = va.ValidateId("Type Id Event You Want to Remove: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);
        Event event = normalEventDao.GetEventbyId(id);
        ImportantEvent importantEvent = importantEventDao.GetImportantEventbyId(id);

        if (event != null && importantEvent == null) {
            String result = participantsDao.deleteGeneralEvent(event.getEventId());
            System.out.println(result);
        } else if (event != null && importantEvent != null) {
            System.out.println("Event is an important event and cannot be removed.");
        } else {
            System.out.println("Wrong Id.");
        }
    }
    public void updateGeneralEvent() {

            System.out.println("=============== Update Event ================");

            int id = va.ValidateId("Type Id Event You Want to Update: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);

            Event eventid = normalEventDao.GetEventbyIdandCateId(id);
            if (eventid != null) {
                System.out.println("Type Title: \n");
                String title = sc.nextLine();
                System.out.println("Type Description: \n");
                String description = sc.nextLine();
                //validate Date
                boolean validDate = false;
                java.sql.Timestamp Start = null;
                java.sql.Timestamp End = null;
                while (!validDate) {
                    try {
                        System.out.println("Type Start Date (pls type dd/MM/yyyy HH:mm:ss): \n");
                        String startDate = sc.nextLine();
                        System.out.println("Type End Date:(pls type dd/MM/yyyy HH:mm:ss) \n");
                        String endDate = sc.nextLine();
                        Start = java.sql.Timestamp.valueOf(startDate);
                        End = java.sql.Timestamp.valueOf(endDate);
                        validDate = true;
                    } catch (Exception e) {
                        System.out.println("Invalid Date pls type dd/MM/yyyy HH:mm:ss");
                    }
                }
                System.out.println("Type Location: ");
                String location = sc.nextLine();

                String event = normalEventDao.UpdateEvent(id, title, description, Start, End, location,1);
            } else {
                System.out.println("============ Wrong Id =================");
            }
        }



    public static void main(String[] args) {
        GeneralEvent event = new GeneralEvent();
        event.updateGeneralEvent();
//        event.addAllEvent();
    }

    public void displayEvent() {
        System.out.println("=====DisplayEvent======");
        List<Event> events = normalEventDao.listEventByCate();
        printEvents(events);
    }
    public void displayEventAdmin(){
        System.out.println("=====DisplayEvent======");
        List<Event> events = normalEventDao.listEventByCate();
        printEvents(events);
    }

    public static void printEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        String leftAlignFormat = "| %-10s | %-30s | %-50s | %-20s | %-20s | %-30s | %-20s |%n";

        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+----------------------+%n");
        System.out.format("| Event ID   | Title                          | Description                                        | Start Time           | End Time             | Location                       | Category Name        |%n");
        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+----------------------+%n");

        for (Event event : events) {
            System.out.format(leftAlignFormat, event.getEventId(),
                    truncate(event.getTitle(), 30),
                    truncate(event.getDescription(), 50),
                    event.getStartDate().toString(),
                    event.getEndDate().toString(),
                    truncate(event.getLocation(), 30),
                    truncate(event.getCategory().getCategoryName(), 20));
        }

        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+----------------------+%n");
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) {
            return "";  // Hoặc một giá trị mặc định khác
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
