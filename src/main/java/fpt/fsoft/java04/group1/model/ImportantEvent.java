package fpt.fsoft.java04.group1.model;

public class ImportantEvent {
    private int importantId;
    private int eventId;
    private int priorrityLevel;
    private String note;

    public ImportantEvent() {
    }

    public ImportantEvent(int importantId, int eventId, int priorrityLevel, String note) {
        this.importantId = importantId;
        this.eventId = eventId;
        this.priorrityLevel = priorrityLevel;
        this.note = note;
    }

    @Override
    public String toString() {
        return "ImportantEventModel{" +
                "importantId=" + importantId +
                ", eventId=" + eventId +
                ", priorrityLevel=" + priorrityLevel +
                ", note='" + note + '\'' +
                '}';
    }
    public ImportantEvent( int eventId, int priorrityLevel, String note) {
        this.eventId = eventId;
        this.priorrityLevel = priorrityLevel;
        this.note = note;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getImportantId() {
        return importantId;
    }

    public void setImportantId(int importantId) {
        this.importantId = importantId;
    }

    public int getPriorrityLevel() {
        return priorrityLevel;
    }

    public void setPriorrityLevel(int priorrityLevel) {
        this.priorrityLevel = priorrityLevel;
    }

}
