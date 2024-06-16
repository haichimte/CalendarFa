package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.CategoryDao;
import fpt.fsoft.java04.group1.dao.ImportantEventDao;
import fpt.fsoft.java04.group1.dao.NormalEventDao;
import fpt.fsoft.java04.group1.dao.eventParticipantsDao;
import fpt.fsoft.java04.group1.model.Event;
import fpt.fsoft.java04.group1.model.EventCategories;
import fpt.fsoft.java04.group1.model.ImportantEvent;
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
        normalEventDao.AddEvent(event);

        int id = importantEventDao.getLastestEventId();
         String add = participantsDao.addPartipant(userId,id);
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
    private int selectCategoryId() {
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
