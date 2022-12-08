package pk.edu.bzu.bzuattendance;

public class AttendanceHistory {
    private String Date;
    private String TimeSlot;
    private String SubjectCode;
    private String Subject;
    private String Teacher;
    private String Status;

    public AttendanceHistory(String date, String timeSlot, String subjectCode, String subject, String teacher, String status) {
        Date = date;
        TimeSlot = timeSlot;
        SubjectCode = subjectCode;
        Subject = subject;
        Teacher = teacher;
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
