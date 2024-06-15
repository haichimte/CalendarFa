package fpt.fsoft.java04.group1.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
    private int eventId;
    private String title;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private String location;
    private EventCategories category;

    public Event() {
    }

    public Event(String title, String description, Timestamp startDate, Timestamp endDate, String location, EventCategories category) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventCategories getCategory() {
        return category;
    }

    public void setCategory(EventCategories category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                ", category=" + category.getCategoryName() +
                '}';
    }
}
