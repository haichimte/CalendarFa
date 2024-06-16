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
    public void RemoveGeneralEvent() {
        System.out.println("=============== Remove Event ================");
        int id = va.ValidateId("Type Id Event You Want to Remove: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);
        Event eventid = normalEventDao.GetEventbyId(id);
        ImportantEvent importantEvent = importantEventDao.GetImportantEventbyId(id);

        if (eventid != null && importantEvent == null) {


            String rulust = participantsDao.deleteGeneralEvent(eventid.getEventId());
            normalEventDao.DeleteEvent(id);
            System.out.println("Event " + id + " removed");
        } else if (eventid != null && importantEvent != null) {
            System.out.println("Event is a important event cant not to remove ");

        } else {
            System.out.println("Wrong Id");
        }
    }

    public static void main(String[] args) {
        GeneralEvent event = new GeneralEvent();
        event.addAllEvent();
    }
}
