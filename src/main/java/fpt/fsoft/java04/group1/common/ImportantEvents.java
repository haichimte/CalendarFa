package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.ImportantEventDao;
import fpt.fsoft.java04.group1.dao.NormalEventDao;
import fpt.fsoft.java04.group1.model.DisplayImportantEvent;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.ImportantEvent;
import fpt.fsoft.java04.group1.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ImportantEvents {
    Scanner sc = new Scanner(System.in);
    NormalEventDao normalEventDao = new NormalEventDao();
    static ImportantEventDao importantEventDao=new ImportantEventDao();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Validator va = new Validator();
    EventNormal eventNormal = new EventNormal();
    //Add
    public void AddImportantEvents() {
        eventNormal.AddEvent();
        int id = importantEventDao.getLastestEventId();
        System.out.println("PriorityLevel : Click 3 to Important , 4 to ExtremlY Important , 5 to Related to Your Life\n");
        int priority = sc.nextInt();
        sc.nextLine();
        System.out.println("Type Note: \n");
        String notebook = sc.nextLine();
        ImportantEvent importantEvent = new ImportantEvent(id,priority,notebook);
        importantEventDao.AddEvent(importantEvent);

        System.out.println("Event added");
    }

    public static void printImportantEvents() {
        List<DisplayImportantEvent> events = importantEventDao.displayImportantEvents();

        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        String leftAlignFormat = "| %-10d | %-10d | %-30s | %-20s | %-30s |%n";

        System.out.format("+------------+------------+--------------------------------+----------------------+--------------------------------+%n");
        System.out.format("| Event ID   | Priority   | Title                          | Start Time           | Note                           |%n");
        System.out.format("+------------+------------+--------------------------------+----------------------+--------------------------------+%n");

        for (DisplayImportantEvent event : events) {
            System.out.format(leftAlignFormat,
                    event.getEventId(),
                    event.getPriority(),
                    truncate(event.getTitle(), 30),
                    event.getStartDate().toString(),
                    truncate(event.getNote(), 30));
        }

        System.out.format("+------------+------------+--------------------------------+----------------------+--------------------------------+%n");
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

    public void updateImportantEvents(){

        int id = va.getInt("Type Id you want to update:","Enter Id > 0","Enter id < 10000","ENTER ID",1,10000);
        ImportantEvent event = importantEventDao.GetImportantEventbyId(id);
        if(event!=null){
            System.out.println("Type priority you want to change :  Click 3 to Important , 4 to ExtremlY Important , 5 to Related to Your Life \n");
            int priority = va.getInt("Enter","Please enter number>= 3","please enter number <=5 ","Please enter number ",3,5);
            System.out.println("Type you want to update note :\n");
            String note = sc.nextLine();

            importantEventDao.UpdateImportantEvent(id,priority,note);
            System.out.println("Event updated");
        }
        else {
            System.out.println("Event not found");
        }


    }
    public void deleteEventId(){



        System.out.println("Event deleted");
        System.out.println("=============== Remove Event ================");
        int id = va.ValidateId("Type Id Event You Want to Remove: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);
        ImportantEvent event = importantEventDao.GetImportantEventbyId(id);
        if (event != null) {
            normalEventDao.DeleteEvent(id);
            importantEventDao.deleteImportantEvent(id);
            System.out.println("Event " + id + " removed");
        } else {
            System.out.println("Wrong Id");
        }
    }



}
