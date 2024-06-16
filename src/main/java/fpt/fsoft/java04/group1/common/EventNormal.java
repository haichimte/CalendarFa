package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.CategoryDao;
import fpt.fsoft.java04.group1.dao.ImportantEventDao;
import fpt.fsoft.java04.group1.dao.NormalEventDao;
import fpt.fsoft.java04.group1.dao.eventParticipantsDao;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.EventParticipants;
import fpt.fsoft.java04.group1.model.ImportantEvent;
import fpt.fsoft.java04.group1.util.Validator;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EventNormal {
    Scanner sc = new Scanner(System.in);
    NormalEventDao normalEventDao = new NormalEventDao();
    ImportantEventDao importantEventDao = new ImportantEventDao();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Validator va = new Validator();
    CategoryDao categoryDao = new CategoryDao();
    eventParticipantsDao participantsDao = new eventParticipantsDao();
    //Add
    public void AddEvent( int userId) {

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
        System.out.println("Select type category you want ");
        int idCategory = selectCategoryId();

        Event event = new Event(title, description, Start, End, location,new EventCategories(idCategory));
        Event event2 = normalEventDao.checkEventExsist(title, description, Start, End, location,idCategory);
        if (event2 != null) {
            System.out.println("Event already exists");
            return;
        }
        normalEventDao.AddEvent(event);

        int id = importantEventDao.getLastestEventId();
//
        String addParticipant = participantsDao.add(userId,id);
        System.out.println("Event added");
    }
    public void RemoveEvent(int userId) {
        System.out.println("=============== Remove Event ================");
        int id = va.ValidateId("Type Id Event You Want to Remove: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);
        Event eventid = normalEventDao.GetEventbyId(id);
        ImportantEvent importantEvent = importantEventDao.GetImportantEventbyId(id);

        if (eventid != null && importantEvent == null) {


            String rulust = participantsDao.DeleteEventParticipants(id,userId);
            normalEventDao.DeleteEvent(id);
            System.out.println("Event " + id + " removed");
        } else if (eventid != null && importantEvent != null) {
            System.out.println("Event is a important event cant not to remove ");

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
            System.out.println("Select type cate you want: ");
            int cate = selectCategoryId();
            String event = normalEventDao.UpdateEvent(id, title, description, Start, End, location,cate);
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
            promptForParticipants();
        }else{
            System.out.println(
                    "Search events:\n" +
                            "1. Search event between 2 days\n" +
                            "2. Search event by month\n" +
                            "3. Search event by year\n" +
                            "4.Exist\n");

            int choice = va.getInt("Search: ", "Wrong", "Wrong", "Wrong",1,4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);

            Timestamp startTimestamp = null;
            Timestamp endTimestamp = null;
            boolean validStartDate = false;
            boolean validEndDate = false;
            Date startDate = null;
            Date endDate = null;
            dateFormat.setLenient(false);

            try {
                switch (choice) {
                    case 1:
                        while (!validStartDate) {
                            try {
                                System.out.println("Enter Start Date");
                                String startDay = va.getString("Enter day: ", "Error", va.REGEX_DAY);
                                String startMonth = va.getString("Enter month: ", "Error", va.REGEX_MONTH);
                                String startYear = va.getString("Enter year: ", "Error", va.REGEX_YEAR);
                                String startDateStr = startDay + "/" + startMonth + "/" + startYear;

                                startDate = dateFormat.parse(startDateStr);
                                validStartDate = true;
                            } catch (Exception e) {
                                System.out.println("Invalid Start Date");
                            }
                        }

                        while (!validEndDate) {
                            try {
                                System.out.println("Enter End Date");
                                String endDay = va.getString("Enter day: ", "Error", va.REGEX_DAY);
                                String endMonth = va.getString("Enter month: ", "Error", va.REGEX_MONTH);
                                String endYear = va.getString("Enter year: ", "Error", va.REGEX_YEAR);
                                String endDateStr = endDay + "/" + endMonth + "/" + endYear;

                                endDate = dateFormat.parse(endDateStr);

                                if (endDate.before(startDate)) {
                                    System.out.println("End Date must be greater than Start Date");
                                } else {
                                    validEndDate = true;
                                }
                            } catch (Exception e) {
                                System.out.println("Invalid End Date");
                            }
                        }

                        startTimestamp = new Timestamp(startDate.getTime());
                        endTimestamp = new Timestamp(endDate.getTime() + (24 * 60 * 60 * 1000) - 1);

                        List<Event> events = normalEventDao.searchEventsByDate(startTimestamp, endTimestamp);
                        printEvents(events);
                        promptForParticipants();
                        break;
                    case 2:
                        int month = va.getInt("Enter month: ", "error", "error", "error",1,12);
                        int year = va.getInt("Enter year: ", "error", "error", "error",Integer.MIN_VALUE,Integer.MAX_VALUE);
                        List<Event> eventsByMonth = normalEventDao.searchByMonth(month, year);
                        printEvents(eventsByMonth);
                        promptForParticipants();
                        break;
                    case 3:
                        year = va.getInt("Enter year: ", "error", "error", "error",Integer.MIN_VALUE,Integer.MAX_VALUE);
                        List<Event> eventsByYear = normalEventDao.searchByYear(year);
                        printEvents(eventsByYear);
                        promptForParticipants();
                        break;
                }

            } catch (Exception e) {
                System.out.println("An error occurred while searching for events.");
            }


        }

    }
    private void promptForParticipants() {
        String choice = va.getString("Do you want to see the list of participants for any event (y/n): ", "Must input y or n", va.REGEX_CONFIRMATION);
        if (choice.equals("y")) {
            int eventId = va.getInt("Enter the event ID: ", "Error", "Error", "Error", Integer.MIN_VALUE, Integer.MAX_VALUE);

            boolean eventExists = false;
            List<Event> events = normalEventDao.searchAllEvents();
            for (Event event : events) {
                if (event.getEventId() == eventId) {
                    eventExists = true;
                    break;
                }
            }

            if (eventExists) {
                System.out.println("=====DisplayParticipants======");
                List<EventParticipants> participants = participantsDao.searchByEventId(eventId);
                printParticipants(participants);
            } else {
                System.out.println("Event with ID " + eventId + " not found.");
            }
        }
    }
    private void printParticipants(List<EventParticipants> participants) {
        if (participants.isEmpty()) {
            System.out.println("No participants found for this event.");
            return;
        }

        String leftAlignFormat = "| %-15s | %-30s | %-30s |%n";

        System.out.format("+-----------------+--------------------------------+--------------------------------+%n");
        System.out.format("| Participant ID  | Username                       | Email                          |%n");
        System.out.format("+-----------------+--------------------------------+--------------------------------+%n");

        for (EventParticipants participant : participants) {
            System.out.format(leftAlignFormat, participant.getParricipantId(),
                    truncate(participant.getUser().getUserName(), 30),
                    truncate(participant.getUser().getEmail(), 30));
        }

        System.out.format("+-----------------+--------------------------------+--------------------------------+%n");
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
    protected int selectCategoryId() {
        List<EventCategories> cate = categoryDao.listCategories();

        // In ra danh sách các category
        System.out.println("Existing Categories:");
        for (int i = 1; i < cate.size(); i++) {
            EventCategories ec = cate.get(i);
            System.out.println((i + 1) + ". Category ID: " + ec.getCategoryId() + " | Category Name: " + ec.getCategoryName());
        }

        int choice = -1;
        boolean validChoice = false;

        do {
            System.out.println("\nOptions:");
            System.out.println("1. Select an existing category");
            System.out.println("2. Create a new category");
            System.out.println("3. Update an existing category");
            int option = va.ValidateId("Please choose an option: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);

            switch (option) {
                case 1:
                    // In ra danh sách các category từ vị trí thứ 1
                    for (int i = 1; i < cate.size(); i++) {
                        EventCategories ec = cate.get(i);
                        System.out.println((i + 1) + ". Category ID: " + ec.getCategoryId() + " | Category Name: " + ec.getCategoryName());
                    }
                    // Yêu cầu người dùng nhập ID category để chọn
                    int indexChoice = va.ValidateId("Type the number of the Category You Want to select: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);

                    // Kiểm tra xem lựa chọn có hợp lệ hay không (chỉ từ vị trí thứ 2 trở đi)
                    if (indexChoice > 1 && indexChoice <= cate.size()) {
                        choice = cate.get(indexChoice - 1).getCategoryId();
                        validChoice = true;
                    } else {
                        System.out.println("Invalid category number. Please try again.");
                    }
                    break;

                case 2:
                    // Gọi hàm tạo mới category
                    createNewCategory();
                    // Cập nhật lại danh sách các category sau khi tạo mới
                    cate = categoryDao.listCategories();
                    break;

                case 3:
                    // In ra danh sách các category từ vị trí thứ 1
                    for (int i = 1; i < cate.size(); i++) {
                        EventCategories ec = cate.get(i);
                        System.out.println((i + 1) + ". Category ID: " + ec.getCategoryId() + " | Category Name: " + ec.getCategoryName());
                    }
                    // Yêu cầu người dùng nhập ID category để cập nhật
                    indexChoice = va.ValidateId("Type the number of the Category You Want to update: ", "Input must be Integer", "Input must be > 0", va.REGEX_PHONE);

                    // Kiểm tra xem lựa chọn có hợp lệ hay không (chỉ từ vị trí thứ 2 trở đi)
                    if (indexChoice > 1 && indexChoice <= cate.size()) {
                        choice = cate.get(indexChoice - 1).getCategoryId();
                        validChoice = true;
                        updateCategory(choice);
                        // Cập nhật lại danh sách các category sau khi cập nhật
                        cate = categoryDao.listCategories();
                    } else {
                        System.out.println("Invalid category number. Please try again.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        } while (!validChoice);

        return choice;
    }

    private void createNewCategory() {
        // Logic để tạo category mới
        String name = sc.nextLine();
        EventCategories newCategory = new EventCategories();
        newCategory.setCategoryName(name);
        categoryDao.addNewCategory(newCategory);
        System.out.println("Category created successfully.");
    }

    private void updateCategory(int categoryId) {
        // Logic để cập nhật category
        String newName = sc.nextLine();
        categoryDao.updateCategory(categoryId,newName);
        System.out.println("Category updated successfully.");

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



    //hetphan2
}