package Models;

public class Classe {
    private int id;
    private int teacherId;
    private int subjectId;
    private int paralel;

    public Classe(int id, int teacherId, int subjectId, int paralel) {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.paralel = paralel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getParalel() {
        return paralel;
    }

    public void setParalel(int paralel) {
        this.paralel = paralel;
    }
}
