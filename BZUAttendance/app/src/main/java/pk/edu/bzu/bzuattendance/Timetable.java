package pk.edu.bzu.bzuattendance;

public class Timetable {
    private String Room;
    private String Teacher;
    private String SubjectCode;
    private String Subject;
    private String TimeSlot;
    private String Session;

    public Timetable(String room, String teacher, String subjectCode, String subject, String timeSlot, String session) {
        Room = room;
        Teacher = teacher;
        SubjectCode = subjectCode;
        Subject = subject;
        TimeSlot = timeSlot;
        Session = session;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }
}
