package Models;

import java.util.Date;

public class Enrollment {
    private int id;
    private int studentId;
    private int classId;
    private String enrollmentDate;

    public Enrollment(int id, int studentId, int classId, String enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
