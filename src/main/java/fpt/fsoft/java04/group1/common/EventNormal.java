package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.ImportantEventDao;
import fpt.fsoft.java04.group1.dao.NormalEventDao;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.util.Validator;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EventNormal {
    Scanner sc = new Scanner(System.in);
    NormalEventDao normalEventDao = new NormalEventDao();
   ImportantEventDao importantEventDao = new ImportantEventDao();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Validator va = new Validator();
    //Add
    public void AddEvent() {

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
        Event event = new Event(title, description, Start, End, location,new EventCategories());
        normalEventDao.AddEvent(event);
        System.out.println("Event added");
    }
    public void RemoveEvent() {
        System.out.println("=============== Remove Event ================");
        int id = va.ValidateId("Type Id Event You Want to Remove: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);
        Event eventid = normalEventDao.GetEventbyId(id);
        if (eventid != null) {
            normalEventDao.DeleteEvent(id);
            System.out.println("Event " + id + " removed");
        } else {
            System.out.println("Wrong Id");
        }
    }
    public void UpdateEvent() {
        System.out.println("=============== Update Event ================");

        int id = va.ValidateId("Type Id Event You Want to Update: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);

        Event eventid = normalEventDao.GetEventbyId(id);
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
            ////////////////////////////////////////
            System.out.println("Type Location: ");
            String location = sc.nextLine();
            String event = normalEventDao.UpdateEvent(id, title, description, Start, End, location,1);
        } else {
            System.out.println("============ Wrong Id =================");
        }
    }
            public void SearchEvent() {
                System.out.println("Search");
                System.out.println("Type Id Event You Want to Search: ");
                String title = sc.nextLine();
                List<Event> list=normalEventDao.SearchEvent(title);
                System.out.printf("%-5s %-20s %-50s %-30s %-30s%n", "ID", "Title", "Description", "Start Date", "End Date");
                for(Event e:list){
                    System.out.printf("%-5s %-20s %-50s %-30s %-30s%n", e.getEventId(), e.getTitle(), e.getDescription(), e.getStartDate(),e.getEndDate());
                }
            }


//phan2
    public void searchEventByDate() {
        String response = va.getString("Do you want to display all events(y/n):", "Must input y or n", va.REGEX_CONFIRMATION);
        System.out.println("=====DisplayEvent======");
        if(response.equals("y")) {
            List<Event> events = normalEventDao.searchAllEvents();
            printEvents(events);
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);

            Timestamp startTimestamp = null;
            Timestamp endTimestamp = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    System.out.println("Type Start Date(dd/MM/yyyy): ");
                    String startDateStr = va.getDate();
                    System.out.println("Type End Date(dd/MM/yyyy): ");
                    String endDateStr = va.getDate();

                    Date endDate = dateFormat.parse(endDateStr);
                    Date startDate = dateFormat.parse(startDateStr);

                    startTimestamp = new Timestamp(startDate.getTime());
                    endTimestamp = new Timestamp(endDate.getTime() + (24 * 60 * 60 * 1000) - 1);
                    validDate = true;
                }catch (Exception e){
                    System.out.println("Invalid Date");
                }
            }
            List<Event> events = normalEventDao.searchEventsByDate(startTimestamp,endTimestamp);
            printEvents(events);

        }

    }

    public static void printEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        String leftAlignFormat = "| %-10s | %-30s | %-50s | %-20s | %-20s | %-30s |%n";

        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+%n");
        System.out.format("| Event ID   | Title                          | Description                                        | Start Time           | End Time             | Location                       |%n");
        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+%n");

        for (Event event : events) {
            System.out.format(leftAlignFormat, event.getEventId(),
                    truncate(event.getTitle(), 30),
                    truncate(event.getDescription(), 50),
                    event.getStartDate().toString(),
                    event.getEndDate().toString(),
                    truncate(event.getLocation(), 30));
        }

        System.out.format("+------------+--------------------------------+----------------------------------------------------+----------------------+----------------------+--------------------------------+%n");
    }

    public static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    //hetphan2
}
