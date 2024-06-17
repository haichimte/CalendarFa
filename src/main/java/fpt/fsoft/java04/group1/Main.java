package fpt.fsoft.java04.group1;

import fpt.fsoft.java04.group1.common.EventNormal;
import fpt.fsoft.java04.group1.common.GeneralEvent;
import fpt.fsoft.java04.group1.common.ImportantEvents;
import fpt.fsoft.java04.group1.common.UserCommon;
import fpt.fsoft.java04.group1.model.User;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        EventNormal eventNormal = new EventNormal();
        ImportantEvents importantEvents = new ImportantEvents();

        UserCommon userCommon = new UserCommon();
        User userCurrent = userCommon.loginCommon();

        if (userCurrent.getRole().equals("admin")) AdminAccess(eventNormal, importantEvents, userCommon, userCurrent);
        else UserAccess(eventNormal, importantEvents, userCommon, userCurrent);

    }

    private static void AdminAccess(EventNormal eventNormal, ImportantEvents importantEvents, UserCommon userCommon, User userCurrent) {
        int choice = 0;
        while (true) {
            System.out.println(
                    "======================  Admin ========================\n" +
                            "1.Manager Calendar for Individual\n" +
                            "2.Manager Calendar for Class\n" +
                            "3.Exit");
            choice = getChoice();
            switch (choice) {
                case 1: {
                    UserAccess(eventNormal, importantEvents, userCommon, userCurrent);
                    break;
                }
                case 2: {
                    optionAdvanceForAdmin();
                    break;
                }
                case 3: {

                    return;
                }

            }
        }
    }

    private static void UserAccess(EventNormal eventNormal, ImportantEvents importantEvents, UserCommon userCommon, User userCurrent) {
        int choice = 0;

        while (true) {
            System.out.println(
                    "========= Manager Calendar for Individual ==========\n" +
                            "1.Manager Event Normal\n" +
                            "2.Manager Event Important\n" +
                            "3.Display Event\n" +
                            "4.Exit");
            choice = getChoice();
            switch (choice) {
                case 1: {
                    ManageNormal(eventNormal , userCurrent.getUserId());
                    break;
                }
                case 2: {
                    ManageImportantsEvent(importantEvents, userCurrent.getUserId());
                    break;
                }
                case 3: {
                    //phan2
                    displayEvent(eventNormal, userCurrent.getUserId());
                    break;
                }
                case 4: {
                    if (userCurrent.getRole().equals("admin"))
                        AdminAccess(eventNormal, importantEvents, userCommon, userCurrent);
                    System.exit(0);
                }
            }
        }
    }

    //phan2
    private static void displayEvent(EventNormal eventNormal , int userId ) {
        int userID = userId;
        eventNormal.searchEventByDate();
    }
    //hetphan2

    private static void ManageNormal(EventNormal eventNormal , int userId) {

        int choice = 0;


        while (true) {
            System.out.println(
                    "Quản lý lịch làm việc cá nhân\n" +
                            "1.Add Event Normal\n" +
                            "2.Update Event Normal\n" +
                            "3.Remove Event Normal\n" +
                            "4.Search Event Normal\n" +
                            "5.Exist\n");
            choice = getChoice();
            switch (choice) {
                case 1: {
                    eventNormal.AddEvent(userId);
                    break;
                }
                case 2: {
                    eventNormal.UpdateEvent();
                    break;
                }
                case 3: {
                    eventNormal.RemoveEvent(userId);
                    break;
                }
                case 4: {
                    eventNormal.SearchEvent();
                    break;
                }
                case 5: {
                    return;
                }

            }
        }
    }

    private static void ManageImportantsEvent(ImportantEvents event , int userId) {

        int choice = 0;
        GeneralEvent generalEvent = new GeneralEvent();
        generalEvent.displayEvent();

        while (true) {
            event.printImportantEvents(userId);
            System.out.println(
                    "Manager Calendar for Individual\n" +
                            "1.Add Event Important\n" +
                            "2.Update Event Importan\n" +
                            "3.Remove Event Importan\n" +
                            "4.Exist\n");
            choice = getChoice();

            switch (choice) {
                case 1: {
                    event.AddImportantEvents(userId);
                    break;
                }
                case 2: {
                    event.updateImportantEvents();
                    break;
                }
                case 3: {
                    event.deleteEventId(userId);

                    break;
                }
                case 4: {

                    return;
                }
            }
        }
    }

    public static int getChoice() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
            if (choice <= 0 || choice >= 6) {
                System.out.println("Please enter a number 1-4");
            }
        } catch (Exception e) {
            System.out.println("Pls enter a number 1-4");
        }
        return choice;
    }



    private static void optionAdvanceForAdmin() {
        int choice = 0;
        GeneralEvent generalEvent = new GeneralEvent();
        generalEvent.displayEvent();
        while (true) {
            System.out.println(
                    "Manager Calendar for Class \n" +
                            "1.Add Event \n" +
                            "2.Update event\n" +
                            "3.DeleteEvent\n" +
                            "4.Exit");
            choice = getChoice();
            switch (choice) {
                case 1: {
                    generalEvent.addAllEvent();
                    generalEvent.displayEvent();
                    break;
                }
                case 2: {
generalEvent.updateGeneralEvent();
                    generalEvent.displayEvent();
                    break;
                }
                case 3: {
                    //phan2
generalEvent.removeGeneralEvent();
                    generalEvent.displayEvent();
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }


}