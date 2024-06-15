package fpt.fsoft.java04.group1.model;

public class EventParticipants {
    private int parricipantId;
    private int eventId;
    private int userId;

    public EventParticipants(int parricipantId, int eventId, int userId) {
        this.parricipantId = parricipantId;
        this.eventId = eventId;
        this.userId = userId;
    }
    public EventParticipants(int eventId, int userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public EventParticipants() {
    }

    public int getParricipantId() {
        return parricipantId;
    }

    public void setParricipantId(int parricipantId) {
        this.parricipantId = parricipantId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "eventParticipants{" +
                "eventId=" + eventId +
                ", parricipantId=" + parricipantId +
                ", userId=" + userId +
                '}';
    }
}
