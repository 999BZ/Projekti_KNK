package Models;

public class Grade {
    private int id;
    private int studentId;
    private int subjectId;
    private int grade;

    public Grade(int id, int studentId, int subjectId, int grade) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.grade = grade;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
