package fpt.fsoft.java04.group1.model;

import java.sql.Timestamp;

public class DisplayImportantEvent extends Event{

    int priority;
    String note;


    public DisplayImportantEvent(String title, String description, Timestamp startDate, Timestamp endDate, String location,EventCategories categories, int priority, String note) {
        super(title, description, startDate, endDate, location, categories);
        this.priority = priority;
        this.note = note;
    }

    public DisplayImportantEvent( ) {

    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "DisplayImportantEvent{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", startDate=" + getStartDate() +
                ", endDate=" + getEndDate() +
                ", location='" + getLocation() + '\'' +
                ", priority=" + priority +
                ", note='" + note + '\'' +
                '}';
    }
}
